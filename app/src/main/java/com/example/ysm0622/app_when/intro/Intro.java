package com.example.ysm0622.app_when.intro;

import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.intro_main);
        new CountDownTimer(1000, 1000) {
            public void onTick(long millisUntilFinished) {
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
}
