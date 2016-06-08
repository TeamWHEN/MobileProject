package com.example.ysm0622.app_when.intro;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.login.Login;

public class Intro extends AppCompatActivity {

    // TAG
    private static final String TAG = Intro.class.getName();

    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSharedPref = getSharedPreferences(Global.FILE_NAME_NOTICE, MODE_PRIVATE);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_main);

        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
                if (mSharedPref == null || !mSharedPref.contains(Global.NOTICE_CHECK))
                    noticeInit();
            }

            public void onFinish() {
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
        mEdit = mSharedPref.edit();
        mEdit.putBoolean(Global.NOTICE_CHECK, false);
        mEdit.putBoolean(Global.NOTICE_SOUND, false);
        mEdit.putBoolean(Global.NOTICE_VIBRATION, false);
        mEdit.putBoolean(Global.NOTICE_POPUP, false);
        mEdit.apply();
    }
}
