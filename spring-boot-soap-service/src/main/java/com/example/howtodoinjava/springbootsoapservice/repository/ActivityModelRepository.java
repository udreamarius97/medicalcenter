package com.example.howtodoinjava.springbootsoapservice.repository;

import com.example.howtodoinjava.springbootsoapservice.model.ActivityModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ActivityModelRepository extends JpaRepository<ActivityModel,Integer> {


    List<ActivityModel> findAllByUser(String user);
}
