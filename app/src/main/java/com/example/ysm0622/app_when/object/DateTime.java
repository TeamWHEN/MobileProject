package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DateTime implements Serializable {

    // TAG
    private static final String TAG = DateTime.class.getName();

    // Variables
    private User User;
    private ArrayList<ArrayList<Calendar>> SelectTime;

    public DateTime() {

    }

    public com.example.ysm0622.app_when.object.User getUser() {
        return User;
    }

    public void setUser(com.example.ysm0622.app_when.object.User user) {
        User = user;
    }

    public ArrayList<ArrayList<Calendar>> getSelectTime() {
        return SelectTime;
    }

    public void setSelectTime(ArrayList<ArrayList<Calendar>> selectTime) {
        SelectTime = selectTime;
    }
}
