package com.teamw.ysm0622.app_when.object;

import com.teamw.ysm0622.app_when.global.Gl;

import java.io.Serializable;
import java.util.ArrayList;

public class Group implements Serializable {

    // TAG
    private static final String TAG = Group.class.getName();

    // Variables
    private int Id;
    private String Title;
    private String Descr;
    private int MasterId;

    public User Master;
    public ArrayList<User> Member;

    public Group() {
        Id = 0;
        Title = "";
        Descr = "";
        MasterId = 0;
        Master = null;
        Member = new ArrayList<>();
    }

    public Group(String Title, String Descr, int MasterId, User Master, ArrayList<User> Member) {
        if(Gl.GROUPS.size()==0){
            this.Id=1;
        }else {
            this.Id = Gl.getGroup(Gl.GROUPS.size() - 1).getId() + 1;
        }
        this.Title = Title;
        this.Descr = Descr;
        this.MasterId = MasterId;
        this.Master = Master;
        this.Member = Member;
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

    public void setDesc(String descr) {
        Descr = descr;
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

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getMasterId() {
        return MasterId;
    }

    public void setMasterId(int masterId) {
        MasterId = masterId;
    }
}
