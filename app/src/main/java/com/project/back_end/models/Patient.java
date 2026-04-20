package com.project.back_end.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "patient")
public class Patient {

    // 1. id
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 2. name
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    // 3. email
    @NotNull
    @Email
    private String email;

    // 4. password
    @NotNull
    @Size(min = 6)
    private String password;

    // 5. phone
    @NotNull
    @Pattern(regexp = "^[0-9]{10}$")
    private String phone;

    // 6. address
    @NotNull
    @Size(max = 255)
    private String address;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}