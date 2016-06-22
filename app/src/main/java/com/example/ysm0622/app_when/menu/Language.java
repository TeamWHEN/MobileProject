package com.example.ysm0622.app_when.menu;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;

import java.util.Locale;

public class Language extends Activity implements View.OnClickListener {

    // TAG
    private static final String TAG = Language.class.getName();

    // Const
    private static final int COUNT = 2;
    private static final int mToolBtnNum = 1;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private LinearLayout mLinearLayout[];
    private ImageView mImageView[];
    private TextView mTextView[];
    private ImageView mImageViewRadio[];
    private boolean mCheck[];

    // Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.language_main);

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LANGUAGE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getResources().getString(R.string.language);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initToolbar(Drawable Icon[], String Title) {
        mToolbarAction = new ImageView[2];
        mToolbarAction[0] = (ImageView) findViewById(R.id.Toolbar_Action0);
        mToolbarAction[1] = (ImageView) findViewById(R.id.Toolbar_Action1);
        mToolbarTitle = (TextView) findViewById(R.id.Toolbar_Title);

        for (int i = 0; i < mToolBtnNum; i++) {
            mToolbarAction[i].setOnClickListener(this);
            mToolbarAction[i].setImageDrawable(Icon[i]);
            mToolbarAction[i].setBackground(getResources().getDrawable(R.drawable.selector_btn));
        }
        mToolbarTitle.setText(Title);
    }

    private void initialize() {

        // Array allocation
        mLinearLayout = new LinearLayout[COUNT];
        mImageView = new ImageView[COUNT];
        mTextView = new TextView[COUNT];
        mImageViewRadio = new ImageView[COUNT];
        mCheck = new boolean[COUNT];

        // Create instance

        // View allocation
        mLinearLayout[0] = (LinearLayout) findViewById(R.id.LinearLayout0);
        mLinearLayout[1] = (LinearLayout) findViewById(R.id.LinearLayout1);
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mTextView[0] = (TextView) findViewById(R.id.TextView0);
        mTextView[1] = (TextView) findViewById(R.id.TextView1);
        mImageViewRadio[0] = (ImageView) findViewById(R.id.ImageView_Radio0);
        mImageViewRadio[1] = (ImageView) findViewById(R.id.ImageView_Radio1);

        // Add listener
        for (int i = 0; i < COUNT; i++) {
            mLinearLayout[i].setOnClickListener(this);
        }

        // Default setting
        for (int i = 0; i < COUNT; i++) {
            mImageViewRadio[i].setColorFilter(getResources().getColor(R.color.colorAccent));
        }

        //유동적으로 선택한 언어 표시
        if (mSharedPref.contains(Gl.LANGUAGE_CHECK)) {
            if (mSharedPref.getString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_KOREAN).equalsIgnoreCase(Gl.LANGUAGE_KOREAN)) {
                mCheck[0] = true;
                mImageViewRadio[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp));
                mCheck[1] = false;
                mImageViewRadio[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp));
            } else {
                mCheck[0] = false;
                mImageViewRadio[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp));
                mCheck[1] = true;
                mImageViewRadio[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp));
            }

        } else {
            mCheck[0] = true;
            mCheck[1] = false;
        }
    }

    @Override
    public void onClick(View v) { // 해당 언어 클릭시 언어 변경 이벤트 처리
        if (v.equals(mToolbarAction[0])) {
            super.onBackPressed();
        } else {
            for (int i = 0; i < COUNT; i++) {
                mCheck[i] = false;
                mImageViewRadio[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_unchecked_black_24dp));
                if (v.equals(mLinearLayout[i])) {
                    mCheck[i] = true;
                    mImageViewRadio[i].setImageDrawable(getResources().getDrawable(R.drawable.ic_radio_button_checked_black_24dp));
                    if (i == 0) {
                        mEdit.putString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_KOREAN);
                        setLocale("ko");
                    } else {
                        mEdit.putString(Gl.LANGUAGE_CHECK, Gl.LANGUAGE_ENGLISH);
                        setLocale("en");
                    }
                    mEdit.commit();
                }
            }
            Toast.makeText(getApplicationContext(), R.string.restart_msg, Toast.LENGTH_LONG).show();
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
}
