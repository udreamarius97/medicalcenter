package com.example.howtodoinjava.springbootsoapservice.repository;

import com.example.howtodoinjava.springbootsoapservice.model.MedDayModel;
import com.example.howtodoinjava.springbootsoapservice.model.RecomandationModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecomandationModelRepository extends JpaRepository<RecomandationModel,Integer> {
}
