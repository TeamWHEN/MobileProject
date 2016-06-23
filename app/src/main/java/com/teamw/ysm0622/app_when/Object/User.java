package com.teamw.ysm0622.app_when.object;

import com.teamw.ysm0622.app_when.global.Gl;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

public class User implements Serializable {

    // TAG
    private static final String TAG = User.class.getName();

    // Variables
    private int Id;
    private String Name;
    private String Email;
    private String Password;
    public String ImageFilePath = "";

    private long JoinDate;
    private Date Joined;

    public User() {
        Id = 0;
        Name = "";
        Email = "";
        Password = "";
        JoinDate = 0;
        Joined = null;
    }

    public User(String Name, String Email, String Password) {
        this.Id = Gl.getUser(Gl.USERS.size() - 1).getId() + 1;
        this.Name = Name;
        this.Email = Email;
        this.Password = Password;
        //this.ImageFilePath = "/data/data/com.example.ysm0622.app_when/files/" + this.Id + ".jpg";
        Calendar c = Calendar.getInstance();
        Date d = c.getTime();
        this.Joined = d;
        this.JoinDate = d.getTime();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
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

    public String getImageFilePath() {
        return ImageFilePath;
    }

    public long getJoinDate() {
        return JoinDate;
    }

    public void setJoinDate(long joinDate) {
        JoinDate = joinDate;
    }

    public Date getJoined() {
        return Joined;
    }

    public void setJoined(Date joined) {
        Joined = joined;
    }

    public void setImageFilePath(String imageFilePath) {
        ImageFilePath = imageFilePath;
    }
}
