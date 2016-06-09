package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Meet implements Serializable {

    // TAG
    private static final String TAG = Meet.class.getName();

    // Variables
    private Group Group;
    private User Master;
    private String Title;
    private String Desc;
    private String Location;
    private Date CreateDate;
    private ArrayList<Calendar> SelectedDate;
    private DateTime DateTime;

    public Meet() {

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

    public ArrayList<Calendar> getSelectedDate() {
        return SelectedDate;
    }

    public void setSelectedDate(ArrayList<Calendar> selectedDate) {
        SelectedDate = selectedDate;
    }

    public int getDateNum() {
        return SelectedDate.size();
    }

    public com.example.ysm0622.app_when.object.DateTime getDateTime() {
        return DateTime;
    }

    public void setDateTime(com.example.ysm0622.app_when.object.DateTime dateTime) {
        DateTime = dateTime;
    }
}
