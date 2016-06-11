package com.example.ysm0622.app_when.menu;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.object.User;


public class EditProfile extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener {

    // TAG
    private static final String TAG = EditProfile.class.getName();

    // Const
    private static final int mToolBtnNum = 2;
    private static final int mInputNum = 2;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    // VIews
    private LinearLayout mLinearLayout[];
    private ImageView mMyPhoto;
    private TextView mMyProfile[];
    private View mInclude[];
    private TextInputLayout mTextInputLayout[];
    private ImageView mImageView[];
    private EditText mEditText[];
    private TextView mTextViewErrMsg[];
    private TextView mTextViewCounter[];
    private int mMinLength[];
    private int mMaxLength[];
    private String mErrMsg[];

    private LinearLayout mLinearLayoutPW;
    private ImageView mImageViewPW;
    private TextView mTextViewPW;

    private FloatingActionButton mFab;
    private Button mButton;

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_main);

        mIntent = getIntent();
        u = (User) mIntent.getSerializableExtra(Global.USER);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getString(R.string.edit_profile);

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
        mLinearLayout = new LinearLayout[2];
        mMyProfile = new TextView[2];
        mInclude = new View[3];
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
        mLinearLayout[0] = (LinearLayout) findViewById(R.id.TopLinearLayout);
        mLinearLayout[1] = (LinearLayout) findViewById(R.id.BottomLinearLayout);

        mMyPhoto = (ImageView) findViewById(R.id.MyPhoto);

        mMyProfile[0] = (TextView) findViewById(R.id.MyName);
        mMyProfile[1] = (TextView) findViewById(R.id.MyEmail);

        mFab = (FloatingActionButton) findViewById(R.id.Fab0);

        mInclude[0] = (View) findViewById(R.id.Include0);
        mInclude[1] = (View) findViewById(R.id.Include1);
        mInclude[2] = (View) findViewById(R.id.Include2);

        for (int i = 0; i < mInputNum; i++) {
            mTextInputLayout[i] = (TextInputLayout) mInclude[i].findViewById(R.id.TextInputLayout0);
            mImageView[i] = (ImageView) mInclude[i].findViewById(R.id.ImageView0);
            mEditText[i] = (EditText) mInclude[i].findViewById(R.id.EditText0);
            mTextViewErrMsg[i] = (TextView) mInclude[i].findViewById(R.id.TextView0);
            mTextViewCounter[i] = (TextView) mInclude[i].findViewById(R.id.TextView1);
        }

        mLinearLayoutPW = (LinearLayout) findViewById(R.id.Include2);
        mImageViewPW = (ImageView) mInclude[2].findViewById(R.id.ImageView0);
        mTextViewPW = (TextView) mInclude[2].findViewById(R.id.TextView0);

        mButton = (Button) findViewById(R.id.Button0);

        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mEditText[i].setOnFocusChangeListener(this);
            mEditText[i].addTextChangedListener(this);
        }
        mButton.setOnClickListener(this);
        mFab.setOnClickListener(this);
        mLinearLayoutPW.setOnClickListener(this);

        // Default setting
        mMyPhoto.setColorFilter(getResources().getColor(R.color.white));
        mMyProfile[0].setText(u.getName());
        mMyProfile[1].setText(u.getEmail());

        mTextInputLayout[0].setHint(getResources().getString(R.string.name_hint));
        mTextInputLayout[1].setHint(getResources().getString(R.string.email_hint));

        mImageView[0].setImageDrawable(getResources().getDrawable(R.drawable.ic_person));
        mImageView[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_email));

        mEditText[0].setText(u.getName());
        mEditText[1].setText(u.getEmail());
        mEditText[1].setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        mLinearLayoutPW.setBackground(getResources().getDrawable(R.drawable.selector_btn));
        mImageViewPW.setImageDrawable(getResources().getDrawable(R.drawable.ic_lock));
        mImageViewPW.setColorFilter(getResources().getColor(R.color.colorPrimary));
        mTextViewPW.setText(R.string.change_pw);

        mToolbarAction[1].setVisibility(View.INVISIBLE);

        mMinLength[0] = 1;
        mMinLength[1] = 5;

        mMaxLength[0] = 20;
        mMaxLength[1] = 128;

        for (int i = 0; i < mInputNum; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary));
            mTextViewErrMsg[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setVisibility(View.INVISIBLE);
            mTextViewCounter[i].setTextColor(getResources().getColor(R.color.colorAccent));
            mTextViewErrMsg[i].setText("");
            mEditText[i].getBackground().mutate().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            InputFilter filter[] = new InputFilter[1];
            filter[0] = new InputFilter.LengthFilter(mMaxLength[i]);
            mEditText[i].setFilters(filter);
        }

        mErrMsg[0] = getResources().getString(R.string.name_errmsg1);
        mErrMsg[1] = getResources().getString(R.string.email_errmsg1);

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        for (int i = 0; i < mInputNum; i++) {
            if (v.equals(mEditText[i])) {
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
        if (v.getId() == mToolbarAction[1].getId()) { // done button
            String name = mEditText[0].getText().toString();
            String email = mEditText[1].getText().toString();
            User u = (User) mIntent.getSerializableExtra(Global.USER);
            u.setName(name);
            u.setEmail(email);
            mIntent.putExtra(Global.USER, u);
            setResult(RESULT_OK, mIntent);
            finish();
        }
        if (v.equals(mFab)) {

        }
        if (v.equals(mButton)) {

        }
        if (v.equals(mLinearLayoutPW)) {

        }
    }
}