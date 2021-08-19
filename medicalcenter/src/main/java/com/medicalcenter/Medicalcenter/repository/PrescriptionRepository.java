package com.medicalcenter.Medicalcenter.repository;

import com.medicalcenter.Medicalcenter.model.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {


}
