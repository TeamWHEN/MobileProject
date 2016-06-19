package com.example.ysm0622.app_when.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.meet.SelectDay;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.MeetDate;
import com.example.ysm0622.app_when.object.Times;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.object.UserGroup;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ServerConnection extends AsyncTask<String, String, String> {

    public static final String TAG = ServerConnection.class.getName();

    public String TYPE;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d("Gl", args[0]);
        TYPE = args[0];
        switch (TYPE) {
            case Gl.SELECT_ALL_USER:
            case Gl.SELECT_ALL_GROUP:
            case Gl.SELECT_ALL_USERGROUP:
                return getStringFromServer(new ArrayList<NameValuePair>(), args[0]);
            default:
                return getStringFromServer(getNameValuePair(args[1]), args[0]);
        }
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("Gl", result);
        switch (TYPE) {
            case Gl.SELECT_ALL_USER:
                SelectAllUser(result);
                break;
            case Gl.SELECT_ALL_GROUP:
                SelectAllGroup(result);
                break;
            case Gl.SELECT_ALL_USERGROUP:
                SelectAllUserGroup(result);
                break;
            case Gl.SELECT_MEET_BY_GROUP:
                SelectMeetByGroup(result);
                break;
            case Gl.SELECT_TIME_BY_MEET:
                SelectTimeByMeet(result);
                break;
            case Gl.SELECT_MEETDATE_BY_GROUP:
                SelectMeetDateByGroup(result);
                break;
            default:
        }
    }

    public ArrayList<NameValuePair> getNameValuePair(String index) {
        ArrayList<NameValuePair> arrayList = new ArrayList<>();
        switch (TYPE) {
            case Gl.SELECT_MEET_BY_GROUP:
//                arrayList = SelectMeetByGroup(Integer.parseInt(index));
                break;
            case Gl.SELECT_MEETDATE_BY_GROUP:
//                arrayList = SelectMeetDateByMeet(Integer.parseInt(index));
                break;
            case Gl.SELECT_TIME_BY_MEET:
//                arrayList = SelectTimeByMeet(Integer.parseInt(index));
                break;
            case Gl.INSERT_USER:
//                arrayList = InsertUser(Integer.parseInt(index));
                break;
            case Gl.DELETE_USER:
//                arrayList = DeleteUser(Integer.parseInt(index));
                break;
            case Gl.UPDATE_USER:
//                arrayList = UpdateUser(Integer.parseInt(index));
                break;
            case Gl.INSERT_GROUP:
//                arrayList = InsertGroup(Integer.parseInt(index));
                break;
            case Gl.DELETE_GROUP:
                arrayList = DeleteGroup(Integer.parseInt(index));
                break;
//            case Gl.UPDATE_GROUP:
//                arrayList = UpdateGroup(Integer.parseInt(index));
//                break;
            case Gl.INSERT_USERGROUP:
//                arrayList = InsertUserGroup(Integer.parseInt(index));
                break;
            case Gl.DELETE_USERGROUP:
//                arrayList = DeleteUserGroup(Integer.parseInt(index));
                break;
            case Gl.INSERT_MEET:
//                arrayList = InsertMeet(Integer.parseInt(index));
//                break;
            case Gl.DELETE_MEET:
                arrayList = DeleteMeet(Integer.parseInt(index));
                break;
//            case Gl.UPDATE_MEET:
//                arrayList = UpdateMeet(Integer.parseInt(index));
//                break;
            case Gl.INSERT_MEETDATE:
//                arrayList = InsertMeetDate(Integer.parseInt(index));
//                break;
            case Gl.INSERT_TIME:
//                arrayList = InsertTime(Integer.parseInt(index));
                break;
            case Gl.DELETE_TIME:
                arrayList = DeleteTime(Integer.parseInt(index));
                break;

        }
        return arrayList;
    }

    public static String getStringFromServer(ArrayList<NameValuePair> post, String url) {
        String result = "";

        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, HTTP.UTF_8);
            httpPost.setEntity(entity);
