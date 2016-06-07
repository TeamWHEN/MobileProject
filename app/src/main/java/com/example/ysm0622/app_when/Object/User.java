package com.example.ysm0622.app_when.object;

import java.io.Serializable;

public class User implements Serializable {

    private static long AutoGenerateId = 0;
    private long Id;
    private String Name;
    private String Email;
    private String Password;

    public User(String Name, String Email, String Password) {
        this.Id = AutoGenerateId++;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
