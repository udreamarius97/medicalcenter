package com.medicalcenter.Medicalcenter.service;

import com.medicalcenter.Medicalcenter.model.PrescriptedMeds;
import com.medicalcenter.Medicalcenter.model.Prescription;
import com.medicalcenter.Medicalcenter.payload.PrescriptionResponse;
import com.medicalcenter.Medicalcenter.repository.PrescriptedMedsRepository;
import com.medicalcenter.Medicalcenter.repository.PrescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptedMedsRepository prescriptedMedsRepository;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public void savePrescriptedMed(PrescriptedMeds prescriptedMeds){
        prescriptedMedsRepository.save(prescriptedMeds);
    }

    public void savePrescription(Prescription prescription){
        prescriptionRepository.save(prescription);
    }
}
