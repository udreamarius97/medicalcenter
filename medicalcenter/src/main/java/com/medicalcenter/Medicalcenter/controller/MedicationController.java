package com.medicalcenter.Medicalcenter.controller;

import com.medicalcenter.Medicalcenter.model.Medication;
import com.medicalcenter.Medicalcenter.model.PrescriptedMeds;
import com.medicalcenter.Medicalcenter.model.Prescription;
import com.medicalcenter.Medicalcenter.model.User;
import com.medicalcenter.Medicalcenter.payload.ApiResponse;
import com.medicalcenter.Medicalcenter.payload.MedicationResponse;
import com.medicalcenter.Medicalcenter.payload.MedsPresc;
import com.medicalcenter.Medicalcenter.payload.PrescriptionResponse;
import com.medicalcenter.Medicalcenter.repository.MedicationRepository;
import com.medicalcenter.Medicalcenter.repository.UserRepository;
import com.medicalcenter.Medicalcenter.security.CurrentUser;
import com.medicalcenter.Medicalcenter.security.UserPrincipal;
import com.medicalcenter.Medicalcenter.service.MedicationService;
import com.medicalcenter.Medicalcenter.service.PrescriptionService;
import com.medicalcenter.Medicalcenter.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @PostMapping("/medications/delete")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> deleteByName(@RequestBody String name){
        medicationService.deleteByName(name);
        return ResponseEntity.ok(new ApiResponse(true, "Medication deleted"));
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
    public ResponseEntity<List<MedicationResponse>> getAllMeds(){
        List<MedicationResponse> list=new ArrayList<>();
        for(Medication m:medicationService.getAll()){
            list.add(new MedicationResponse(m.getName(),m.getSideEfects(),m.getDosage()));
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/doctor/tratement")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<?> addTratement(@CurrentUser UserPrincipal currentUser,@RequestBody PrescriptionResponse prescriptionResponse) throws ParseException {
        Prescription pres=new Prescription();
        System.out.println("salut");
        Date startDate=new SimpleDateFormat("dd-MM-yyyy").parse(prescriptionResponse.getStartDate());
        Date endDate=new SimpleDateFormat("dd-MM-yyyy").parse(prescriptionResponse.getEndDate());
        if(medicationRepository.existsByName(prescriptionResponse.getMed1())){
            System.out.println(prescriptionResponse.getDozaj1());
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
        User pa=userService.findByUsernameOrEmail(prescriptionResponse.getEmail());
        pres.setDoctor(u);
        pres.setStartDate(startDate);
        pres.setEndDate(endDate);
        prescriptionService.savePrescription(pres);
        pa.addPrescription(pres);
        userService.save(pa);
        return ResponseEntity.ok(new ApiResponse(true,"Tratememt added succesfully"));
    }



}
