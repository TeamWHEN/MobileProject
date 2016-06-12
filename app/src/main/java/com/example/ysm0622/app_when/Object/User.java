package com.example.ysm0622.app_when.object;

import java.io.Serializable;

public class User implements Serializable {

    // TAG
    private static final String TAG = User.class.getName();

    // Variables
    private static long AutoGenerateId = 0;
    private long Id;
    private String Name;
    private String Email;
    private String Password;


    private boolean Image;
    private String ImageFilePath;

    public User(String Name, String Email, String Password) {
        this.Id = AutoGenerateId++;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        this.Image = false;
        this.ImageFilePath = "/data/data/com.example.ysm0622.app_when/files/" + this.Id + ".jpg";
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

    public boolean isImage() {
        return Image;
    }

    public void setImage(boolean image) {
        Image = image;
    }

    public String getImageFilePath() {
        return ImageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        ImageFilePath = imageFilePath;
    }
}
