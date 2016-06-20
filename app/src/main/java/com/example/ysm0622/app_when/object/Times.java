package com.example.ysm0622.app_when.object;

import java.io.Serializable;

public class Times implements Serializable {

    // TAG
    private static final String TAG = Times.class.getName();

    private int GroupId;
    private int MeetId;
    private int UserId;
    private long Time;

    public Times() {
    }

    public Times(int groupId,int meetId, int userId, long time) {
        GroupId = groupId;
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

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }
}
