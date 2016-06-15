package com.example.ysm0622.app_when.object;


import java.io.Serializable;

public class UserGroup implements Serializable {

    // TAG
    private static final String TAG = UserGroup.class.getName();

    // Variables
    private int UserId;
    private int GroupId;

    public UserGroup() {
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }
}
