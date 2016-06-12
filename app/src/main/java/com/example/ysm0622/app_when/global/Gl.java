package com.example.ysm0622.app_when.global;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;

public class Gl extends Application {

    // TAG
    private static final String TAG = Gl.class.getName();

    // Gl static
    public static final String USER = "USER";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";
    public static final String USER_PASSWORD = "USER_PASSWORD";

    public static final String GROUP = "GROUP";
    public static final String GROUP_TITLE = "GROUP_TITLE";
    public static final String GROUP_DESC = "GROUP_DESC";
    public static final String GROUP_MASTER = "GROUP_MASTER";
    public static final String GROUP_MEMBER = "GROUP_MEMBER";

    public static final String MEET = "MEET";
    public static final String MEET_GROUP = "MEET_GROUP";
    public static final String MEET_MASTER = "MEET_MASTER";
    public static final String MEET_TITLE = "MEET_TITLE";
    public static final String MEET_DESC = "MEET_DESC";
    public static final String MEET_CREATEDATE = "MEET_CREATEDATE";
    public static final String MEET_LOCATION = "MEET_LOCATION";
    public static final String MEET_SELECTEDDATE = "MEET_SELECTEDDATE";
    public static final String MEET_DATETIME = "MEET_DATETIME";

    public static final String DATETIME_USER = "DATETIME_USER";
    public static final String DATETIME_STARTTIME = "DATETIME_STARTTIME";
    public static final String DATETIME_ENDTIME = "DATETIME_ENDTIME";

    public static final String TAB_NUMBER = "TAB_NUMBER";
    public static final String SELECT_DAY_MODE = "SELECT_DAY_MODE";
    public static final String INVITE_MODE = "INVITE_MODE";

    // Request code
    public static final int GROUPLIST_CREATEGROUP = 1000;
    public static final int GROUPLIST_GROUPMANAGE = 1001;
    public static final int CREATEGROUP_INVITEPEOPLE = 1002;
    public static final int GROUPMANAGE_CREATEMEET = 1003;
    public static final int GROUPMANAGE_INVITEPEOPLE = 1004;
    public static final int GROUPMANAGE_SELECTDAY = 1005;
    public static final int GROUPMANAGE_POLLSTATE = 1006;
    public static final int CREATEMEET_SELECTDAY = 1007;
    public static final int SETTINGS_EDITPROFILE = 1008;
    public static final int GROUPLIST_SETTINGS = 1009;
    public static final int GROUPMANAGE_SETTINGS = 1010;
    public static final int LOGIN_GROUPLIST = 1011;
    public static final int INTRO_GROUPLIST = 1012;
    public static final int INTRO_LOGIN = 1013;

    // Result Code
    public static final int RESULT_LOGOUT = 2000;
    public static final int RESULT_DELETE = 3000;

    public static Context CONTEXT;
    public static float DENSITY;

    public static ArrayList<User> USERS;
    public static ArrayList<Group> GROUPS;
    public static ArrayList<Meet> MEETS;

    public static void initialize(Context context) {
        CONTEXT = context;
        DENSITY = CONTEXT.getResources().getDisplayMetrics().density;
    }

    // User method
    public static void setUsers() {
        USERS = new ArrayList<>();
    }

    public static ArrayList<User> getUsers() {
        return USERS;
    }

    public static ArrayList<User> getUsers(Group g) {
        return g.getMember();
    }

    public static User getUser(int i) {
        return USERS.get(i);
    }

    public static int getUserCount() {
        return USERS.size();
    }

    public static void add(User u) {
        USERS.add(u);
    }

    public static void add(int i, User u) {
        USERS.add(i, u);
    }

