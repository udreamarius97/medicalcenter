package com.medicalcenter.Medicalcenter.repository;

import com.medicalcenter.Medicalcenter.model.PrescriptedMeds;
import com.medicalcenter.Medicalcenter.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptedMedsRepository extends JpaRepository<PrescriptedMeds, Integer> {

}
