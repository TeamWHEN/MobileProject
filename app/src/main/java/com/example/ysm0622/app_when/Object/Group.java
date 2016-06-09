package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    // TAG
    private static final String TAG = Group.class.getName();

    // Variables
    private String Title;
    private String Desc;
    private User Master;
    private ArrayList<User> Member;

    public Group() {

    }

    public Group(String Title, String Desc, User Master) {
        this.Title = Title;
        this.Desc = Desc;
        this.Master = Master;
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
}
