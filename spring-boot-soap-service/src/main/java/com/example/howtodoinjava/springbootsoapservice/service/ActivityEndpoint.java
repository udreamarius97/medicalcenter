package com.example.howtodoinjava.springbootsoapservice.service;

import com.example.howtodoinjava.springbootsoapservice.model.ActivityModel;
import com.example.howtodoinjava.springbootsoapservice.model.MedDayModel;
import com.example.howtodoinjava.springbootsoapservice.model.RecomandationModel;
import com.example.howtodoinjava.springbootsoapservice.repository.ActivityModelRepository;
import com.example.howtodoinjava.springbootsoapservice.repository.MedDayModelRepository;
import com.example.howtodoinjava.springbootsoapservice.repository.RecomandationModelRepository;
import com.howtodoinjava.xml.school.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.ArrayList;
import java.util.List;

@Endpoint
public class ActivityEndpoint {
    private static final String NAMESPACE_URI = "http://www.howtodoinjava.com/xml/school";

    @Autowired
    private ActivityModelRepository activityModelRepository;

    @Autowired
    private MedDayModelRepository medDayModelRepository;

    @Autowired
    private RecomandationModelRepository recomandationModelRepository;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActivityDetailsRequest")
    @ResponsePayload
    public ActivityDetailsResponse getActivity(@RequestPayload ActivityDetailsRequest request) {
        ActivityDetailsResponse acts=new ActivityDetailsResponse();
        for(ActivityModel am:activityModelRepository.findAllByUser(request.getName())){
            Activity response = new Activity();
            response.setId(am.getId());
            response.setBehavior(am.getBehavior());
            response.setStartDate(am.getStartDate());
            response.setEndDate(am.getEndDate());
            response.setActivity(am.getActivity());
            response.setUser(am.getUser());
            acts.getActivity().add(response);
        }
        return acts;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "ActivityDetailsRequest2")
    @ResponsePayload
    public ActivityDetailsResponse2 getActivity2(@RequestPayload ActivityDetailsRequest2 request){
        ActivityModel ac=new ActivityModel();
        ac.setId(request.getActivity().getId());
        ac.setActivity(request.getActivity().getActivity());
        ac.setStartDate(request.getActivity().getStartDate());
        ac.setEndDate(request.getActivity().getEndDate());
        ac.setBehavior(request.getActivity().getBehavior());
        ac.setUser(request.getActivity().getUser());
        activityModelRepository.save(ac);
        ActivityDetailsResponse2 acm2=new ActivityDetailsResponse2();
        acm2.setMesaj("success");
        return acm2;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "MedDayDetailsRequest")
    @ResponsePayload
    public MedDayDetailsResponse getMedDay(@RequestPayload MedDayDetailsRequest request){
        List<MedDayModel> medDayModel=medDayModelRepository.findAll();
        System.out.println(request.getName());
        MedDayDetailsResponse medDayDetailsResponse=new MedDayDetailsResponse();
        for(MedDayModel mdm:medDayModel) {
            System.out.println(medDayModel.size());
            System.out.println(mdm.getDay().toString().equals(request.getDay().toString()));
            System.out.println(mdm.getUser().toString().equals(request.getName().toString()));
            if (mdm.getDay().equals(request.getDay()) && mdm.getUser().equals(request.getName())) {

                MedDay m = new MedDay();
                m.setDay(mdm.getDay());
                m.setInterval(mdm.getInterval());
                m.setId(mdm.getId());
                m.setMedication(mdm.getMedication());
                m.setUser(mdm.getUser());
                m.setIsTaken(mdm.getIsTaken());
                medDayDetailsResponse.getMedDay().add(m);
            }
        }
        System.out.println(medDayDetailsResponse.getMedDay().size());
        return medDayDetailsResponse;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "RecomandationDetailsRequest")
    @ResponsePayload
    public void setRecomandation(@RequestPayload RecomandationDetailsRequest request){
       RecomandationModel recomandationModel=new RecomandationModel();
       recomandationModel.setCaregiver(request.getRecomandation().getCaregiver());
       recomandationModel.setPatient(request.getRecomandation().getPatient());
       recomandationModel.setMassage(request.getRecomandation().getMessage());
       recomandationModelRepository.save(recomandationModel);
    }
}
