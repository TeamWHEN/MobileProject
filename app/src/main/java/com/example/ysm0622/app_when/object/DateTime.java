package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DateTime implements Serializable {

    // TAG
    private static final String TAG = DateTime.class.getName();

    // Variables
    private User User;
    private ArrayList<Calendar> StartTime;
    private ArrayList<Calendar> EndTime;

    public DateTime() {

    }

    public com.example.ysm0622.app_when.object.User getUser() {
        return User;
    }

    public void setUser(com.example.ysm0622.app_when.object.User user) {
        User = user;
    }

    public ArrayList<Calendar> getStartTime() {
        return StartTime;
    }

    public void setStartTime(ArrayList<Calendar> startTime) {
        StartTime = startTime;
    }

    public ArrayList<Calendar> getEndTime() {
        return EndTime;
    }

    public void setEndTime(ArrayList<Calendar> endTime) {
        EndTime = endTime;
    }
}
