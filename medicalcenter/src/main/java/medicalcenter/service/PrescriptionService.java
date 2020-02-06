package medicalcenter.service;

import medicalcenter.model.PrescriptedMeds;
import medicalcenter.model.Prescription;
import medicalcenter.payload.PrescriptionResponse;
import medicalcenter.repository.PrescriptedMedsRepository;
import medicalcenter.repository.PrescriptionRepository;
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
