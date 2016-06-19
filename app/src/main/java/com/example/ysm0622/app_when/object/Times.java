package com.example.ysm0622.app_when.object;

import java.io.Serializable;

public class Times implements Serializable {

    // TAG
    private static final String TAG = Times.class.getName();

    private int MeetId;
    private int UserId;
    private long Time;

    public Times() {
    }

    public Times(int meetId, int userId, long time) {
        MeetId = meetId;
        UserId = userId;
        Time = time;
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
