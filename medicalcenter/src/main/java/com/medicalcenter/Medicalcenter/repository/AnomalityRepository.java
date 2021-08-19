package com.medicalcenter.Medicalcenter.repository;

import com.medicalcenter.Medicalcenter.model.Activity;
import com.medicalcenter.Medicalcenter.model.Anomality;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnomalityRepository extends JpaRepository<Anomality,Integer> {
}
