package com.example.ysm0622.app_when.menu;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.server.ServerConnection;

import org.apache.http.NameValuePair;

import java.util.ArrayList;


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
    private boolean mFabCheck = false;
    private static final int PICK_FROM_GALLERY = 1;

    private User u;

    public static final int PROGRESS_DIALOG = 1001;
    public ProgressDialog progressDialog;

    //Dialog
    private AlertDialog mDialBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editprofile_main);

        mIntent = getIntent();
        u = (User) mIntent.getSerializableExtra(Gl.USER);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getString(R.string.edit_profile);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();

//        Bitmap photo = StringToBitMap(u.getImageFilePath());
//        mMyPhoto.clearColorFilter();
//        mMyPhoto.setImageBitmap(Gl.getCircleBitmap(photo));
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

        mEditText[1].setEnabled(false);

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

        if (u.ImageFilePath != null) {//프로필 이미지가 존재
            mMyPhoto.clearColorFilter();
            mMyPhoto.setImageBitmap(Gl.getCircleBitmap(Gl.PROFILES.get(String.valueOf(u.getId()))));
        }

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
        if (cnt == mInputNum && !Gl.MyUser.getName().equals(mEditText[0].getText().toString())) {
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
            User u = (User) mIntent.getSerializableExtra(Gl.USER);
            u.setName(name);
            u.setEmail(email);
//            if (mFabCheck)
            mIntent.putExtra(Gl.USER, u);
            setResult(RESULT_OK, mIntent);
            finish();
        }
        if (v.equals(mFab)) {
            callGallery();
        }
        if (v.equals(mButton)) {
            removeDialogBox();
        }
        if (v.equals(mLinearLayoutPW)) {
            changepwDialogBox();
        }
    }

    // 비밀번호 변경 다이어로그
    public void changepwDialogBox() {
        LayoutInflater inflater = (LayoutInflater) EditProfile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.changepw_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.changepw_title);
        View Include0 = (View) view.findViewById(R.id.Include0);
        View Include1 = (View) view.findViewById(R.id.Include1);
        final EditText NewPW0 = (EditText) Include0.findViewById(R.id.EditText0);
        final EditText NewPW1 = (EditText) Include1.findViewById(R.id.EditText0);
        TextView Btn1 = (TextView) view.findViewById(R.id.changepw_btn1);
        TextView Btn2 = (TextView) view.findViewById(R.id.changepw_btn2);

        String test = "string";
        Title.setText(R.string.changepw);
        Btn1.setText(R.string.cancel);
        Btn2.setText(R.string.ok);

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
            }
        });//취소

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NewPW0.getText().toString().equals(NewPW1.getText().toString())) {//새로운 비밀번호로 변경
                    Gl.MyUser.setPassword(NewPW1.getText().toString());
                    mIntent.putExtra(Gl.USER, Gl.MyUser);
                    BackgroundTask2 mTask = new BackgroundTask2();
                    mTask.execute(Gl.MyUser);
                    Toast.makeText(getApplicationContext(), R.string.changed_pw, Toast.LENGTH_SHORT).show();
                    setResult(RESULT_OK, mIntent);
                    mDialBox.cancel();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), R.string.re_input_pw, Toast.LENGTH_SHORT).show();
                }//새로운 비밀번호를 잘못 입력
            }
        });//확인

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }

    //계정삭제 다이어로그
    public void removeDialogBox() {
        LayoutInflater inflater = (LayoutInflater) EditProfile.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.remove_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.remove_title);
        TextView Content = (TextView) view.findViewById(R.id.remove_content);
        TextView Btn1 = (TextView) view.findViewById(R.id.remove_btn1);
        TextView Btn2 = (TextView) view.findViewById(R.id.remove_btn2);

        Title.setText(R.string.remove_title);
        Content.setText(R.string.remove_content);
        Btn1.setText(R.string.cancel);
        Btn2.setText(R.string.delete);

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
            }
        });//취소

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
                BackgroundTask mTask = new BackgroundTask();
                mTask.execute(Gl.MyUser);
                Gl.remove(u);
                Gl.LogAllUser();
                setResult(Gl.RESULT_DELETE);

                finish();
            }
        });//삭제

        AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }

    class BackgroundTask extends AsyncTask<User, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(User... args) {
            ArrayList<NameValuePair> param1 = ServerConnection.DeleteUser(args[0]);
            ServerConnection.getStringFromServer(param1, Gl.DELETE_USER);
            return null;
        }

        protected void onPostExecute(Integer a) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    //비밀번호 변경시 콜
    class BackgroundTask2 extends AsyncTask<User, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(User... args) {
            ServerConnection.UpdateUser(args[0]);
            return null;
        }

        protected void onPostExecute(Integer a) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }

    public Dialog onCreateDialog(int id) {
        if (id == PROGRESS_DIALOG) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getResources().getString(R.string.saving));

            return progressDialog;
        }
        return null;
    }

    public void callGallery() {
        Intent intent = new Intent();
        // Gallery 호출
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        // 잘라내기 셋팅
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 400);
        intent.putExtra("outputY", 400);
        try {
            intent.putExtra("return-data", true);
            startActivityForResult(intent, PICK_FROM_GALLERY);
        } catch (ActivityNotFoundException e) {
            // Do nothing for now
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_FROM_GALLERY) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap photo = extras.getParcelable("data");
                    String test = Gl.BitmapToString(photo);
                    Log.d("testtest", test);
                    BackgroundTask3 task = new BackgroundTask3();
                    u.setImageFilePath(test);
                    task.execute(u);
                    mMyPhoto.clearColorFilter();
                    mMyPhoto.setImageBitmap(Gl.getCircleBitmap(photo));
                    mFabCheck = true;
                    mToolbarAction[1].setVisibility(View.VISIBLE);
                }
            }
        }
    }

    class BackgroundTask3 extends AsyncTask<User, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(User... args) {
            ArrayList<NameValuePair> param1 = ServerConnection.UpdateUser(args[0]);
            ServerConnection.getStringFromServer(param1, Gl.UPDATE_USER);
            return null;
        }

        protected void onPostExecute(Integer a) {
            if (progressDialog != null)
                progressDialog.dismiss();
        }
    }
}