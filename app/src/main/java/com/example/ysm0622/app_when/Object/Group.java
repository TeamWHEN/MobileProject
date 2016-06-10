package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    // TAG
    private static final String TAG = Group.class.getName();

    // Variables
    private static long AutoGenerateId = 0;
    private long Id;
    private String Title;
    private String Desc;
    private User Master;
    private ArrayList<User> Member;

    public Group() {
        this.Id = AutoGenerateId++;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public User getMaster() {
        return Master;
    }

    public void setMaster(User master) {
        Master = master;
    }

    public ArrayList<User> getMember() {
        return Member;
    }

    public User getMember(int i) {
        return Member.get(i);
    }

    public void setMember(ArrayList<User> member) {
        Member = member;
    }

    public int getMemberNum() {
        return Member.size();
    }

    public void addMember(User user) {
        Member.add(user);
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }
}