    public static void remove(User u) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).getId() == u.getId()) {
                USERS.remove(i);
                break;
            }
        }
    }

    // Group method
    public static void setGroups() {
        GROUPS = new ArrayList<>();
    }

    public static ArrayList<Group> getGroups() {
        return GROUPS;
    }

    public static ArrayList<Group> getGroups(User u) {
        ArrayList<Group> arrayList = new ArrayList<>();
        for (int i = 0; i < GROUPS.size(); i++) {
            ArrayList<User> arrayList1 = GROUPS.get(i).getMember();
            for (int j = 0; j < arrayList1.size(); j++) {
                if (u.getId() == arrayList1.get(j).getId()) {
                    arrayList.add(GROUPS.get(i));
                    break;
                }
            }
        }
        return arrayList;
    }

    public static Group getGroup(int i) {
        return GROUPS.get(i);
    }

    public static int getGroupCount() {
        return GROUPS.size();
    }

    public static void add(Group g) {
        GROUPS.add(g);
    }

    public static void add(int i, Group g) {
        GROUPS.add(i, g);
    }

    public static void remove(Group g) {
        GROUPS.remove(g);
    }

    // Meet method
    public static void setMeets() {
        MEETS = new ArrayList<>();
    }

    public static ArrayList<Meet> getMeets() {
        return MEETS;
    }

    public static ArrayList<Meet> getMeets(Group g) {
        ArrayList<Meet> arrayList = new ArrayList<>();
        for (int i = 0; i < MEETS.size(); i++) {
            if (MEETS.get(i).getGroup().getId() == g.getId()) {
                arrayList.add(MEETS.get(i));
            }
        }
        return arrayList;
    }

    public static Meet getMeet(int i) {
        return MEETS.get(i);
    }

    public static int getMeetCount() {
        return MEETS.size();
    }

    public static void add(Meet m) {
        MEETS.add(m);
    }

    public static void add(int i, Meet m) {
        MEETS.add(i, m);
    }

    public static void remove(Meet m) {
        MEETS.remove(m);
    }

    // Test method
    public static void setTestUsers() {

        USERS.clear();
        USERS.add(new User("양성민", "ysm0622@gmail.com", "1234"));
        USERS.add(new User("지정한", "wlwjdgks123@gmail.com", "1234"));
        USERS.add(new User("조동현", "ehdguso@naver.com", "1234"));
        USERS.add(new User("조서형", "westbro00@naver.com", "1234"));
        USERS.add(new User("김영송", "infall346@naver.com", "1234"));
        USERS.add(new User("장영준", "cyj9212@gmail.com", "1234"));
        USERS.add(new User("유영준", "yyj@gmail.com", "1234"));
        USERS.add(new User("김원", "wonkimtx@gachon.ac.kr", "1234"));
        USERS.add(new User("정옥란", "orjeong@gachon.ac.kr", "1234"));
        USERS.add(new User("최재혁", "jchoi@gachon.ac.kr", "1234"));
        USERS.add(new User("유준", "joon.yoo@gachon.ac.kr", "1234"));
        USERS.add(new User("노웅기", "wkloh2@gachon.ac.kr", "1234"));
        USERS.add(new User("최아영", "aychoi@gachon.ac.kr", "1234"));
        USERS.add(new User("정용주", "coolyj.jung@gmail.com", "1234"));

    }


    // Shared Preferences
    public static final String FILE_NAME_NOTICE = "NOTICE_DATA";
    public static final String FILE_NAME_LOGIN = "LOGIN_DATA";
    public static final String FILE_NAME_LANGUAGE = "LANGUAGE_DATA";
    public static final String FILE_NAME_MEET = "MEET_DATA";
    public static final String NOTICE_CHECK = "CHECK";
    public static final String NOTICE_SOUND = "SOUND";
    public static final String NOTICE_VIBRATION = "VIBRATION";
    public static final String NOTICE_POPUP = "POPUP";
    public static final String LANGUAGE_CHECK = "LANGUAGE_CHECK";
    public static final String LANGUAGE_KOREAN = "KOREAN";
    public static final String LANGUAGE_ENGLISH = "ENGLISH";
    public static final String MEET_NOTICE="MEET_";

    public static float dpToPx(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    public static void Log(User u) {
        Log.w(TAG, "User > Id : " + u.getId() + " / Name : " + u.getName() + " / Email : " + u.getEmail() + " / PW : " + u.getPassword());
    }

    public static void Log(Group g) {
        Log.w(TAG, "Group > Id : " + g.getId() + " / Title : " + g.getTitle() + " / Master : " + g.getMaster().getName() + " / MemberNum : " + g.getMemberNum());
    }

    public static void Log(Meet m) {
        Log.w(TAG, "Meet > Id : " + m.getId() + " / Title : " + m.getTitle() + " / Group : " + m.getGroup().getTitle() + " / Master : " + m.getMaster().getName() + " / DateTimeNum : " + m.getDateTimeNum());
    }
}
