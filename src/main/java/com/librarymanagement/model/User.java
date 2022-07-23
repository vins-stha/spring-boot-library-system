package com.librarymanagement.model;

import java.util.ArrayList;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(uniqueConstraints = { // other constraints
        @UniqueConstraint(name = "uniqueEmail", columnNames = { "email" }),
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Name can't be empty")
    @Size(min = 3, message = "Name must be at least 3 characters long!")

    private String fname;

    @NotBlank(message = "Last Name can't be empty")
    private String lname;

    @NotBlank(message = "Email can't be empty")
    @Email(message = "Please enter a valid e-mail address")
    private String email;

    private String password;
    private String role;

    private int borrowedItemsCount; // default 0

    // 1 to many
    private ArrayList<BorrowedItem> borrowedItems;

    public User() {
    }

    public User(Integer id, String fname, String lname, String email, String password, String role) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.email = email;
        this.password = password;
        this.role = role;
        this.borrowedItems = new ArrayList<>();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
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

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", fname='" + fname + '\'' +
                ", lname='" + lname + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}