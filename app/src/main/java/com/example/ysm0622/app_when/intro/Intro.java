package com.example.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.G;
import com.example.ysm0622.app_when.group.GroupList;
import com.example.ysm0622.app_when.login.Login;

public class Intro extends AppCompatActivity {


    // TAG
    private static final String TAG = Intro.class.getName();

    // Intent
    private Intent mIntent;

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = new Intent(Intro.this, GroupList.class);
        setContentView(R.layout.intro_main);

        mSharedPref = getSharedPreferences(G.FILE_NAME_NOTICE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (mSharedPref == null || !mSharedPref.contains(G.NOTICE_CHECK))
                    noticeInit();
                if (mSharedPref == null || !mSharedPref.contains(G.LANGUAGE))//처음 한글 언어 선택
                    languageInit();
            }

            public void onFinish() {
                if (PRF_AUTO_LOGIN()) {
                    startActivity(mIntent);
                } else
                    startActivity(new Intent(Intro.this, Login.class));

                finish();
            }
        }.start();

        G.initialize(this);
        G.setUsers();
        G.setGroups();
        G.setMeets();

        //testData
        G.setTestUsers();
        // Preferences 이용 -> Login한 기록이 있다면 자동로그인

    }

    public void onBackPressed() {
        //super.onBackPressed();
    }

    public void languageInit() {
        mEdit.putString(G.LANGUAGE, G.LANGUAGE_KOREAN);
        mEdit.commit();
    }

    public void noticeInit() {
        mEdit.putBoolean(G.NOTICE_CHECK, false);
        mEdit.putBoolean(G.NOTICE_SOUND, false);
        mEdit.putBoolean(G.NOTICE_VIBRATION, false);
        mEdit.putBoolean(G.NOTICE_POPUP, false);
        mEdit.apply();
    }

    public boolean PRF_AUTO_LOGIN() {
        String email, password;

        mSharedPref = getSharedPreferences(G.FILE_NAME_LOGIN, MODE_PRIVATE);

        //로그인 상태
        if (mSharedPref != null && mSharedPref.contains(G.USER_EMAIL)) {
            email = mSharedPref.getString(G.USER_EMAIL, "DEFAULT");
            password = mSharedPref.getString(G.USER_PASSWORD, "DEFAULT");
            mIntent.putExtra(G.USER, G.getUser(isExistEmail(email)));
            return true;
        }
        return false;
    }

    private int isExistEmail(String s) {
        for (int i = 0; i < G.getUserCount(); i++) {
            if (G.getUser(i).getEmail().equals(s)) {
                return i;
            }
        }
        return -1;
    }
}
