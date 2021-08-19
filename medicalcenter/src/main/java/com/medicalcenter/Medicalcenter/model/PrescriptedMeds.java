package com.medicalcenter.Medicalcenter.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="prescriptedMeds")
public class PrescriptedMeds {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String dailyIntervals;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "medication_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Medication med;

    public PrescriptedMeds(){

    }

    public PrescriptedMeds(int id,String dailyIntervals, Medication med) {
        this.id=id;
        this.dailyIntervals = dailyIntervals;
        this.med = med;
    }

    public PrescriptedMeds(String dailyIntervals, Medication med) {
        this.dailyIntervals = dailyIntervals;
        this.med = med;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDailyIntervals() {
        return dailyIntervals;
    }

    public void setDailyIntervals(String dailyIntervals) {
        this.dailyIntervals = dailyIntervals;
    }

    public Medication getMed() {
        return med;
    }

    public void setMed(Medication med) {
        this.med = med;
    }
}
