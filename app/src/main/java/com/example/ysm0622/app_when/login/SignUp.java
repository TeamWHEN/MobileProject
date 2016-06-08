package com.example.ysm0622.app_when.login;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.group.GroupList;
import com.example.ysm0622.app_when.object.User;

public class SignUp extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    // TAG
    private static final String TAG = SignUp.class.getName();

    // Const
    private static final int mToolBtnNum = 2;
    private static final int mInputNum = 3;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private TextInputLayout mTextInputLayout[];
    private ImageView mImageView[];
    private EditText mEditText[];
    private TextView mTextViewErrMsg[];
    private TextView mTextViewCounter[];
    private int mMinLength[];
    private int mMaxLength[];
    private String mErrMsg[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_main);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_create_account);

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
        mTextInputLayout = new TextInputLayout[mInputNum];
        mImageView = new ImageView[mInputNum];
        mEditText = new EditText[mInputNum];
        mTextViewErrMsg = new TextView[mInputNum];
        mTextViewCounter = new TextView[mInputNum];
        mMinLength = new int[mInputNum];
        mMaxLength = new int[mInputNum];
        mErrMsg = new String[mInputNum];

        // Create instance

        // View allocation
        mTextInputLayout[0] = (TextInputLayout) findViewById(R.id.TextInputLayout0);
        mTextInputLayout[1] = (TextInputLayout) findViewById(R.id.TextInputLayout1);
        mTextInputLayout[2] = (TextInputLayout) findViewById(R.id.TextInputLayout2);

        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mImageView[2] = (ImageView) findViewById(R.id.ImageView2);

        mEditText[0] = (EditText) findViewById(R.id.EditText0);
        mEditText[1] = (EditText) findViewById(R.id.EditText1);
        mEditText[2] = (EditText) findViewById(R.id.EditText2);

        mTextViewErrMsg[0] = (TextView) findViewById(R.id.TextView0);
        mTextViewErrMsg[1] = (TextView) findViewById(R.id.TextView2);
        mTextViewErrMsg[2] = (TextView) findViewById(R.id.TextView4);

        mTextViewCounter[0] = (TextView) findViewById(R.id.TextView1);
        mTextViewCounter[1] = (TextView) findViewById(R.id.TextView3);
        mTextViewCounter[2] = (TextView) findViewById(R.id.TextView5);

        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mEditText[i].setOnFocusChangeListener(this);
            mEditText[i].addTextChangedListener(this);
        }

        // Default setting
        mToolbarAction[1].setVisibility(View.INVISIBLE);

        for (int i = 0; i < mInputNum; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            mTextViewErrMsg[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setTextColor(getResources().getColor(R.color.colorAccent));
            mTextViewErrMsg[i].setText("");
            mEditText[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }

        mMinLength[0] = 1;
        mMinLength[1] = 5;
        mMinLength[2] = 4;

        mMaxLength[0] = 20;
        mMaxLength[1] = 128;
        mMaxLength[2] = 64;

        mErrMsg[0] = getResources().getString(R.string.name_errmsg1);
        mErrMsg[1] = getResources().getString(R.string.email_errmsg1);
        mErrMsg[2] = getResources().getString(R.string.pw_errmsg1);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (int i = 0; i < mInputNum; i++) {
            if (v.getId() == mEditText[i].getId()) {
                mImageView[i].clearColorFilter();
                if (hasFocus) {
                    mImageView[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                    mTextViewCounter[i].setVisibility(View.VISIBLE);
                    mTextViewCounter[i].setText(mEditText[i].getText().toString().length() + " / " + mMinLength[i] + "–" + mMaxLength[i]);
                } else {
                    mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    mTextViewCounter[i].setVisibility(View.INVISIBLE);
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
        for (int i = 0; i < mInputNum; i++) {
            if (mEditText[i].getText().toString().length() >= mMinLength[i]) {
                cnt++;
            }
        }
        if (cnt == mInputNum && isValidEmail(mEditText[1].getText())) {
            mToolbarAction[1].setVisibility(View.VISIBLE);
        } else {
            mToolbarAction[1].setVisibility(View.INVISIBLE);
        }
        validationCheck();
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    private void validationCheck() { // 인풋 확인
        for (int i = 0; i < mInputNum; i++) {
            if (mEditText[i].hasFocus()) {
                mTextViewCounter[i].setText(mEditText[i].getText().toString().length() + " / " + mMinLength[i] + "–" + mMaxLength[i]);
            }
            if (mEditText[i].getText().toString().length() < mMinLength[i] && mEditText[i].hasFocus()) {
                mTextViewErrMsg[i].setText(mErrMsg[i]);
                mTextViewErrMsg[i].setVisibility(View.VISIBLE);
                mTextViewCounter[i].setTextColor(getResources().getColor(R.color.red_dark));
                mEditText[i].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                //mImageView[i].setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
                //mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_Design_Error_);
            } else if (mEditText[i].getText().toString().length() >= mMinLength[i] && mEditText[i].hasFocus()) {
                mTextViewErrMsg[i].setText("");
                mTextViewErrMsg[i].setVisibility(View.INVISIBLE);
                mTextViewCounter[i].setTextColor(getResources().getColor(R.color.colorAccent));
                mEditText[i].getBackground().setColorFilter(getResources().getColor(R.color.textPrimary), PorterDuff.Mode.SRC_ATOP);
                mEditText[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                //mImageView[i].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
                //mTextInputLayout[i].setHintTextAppearance(R.style.TextAppearance_AppCompat_Display1_);
            }
        }
        if (!isValidEmail(mEditText[1].getText()) && mEditText[1].hasFocus()) {
            mTextViewErrMsg[1].setText(mErrMsg[1]);
            mTextViewErrMsg[1].setVisibility(View.VISIBLE);
            mEditText[1].getBackground().setColorFilter(getResources().getColor(R.color.red_dark), PorterDuff.Mode.SRC_ATOP);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            super.onBackPressed();
        }
        if (v.getId() == mToolbarAction[1].getId()) { // signup button
            // 회원가입버튼 클릭

            // Email 중복체크

            // Query - Select USER_MAIL from ACCOUNT;

            // 중복이없다면 가입 (테이블에 Tuple add)

            // Query - Insert into ACCOUNT values('회원번호', '이메일', '비밀번호', '이름', '가입날짜');
            String name = mEditText[0].getText().toString();
            String email = mEditText[1].getText().toString();
            String password = mEditText[2].getText().toString();
            User user = new User(name, email, password);
            mIntent = new Intent(SignUp.this, GroupList.class);
            mIntent.putExtra(Global.USER, user);
            Global.addUser(user);
            startActivity(mIntent);
            finish();
        }
    }
}