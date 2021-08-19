package com.medicalcenter.Medicalcenter.controller;

import com.medicalcenter.Medicalcenter.config.SOAPConnector;
import com.medicalcenter.Medicalcenter.payload.ActivitiesResponse;
import com.medicalcenter.Medicalcenter.payload.ApiResponse;
import com.medicalcenter.Medicalcenter.payload.MedDayResponse;
import com.medicalcenter.Medicalcenter.payload.RecomandationRequest;
import com.medicalcenter.Medicalcenter.schemas.school.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class SoapController {

    @Autowired
    private SOAPConnector soapConnector;

    @GetMapping("/doctor/activities/name={name}")
    public ResponseEntity<List<ActivitiesResponse>> getActivities(@PathVariable(value = "name") String name){
        String replace=name.replace("_"," ");
        List<ActivitiesResponse> list=new ArrayList<ActivitiesResponse>();
        ActivityDetailsRequest request = new ActivityDetailsRequest();
        request.setName(replace);
        ActivityDetailsResponse list2 = (ActivityDetailsResponse) soapConnector.callWebService("http://localhost:8080/service/student-  details", request);
       for(Activity a:list2.getActivity()) {
           ActivitiesResponse av=new ActivitiesResponse(a.getId(),a.getStartDate(),a.getEndDate(),
                   a.getActivity(),a.getBehavior());
           list.add(av);
       }
       return ResponseEntity.ok(list);
    }

    @PostMapping("/doctor/activities/name={name}")
    public ResponseEntity<String> setActivitate(@RequestBody ActivitiesResponse activitiesResponse,@PathVariable(value = "name") String name){
        String replace=name.replace("_"," ");
        ActivityDetailsRequest2 a=new ActivityDetailsRequest2();
        Activity ac=new Activity();
        ac.setId(activitiesResponse.getId());
        ac.setActivity(activitiesResponse.getActivity());
        ac.setStartDate(activitiesResponse.getStartDate());
        ac.setEndDate(activitiesResponse.getEndDate());
        ac.setBehavior(activitiesResponse.getBehavior());
        ac.setUser(replace);
        a.setActivity(ac);
        ActivityDetailsResponse2 list2 = (ActivityDetailsResponse2) soapConnector.callWebService("http://localhost:8080/service/student-details", a);
        return ResponseEntity.ok(list2.getMesaj());
    }

    @PostMapping("/doctor/medicationTaken/name={name}")
    public ResponseEntity<List<MedDayResponse>> getMedsjvso(@RequestBody String date,@PathVariable(value = "name") String name){
        System.out.println(date);
        List<MedDayResponse> lmd=new ArrayList<MedDayResponse>();
        MedDayDetailsRequest medDayDetailsRequest=new MedDayDetailsRequest();
        medDayDetailsRequest.setDay(date);
        medDayDetailsRequest.setName(name.replace("_"," "));
        MedDayDetailsResponse medDayDetailsResponse=(MedDayDetailsResponse) soapConnector.callWebService("http://localhost:8080/service/student-details", medDayDetailsRequest);
        for(MedDay m:medDayDetailsResponse.getMedDay()){
            MedDayResponse medDayResponse=new MedDayResponse();
            medDayResponse.setId(m.getId());
            medDayResponse.setDay(m.getDay());
            medDayResponse.setInterval(m.getInterval());
            medDayResponse.setMedication(m.getMedication());
            medDayResponse.setUser(m.getUser());
            medDayResponse.setIsTaken(m.getIsTaken());
            lmd.add(medDayResponse);
        }
        return ResponseEntity.ok(lmd);
    }

    @PostMapping("/doctor/recomandation")
    public ResponseEntity<?> addRecomandation(@RequestBody RecomandationRequest recomandationRequest){
        RecomandationDetailsRequest recomandationDetailsRequest=new RecomandationDetailsRequest();
        System.out.println(recomandationRequest.getCaregiver());
        Recomandation recomandation=new Recomandation();
        recomandation.setCaregiver(recomandationRequest.getCaregiver());
        recomandation.setPatient(recomandationRequest.getPatient());
        recomandation.setMessage(recomandationRequest.getMessage());
        //recomandationDetailsRequest.getRecomandation().setCaregiver(recomandationRequest.getCaregiver());
        //recomandationDetailsRequest.getRecomandation().setPatient(recomandationRequest.getPatient());
        //recomandationDetailsRequest.getRecomandation().setMessage(recomandationRequest.getMessage());
        recomandationDetailsRequest.setRecomandation(recomandation);
        soapConnector.callWebService("http://localhost:8080/service/student-details", recomandationDetailsRequest);
        return ResponseEntity.ok(new ApiResponse(true, "Recomandation added succesfully!"));
    }

}
