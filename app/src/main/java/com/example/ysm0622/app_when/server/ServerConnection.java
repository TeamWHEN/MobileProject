package com.example.ysm0622.app_when.server;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
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
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class ServerConnection extends AsyncTask<String, String, String> {

    private static final String TAG = ServerConnection.class.getName();

    private String TYPE;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... args) {
        Log.d("Gl", args[0] + " " + args[1]);
        TYPE = args[0];
        switch (args[0]) {
            case Gl.SELECT_ALL_USER:
            case Gl.SELECT_ALL_GROUP:
            case Gl.SELECT_ALL_USERGROUP:
            case Gl.SELECT_MEET_BY_GROUP:
            case Gl.SELECT_TIME_BY_MEET:
                return getStringFromServer(new ArrayList<NameValuePair>(), args[1]);
            default:
                return getStringFromServer(getNameValuePair(TYPE, args[2]), args[1]);
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
            default:
        }
    }

    private ArrayList<NameValuePair> getNameValuePair(String TYPE, String index) {
        ArrayList<NameValuePair> arrayList = new ArrayList<>();
        switch (TYPE) {
            case Gl.INSERT_USER:
                arrayList = InsertUser(Integer.parseInt(index));
        }
        return arrayList;
    }

    public String getStringFromServer(ArrayList<NameValuePair> post, String url) {
        String result = "";
        switch (TYPE) {
            case Gl.INSERT_USER:

        }
        // 연결 HttpClient 객체 생성
        HttpClient client = new DefaultHttpClient();

        // 객체 연결 설정 부분, 연결 최대시간 등등
        HttpParams params = client.getParams();
        HttpConnectionParams.setConnectionTimeout(params, 5000);
        HttpConnectionParams.setSoTimeout(params, 5000);

        // Post객체 생성
        HttpPost httpPost = new HttpPost(url);
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(post, "UTF-8");
            httpPost.setEntity(entity);
            HttpResponse responsePOST = client.execute(httpPost);
            HttpEntity resEntity = responsePOST.getEntity();
            if (resEntity != null) {
                result = EntityUtils.toString(resEntity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void SelectAllUser(String result) {
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

    private void SelectAllGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<Group> arrayList = new ArrayList<>();
            Log.d("Gl", data.toString());
            for (int i = 0; i < data.length(); i++) {
                Log.d("Gl", data.getJSONObject(i).toString());
                Group g = g = new Gson().fromJson(data.getJSONObject(i).toString(), Group.class);
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

    private void SelectAllUserGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<UserGroup> arrayList = new ArrayList<>();
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

    private void SelectMeetByGroup(String result) {
        try {
            JSONObject jObject = new JSONObject(result);
            JSONArray data = jObject.getJSONArray("user");
            ArrayList<Meet> arrayList = new ArrayList<>();
            for (int i = 0; i < data.length(); i++) {
                Meet m = new Gson().fromJson(data.getJSONObject(i).toString(), Meet.class);
                arrayList.add(m);
            }
            Gl.setMeets(arrayList);
            Gl.LogAllMeet();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void SelectTimeByMeet(String result) {
//        try {
//            JSONObject jObject = new JSONObject(result);
//            JSONArray data = jObject.getJSONArray("user");
//            ArrayList<DateTime> arrayList = new ArrayList<>();
//            for (int i = 0; i < data.length(); i++) {
//                DateTime dt = new Gson().fromJson(data.getJSONObject(i).toString(), DateTime.class);
//                arrayList.add(dt);
//            }
//            for(int i=0;i<Gl.getMeetCount();i++){
//
//            }
//            Gl.setMeets(arrayList);
//            Gl.LogAllTimeByMeet();
//
//            Date d = new Date();
//        } catch (Exception e) {
//            e.getMessage();
//        }
    }

    private ArrayList<NameValuePair> InsertUser(int index) {
        Log.d("Gl", "Index : " + index);
        ArrayList<NameValuePair> post = new ArrayList<>();
        post.add(new BasicNameValuePair("Name", Gl.getUser(index).getName()));
        post.add(new BasicNameValuePair("Email", Gl.getUser(index).getEmail()));
        post.add(new BasicNameValuePair("Password", Gl.getUser(index).getPassword()));
        Log.d("Gl", post.get(0).toString() + " " + post.get(1).toString());
        return post;
    }
}