//            client.getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
            HttpResponse responsePOST = client.execute(httpPost);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
            Log.d("Gl", result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void SelectAllUser(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<User> arrayList = new ArrayList<>();
            Log.d("Gl", data.toString());
            for (int i = 0; i < data.length(); i++) {
                User u = new Gson().fromJson(data.getJSONObject(i).toString(), User.class);
                Date d = new Date(u.getJoinDate());
                u.setJoined(d);
                arrayList.add(u);
            }
            Gl.setUsers(arrayList);
            Gl.LogAllUser();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void SelectAllGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<Group> arrayList = new ArrayList<>();
            Log.d("Gl", data.toString());
            for (int i = 0; i < data.length(); i++) {
                Log.d("Gl", data.getJSONObject(i).toString());
                Group g = new Gson().fromJson(data.getJSONObject(i).toString(), Group.class);
                arrayList.add(g);
                User u = Gl.getUserById(g.getMasterId());
                g.setMaster(u);
            }
            Gl.setGroups(arrayList);
            Gl.LogAllGroup();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void SelectAllUserGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<UserGroup> arrayList = new ArrayList<>();
            Log.d("Gl", data.toString());
            for (int i = 0; i < data.length(); i++) {
                UserGroup ug = new Gson().fromJson(data.getJSONObject(i).toString(), UserGroup.class);
                arrayList.add(ug);
            }
            Gl.setUserGroup(arrayList);

            for (int i = 0; i < Gl.getUserGroup().size(); i++) {
                UserGroup ug = Gl.getUserGroup().get(i);
                Group g = Gl.getGroupById(ug.getGroupId());
                User u = Gl.getUserById(ug.getUserId());
                g.addMember(u);
            }

            Gl.LogAllGroup();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static void SelectMeetByGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<Meet> arrayList = new ArrayList<>();
            Log.d("Gl", data.toString());
            for (int i = 0; i < data.length(); i++) {
                Meet m = new Gson().fromJson(data.getJSONObject(i).toString(), Meet.class);
                m.setGroup(Gl.getGroupById(m.getGroupId()));
                m.setMaster(Gl.getUserById(m.getMasterId()));
                Date d = new Date(m.getCreateDate());
                m.setCreated(d);
                arrayList.add(m);
            }
            Gl.setMeets(arrayList);
            Gl.LogAllMeet();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ArrayList<NameValuePair> SelectMeetByGroup(Group g) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("GroupId", String.valueOf(g.getId())));
        Log.d("Gl", "SelectMeetByGroup(" + g.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static void SelectMeetDateByGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<MeetDate> arrayList = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                MeetDate m = new Gson().fromJson(data.getJSONObject(i).toString(), MeetDate.class);
                Log.d("Gl", "groupid : " + m.getGroupId() + "meetid : " + m.getMeetId() + " date : " + m.getDate());
                arrayList.add(m);
                Calendar c = Calendar.getInstance();
                c.setTimeInMillis(m.getDate());
                Gl.getMeetById(m.getMeetId()).getSelectedDate().add(c);
                Gl.getMeetById(m.getMeetId()).MeetDate.add(m);
            }
            Gl.setMeetDate(arrayList);
            Gl.LogAllMeetDate();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ArrayList<NameValuePair> SelectMeetDateByGroup(Group g) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("GroupId", String.valueOf(g.getId())));
        Log.d("Gl", "SelectMeetDateByGroup(" + g.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static void SelectTimeByMeet(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<Times> arrayList = new ArrayList<>();
            Log.d("Gl","되라아아아아아앙1111111111111");
            for (int i = 0; i < data.length(); i++) {
                Times t = new Gson().fromJson(data.getJSONObject(i).toString(), Times.class);
                arrayList.add(t);
            }
            Log.d("Gl","되라아아아아아앙2222222222222222");
            Gl.getMeetById(arrayList.get(0).getMeetId()).setDateTime(SelectDay.TimesToDateTime(arrayList));
            Log.d("Gl","되라아아아아아앙33333333333333");
            Gl.setTime(arrayList);
            Gl.LogAllTimeByMeet(Gl.getMeet(arrayList.get(0).getMeetId()));
        } catch (Exception e) {
            e.getMessage();
        }
    }

    public static ArrayList<NameValuePair> SelectTimeByMeet(Meet m) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("MeetId", String.valueOf(m.getId())));
        Log.d("Gl", "SelectTimeByMeet(" + m.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> InsertUser(User u) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Name", u.getName()));
        post.add(new BasicNameValuePair("Email", u.getEmail()));
        post.add(new BasicNameValuePair("Password", u.getPassword()));
        Log.d("Gl", "InsertUser(" + u.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> DeleteUser(User u) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Id", String.valueOf(u.getId())));
        Log.d("Gl", "DeleteUser(" + u.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> UpdateUser(User u) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Name", u.getName()));
        post.add(new BasicNameValuePair("Password", u.getPassword()));
        post.add(new BasicNameValuePair("Id", String.valueOf(u.getId())));
        Log.d("Gl", "UpdateUser(" + u.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> InsertGroup(Group g) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Title", g.getTitle()));
        post.add(new BasicNameValuePair("Descr", g.getDesc()));
        post.add(new BasicNameValuePair("MasterId", String.valueOf(g.getMaster().getId())));
        Log.d("Gl", "InsertGroup(" + g.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> DeleteGroup(int index) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Id", String.valueOf(Gl.getGroup(index).getId())));
        Log.d("Gl", "DeleteGroup(" + index + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

//    public ArrayList<NameValuePair> UpdateGroup(int index) {
//        ArrayList<NameValuePair> post = new ArrayList<>();
//        post.add(new BasicNameValuePair("Id", String.valueOf(Gl.getGroup(index).getId())));
//        Log.d("Gl", "UpdateGroup(" + index + ")");
//        for (int i = 0; i < post.size(); i++)
//            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
//        return post;
//    }

    public static ArrayList<NameValuePair> InsertUserGroup(Group g) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        for (int i = 0; i < g.Member.size(); i++) {
            post.add(new BasicNameValuePair("GroupId", String.valueOf(g.getId())));
            post.add(new BasicNameValuePair("UserId", String.valueOf(g.Member.get(i).getId())));
        }
        Log.d("Gl", "InsertUserGroup(" + g.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> DeleteUserGroup(Group g) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("GroupId", String.valueOf(g.getId())));
        post.add(new BasicNameValuePair("UserId", String.valueOf(Gl.MyUser.getId())));
        Log.d("Gl", "DeleteUserGroup(" + g.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> InsertMeet(Meet m) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("GroupId", String.valueOf(m.getGroup().getId())));
        post.add(new BasicNameValuePair("MasterId", String.valueOf(m.getMaster().getId())));
        post.add(new BasicNameValuePair("Title", m.getTitle()));
        post.add(new BasicNameValuePair("Descr", m.getDesc()));
        post.add(new BasicNameValuePair("Location", m.getLocation()));
        Log.d("Gl", "InsertMeet(" + m.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> DeleteMeet(int index) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Id", String.valueOf(Gl.getMeet(index).getId())));
        Log.d("Gl", "DeleteMeet(" + index + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

//    public ArrayList<NameValuePair> UpdateMeet(int index) {
//        ArrayList<NameValuePair> post = new ArrayList<>();
//        post.add(new BasicNameValuePair("Id", String.valueOf(Gl.getMeet(index).getId())));
//        Log.d("Gl", "UpdateMeet(" + index + ")");
//        for (int i = 0; i < post.size(); i++)
//            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
//        return post;
//    }

    public static ArrayList<NameValuePair> InsertMeetDate(Meet m) {

        ArrayList<NameValuePair> post = new ArrayList<>();
        for (int i = 0; i < m.getMeetDate().size(); i++) {
            post.add(new BasicNameValuePair("GroupId", String.valueOf(m.getMeetDate().get(i).getGroupId())));
            post.add(new BasicNameValuePair("MeetId", String.valueOf(m.getMeetDate().get(i).getMeetId())));
            post.add(new BasicNameValuePair("Date", String.valueOf(m.getMeetDate().get(i).getDate())));
        }
        Log.d("Gl", "InsertMeetDate(" + m.getId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> InsertTime(ArrayList<Times> t) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        for (int i = 0; i < t.size(); i++) {
            post.add(new BasicNameValuePair("MeetId", String.valueOf(t.get(i).getMeetId())));
            post.add(new BasicNameValuePair("UserId", String.valueOf(t.get(i).getUserId())));
            post.add(new BasicNameValuePair("Time", String.valueOf(t.get(i).getTime())));
        }
        Log.d("Gl", "InsertTime(" + t.get(0).getMeetId() + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }

    public static ArrayList<NameValuePair> DeleteTime(int index) {
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("MeetId", String.valueOf(Gl.getTime(index).getMeetId())));
        post.add(new BasicNameValuePair("UserId", String.valueOf(Gl.getTime(index).getUserId())));
        Log.d("Gl", "DeleteTime(" + index + ")");
        for (int i = 0; i < post.size(); i++)
            Log.d("Gl", "post.get(" + i + ") : " + post.get(i).toString());
        return post;
    }


    public boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }

}