package com.example.ysm0622.app_when.global;


import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.Log;
import android.util.TypedValue;

import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.MeetDate;
import com.example.ysm0622.app_when.object.Times;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.object.UserGroup;

import java.util.ArrayList;
import java.util.Date;

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
    public static ArrayList<UserGroup> USER_GROUP;
    public static ArrayList<Meet> MEETS;
    public static ArrayList<MeetDate> MEET_DATE;
    public static ArrayList<Times> TIMES;

    public static User MyUser;
    public static Group MyGroup;
    public static Meet MyMeet;

    public static void initialize(Context context) {
        CONTEXT = context;
        DENSITY = CONTEXT.getResources().getDisplayMetrics().density;
        USERS = new ArrayList<>();
        GROUPS = new ArrayList<>();
        MEETS = new ArrayList<>();
        USER_GROUP = new ArrayList<>();
        MEET_DATE = new ArrayList<>();
        TIMES = new ArrayList<>();
    }

    // User-Group method
    public static void add(UserGroup ug) {
        USER_GROUP.add(ug);
    }

    public static ArrayList<UserGroup> getUserGroup() {
        return USER_GROUP;
    }

    public static void setUserGroup(ArrayList<UserGroup> arrayList) {
        USER_GROUP = arrayList;
    }

    public static UserGroup getUserGroup(int i) {
        return USER_GROUP.get(i);
    }

    // Meet-Date method
    public static ArrayList<MeetDate> getMeetDate() {
        return MEET_DATE;
    }

    public static void setMeetDate(ArrayList<MeetDate> arrayList) {
        MEET_DATE = arrayList;
    }

    public static MeetDate getMeetDate(int i) {
        return MEET_DATE.get(i);
    }

    // Times method
    public static ArrayList<Times> getTime() {
        return TIMES;
    }

    public static void setTime(ArrayList<Times> arrayList) {
        TIMES = arrayList;
    }

    public static Times getTime(int i) {
        return TIMES.get(i);
    }

    // User method
    public static void setUsers(ArrayList<User> arrayList) {
        USERS = arrayList;
    }

    public static ArrayList<User> getUsers() {
        return USERS;
    }

    public static ArrayList<User> getUsersByGroupId(int id) {
        ArrayList<User> result = new ArrayList<>();
        for (int i = 0; i < USER_GROUP.size(); i++) {
            if (id == USER_GROUP.get(i).getGroupId())
                result.add(getUserById(USER_GROUP.get(i).getUserId()));
        }
        return result;
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

    public static ArrayList<Group> getGroupsByUserId(int id) {
        ArrayList<Group> result = new ArrayList<>();
        for (int i = 0; i < USER_GROUP.size(); i++) {
            if (id == USER_GROUP.get(i).getUserId())
                result.add(getGroupById(USER_GROUP.get(i).getGroupId()));
        }
        return result;
    }

//    public static ArrayList<Group> getGroups(User u) {
//        ArrayList<Group> arrayList = new ArrayList<>();
//        for (int i = 0; i < GROUPS.size(); i++) {
//            ArrayList<User> arrayList1 = GROUPS.get(i).getMember();
//            for (int j = 0; j < arrayList1.size(); j++) {
//                if (u.getId() == arrayList1.get(j).getId()) {
//                    arrayList.add(GROUPS.get(i));
//                    break;
//                }
//            }
//        }
//        return arrayList;
//    }

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

    public static int getMemberNumByGroup(Group g) {
        int result = 0;
        for (int i = 0; i < USER_GROUP.size(); i++) {
            if (USER_GROUP.get(i).getUserId() == g.getId()) result++;
        }
        return result;
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
            if (MEETS.get(i).getGroupId() == g.getId()) {
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
        for(int i=0;i<MEETS.size();i++){
            if(m.getId()==MEETS.get(i).getId()){
                MEETS.remove(i);
                break;
            }
        }
        MEETS.add(m);
    }

    public static void add(int i, Meet m) {
        MEETS.add(i, m);
    }

    public static void remove(Meet m) {
        MEETS.remove(m);
    }

    public static Meet getMeetById(int id) {
        for (int i = 0; i < MEETS.size(); i++) {
            if (MEETS.get(i).getId() == id) return MEETS.get(i);
        }
        return null;
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
    public static final String SERVER_URL = "http://52.79.132.35:8080/first/sample/";
    public static final String SELECT_ALL_USER = SERVER_URL + "selectUserInfo.do";// --> 됌
    public static final String SELECT_ALL_GROUP = SERVER_URL + "selectGroupList.do";// --> 됌
    public static final String SELECT_ALL_USERGROUP = SERVER_URL + "selectUserGroup.do";// --> 됌
    public static final String SELECT_MEET_BY_GROUP = SERVER_URL + "selectMeet.do";
    public static final String SELECT_MEETDATE_BY_GROUP = SERVER_URL + "selectMeetDate.do";
    public static final String SELECT_TIME_BY_MEET = SERVER_URL + "selectTime.do";
    public static final String INSERT_USER = SERVER_URL + "insertUserAccount.do";
    public static final String DELETE_USER = SERVER_URL + "deleteUserAccount.do";//
    public static final String UPDATE_USER = SERVER_URL + "updateUserAccount.do";
    public static final String INSERT_GROUP = SERVER_URL + "insertGroupList.do";
    public static final String DELETE_GROUP = SERVER_URL + "deleteGroupList.do";
    //    public static final String UPDATE_GROUP = SERVER_URL + "updateGroupList.do";
    public static final String INSERT_USERGROUP = SERVER_URL + "insertUserGroup.do";
    public static final String DELETE_USERGROUP = SERVER_URL + "deleteUserGroup.do";
    public static final String INSERT_MEET = SERVER_URL + "insertMeet.do";
    public static final String DELETE_MEET = SERVER_URL + "deleteMeet.do";
    //    public static final String UPDATE_MEET = SERVER_URL + "updateMeet.do";
    public static final String INSERT_MEETDATE = SERVER_URL + "insertMeetDate.do";
    public static final String INSERT_TIME = SERVER_URL + "insertTime.do";
    public static final String DELETE_TIME = SERVER_URL + "deleteTime.do";


    // Logs
    public static void LogAllUser() {
        for (int i = 0; i < USERS.size(); i++) {
            User u = USERS.get(i);
            Log.d(TAG, "User > Id : " + u.getId() + " / Name : " + u.getName() + " / Email : " + u.getEmail() + " / PW : " + u.getPassword() + " / JoinDate : " + u.getJoinDate());
        }
    }

    public static void LogAllGroup() {
        for (int i = 0; i < GROUPS.size(); i++) {
            Group g = GROUPS.get(i);
            Log.d(TAG, "Group > Id : " + GROUPS.get(i).getId() + " / Title : " + GROUPS.get(i).getTitle() + " / Descr : " + GROUPS.get(i).getDesc() + " / MasterId : " + GROUPS.get(i).getMasterId());
            for (int j = 0; j < g.getMemberNum(); j++) {
                Log.d(TAG, "        Member > Id : " + g.getMember(j).getId() + " / Name : " + g.getMember(j).getName());
            }
        }
    }

    public static void LogAllMeet() {
        for (int i = 0; i < MEETS.size(); i++) {
            Meet m = MEETS.get(i);
            Log.d(TAG, "Meet > Id : " + m.getId() + " / Title : " + m.getTitle() + " / Group : " + m.getGroup().getTitle() + " / Master : " + m.getMaster().getName());
            for (int j = 0; j < m.MeetDate.size(); j++) {
                Date d = new Date(m.MeetDate.get(j).getDate());
                Log.d(TAG, "       SelectDate > Date : " + (d.getYear() + 1900) + "/" + (d.getMonth() + 1) + "/" + d.getDate());
            }
//            for (int j = 0; j < m.getDateTimeNum(); j++) {
//                Date d = new Date(m.MeetDate.get(j).getDate());
//                Log.d(TAG, "            SelectTime > User : " + m.getDateTime().get(j).getUser().getName());
//            }
        }
    }

    public static void LogAllMeetDate() {
        for (int i = 0; i < MEETS.size(); i++) {
            MeetDate m = MEET_DATE.get(i);
            Log.d(TAG, "MeetDate > GroupId : " + m.getGroupId() + " / MeetId : " + m.getMeetId() + " / Date : " + m.getDate());

        }
    }

    public static void LogAllTimeByMeet(Meet m) {
        for (int i = 0; i < m.getDateTimeNum(); i++) {
            DateTime dt = m.getDateTime(i);
            Log.d(TAG, "DATETIME > GroupId : " + m.getGroup().getId() + " / MeetId : " + m.getId() + " / UserId : " + dt.getUser().getId());
        }
    }

    //이미지 원형으로 전환
    public static Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth() / 2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }

    //이미지 파일 경로
    public static String getImage(User u) {
        return u.getImageFilePath() + u.getId() + ".jpg";
    }
}
