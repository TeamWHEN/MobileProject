package com.example.ysm0622.app_when.menu;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

public class Notifications extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private static final String TAG = "Notifications";
    private static final int COUNT = 4;
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;
    private LinearLayout mLinearLayout[];
    private ImageView mImageView[];
    private TextView mTextView[];
    private Switch mSwitch[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

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

        for (int i = 0; i < Icon.length; i++) {
            mToolbarAction[i].setOnClickListener(this);
            mToolbarAction[i].setImageDrawable(Icon[i]);
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
        }

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
        }
    }
}
