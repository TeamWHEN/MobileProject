package com.example.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.group.GroupList;
import com.example.ysm0622.app_when.login.Login;
import com.example.ysm0622.app_when.server.ServerConnection;

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
        new ServerConnection().execute(Gl.SELECT_ALL_USER);
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_NOTICE, MODE_PRIVATE);

        Gl.initialize(this);
        if (mSharedPref == null || !mSharedPref.contains(Gl.NOTICE_CHECK))
            noticeInit();
        if (mSharedPref == null || !mSharedPref.contains(Gl.LANGUAGE_CHECK))//처음 한글 언어 선택
            languageInit();
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
//                new JSONParse().execute();
//                if (PRF_AUTO_LOGIN()) {
//                    startActivityForResult(mIntent, Gl.INTRO_GROUPLIST);
////                    new JSONParse().execute();
//
//                } else {
//                    new JSONParse().execute();
                startActivity(new Intent(Intro.this, Login.class));
                finish();
//                }
            }
        }.start();
//
//        Gl.USERS.add(new User("지정한", "wlwjdgks123@gmail.com", "1234"));
//        Gl.USERS.add(new User("조동현", "ehdguso@naver.com", "1234"));
//        Gl.USERS.add(new User("조동현", "aa.com", "1234"));
//
//        Gl.USERS.add(new User("조서형", "westbro00@naver.com", "1234"));
//        Gl.USERS.add(new User("김영송", "infall346@naver.com", "1234"));
//        Gl.USERS.add(new User("장영준", "cyj9212@gmail.com", "1234"));
//        Gl.USERS.add(new User("유영준", "yyj@gmail.com", "1234"));
//        Gl.USERS.add(new User("김원", "wonkimtx@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("정옥란", "orjeong@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("최재혁", "jchoi@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("유준", "joon.yoo@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("노웅기", "wkloh2@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("최아영", "aychoi@gachon.ac.kr", "1234"));
//        Gl.USERS.add(new User("정용주", "coolyj.jung@gmail.com", "1234"));
    }

    public void onBackPressed() {
    }

    public void languageInit() {
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LANGUAGE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mEdit.putString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_KOREAN);
        mEdit.apply();
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