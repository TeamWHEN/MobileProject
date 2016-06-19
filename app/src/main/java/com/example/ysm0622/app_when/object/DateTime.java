package com.example.ysm0622.app_when.object;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class DateTime implements Serializable {

    // TAG
    private static final String TAG = DateTime.class.getName();

    private User User;
    private ArrayList<ArrayList<Calendar>> SelectTime;
    private ArrayList<Calendar> temp;

    public DateTime() {
        SelectTime = new ArrayList<>();
    }

    public DateTime(User u, ArrayList<ArrayList<Calendar>> arrayLists) {
        User = u;
        SelectTime = arrayLists;
    }

    public com.example.ysm0622.app_when.object.User getUser() {
        return User;
    }

    public void setUser(com.example.ysm0622.app_when.object.User user) {
        User = user;
    }

    public ArrayList<ArrayList<Calendar>> getSelectTime() {
        return SelectTime;
    }

    public void setSelectTime(ArrayList<ArrayList<Calendar>> selectTime) {
        SelectTime = selectTime;
    }


    public ArrayList<Calendar> getTemp() {
        return temp;
    }

    public void setTemp(ArrayList<Calendar> temp) {
        this.temp = temp;
        ArrayList<ArrayList<Calendar>> result = new ArrayList<>();
        ArrayList<Calendar> cal = new ArrayList<>();
        for(int i=0;i<temp.size();i++){
            if(i==0 || !isEqual(temp.get(i-1),temp.get(i))){
                if(cal.size()>0) result.add(cal);
                cal = new ArrayList<>();
                cal.add(temp.get(i));
            }else{
                cal.add(temp.get(i));
            }
            if(i==temp.size()-1){
                result.add(cal);
            }
        }
        SelectTime = result;
    }

    private static boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }
}
