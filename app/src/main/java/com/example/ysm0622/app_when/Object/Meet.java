package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

public class Meet implements Serializable {

    // TAG
    private static final String TAG = Meet.class.getName();

    // Variables
    private int Id;
    private int GroupId;
    private int MasterId;
    private String Title;
    private String Desc;
    private String Location;
    private Date SelectDate;
    private DateTime CreateDate;
    private ArrayList<Calendar> SelectedDate;
    private ArrayList<DateTime> DateTime;

    private Group Group;
    private User Master;

    public Meet() {
        this.DateTime = new ArrayList<>();
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

    public DateTime getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(DateTime createDate) {
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

    public ArrayList<com.example.ysm0622.app_when.object.DateTime> getDateTime() {
        return DateTime;
    }

    public void setDateTime(ArrayList<com.example.ysm0622.app_when.object.DateTime> dateTime) {
        DateTime = dateTime;
    }

    public Meet addDateTime(DateTime D) {
        for (int i = 0; i < getDateTimeNum(); i++) {
            if (D.getUser().getId() == getDateTime().get(i).getUser().getId()) {
                DateTime.remove(i);
                DateTime.add(i, D);
                return this;
            }
        }
        DateTime.add(D);
        return this;
    }

    public DateTime getDateTime(int i) {
        return DateTime.get(i);
    }

    public int getDateTimeNum() {
        return DateTime.size();
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getGroupId() {
        return GroupId;
    }

    public void setGroupId(int groupId) {
        GroupId = groupId;
    }

    public int getMasterId() {
        return MasterId;
    }

    public void setMasterId(int masterId) {
        MasterId = masterId;
    }

    public Date getSelectDate() {
        return SelectDate;
    }

    public void setSelectDate(Date selectDate) {
        SelectDate = selectDate;
    }
}
