package com.example.ysm0622.app_when;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class CreateAccount extends AppCompatActivity
        implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    private static final int inputCnt = 3;
    private TextInputLayout mTextInputLayout[] = new TextInputLayout[inputCnt];
    private ImageView icon[] = new ImageView[inputCnt];
    private EditText edittext[] = new EditText[inputCnt];
    private TextView errmsg[] = new TextView[inputCnt];
    private TextView counter[] = new TextView[inputCnt];
    private int minlength[] = new int[inputCnt];
    private int maxlength[] = new int[inputCnt];
    private String str_errmsg[] = new String[inputCnt];
    private ImageButton toolbtn[] = new ImageButton[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        // View allocation

        mTextInputLayout[0] = (TextInputLayout) findViewById(R.id.TextInputLayout0);
        mTextInputLayout[1] = (TextInputLayout) findViewById(R.id.TextInputLayout1);
        mTextInputLayout[2] = (TextInputLayout) findViewById(R.id.TextInputLayout2);

        icon[0] = (ImageView) findViewById(R.id.ic_name);
        icon[1] = (ImageView) findViewById(R.id.ic_email);
        icon[2] = (ImageView) findViewById(R.id.ic_pw);

        edittext[0] = (EditText) findViewById(R.id.et_name);
        edittext[1] = (EditText) findViewById(R.id.et_email);
        edittext[2] = (EditText) findViewById(R.id.et_pw);

        errmsg[0] = (TextView) findViewById(R.id.name_errmsg);
        errmsg[1] = (TextView) findViewById(R.id.email_errmsg);
        errmsg[2] = (TextView) findViewById(R.id.pw_errmsg);

        counter[0] = (TextView) findViewById(R.id.name_counter);
        counter[1] = (TextView) findViewById(R.id.email_counter);
        counter[2] = (TextView) findViewById(R.id.pw_counter);

        toolbtn[0] = (ImageButton) findViewById(R.id.toolbar_back);
        toolbtn[1] = (ImageButton) findViewById(R.id.toolbar_done);

        minlength[0] = 1;
        minlength[1] = 5;
        minlength[2] = 4;

        maxlength[0] = 20;
        maxlength[1] = 128;
        maxlength[2] = 64;

        str_errmsg[0] = getResources().getString(R.string.name_errmsg1);
        str_errmsg[1] = getResources().getString(R.string.email_errmsg1);
        str_errmsg[2] = getResources().getString(R.string.pw_errmsg1);

        // Listener setting
        for (int i = 0; i < inputCnt; i++) {
            edittext[i].setOnFocusChangeListener(this);
            edittext[i].addTextChangedListener(this);
        }
        for (int i = 0; i < 2; i++) {
            toolbtn[i].setOnClickListener(this);
        }

        // Default setting
        for (int i = 0; i < inputCnt; i++) {
            icon[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            errmsg[i].setVisibility(View.INVISIBLE);
            counter[i].setVisibility(View.INVISIBLE);
            counter[i].setTextColor(getResources().getColor(R.color.colorAccent));
            errmsg[i].setText("");
            edittext[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (int i = 0; i < inputCnt; i++) {
            if (v.getId() == edittext[i].getId()) {
                if (hasFocus) {
                    icon[i].clearColorFilter();
                    icon[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    counter[i].setVisibility(View.VISIBLE);
                    counter[i].setText(edittext[i].getText().toString().length() + " / " + minlength[i] + "–" + maxlength[i]);
                } else {
                    icon[i].clearColorFilter();
                    icon[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
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
        for (int i = 0; i < inputCnt; i++) {
            if (edittext[i].getText().toString().length() >= minlength[i]) {
                cnt++;
            }
        }
        if (cnt == inputCnt && isValidEmail(edittext[1].getText())) {
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
        for (int i = 0; i < inputCnt; i++) {
            if (edittext[i].hasFocus()) {
                counter[i].setText(edittext[i].getText().toString().length() + " / " + minlength[i] + "–" + maxlength[i]);
            }
            if (edittext[i].getText().toString().length() < minlength[i] && edittext[i].hasFocus()) {
                errmsg[i].setText(str_errmsg[i]);
                errmsg[i].setVisibility(View.VISIBLE);
                counter[i].setTextColor(getResources().getColor(R.color.red_dark));
                edittext[i].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                //icon[i].setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_Design_Error_);
            } else if (edittext[i].getText().toString().length() >= minlength[i] && edittext[i].hasFocus()) {
                errmsg[i].setText("");
                errmsg[i].setVisibility(View.INVISIBLE);
                counter[i].setTextColor(getResources().getColor(R.color.colorAccent));
                edittext[i].getBackground().setColorFilter(getResources().getColor(R.color.textPrimary), PorterDuff.Mode.SRC_ATOP);
                edittext[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                //icon[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_AppCompat_Display1_);
            }
        }
        if (!isValidEmail(edittext[1].getText()) && edittext[1].hasFocus()) {
            errmsg[1].setText(str_errmsg[1]);
            errmsg[1].setVisibility(View.VISIBLE);
            edittext[1].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == toolbtn[0].getId()) { // back button
            super.onBackPressed();
        }
        if (v.getId() == toolbtn[1].getId()) { // signup button
            // 회원가입버튼 클릭

            // Email 중복체크

            // Query - Select USER_MAIL from ACCOUNT;

            // 중복이없다면 가입 (테이블에 Tuple add)

            // Query - Insert into ACCOUNT values('회원번호', '이메일', '비밀번호', '이름', '가입날짜');
        }
    }
}