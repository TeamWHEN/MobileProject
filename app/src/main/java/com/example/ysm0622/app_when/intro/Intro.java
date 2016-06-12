package com.example.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.group.GroupList;
import com.example.ysm0622.app_when.login.JSONParser;
import com.example.ysm0622.app_when.login.Login;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class Intro extends AppCompatActivity {


    // TAG
    private static final String TAG = Intro.class.getName();

    // Intent
    private Intent mIntent;

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;
    JSONArray user1 = null;
    //URL to get JSON Array
    private static String url = "http://52.79.132.35:8080/first/sample/selectUserInfo.do";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = new Intent(Intro.this, GroupList.class);
        setContentView(R.layout.intro_main);



        mSharedPref = getSharedPreferences(Global.FILE_NAME_NOTICE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (mSharedPref == null || !mSharedPref.contains(Global.NOTICE_CHECK))
                    noticeInit();
            }

            public void onFinish() {

                if (PRF_AUTO_LOGIN()) {
                    startActivity(mIntent);
//                    new JSONParse().execute();
                } else
                    new JSONParse().execute();
                    startActivity(new Intent(Intro.this, Login.class));

                finish();
            }
        }.start();

        Global.initialize(this);
        Global.setUsers();
        // Preferences 이용 -> Login한 기록이 있다면 자동로그인

    }

    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void noticeInit() {
        mEdit.putBoolean(Global.NOTICE_CHECK, false);
        mEdit.putBoolean(Global.NOTICE_SOUND, false);
        mEdit.putBoolean(Global.NOTICE_VIBRATION, false);
        mEdit.putBoolean(Global.NOTICE_POPUP, false);
        mEdit.apply();
    }

    public boolean PRF_AUTO_LOGIN() {
        String email, password;

        mSharedPref = getSharedPreferences(Global.FILE_NAME_LOGIN, MODE_PRIVATE);

        //로그인 상태
        if (mSharedPref != null && mSharedPref.contains(Global.USER_EMAIL)) {
            email = mSharedPref.getString(Global.USER_EMAIL, "DEFAULT");
            password = mSharedPref.getString(Global.USER_PASSWORD, "DEFAULT");
            mIntent.putExtra(Global.USER, Global.getUser(isExistEmail(email)));
            return true;
        }
        return false;
    }

    private int isExistEmail(String s) {
        for (int i = 0; i < Global.getUserCount(); i++) {
            if (Global.getUser(i).getEmail().equals(s)) {
                return i;
            }
        }
        return -1;
    }



    public class JSONParse extends AsyncTask<String, String, String> {
//        private ProgressDialog pDialog;
        String aa = "";
        final String TAG = "AsyncTaskParseJson.java";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... args) {
//            aa = executeClient(url);
            Log.d("ASYNC", "result1 url주소 = " + url);
//            Toast.makeText(getApplicationContext(), "doInBackground :"+url, Toast.LENGTH_LONG).show();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(getApplicationContext(), "onPostExecute :"+aa, Toast.LENGTH_LONG).show();
         if (aa != null) {
                Log.d("ASYNC", "result2 = " + aa);
            }
//            try {

                JSONParser jParser = new JSONParser();
//
                // Getting JSON from URL
                JSONObject json = jParser.getJSONFromUrl1(url);
//                user1 = json.getJSONArray("user");

//                JSONObject name = user1.getJSONObject(0);
//                name = user1.get(0).getClass("wn_user_name");

//                Toast.makeText(getApplicationContext(),"--22-"+name.toString(),Toast.LENGTH_LONG).show();


//            Intent intent = new Intent(getApplicationContext(), GroupList.class);
//            intent.putExtra("user1",aa);
//            intent.putExtra("email",email);
//                intent.putExtra("name",name.toString());
//                intent.putExtra("wn_leader_name",leader_name.toString());
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            startActivity(intent);

//                Toast.makeText(getApplicationContext(),id,Toast.LENGTH_LONG).show();
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }


        }


        public String executeClient(String url) {

//        email =  mEditText[0].getText().toString();
//        String pass =  mEditText[1].getText().toString();
//            String bb = "";
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
//        post.add(new BasicNameValuePair("wn_user_email", email));
//        post.add(new BasicNameValuePair("wn_user_pw", pass));


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
//                httpPost.setEntity(en);
                HttpResponse responsePOST=  client.execute(httpPost);
                HttpEntity resEntity = responsePOST.getEntity();
                if (resEntity != null) {
                    aa = EntityUtils.toString(resEntity);
                }

                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }}



