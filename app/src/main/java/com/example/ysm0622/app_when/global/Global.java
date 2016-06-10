package com.example.ysm0622.app_when.global;


import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;

public class Global extends Application {

    // TAG
    private static final String TAG = Global.class.getName();

    // Global static
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

    // Result Code
    public static final int RESULT_LOGOUT = 2000;

    public static Context CONTEXT;
    public static float DENSITY;

    public static ArrayList<User> USERS;
    private static final int TEST_NUM = 10;

    public static void initialize(Context context) {
        CONTEXT = context;
        DENSITY = CONTEXT.getResources().getDisplayMetrics().density;
    }

    public static void setUsers() {
        USERS = new ArrayList<>();
        final int TEST_NUM = 10;
        User testuser[] = new User[TEST_NUM];
        testuser[0] = new User("양성민", "ysm0622@gmail.com", "1234");
        testuser[1] = new User("지정한", "wlwjdgks123@gmail.com", "1234");
        testuser[2] = new User("조동현", "ehdguso@naver.com", "1234");
        testuser[3] = new User("조서형", "westbro00@naver.com", "1234");
        testuser[4] = new User("김영송", "infall346@naver.com", "1234");
        testuser[5] = new User("장영준", "cyj9212@gmail.com", "1234");
        testuser[6] = new User("유영준", "yyj@gmail.com", "1234");
        testuser[7] = new User("양영선", "goodceo@gmail.com", "1234");
        testuser[8] = new User("이수현", "freejia65@gmail.com", "1234");
        testuser[9] = new User("박정호", "pjh3001@gmail.com", "1234");
        for (int i = 0; i < testuser.length; i++)
            USERS.add(testuser[i]);
    }

    public static ArrayList<User> getUsers() {
        return USERS;
    }

    public static User getUser(int i) {
        return USERS.get(i);
    }

    public static int getUserCount() {
        return USERS.size();
    }

    public static void addUser(User user) {
        USERS.add(user);
    }

    // Shared Preferences
    public static final String FILE_NAME_NOTICE = "NOTICE_DATA";
    public static final String FILE_NAME_LOGIN = "LOGIN_DATA";
    public static final String NOTICE_CHECK = "CHECK";
    public static final String NOTICE_SOUND = "SOUND";
    public static final String NOTICE_VIBRATION = "VIBRATION";
    public static final String NOTICE_POPUP = "POPUP";

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
