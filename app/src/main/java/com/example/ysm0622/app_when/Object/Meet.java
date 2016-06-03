package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.Date;

public class Meet implements Serializable {

    private long Id;
    private Group Group;
    private User Master;
    private String Title;
    private String Desc;
    private Date CreateDate;
    private String Location;

    public Meet() {

    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Group getGroup() {
        return Group;
    }

    public void setGroup(Group group) {
        Group = group;
    }

    public User getMaster() {
        return Master;
    }

    public void setMaster(User master) {
        Master = master;
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

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }
}
