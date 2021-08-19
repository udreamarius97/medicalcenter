package com.medicalcenter.Medicalcenter.payload;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PrescriptionResponse {

    private String email;

    private String startDate;

    private String endDate;

    private String med1;
    private String dozaj1;

    private String med2;
    private String dozaj2;

    private String med3;
    private String dozaj3;

    private String med4;
    private String dozaj4;

    public PrescriptionResponse(){

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

    public String getMed1() {
        return med1;
    }

    public void setMed1(String med1) {
        this.med1 = med1;
    }

    public String getDozaj1() {
        return dozaj1;
    }

    public void setDozaj1(String dozaj1) {
        this.dozaj1 = dozaj1;
    }

    public String getMed2() {
        return med2;
    }

    public void setMed2(String med2) {
        this.med2 = med2;
    }

    public String getDozaj2() {
        return dozaj2;
    }

    public void setDozaj2(String dozaj2) {
        this.dozaj2 = dozaj2;
    }

    public String getMed3() {
        return med3;
    }

    public void setMed3(String med3) {
        this.med3 = med3;
    }

    public String getDozaj3() {
        return dozaj3;
    }

    public void setDozaj3(String dozaj3) {
        this.dozaj3 = dozaj3;
    }

    public String getMed4() {
        return med4;
    }

    public void setMed4(String med4) {
        this.med4 = med4;
    }

    public String getDozaj4() {
        return dozaj4;
    }

    public void setDozaj4(String dozaj4) {
        this.dozaj4 = dozaj4;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
