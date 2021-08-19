package com.medicalcenter.Medicalcenter.controller;

import com.medicalcenter.Medicalcenter.model.*;
import com.medicalcenter.Medicalcenter.payload.*;
import com.medicalcenter.Medicalcenter.repository.AnomalityRepository;
import com.medicalcenter.Medicalcenter.repository.MedicationRepository;
import com.medicalcenter.Medicalcenter.repository.UserRepository;
import com.medicalcenter.Medicalcenter.security.CurrentUser;
import com.medicalcenter.Medicalcenter.security.UserPrincipal;
import com.medicalcenter.Medicalcenter.service.MedicationService;
import com.medicalcenter.Medicalcenter.service.PrescriptionService;
import com.medicalcenter.Medicalcenter.service.UserService;
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

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private MedicationRepository medicationRepository;

    @GetMapping("/user/me")
    public ResponseEntity<UserSummary> getCurrentUser(@CurrentUser UserPrincipal currentUser) {
        System.out.println("currentUser");
        System.out.println(currentUser.getUsername());
        UserSummary userSummary = new UserSummary(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),currentUser.getAuthorities().stream().findFirst().toString()
                .substring(9,currentUser.getAuthorities().stream().findFirst().toString().length()-1),
                currentUser.getBirthdate(),currentUser.getGender(),currentUser.getAddress());
        return ResponseEntity.ok(userSummary);
    }

    @PostMapping("/users/doctor/patients")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<UserSummary2>> getAllUsers(){
        System.out.println("ceva");
        List<UserSummary2> u=new ArrayList<>();
        for(User currentUser:userService.getAllUsers()){
            UserSummary2 us=new UserSummary2(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),
                    currentUser.getRole(),currentUser.getBirthdate().toString().substring(0,10),currentUser.getGender(),currentUser.getAddress());
            u.add(us);
        }
        return ResponseEntity.ok(u);
    }
    @GetMapping("/user/{username}")
    public ResponseEntity<UserSummary> getYserProfile(@PathVariable(value = "username") String username) {
        User currentUser=userService.findByUsernameOrEmail(username);
        UserSummary userSummary = new UserSummary(currentUser.getName(),currentUser.getUsername(), currentUser.getEmail(),
                currentUser.getBirthdate(),currentUser.getGender(),currentUser.getAddress());
        return ResponseEntity.ok(userSummary);
    }
    @GetMapping("/user/users/prescriptions")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<List<PrescriptionResponse2>> getPrescriptedMeds(@CurrentUser UserPrincipal currentUser){
        User u=userService.findByUsernameOrEmail(currentUser.getUsername());
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
        return ResponseEntity.ok(lp);
    }

    @PostMapping("/user/doctor")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity deleteByEmail(@RequestBody String email){
        System.out.println(email);
        userService.deleteByEmail(email);
        return ResponseEntity.ok(new ApiResponse(false, "User deleted"));
    }

    @GetMapping("user/users/{email}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<User> getUserDetailsByDoctor(@PathVariable(value = "email") String email){
        return ResponseEntity.ok(userService.findByUsernameOrEmail(email));
    }

    @PostMapping("user/users/{email}")
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
        System.out.println("ceva");
        System.out.println(id);
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

    @PostMapping("/users/userlist/adduser")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addUser(@Valid @RequestBody UserSummary2 usersummay) throws ParseException {
        System.out.println(usersummay.getBirthdate());
        Date date=new SimpleDateFormat("yyyy-MM-dd").parse(usersummay.getBirthdate());
        User user;
        if(!userRepository.existsByEmail(usersummay.getEmail())){
            user=new User();
            user.setPassword(passwordEncoder.encode(usersummay.getUsername()));
            user.setAddress(usersummay.getAddress());
            user.setBirthdate(date);
            user.setGender(usersummay.getGender());
            user.setName(usersummay.getName());
            user.setUsername(usersummay.getUsername());
            user.setEmail(usersummay.getEmail());
            user.setRole(usersummay.getRole());
        }
        else{
            user=userService.findByUsernameOrEmail(usersummay.getEmail());
            user.setAddress(usersummay.getAddress());
            user.setBirthdate(date);
            user.setGender(usersummay.getGender());
            user.setName(usersummay.getName());
            user.setUsername(usersummay.getUsername());
            user.setEmail(usersummay.getEmail());
            user.setRole(usersummay.getRole());
        }
        userService.save(user);
        return ResponseEntity.ok(new ApiResponse(true, "User added successfully"));
    }

}



