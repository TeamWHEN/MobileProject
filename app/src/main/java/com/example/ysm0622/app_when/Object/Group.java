package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Group implements Serializable {

    private long Id;
    private String Title;
    private String Desc;
    private User Master;
    private Date CreateDate;
    private ArrayList<User> Member = new ArrayList<User>();

    public Group() {

    }

    public Group(String Title, String Desc, User Master) {
        this.Title = Title;
        this.Desc = Desc;
        this.Master = Master;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public void setDesc(String Desc) {
        this.Desc = Desc;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public User getMaster() {
        return Master;
    }

    public int getMemberNum() {
        return Member.size();
    }
}
