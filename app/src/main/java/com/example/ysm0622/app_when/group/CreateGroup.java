package com.example.ysm0622.app_when.group;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

public class CreateGroup extends AppCompatActivity
        implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    private static final int input_cnt = 2;
    private TextInputLayout inputLayout[] = new TextInputLayout[input_cnt];
    private ImageView icon[] = new ImageView[input_cnt];
    private EditText edittext[] = new EditText[input_cnt];
    private TextView errmsg[] = new TextView[input_cnt];
    private TextView counter[] = new TextView[input_cnt];
    private int minlength[] = new int[input_cnt];
    private int maxlength[] = new int[input_cnt];
    private String str_errmsg[] = new String[input_cnt];
    private ImageButton toolbtn[] = new ImageButton[input_cnt];
    private Intent mIntent;
    private Bundle mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Receive intent
        mIntent = getIntent();

        // View allocation
        inputLayout[0] = (TextInputLayout) findViewById(R.id.group_title);
        inputLayout[1] = (TextInputLayout) findViewById(R.id.desc);

        icon[0] = (ImageView) findViewById(R.id.ic_group_title);
        icon[1] = (ImageView) findViewById(R.id.ic_desc);

        edittext[0] = (EditText) findViewById(R.id.et_group_title);
        edittext[1] = (EditText) findViewById(R.id.et_desc);

        errmsg[0] = (TextView) findViewById(R.id.group_title_errmsg);
        errmsg[1] = (TextView) findViewById(R.id.desc_errmsg);

        counter[0] = (TextView) findViewById(R.id.group_title_counter);
        counter[1] = (TextView) findViewById(R.id.desc_counter);

        minlength[0] = 1;
        minlength[1] = 0;

        maxlength[0] = 20;
        maxlength[1] = 128;

        str_errmsg[0] = getResources().getString(R.string.group_title_errmsg1);
        str_errmsg[1] = getResources().getString(R.string.desc_errmsg1);

        toolbtn[0] = (ImageButton) findViewById(R.id.toolbar_back);
        toolbtn[1] = (ImageButton) findViewById(R.id.toolbar_forward);


        // Listener setting
        for (int i = 0; i < input_cnt; i++) {
            edittext[i].setOnFocusChangeListener(this);
            edittext[i].addTextChangedListener(this);
            toolbtn[i].setOnClickListener(this);
        }

        // Default setting
        for (int i = 0; i < input_cnt; i++) {
            icon[i].setColorFilter(getResources().getColor(R.color.grey7), PorterDuff.Mode.SRC_ATOP);
            errmsg[i].setVisibility(View.INVISIBLE);
            counter[i].setVisibility(View.INVISIBLE);
            counter[i].setTextColor(getResources().getColor(R.color.grey6));
            errmsg[i].setText("");
        }
    }

    @Override
    public void onClick(View v) {
        if (toolbtn[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (toolbtn[1].getId() == v.getId()) {
            mIntent = new Intent(CreateGroup.this, InvitePeople.class);
            mData = new Bundle();
            mData.putString("Title", edittext[0].getText().toString());
            mData.putString("Desc", edittext[1].getText().toString());
            mIntent.putExtras(mData);

            startActivityForResult(mIntent, 1001);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (int i = 0; i < input_cnt; i++) {
            if (v.getId() == edittext[i].getId()) {
                if (hasFocus) {
                    icon[i].clearColorFilter();
                    icon[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    counter[i].setVisibility(View.VISIBLE);
                    counter[i].setText(edittext[i].getText().toString().length() + " / " + minlength[i] + "–" + maxlength[i]);
                } else {
                    icon[i].clearColorFilter();
                    icon[i].setColorFilter(getResources().getColor(R.color.grey7), PorterDuff.Mode.SRC_ATOP);
                    counter[i].setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int cnt = 0;
        for (int i = 0; i < input_cnt; i++) {
            if (edittext[i].getText().toString().length() >= minlength[i]) {
                cnt++;
            }
        }
        if (cnt == input_cnt) {
            toolbtn[1].setVisibility(View.VISIBLE);
        } else {
            toolbtn[1].setVisibility(View.INVISIBLE);
        }
        input_valid_check();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void input_valid_check() { // 인풋 확인
        for (int i = 0; i < input_cnt; i++) {
            if (edittext[i].hasFocus()) {
                counter[i].setText(edittext[i].getText().toString().length() + " / " + minlength[i] + "–" + maxlength[i]);
            }
            if (edittext[i].getText().toString().length() < minlength[i] && edittext[i].hasFocus()) {
                errmsg[i].setText(str_errmsg[i]);
                errmsg[i].setVisibility(View.VISIBLE);
                counter[i].setTextColor(getResources().getColor(R.color.red_dark));
                edittext[i].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
            } else if (edittext[i].getText().toString().length() >= minlength[i] && edittext[i].hasFocus()) {
                errmsg[i].setText("");
                errmsg[i].setVisibility(View.INVISIBLE);
                counter[i].setTextColor(getResources().getColor(R.color.grey6));
                edittext[i].getBackground().setColorFilter(getResources().getColor(R.color.grey6), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK, intent);
                finish();
            }
        }
    }
}
