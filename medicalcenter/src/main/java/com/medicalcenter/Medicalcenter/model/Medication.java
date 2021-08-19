package com.medicalcenter.Medicalcenter.model;

import org.hibernate.annotations.NaturalId;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.UUID;

@Entity
@Table(name="medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 40)
    @NaturalId(mutable = true)
    private String name;

    @NotBlank
    private String sideEfects;

    @NotBlank
    private String dosage;

    public Medication(){

    }

    public Medication(String name, String sideEfects,String dosage) {
        this.name = name;
        this.sideEfects = sideEfects;
        this.dosage = dosage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSideEfects() {
        return sideEfects;
    }

    public void setSideEfects(String sideEfects) {
        this.sideEfects = sideEfects;
    }

    public String getDosage() {
        return dosage;
    }

    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
}
