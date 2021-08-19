package com.medicalcenter.Medicalcenter.repository;

import com.medicalcenter.Medicalcenter.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityRepository extends JpaRepository<Activity,Integer> {
}
