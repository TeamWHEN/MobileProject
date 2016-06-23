package com.teamw.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.widget.Toast;

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.global.Gl;
import com.teamw.ysm0622.app_when.group.GroupList;
import com.teamw.ysm0622.app_when.login.Login;
import com.teamw.ysm0622.app_when.server.ServerConnection;

import java.util.Locale;

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
        Log.d("DEBUG0", "Heap Size : "+Long.toString(Debug.getNativeHeapAllocatedSize()));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        mIntent = new Intent(Intro.this, GroupList.class);
        setContentView(R.layout.intro_main);
        new ServerConnection().execute(Gl.SELECT_ALL_USER);

        Gl g = new Gl();
        mIntent.putExtra("G",g);
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_NOTICE, MODE_PRIVATE);

        Gl.initialize(this);

        if (mSharedPref == null || !mSharedPref.contains(Gl.NOTICE_CHECK))
            noticeInit();

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LANGUAGE, MODE_PRIVATE);

        if (mSharedPref == null || !mSharedPref.contains(Gl.LANGUAGE_CHECK)) {//처음 한글 언어 선택
            languageInit();
        } else {//설정된 언어로 표시
            if (!mSharedPref.getString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_KOREAN).equalsIgnoreCase(Gl.LANGUAGE_KOREAN)) {
                setLocale("en");
            }
        }

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                if (PRF_AUTO_LOGIN()) {
                    startActivityForResult(mIntent, Gl.INTRO_GROUPLIST);
                } else {
                    startActivity(new Intent(Intro.this, Login.class));
                    finish();
                }
            }
        }.start();
    }

    public void onBackPressed() {
    }

    //언어 초기화
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

    //자동 로그인 기능
    public boolean PRF_AUTO_LOGIN() {
        String email, password;

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LOGIN, MODE_PRIVATE);
        //로그인 상태
        if (mSharedPref != null && mSharedPref.contains(Gl.USER_EMAIL)) {
            email = mSharedPref.getString(Gl.USER_EMAIL, "DEFAULT");
            if(isExistEmail(email)==-1) return false;
            password = mSharedPref.getString(Gl.USER_PASSWORD, "DEFAULT");
            if (isRightPassword(password, isExistEmail(email))) {
                mIntent.putExtra(Gl.USER, Gl.getUser(isExistEmail(email)));
                return true;
            }
            return false;
        }
        return false;
    }

    //이메일 valid check
    private int isExistEmail(String s) {
        for (int i = 0; i < Gl.getUserCount(); i++) {
            if (Gl.getUser(i).getEmail().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    //비밀번혼 valid check
    private boolean isRightPassword(String s, int i) {
        if (Gl.getUser(i).getPassword().equals(s)) {
            return true;
        } else {
            return false;
        }
    }

    // 언어 설정 메소드
    public void setLocale(String charicter) {
        Locale locale = new Locale(charicter);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.INTRO_GROUPLIST) {
            if (resultCode == Gl.RESULT_DELETE) {
                startActivity(new Intent(Intro.this, Login.class));
                Toast.makeText(this, R.string.delete_acc_msg, Toast.LENGTH_SHORT).show();
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