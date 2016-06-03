package com.example.ysm0622.app_when.group;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

import java.util.ArrayList;

/**
 * Created by ysm0622 on 2016-05-19.
 */
public class InvitePeople extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    private static final String TAG = "InvitePeople";
    private static final int mToolBtnNum = 2;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private ImageView mImageView[] = new ImageView[2];
    private EditText mEditText;
    private ArrayList<String> Member = new ArrayList<String>();
    private Intent mIntent;
    private Bundle mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitepeople_main);

        // Receive intent
        mIntent = getIntent();
        
        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_invite_people);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }
    private void initialize() {

        // Array allocation

        // Create instance

        // View allocation
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);

        mEditText = (EditText) findViewById(R.id.EditText0);
        
        // Add listener
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(this);

        // Default setting

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
        if (mToolbarAction[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (mToolbarAction[1].getId() == v.getId()) {
            mData = mIntent.getExtras();
            mData.putStringArrayList("Member", Member);
            mIntent.putExtras(mData);
            setResult(RESULT_OK, mIntent);
            finish();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (mEditText.getText().toString().length() >= 1) {
            mImageView[1].setVisibility(View.VISIBLE);
        } else {
            mImageView[1].setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == mEditText.getId()) {
            mImageView[0].clearColorFilter();
            if (hasFocus) {
                mImageView[0].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            } else {
                mImageView[0].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
