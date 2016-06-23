package com.teamw.ysm0622.app_when.object;

import com.teamw.ysm0622.app_when.global.Gl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Meet implements Serializable {

    // TAG
    private static final String TAG = Meet.class.getName();

    // Variables
    private int Id;
    private int GroupId;
    private int MasterId;
    private String Title;
    private String Descr;
    private String Location;
    private long CreateDate;

    private Date Created;
    private Group Group;
    private User Master;

    public ArrayList<MeetDate> MeetDate;
    private ArrayList<Calendar> SelectedDate;

    private ArrayList<DateTime> DateTime;
    private ArrayList<Times> Times;

    public Meet() {
        this.Id = 0;
        this.GroupId = 0;
        this.MasterId = 0;
        this.Title = "";
        this.Descr = "";
        this.Location = "";
        this.CreateDate = 0;
        this.MeetDate = new ArrayList<>();
        this.SelectedDate = new ArrayList<>();
        this.DateTime = new ArrayList<>();
        this.Times = new ArrayList<>();
        this.Group = null;
        this.Master = null;
    }

    public Meet(int GroupId, int MasterId, String Title, String Descr, String Location) {
        if(Gl.getMeets().size()==0){
            this.Id = 1;
        }else {
            this.Id = Gl.getMeet(Gl.MEETS.size() - 1).getId() + 1;
        }
        this.GroupId = GroupId;
        this.MasterId = MasterId;
        this.Title = Title;
        this.Descr = Descr;
        this.Location = Location;
        this.CreateDate = 0;
        this.MeetDate = new ArrayList<>();
        this.SelectedDate = new ArrayList<>();
        this.DateTime = new ArrayList<>();
        this.Times = new ArrayList<>();
        this.Group = Gl.getGroupById(GroupId);
        this.Master = Gl.getUserById(MasterId);
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
        return Descr;
    }

    public void setDesc(String desc) {
        Descr = desc;
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

    public ArrayList<com.teamw.ysm0622.app_when.object.DateTime> getDateTime() {
        return DateTime;
    }

    public void setDateTime(ArrayList<com.teamw.ysm0622.app_when.object.DateTime> dateTime) {
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


    public long getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(long createDate) {
        CreateDate = createDate;
    }

    public Date getCreated() {
        return Created;
    }

    public void setCreated(Date created) {
        Created = created;
    }

    public ArrayList<com.teamw.ysm0622.app_when.object.MeetDate> getMeetDate() {
        return MeetDate;
    }

    public void setMeetDate(ArrayList<com.teamw.ysm0622.app_when.object.MeetDate> meetDate) {
        MeetDate = meetDate;
    }

    public ArrayList<Times> getTimes() {
        return Times;
    }

    public void setTimes(ArrayList<Times> times) {
        Times = times;
    }
}
