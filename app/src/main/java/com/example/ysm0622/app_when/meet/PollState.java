package com.example.ysm0622.app_when.meet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.group.CustomSurfaceView;
import com.example.ysm0622.app_when.group.SummaryView;

public class PollState extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = PollState.class.getName();

    // Const
    private static final int mInputNum = 3;
    private static final int mToolBtnNum = 2;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_state);

        // Receive intent
        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getString(R.string.state);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initialize() {

        // Array allocation

        // Create instance

        // View allocation

        // Add listener

        // Default setting
        SummaryView mSummaryView = (SummaryView) findViewById(R.id.SummaryView);
        LinearLayout mLinearLayout0 = (LinearLayout) findViewById(R.id.LinearLayoutInScroll);
        LinearLayout mLinearLayout1 = (LinearLayout) findViewById(R.id.LinearLayoutLeft);
        CustomSurfaceView mSurfaceView = new CustomSurfaceView(this);
        mLinearLayout0.addView(mSurfaceView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mLinearLayout1.setElevation((float) 10.0);
        }
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

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            super.onBackPressed();
        }
    }
}