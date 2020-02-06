package medicalcenter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotBlank
    @Size(max = 40)
    private String name;

    @NotBlank
    @NaturalId (mutable = true)
    @Size(max = 30)
    private String username;

    @NaturalId (mutable = true)
    @NotBlank
    @Size(max = 40)
    @Email
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(max = 100)
    private String password;

    private Date birthdate;
    @NotBlank
    private String address;

    @NotBlank
    private String gender;



    @NaturalId
    @Column(length = 60)
    private String role;

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="caregiver_patient",
            joinColumns = @JoinColumn(name = "caregiver_id"),
            inverseJoinColumns = @JoinColumn(name ="patient_id"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private List<User> patiens=new ArrayList<User>();

    @ManyToMany(cascade=CascadeType.ALL)
    @JoinTable(name="patient_prescription",
            joinColumns = @JoinColumn(name = "patient_id"),
            inverseJoinColumns = @JoinColumn(name ="prescription_id"))
    @OnDelete(action= OnDeleteAction.CASCADE)
    private List<Prescription> prescriptions= new ArrayList<Prescription>();


    public User(){

    }

    public User(String name, String username, String email, Date birthdate,String address, String gender) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.birthdate = birthdate;
        this.address = address;
        this.gender = gender;
    }

    public User(String name, String username, String email, String password, Date birthdate, String gender, String address, String role) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.role = role;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<User> getPatiens() {
        return patiens;
    }

    public void setPatiens(List<User> patiens) {
        this.patiens = patiens;
    }

    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

    public void addPatient(User u){
        this.patiens.add(u);
    }

    public void addPrescription(Prescription p){
        this.prescriptions.add(p);
    }
}
