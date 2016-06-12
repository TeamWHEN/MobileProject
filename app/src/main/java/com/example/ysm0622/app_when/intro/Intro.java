package com.example.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.group.GroupList;
import com.example.ysm0622.app_when.login.JSONParser;
import com.example.ysm0622.app_when.login.Login;
import com.example.ysm0622.app_when.object.User;

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
    public static ArrayList<User> USERS;
    // Intent
    private Intent mIntent;

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;
    JSONArray user1 = null;
    String logaa = "";
    //URL to get JSON Array
    private static String url = "http://52.79.132.35:8080/first/sample/selectUserInfo.do";
    public String aa="";
    public String bb="";
    public String cc="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = new Intent(Intro.this, GroupList.class);
        setContentView(R.layout.intro_main);
        new JSONParse().execute();
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_NOTICE, MODE_PRIVATE);


        Gl.initialize(this);
        Gl.setUsers();
        Gl.setGroups();
        Gl.setMeets();

        //testData
        Gl.setTestUsers();

        // Logs
        for (int i = 0; i < Gl.USERS.size(); i++)
            Gl.Log(Gl.getUser(i));

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (mSharedPref == null || !mSharedPref.contains(Gl.NOTICE_CHECK))
                    noticeInit();
                if (mSharedPref == null || !mSharedPref.contains(Gl.LANGUAGE_CHECK))//처음 한글 언어 선택
                    languageInit();
            }

            public void onFinish() {
//                new JSONParse().execute();
                if (PRF_AUTO_LOGIN()) {
                    startActivityForResult(mIntent, Gl.INTRO_GROUPLIST);
//                    new JSONParse().execute();

                } else {
                    new JSONParse().execute();
                    startActivity(new Intent(Intro.this, Login.class));
                    finish();
                }
            }
        }.start();

        Gl.initialize(this);
        Gl.setUsers();
        Gl.setGroups();
        Gl.setMeets();

        //testData
        Gl.setTestUsers(aa,bb,cc);
        // Preferences 이용 -> Login한 기록이 있다면 자동로그인

    }

    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void languageInit() {
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LANGUAGE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mEdit.putString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_KOREAN);
        mEdit.commit();
    }

    public void noticeInit() {
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_NOTICE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mEdit.putBoolean(Gl.NOTICE_CHECK, false);
        mEdit.putBoolean(Gl.NOTICE_SOUND, false);
        mEdit.putBoolean(Gl.NOTICE_VIBRATION, false);
        mEdit.putBoolean(Gl.NOTICE_POPUP, false);
        mEdit.apply();
    }

    public boolean PRF_AUTO_LOGIN() {
        String email, password;

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LOGIN, MODE_PRIVATE);

        //로그인 상태
        if (mSharedPref != null && mSharedPref.contains(Gl.USER_EMAIL)) {
            email = mSharedPref.getString(Gl.USER_EMAIL, "DEFAULT");
            password = mSharedPref.getString(Gl.USER_PASSWORD, "DEFAULT");
            mIntent.putExtra(Gl.USER, Gl.getUser(isExistEmail(email)));
            return true;
        }

        return false;
    }

    private int isExistEmail(String s) {
        for (int i = 0; i < Gl.getUserCount(); i++) {
            if (Gl.getUser(i).getEmail().equals(s)) {
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
            logaa = executeClient(url);
            Log.d("ASYNC", "result1 url주소 = " + url);
            Log.d("ASYNC", "result1 json = " + logaa);
//            Toast.makeText(getApplicationContext(), "doInBackground :"+url, Toast.LENGTH_LONG).show();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            try
            {
                JSONObject jObject = new JSONObject();
                JSONArray jArray = new JSONArray();//배열이 필요할때

                //받은데이터 파싱
                jObject = new JSONObject(logaa);
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

//                id = jObject.getString("id");
//                Log.d("id", id);

                JSONArray datas = jObject.getJSONArray("user");
                int size = datas.length();
                Log.i("size", String.valueOf(size));

                for(int i=0; i<size; i++)
                {

                   String name = datas.getJSONObject(i).getString("wn_user_name");
                    Log.d("ASYNCname", name);

                    String email = datas.getJSONObject(i).getString("wn_user_email");
                    Log.d("ASYNCemail", email);

                    String pw = datas.getJSONObject(i).getString("wn_user_pw");
                    Log.d("ASYNCpw", pw);

                    Gl aa = new Gl();
//
//                    ArrayList<User> car = new ArrayList<>();
//                    car.add(new User(name, email, pw));

                    aa.setTestUsers(name,email,pw);
                }

            }
            catch (Exception e)
            {
                e.getMessage();
            }

        }


        public String executeClient(String url) {

//            email = mEditText[0].getText().toString();
//            String pass = mEditText[1].getText().toString();
//            String bb = "";
            ArrayList<NameValuePair> post = new ArrayList<NameValuePair>();
//            post.add(new BasicNameValuePair("wn_user_email", email));
//            post.add(new BasicNameValuePair("wn_user_pw", pass));


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
                HttpResponse responsePOST = client.execute(httpPost);
                HttpEntity resEntity = responsePOST.getEntity();
                if (resEntity != null) {
                    aa = EntityUtils.toString(resEntity);
                }

//                return EntityUtils.getContentCharSet(entity);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return aa;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.INTRO_GROUPLIST) {
            if (resultCode == Gl.RESULT_DELETE) {
                startActivity(new Intent(Intro.this, Login.class));
                Toast.makeText(getApplicationContext(), R.string.delete_acc_msg, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == Gl.RESULT_LOGOUT) {
                startActivity(new Intent(Intro.this, Login.class));
                finish();
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
                System.exit(0);
                super.onBackPressed();
            }
        }
    }
}



