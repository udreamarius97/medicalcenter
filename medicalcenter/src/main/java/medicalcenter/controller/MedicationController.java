package medicalcenter.controller;

import medicalcenter.model.Medication;
import medicalcenter.model.PrescriptedMeds;
import medicalcenter.model.Prescription;
import medicalcenter.model.User;
import medicalcenter.payload.ApiResponse;
import medicalcenter.payload.MedicationResponse;
import medicalcenter.payload.MedsPresc;
import medicalcenter.payload.PrescriptionResponse;
import medicalcenter.repository.MedicationRepository;
import medicalcenter.repository.UserRepository;
import medicalcenter.security.CurrentUser;
import medicalcenter.security.UserPrincipal;
import medicalcenter.service.MedicationService;
import medicalcenter.service.PrescriptionService;
import medicalcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MedicationController {

    @Autowired
    private MedicationService medicationService;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private UserService userService;

    @Autowired
    private MedicationRepository medicationRepository;

    @DeleteMapping("/medications/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity deleteByName(@RequestBody String name){
        medicationService.deleteByName(name);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @PostMapping("/medications/addMedication")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addMedication(@RequestBody MedicationResponse medicationResponse){
        if(medicationRepository.existsByName(medicationResponse.getName())){
            Medication medication=medicationService.findByName(medicationResponse.getName());
            medication.setDosage(medicationResponse.getDosage());
            medication.setSideEfects(medicationResponse.getSideEfects());
            medicationService.save(medication);
        }
        else {
            Medication medication = new Medication(medicationResponse.getName(), medicationResponse.getSideEfects(), medicationResponse.getDosage());
            medicationService.save(medication);
        }
        return ResponseEntity.ok(new ApiResponse(true, "New medication added"));
    }

    @GetMapping("/medication/medications")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<List<Medication>> getAllMeds(){
        return ResponseEntity.ok(medicationService.getAll());
    }

    @PostMapping("/doctor/tratement")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addTratement(@CurrentUser UserPrincipal currentUser,@RequestBody PrescriptionResponse prescriptionResponse) throws ParseException {
        Prescription pres=new Prescription();
        System.out.println("salut");
        Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(prescriptionResponse.getStartDate());
        Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(prescriptionResponse.getEndDate());
        System.out.println("ceva");
        System.out.println(prescriptionResponse.toString());
        if(medicationRepository.existsByName(prescriptionResponse.getMed1())){
            Medication med1=medicationService.findByName(prescriptionResponse.getMed1());
            PrescriptedMeds prescriptedMeds1=new PrescriptedMeds(prescriptionResponse.getDozaj1(),med1);
            prescriptionService.savePrescriptedMed(prescriptedMeds1);
            pres.addMed(prescriptedMeds1);
        }

        if(medicationRepository.existsByName(prescriptionResponse.getMed2())){
            Medication med2=medicationService.findByName(prescriptionResponse.getMed2());
            PrescriptedMeds prescriptedMeds2=new PrescriptedMeds(prescriptionResponse.getDozaj2(),med2);
            prescriptionService.savePrescriptedMed(prescriptedMeds2);
            pres.addMed(prescriptedMeds2);
        }
        if(medicationRepository.existsByName(prescriptionResponse.getMed3())){
            Medication med3=medicationService.findByName(prescriptionResponse.getMed3());
            PrescriptedMeds prescriptedMeds3=new PrescriptedMeds(prescriptionResponse.getDozaj3(),med3);
            prescriptionService.savePrescriptedMed(prescriptedMeds3);
            pres.addMed(prescriptedMeds3);
        }
        if(medicationRepository.existsByName(prescriptionResponse.getMed4())){
            Medication med4=medicationService.findByName(prescriptionResponse.getMed4());
            PrescriptedMeds prescriptedMeds4=new PrescriptedMeds(prescriptionResponse.getDozaj4(),med4);
            prescriptionService.savePrescriptedMed(prescriptedMeds4);
            pres.addMed(prescriptedMeds4);
        }
        User u=userService.findByUsernameOrEmail(currentUser.getUsername());
        User pa=userService.findByUsernameOrEmail(prescriptionResponse.getPatientUsername());
        pres.setDoctor(u);
        pres.setStartDate(startDate);
        pres.setEndDate(endDate);
        prescriptionService.savePrescription(pres);
        pa.addPrescription(pres);
        userService.save(pa);
        return ResponseEntity.ok(new ApiResponse(true,"Tratememt added succesfully"));
    }



}
