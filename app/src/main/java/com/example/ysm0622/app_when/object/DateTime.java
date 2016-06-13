package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DateTime implements Serializable {

    // TAG
    private static final String TAG = DateTime.class.getName();

    // Variables
    private int GroupId;
    private int MeetId;
    private int UserId;
    private Group Group;
    private Meet Meet;
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

    public com.example.ysm0622.app_when.object.Group getGroup() {
        return Group;
    }

    public void setGroup(com.example.ysm0622.app_when.object.Group group) {
        Group = group;
    }

    public com.example.ysm0622.app_when.object.Meet getMeet() {
        return Meet;
    }

    public void setMeet(com.example.ysm0622.app_when.object.Meet meet) {
        Meet = meet;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public int getMeetId() {
        return MeetId;
    }

    public void setMeetId(int meetId) {
        MeetId = meetId;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }
}
