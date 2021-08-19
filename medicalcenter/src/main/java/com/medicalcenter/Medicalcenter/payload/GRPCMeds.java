package com.medicalcenter.Medicalcenter.payload;

public class GRPCMeds {

    private String name_med;

    private String  dozaj;

    private String tratment;

    public GRPCMeds() {

    }

    public GRPCMeds(String name_med, String dozaj, String tratment) {
        this.name_med = name_med;
        this.dozaj = dozaj;
        this.tratment = tratment;
    }

    public String getName_med() {
        return name_med;
    }

    public void setName_med(String name_med) {
        this.name_med = name_med;
    }

    public String getDozaj() {
        return dozaj;
    }

    public void setDozaj(String dozaj) {
        this.dozaj = dozaj;
    }

    public String getTratment() {
        return tratment;
    }

    public void setTratment(String tratment) {
        this.tratment = tratment;
    }
}
