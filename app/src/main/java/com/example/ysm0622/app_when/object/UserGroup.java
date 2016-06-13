package com.example.ysm0622.app_when.object;


public class UserGroup {

    // TAG
    private static final String TAG = UserGroup.class.getName();

    // Variables
    private int GroupId;
    private int UserId;

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
