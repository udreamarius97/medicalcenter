package medicalcenter.controller;

import medicalcenter.model.*;
import medicalcenter.payload.*;
import medicalcenter.repository.AnomalityRepository;
import medicalcenter.repository.UserRepository;
import medicalcenter.security.CurrentUser;
import medicalcenter.security.UserPrincipal;
import medicalcenter.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AnomalityRepository anomalityRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/user/me")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println(currentUser.getAuthorities().stream().findFirst().toString().substring(9,currentUser.getAuthorities().stream().findFirst().toString().length()-1));
        UserSummary userSummary = new UserSummary(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),currentUser.getAuthorities().stream().findFirst().toString()
                .substring(9,currentUser.getAuthorities().stream().findFirst().toString().length()-1),
                currentUser.getBirthdate(),currentUser.getGender(),currentUser.getAddress());
        return ResponseEntity.ok(userSummary);
    }

    @PostMapping("/users/doctor/patients")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<UserSummary>> getAllUsers(@RequestBody String role){
        System.out.println("ceva");
        List<UserSummary> u=new ArrayList<>();
        for(User currentUser:userService.getAllUsers()){
            UserSummary us=new UserSummary(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),
                    currentUser.getRole(),currentUser.getBirthdate(),currentUser.getGender(),currentUser.getAddress());
            if(us.getRole().equals(role))
            u.add(us);
        }
        return ResponseEntity.ok(u);
    }
    @GetMapping("/user/email={username}")
    public ResponseEntity<UserSummary> getYserProfile(@PathVariable(value = "username") String username) {
        User currentUser=userService.findByUsernameOrEmail(username);
        UserSummary userSummary = new UserSummary(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),
                currentUser.getBirthdate(),currentUser.getGender(),currentUser.getAddress());
        return ResponseEntity.ok(userSummary);
    }
    @GetMapping("/user/users/email={username}/prescriptions")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PrescriptionResponse2>> getPrescriptedMeds(@PathVariable(value = "username") String username){
        User u=userService.findByUsernameOrEmail(username);
        List<PrescriptionResponse2> lp=new ArrayList<>();
        for(Prescription pr:u.getPrescriptions()){
            PrescriptionResponse2 p=new PrescriptionResponse2();
            p.setPatientUsername(pr.getDoctor().getName());
            p.setStartDate(pr.getStartDate());
            p.setEndDate(pr.getEndDate());
            for(PrescriptedMeds pmd:pr.getMedicationSet()){
                MedsPresc m=new MedsPresc(pmd.getMed().getName(),pmd.getDailyIntervals());
                p.add(m);
            }
            lp.add(p);

        }
        System.out.println("prescriptii");
        return ResponseEntity.ok(lp);
    }

    @DeleteMapping("user/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity deleteByEmail(@RequestBody String email){
        userService.deleteByEmail(email);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @GetMapping("user/users/email={email}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<User> getUserDetailsByDoctor(@PathVariable(value = "email") String email){
        return ResponseEntity.ok(userService.findByUsernameOrEmail(email));
    }

    @PostMapping("user/users/email={email}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addPatientToCeregiver(@RequestBody String emailPatient,@PathVariable(value = "email") String email){
        User caregiver=userService.findByUsernameOrEmail(email);
        User patient=userService.findByUsernameOrEmail(emailPatient);
        caregiver.addPatient(patient);
        userService.save(caregiver);
        return ResponseEntity.ok(new ApiResponse(true, "Added patient to caregiver"));
    }

    @GetMapping("/users/patiens")
    @PreAuthorize("hasRole('CAREGIVER')")
    public ResponseEntity<List<UserSummary>> getAsigneePatiens(@CurrentUser UserPrincipal currentUser){
        System.out.println("cava");
        User u=userService.findByUsernameOrEmail(currentUser.getEmail());
        List<UserSummary> s=new ArrayList<>();
        System.out.println(u.getPatiens().size()+"cava");
        for(User currentUser2:u.getPatiens()){
            UserSummary us=new UserSummary(currentUser2.getName(),currentUser2.getUsername(), currentUser2.getEmail(),
                    currentUser2.getBirthdate(),currentUser2.getGender(),currentUser2.getAddress());
            s.add(us);
        }
        return ResponseEntity.ok(s);

    }

    @GetMapping("/user/messages")
    @PreAuthorize("hasRole('CAREGIVER')")
    public ResponseEntity<List<String>> getMessages(@CurrentUser UserPrincipal currentUser){
        List<String> messages=new ArrayList<String>();
        User u=userService.findById(currentUser.getId());
        System.out.println(u.getPatiens().size());
        List<Anomality> l=anomalityRepository.findAll();
        for(Anomality a:l){
            if(u.getPatiens().contains(a.getUser())){
                messages.add("Pacientul cu numele "+a.getUser().getName()+ " a facut activitatea "+a.getActivity().replace('\t',' ')
                +"in perioada "+a.getStartDate()+" : "+a.getEndDate());
            }
        }
        return ResponseEntity.ok(messages);
    }


    @PostMapping("/auth/medication")
    public ResponseEntity<List<GRPCMeds>> getMedsForPatient(@RequestBody String id){
        List<GRPCMeds> grpcMeds=new ArrayList<GRPCMeds>();
        User u=userService.findById(Integer.parseInt(id));
        for(Prescription p1:u.getPrescriptions()){
            if(p1.getEndDate().toInstant().isAfter(Instant.now())){
                for(PrescriptedMeds pm:p1.getMedicationSet()){
                    GRPCMeds gp=new GRPCMeds(pm.getMed().getName(),pm.getMed().getDosage(),pm.getDailyIntervals());
                    grpcMeds.add(gp);
                }
            }
        }
        return ResponseEntity.ok(grpcMeds);
    }

    @PostMapping("/signup/caregiver")
    public ResponseEntity<?> registerCaregiver(@Valid @RequestBody SignUpRequest signUpRequest) throws ParseException {
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(signUpRequest.getBirthdate());
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            User user=userService.findByUsernameOrEmail(signUpRequest.getEmail());
            user.setAddress(signUpRequest.getAddress());
            user.setName(signUpRequest.getName());
            user.setBirthdate(date1);
            user.setGender(signUpRequest.getGender());
            user.setUsername(signUpRequest.getUsername());
            userService.save(user);
        }
        else {
            if(userRepository.existsByEmail(signUpRequest.getEmail())) {
                User user=userService.findByUsernameOrEmail(signUpRequest.getEmail());
                user.setAddress(signUpRequest.getAddress());
                user.setName(signUpRequest.getName());
                user.setBirthdate(date1);
                user.setGender(signUpRequest.getGender());
                user.setEmail(signUpRequest.getEmail());
                userService.save(user);
            }
            else {
                User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                        date1, signUpRequest.getAddress(), signUpRequest.getGender());
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                user.setRole("ROLE_CAREGIVER");
                userService.save(user);
            }
        }
        return ResponseEntity.ok(new ApiResponse(true, "Caregiver registered successfully"));
    }

    @PostMapping("/signup/patient")
    public ResponseEntity<?> registerPatient(@Valid @RequestBody SignUpRequest signUpRequest) throws ParseException {
        Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(signUpRequest.getBirthdate());
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            User user=userService.findByUsernameOrEmail(signUpRequest.getEmail());
            user.setAddress(signUpRequest.getAddress());
            user.setName(signUpRequest.getName());
            user.setBirthdate(date1);
            user.setGender(signUpRequest.getGender());
            user.setUsername(signUpRequest.getUsername());
            userService.save(user);
        }
        else {
            if(userRepository.existsByEmail(signUpRequest.getEmail())) {
                User user=userService.findByUsernameOrEmail(signUpRequest.getEmail());
                user.setAddress(signUpRequest.getAddress());
                user.setName(signUpRequest.getName());
                user.setBirthdate(date1);
                user.setGender(signUpRequest.getGender());
                user.setEmail(signUpRequest.getEmail());
                userService.save(user);
            }
            else {
                User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                        date1, signUpRequest.getAddress(), signUpRequest.getGender());
                user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
                user.setRole("ROLE_PATIENT");
                userService.save(user);
            }
        }
        return ResponseEntity.ok(new ApiResponse(true, "Caregiver registered successfully"));
    }
}



