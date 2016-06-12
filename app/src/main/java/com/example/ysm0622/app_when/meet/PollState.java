package com.example.ysm0622.app_when.meet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.G;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;

public class PollState extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = PollState.class.getName();

    // Const
    private static final int COUNT = 2;
    private static final int mToolBtnNum = 2;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    // Views
    private ImageView mImageView[] = new ImageView[COUNT];
    private TextView mTextView[] = new TextView[COUNT];

    private Group g;
    private Meet m;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.poll_state);

        // Receive intent
        mIntent = getIntent();
        g = (Group) mIntent.getSerializableExtra(G.GROUP);
        m = (Meet) mIntent.getSerializableExtra(G.MEET);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getString(R.string.state);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initialize() {

        // Array allocation
        LinearLayout mLinearLayout[] = new LinearLayout[4];
        View mView;

        // Create instance

        // View allocation
        mLinearLayout[0] = (LinearLayout) findViewById(R.id.TimeLinearLayout);
        mLinearLayout[1] = (LinearLayout) findViewById(R.id.DateLinearLayout);
        mLinearLayout[2] = (LinearLayout) findViewById(R.id.LinearLayoutInScroll);
        mLinearLayout[3] = (LinearLayout) findViewById(R.id.RightLinearLayout);

        mView = (View) findViewById(R.id.View0);

        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mTextView[0] = (TextView) findViewById(R.id.TextView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mTextView[1] = (TextView) findViewById(R.id.TextView1);

        // Add listener

        // Default setting
        SummaryView mSummaryView = (SummaryView) findViewById(R.id.SummaryView);
        CustomSurfaceView mSurfaceView = (CustomSurfaceView) findViewById(R.id.CustomSurfaceView);
        mSummaryView.setLayout(mLinearLayout, mView, mSurfaceView);
        mSummaryView.drawState(mIntent);

        for (int i = 0; i < COUNT; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        mTextView[0].setText(m.getTitle());
        mTextView[1].setText(m.getDateTimeNum() + " / " + g.getMemberNum());
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