package com.example.ysm0622.app_when.object;


import java.io.Serializable;

public class MeetDate implements Serializable {

    // TAG
    private static final String TAG = MeetDate.class.getName();

    // Variables
    private int GroupId;
    private int MeetId;
    private long Date;

    public int getMeetId() {
        return MeetId;
    }

    public void setMeetId(int meetId) {
        MeetId = meetId;
    }

    public long getDate() {
        return Date;
    }

    public void setDate(long date) {
        Date = date;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }
}
