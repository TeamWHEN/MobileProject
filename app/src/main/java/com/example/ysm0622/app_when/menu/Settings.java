package com.example.ysm0622.app_when.menu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;

public class Settings extends Activity implements View.OnClickListener {

    // TAG
    private static final String TAG = Settings.class.getName();

    // Const
    private static final int COUNT = 3;
    private static final int mToolBtnNum = 1;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private LinearLayout mLinearLayout[];
    private ImageView mImageView[];
    private ImageView mImageViewArrow[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getResources().getString(R.string.setting);

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
        mImageViewArrow = new ImageView[COUNT];

        // Create instance

        // View allocation
        mLinearLayout[0] = (LinearLayout) findViewById(R.id.LinearLayout0);
        mLinearLayout[1] = (LinearLayout) findViewById(R.id.LinearLayout1);
        mLinearLayout[2] = (LinearLayout) findViewById(R.id.LinearLayout2);
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mImageView[2] = (ImageView) findViewById(R.id.ImageView2);
        mImageViewArrow[0] = (ImageView) findViewById(R.id.ImageView_Arrow0);
        mImageViewArrow[1] = (ImageView) findViewById(R.id.ImageView_Arrow1);
        mImageViewArrow[2] = (ImageView) findViewById(R.id.ImageView_Arrow2);

        // Add listener
        for (int i = 0; i < COUNT; i++) {
            mLinearLayout[i].setOnClickListener(this);
        }

        // Default setting
        for (int i = 0; i < COUNT; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            mImageViewArrow[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
        }

    }

    public void onBackPressed() {
        setResult(RESULT_OK,mIntent);
        super.onBackPressed();
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mToolbarAction[0])) {
            setResult(RESULT_OK,mIntent);
            super.onBackPressed();
        }
        if (v.equals(mLinearLayout[0])) {
            mIntent.setClass(Settings.this, EditProfile.class);
            startActivityForResult(mIntent, Gl.SETTINGS_EDITPROFILE);
        }
        if (v.equals(mLinearLayout[1])) {
            startActivity(new Intent(Settings.this, Notifications.class));
        }
        if (v.equals(mLinearLayout[2])) {
            startActivity(new Intent(Settings.this, Language.class));
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.SETTINGS_EDITPROFILE) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
            }else if(resultCode == Gl.RESULT_DELETE) {
                setResult(Gl.RESULT_DELETE);
                finish();
            }
        }
    }
}
