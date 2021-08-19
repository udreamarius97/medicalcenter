package com.medicalcenter.Medicalcenter.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name="anomality")
public class Anomality {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String startDate;

    private String endDate;

    private String activity;

    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "pacient_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Anomality(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
