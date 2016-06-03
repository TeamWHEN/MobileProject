package com.example.ysm0622.app_when;

/**
 * Created by ysm0622 on 2016-05-23.
 */
public class User {
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

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }
}
