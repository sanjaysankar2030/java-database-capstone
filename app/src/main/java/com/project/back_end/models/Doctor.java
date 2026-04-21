package com.project.back_end.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.List;

@Entity
@Table(name = "doctor")
public class Doctor {

    // 1. id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. name
    @NotNull(message = "Name should be specified")
    @Size(min = 3, max = 100)
    private String name;

    // 3. specialty
    @NotNull(message = "Specialty should be specified")
    @Size(min = 3, max = 50)
    private String specialty;

    // 4. email
    @NotNull(message = "Email is required")
    @Email
    private String email;

    // 5. password
    @NotNull
    @Size(min = 6)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // 6. phone
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 digits")
    private String phone;

    // 7. availableTimes
    @ElementCollection
    @CollectionTable(
        name = "doctor_available_times",
        joinColumns = @JoinColumn(name = "doctor_id")
    )
    @Column(name = "available_times")
    private List<String> available_times;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<String> getAvailableTimes() {
        return available_times;
    }

    public void setAvailableTimes(List<String> available_times) {
        this.available_times = available_times;
    }
}