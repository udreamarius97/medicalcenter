package medicalcenter.service;


import medicalcenter.model.Medication;
import medicalcenter.repository.MedicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicationService {

    @Autowired
    private MedicationRepository medicationRepository;

    public void deleteByName(String name){
        Medication med=medicationRepository.findByName(name);
        medicationRepository.delete(med);
    }

    public void save(Medication med){
        medicationRepository.save(med);
    }

    public List<Medication> getAll(){
        return medicationRepository.findAll();
    }

    public Medication findByName(String name){
        return medicationRepository.findByName(name);
    }
}
