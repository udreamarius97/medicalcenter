package com.medicalcenter.Medicalcenter.payload;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

public class UserSummary2 {


    private String name;

    private String username;

    private String email;

    private String role;

    private String birthdate;

    private String gender;

    private String address;

    public UserSummary2() {

    }

    public UserSummary2(String name,String username,String email, String role, String birthdate, String gender, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.role = role;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
    }

    public UserSummary2( String name,String username,String email, String birthdate, String gender, String address) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
