package com.example.ysm0622.app_when.menu;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;

public class Notifications extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // TAG
    private static final String TAG = Notifications.class.getName();

    // Const
    private static final int COUNT = 4;
    private static final int mToolBtnNum = 1;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private LinearLayout mLinearLayout[];
    private ImageView mImageView[];
    private TextView mTextView[];
    private Switch mSwitch[];

    // Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    //Sample Notice
    private boolean mSampleInit;
    private int mSoundId, mStreamId;
    private SoundPool mSound;
    private Vibrator mVibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        mSharedPref = getSharedPreferences(Global.FILE_NAME_NOTICE, MODE_PRIVATE);
        mEdit = mSharedPref.edit();
        mSampleInit = false;
        mSound = new SoundPool(1, AudioManager.STREAM_ALARM, 0);// maxStreams, streamType, srcQuality
        mSoundId = mSound.load(this, R.raw.metal_clang, 1);
        mVibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);


        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getResources().getString(R.string.notifications);

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
        mSwitch = new Switch[COUNT];
        mTextView = new TextView[COUNT];

        // Create instance

        // View allocation
        mLinearLayout[0] = (LinearLayout) findViewById(R.id.LinearLayout0);
        mLinearLayout[1] = (LinearLayout) findViewById(R.id.LinearLayout1);
        mLinearLayout[2] = (LinearLayout) findViewById(R.id.LinearLayout2);
        mLinearLayout[3] = (LinearLayout) findViewById(R.id.LinearLayout3);
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mImageView[2] = (ImageView) findViewById(R.id.ImageView2);
        mImageView[3] = (ImageView) findViewById(R.id.ImageView3);
        mTextView[0] = (TextView) findViewById(R.id.TextView0);
        mTextView[1] = (TextView) findViewById(R.id.TextView1);
        mTextView[2] = (TextView) findViewById(R.id.TextView2);
        mTextView[3] = (TextView) findViewById(R.id.TextView3);
        mSwitch[0] = (Switch) findViewById(R.id.Switch0);
        mSwitch[1] = (Switch) findViewById(R.id.Switch1);
        mSwitch[2] = (Switch) findViewById(R.id.Switch2);
        mSwitch[3] = (Switch) findViewById(R.id.Switch3);

        // Add listener
        for (int i = 0; i < COUNT; i++) {
            mLinearLayout[i].setOnClickListener(this);
            mSwitch[i].setOnCheckedChangeListener(this);
            mSwitch[i].setChecked(getNotice(i));
        }
        mSampleInit = true;

        // Default setting
        for (int i = 0; i < COUNT; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }
        if (mSwitch[0].isChecked()) {
            for (int i = 1; i < COUNT; i++) {
                mLinearLayout[i].setEnabled(true);
                mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                mTextView[i].setTextColor(getResources().getColor(R.color.textPrimary));
                mSwitch[i].setEnabled(true);
            }
        } else {
            for (int i = 1; i < COUNT; i++) {
                mLinearLayout[i].setEnabled(false);
                mImageView[i].setColorFilter(getResources().getColor(R.color.divider), PorterDuff.Mode.SRC_ATOP);
                mTextView[i].setTextColor(getResources().getColor(R.color.divider));
                mSwitch[i].setEnabled(false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mToolbarAction[0])) {
            super.onBackPressed();
        }
        for (int i = 0; i < COUNT; i++) {
            if (v.equals(mLinearLayout[i])) {
                if (mSwitch[i].isChecked()) {
                    mSwitch[i].setChecked(false);
                } else {
                    mSwitch[i].setChecked(true);
                }
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton v, boolean isChecked) {
        if (v.equals(mSwitch[0])) {
            if (isChecked) {
                for (int i = 1; i < COUNT; i++) {
                    mLinearLayout[i].setEnabled(true);
                    mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    mTextView[i].setTextColor(getResources().getColor(R.color.textPrimary));
                    mSwitch[i].setEnabled(true);
                }
            } else {
                for (int i = 1; i < COUNT; i++) {
                    mLinearLayout[i].setEnabled(false);
                    mImageView[i].setColorFilter(getResources().getColor(R.color.divider), PorterDuff.Mode.SRC_ATOP);
                    mTextView[i].setTextColor(getResources().getColor(R.color.divider));
                    mSwitch[i].setEnabled(false);
                }
            }
            setNotice(0, isChecked);
        } else if (v.equals(mSwitch[1])) {
            setNotice(1, isChecked);
        } else if (v.equals(mSwitch[2])) {
            setNotice(2, !isChecked);
        } else if (v.equals(mSwitch[3])) {
            setNotice(3, !isChecked);
        }
    }

    public void setNotice(int index, boolean state) {

        if (index == 0) {
            mEdit.putBoolean(Global.NOTICE_CHECK, state);
        } else if (index == 1) {
            mEdit.putBoolean(Global.NOTICE_SOUND, state);
        } else if (index == 2) {
            mEdit.putBoolean(Global.NOTICE_VIBRATION, state);
            Toast.makeText(getApplicationContext(), "NOTICE_VIBRATION : " + mSharedPref.getBoolean(Global.NOTICE_VIBRATION, false), Toast.LENGTH_SHORT).show();
        } else if (index == 3) {
            mEdit.putBoolean(Global.NOTICE_POPUP, state);
        }
        mEdit.commit();
        sampleNotice(index, state);
    }

    public boolean getNotice(int index) {
        boolean result;
        if (index == 0) {
            result = mSharedPref.getBoolean(Global.NOTICE_CHECK, false);
        } else if (index == 1) {
            result = mSharedPref.getBoolean(Global.NOTICE_SOUND, false);
        } else if (index == 2) {
            result = mSharedPref.getBoolean(Global.NOTICE_VIBRATION, false);
        } else {
            result = mSharedPref.getBoolean(Global.NOTICE_POPUP, false);
        }
        return result;
    }

    public void sampleNotice(int index, boolean state) {
        if (state && getNotice(0) && mSampleInit) {
            if (index == 0) {//소리, 진동, 팝업
            } else if (index == 1) {//소리
                mStreamId = mSound.play(mSoundId, 1.0F, 1.0F, 1, 0, 1.0F);
                //mSound.stop(mStreamId); 소리 정지
            } else if (index == 2) {//진동
                mVibe.vibrate(500);
            } else {//팝업
            }
        }
    }
}
