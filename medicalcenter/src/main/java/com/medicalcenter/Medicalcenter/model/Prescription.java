package com.medicalcenter.Medicalcenter.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@Table(name="prescription")
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "doctor_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User doctor;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "prescription_meds",
            joinColumns = @JoinColumn(name = "prescription_id"),
            inverseJoinColumns = @JoinColumn(name = "medication_id"))
    private List<PrescriptedMeds> medicationSet = new ArrayList<>();

    private Date startDate;

    private Date endDate;

    public Prescription(){

    }

    public Prescription(User doctor,Date startDate,Date endDate) {
        this.doctor = doctor;
        this.startDate=startDate;
        this.endDate=endDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public List<PrescriptedMeds> getMedicationSet() {
        return medicationSet;
    }

    public void setMedicationSet(List<PrescriptedMeds> medicationSet) {
        this.medicationSet = medicationSet;
    }

    public void addMed(PrescriptedMeds prescriptedMeds){
        this.medicationSet.add(prescriptedMeds);
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
