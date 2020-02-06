package medicalcenter.controller;


import medicalcenter.model.Activity;
import medicalcenter.model.Anomality;
import medicalcenter.model.Patient;
import medicalcenter.model.User;
import medicalcenter.repository.ActivityRepository;
import medicalcenter.repository.AnomalityRepository;
import medicalcenter.repository.UserRepository;
import medicalcenter.security.CurrentUser;
import medicalcenter.security.UserPrincipal;
import medicalcenter.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@CrossOrigin(origins="http://localhost:3000/#")
@RestController
@RequestMapping("/api")
public class RabbitMQConsumer {

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private AnomalityRepository anomalityRepository;

    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);

    @RabbitListener(queues = "javainuse.queue")
    public void recievedMessage(Patient patient) throws Exception{
        //logger.info("Recieved Message From RabbitMQ: " + employee);

        Activity a=new Activity();
        a.setStartDate(patient.getStartTime());
        a.setEndDate(patient.getEndTime());
        a.setActivity(patient.getActivity());
        a.setUser(userRepository.getOne(Integer.parseInt(patient.getIdPacient())));
        a.setBehavior("not specified");
        System.out.println(a.getActivity()+" "+a.getStartDate()+" "+a.getEndDate()+" "+a.getUser().getId());
        LocalDateTime startTime = LocalDateTime.parse(a.getStartDate(), formatter);
        LocalDateTime endTime = LocalDateTime.parse(a.getEndDate(), formatter);
        if(a.getActivity().equals("Sleeping\t\t")){
            System.out.println(Duration.between(endTime, startTime).abs().toHours());
            a.setBehavior("normal");
            if(Duration.between(endTime, startTime).abs().toHours()>6){
                a.setBehavior("not normal");
                Anomality am=new Anomality();
                am.setUser(a.getUser());
                am.setActivity(a.getActivity());
                am.setStartDate(a.getStartDate());
                am.setEndDate(a.getEndDate());
                System.out.println("Anomalie R1");
                anomalityRepository.save(am);

            }
        }
            if(a.getActivity().equals("Leaving\t\t")){
                a.setBehavior("normal");
             if(Duration.between(endTime, startTime).abs().toHours()>6){
                   a.setBehavior("not normal");
                   Anomality am=new Anomality();
                   am.setUser(a.getUser());
                   am.setActivity(a.getActivity());
                   am.setStartDate(a.getStartDate());
                   am.setEndDate(a.getEndDate());
                   System.out.println("Anomalie R2");
                   anomalityRepository.save(am);
              }
        }
        if(a.getActivity().equals("Toileting\t\t")){
            a.setBehavior("normal");
            System.out.println(a.getBehavior());
            if(Duration.between(endTime, startTime).abs().toHours()>0.5){
                a.setBehavior("not normal");
                Anomality am=new Anomality();
                am.setUser(a.getUser());
                am.setActivity(a.getActivity());
                am.setStartDate(a.getStartDate());
                am.setEndDate(a.getEndDate());
                System.out.println("Anomalie R3");
                anomalityRepository.save(am);
            }
        }

        activityRepository.save(a);
        //System.out.println(patient.getIdPacient()+" " + patient.getStartTime()+" "+ patient.getEndTime()+" "+ patient.getActivity());
    }

}