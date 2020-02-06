package com.example.howtodoinjava.springbootsoapservice.repository;

import com.example.howtodoinjava.springbootsoapservice.model.MedDayModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedDayModelRepository extends JpaRepository<MedDayModel,Integer> {
    List<MedDayModel> findAllByUser(String user);

    List<MedDayModel> findAll();
}
