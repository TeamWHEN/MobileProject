package com.example.ysm0622.app_when.global;


import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;

import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.object.UserGroup;

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
    public static ArrayList<UserGroup> USER_GROUP;

    public static void initialize(Context context) {
        CONTEXT = context;
        DENSITY = CONTEXT.getResources().getDisplayMetrics().density;
        USERS = new ArrayList<>();
        GROUPS = new ArrayList<>();
        MEETS = new ArrayList<>();
        USER_GROUP = new ArrayList<>();
    }

    // User-Group method
    public static ArrayList<UserGroup> getUserGroup() {
        return USER_GROUP;
    }

    public static void setUserGroup(ArrayList<UserGroup> arrayList) {
        USER_GROUP = arrayList;
    }

    // User method
    public static void setUsers(ArrayList<User> arrayList) {
        USERS = arrayList;
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

    public static User getUserById(int id) {
        for (int i = 0; i < USERS.size(); i++) {
            if (USERS.get(i).getId() == id) return USERS.get(i);
        }
        return null;
    }

    // Group method
    public static void setGroups(ArrayList<Group> arrayList) {
        GROUPS = arrayList;
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

    public static Group getGroupById(int id) {
        for (int i = 0; i < GROUPS.size(); i++) {
            if (GROUPS.get(i).getId() == id) return GROUPS.get(i);
        }
        return null;
    }

    // Meet method
    public static void setMeets(ArrayList<Meet> arrayList) {
        MEETS = arrayList;
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
    public static void setTestUsers(String aa, String bb, String cc) {

        USERS.add(new User(aa, bb, cc));

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
    public static final String MEET_NOTICE = "MEET_";

    public static float dpToPx(Context context, float dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics());
        return px;
    }

    // Request Server Query
    public static final String SELECT_ALL_USER = "SELECT_ALL_USER";
    public static final String SELECT_ALL_GROUP = "SELECT_ALL_GROUP";
    public static final String SELECT_ALL_USERGROUP = "SELECT_ALL_USERGROUP";
    public static final String SELECT_MEET_BY_GROUP = "SELECT_MEET_BY_GROUP";
    public static final String SELECT_TIME_BY_MEET = "SELECT_TIME_BY_MEET";
    public static final String INSERT_USER = "INESRT_USER";
    public static final String DELETE_USER = "DELETE_USER";
    public static final String UPDATE_USER = "UPDATE_USER";
    public static final String INSERT_GROUP = "INSERT_GROUP";
    public static final String DELETE_GROUP = "DELETE_GROUP";
    public static final String UPDATE_GROUP = "UPDATE_GROUP";
    public static final String INSERT_USERGROUP = "INSERT_USERGROUP";
    public static final String DELETE_USERGROUP = "DELETE_USERGROUP";
    public static final String UPDATE_USERGROUP = "UPDATE_USERGROUP";
    public static final String INSERT_MEET = "INSERT_MEET";
    public static final String DELETE_MEET = "DELETE_MEET";
    public static final String UPDATE_MEET = "UPDATE_MEET";
    public static final String INSERT_TIME = "INSERT_TIME";
    public static final String DELETE_TIME = "DELETE_TIME";
    public static final String UPDATE_TIME = "UPDATE_TIME";

    public static void LogAllUser() {
        for (int i = 0; i < USERS.size(); i++) {
            User u = USERS.get(i);
            Log.d(TAG, "User > Id : " + u.getId() + " / Name : " + u.getName() + " / Email : " + u.getEmail() + " / PW : " + u.getPassword() + " / JoinDate : " + u.getJoinDate());
        }
    }

    public static void LogAllGroup() {
        for (int i = 0; i < GROUPS.size(); i++) {
            Group g = GROUPS.get(i);
            Log.d(TAG, "Group > Id : " + g.getId() + " / Title : " + g.getTitle() + " / Desc : " + g.getDesc() + " / Master : " + g.getMaster().getName() + " / MemberNum : " + g.getMemberNum());
            for (int j = 0; j < g.getMemberNum(); j++) {
                Log.d(TAG, "                                   Member > Name : " + g.getMember(j).getName());
            }
        }
    }

    public static void LogAllMeet() {
        for (int i = 0; i < MEETS.size(); i++) {
            Meet m = MEETS.get(i);
            Log.d(TAG, "Meet > Id : " + m.getId() + " / Title : " + m.getTitle() + " / Group : " + m.getGroup().getTitle() + " / Master : " + m.getMaster().getName() + " / DateTimeNum : " + m.getDateTimeNum());
        }
    }

    public static void LogAllTimeByMeet(Meet m) {
        for (int i = 0; i < m.getDateTimeNum(); i++) {
            DateTime dt = m.getDateTime(i);
            Log.d(TAG, "DateTime > GroupId : " + m.getGroup().getId() + " / MeetId : " + m.getId() + " / UserId : " + dt.getUser().getId());
        }
    }
}
