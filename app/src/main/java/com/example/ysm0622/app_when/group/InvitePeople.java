package com.example.ysm0622.app_when.group;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.ysm0622.app_when.R;

import java.util.ArrayList;

/**
 * Created by ysm0622 on 2016-05-19.
 */
public class InvitePeople extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {
    private ImageButton toolbtn[] = new ImageButton[2];
    private ImageView icon[] = new ImageView[2];
    private ArrayList<String> Member = new ArrayList<String>();
    private EditText edittext;
    private Intent mIntent;
    private Bundle mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_people);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Receive intent
        mIntent = getIntent();

        // View allocation
        toolbtn[0] = (ImageButton) findViewById(R.id.toolbar_back);
        toolbtn[1] = (ImageButton) findViewById(R.id.toolbar_done);

        icon[0] = (ImageView) findViewById(R.id.ic_search);
        icon[1] = (ImageView) findViewById(R.id.ic_clear);

        edittext = (EditText) findViewById(R.id.et_search);

        // Listener setting
        for (int i = 0; i < 2; i++) {
            toolbtn[i].setOnClickListener(this);
            edittext.addTextChangedListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        if (toolbtn[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (toolbtn[1].getId() == v.getId()) {
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
        if (edittext.getText().toString().length() >= 1) {
            icon[1].setVisibility(View.VISIBLE);
        } else {
            icon[1].setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == edittext.getId()) {
            if (hasFocus) {
                icon[0].clearColorFilter();
                icon[0].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            } else {
                icon[0].clearColorFilter();
                icon[0].setColorFilter(getResources().getColor(R.color.grey7), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }
}
