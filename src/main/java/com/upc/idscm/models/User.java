package com.upc.idscm.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;

    public User() {
        this.name = "";
        this.surname = "";
        this.email = "";
        this.username = "";
        this.password = "";
        
        this.id = -1;
    }
    
    public User(String name, String surname, String email, 
                String username, String password) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username; 
        this.password = password;
        
        this.id = -1;
    }
    
    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getUsername() {
        return username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    /*public boolean isValid() {
        return this.name != null && !this.name.isEmpty()
                && this.surname != null && !this.surname.isEmpty()
                && this.username != null && !this.username.isEmpty()
                && this.email != null && !this.email.isEmpty()
                && this.password != null && !this.password.isEmpty();
    }*/
}
