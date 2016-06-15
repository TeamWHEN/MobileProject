package com.example.ysm0622.app_when.object;

import java.io.Serializable;

public class Time implements Serializable {

    // TAG
    private static final String TAG = Time.class.getName();

    private int MeetId;
    private int UserId;
    private long Time;

    public Time() {
    }

    public int getMeetId() {
        return MeetId;
    }

    public void setMeetId(int meetId) {
        MeetId = meetId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public long getTime() {
        return Time;
    }

    public void setTime(long time) {
        Time = time;
    }
}
