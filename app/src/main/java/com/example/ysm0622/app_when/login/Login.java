package com.example.ysm0622.app_when.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.group.GroupList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity implements TextWatcher, View.OnClickListener {

    // TAG
    private static final String TAG = Login.class.getName();

    // Const
    private static final int mInputNum = 2;
    public static final int PROGRESS_DIALOG = 1001;

    // Intent
    private Intent mIntent;

    //Shared Preferences
    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEdit;

    public ProgressDialog progressDialog;

    private EditText mEditText[];
    private FloatingActionButton mFab;
    private Button mButton;
    private String mURL;
    private String mAddress;
    private String mResult;
    private BackgroundTask mTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_main);

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LOGIN, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        initialize();
    }

    private void initialize() {

        // Array allocation
        mEditText = new EditText[mInputNum];

        // Create instance

        // View allocation
        mEditText[0] = (EditText) findViewById(R.id.EditText0); // Email
        mEditText[1] = (EditText) findViewById(R.id.EditText1); // Password

        mFab = (FloatingActionButton) findViewById(R.id.Fab0); // Login Button

        mButton = (Button) findViewById(R.id.Button0); // Sign up Button

        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mEditText[i].addTextChangedListener(this);
        }
        mFab.setOnClickListener(this);
        mButton.setOnClickListener(this);

        // Default setting
        for (int i = 0; i < mInputNum; i++) {
            mEditText[i].getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        }

        mFab.setVisibility(View.INVISIBLE);
        mURL = "http://52.79.132.35:8080/JSP_test.jsp";
        mAddress = "";
        mResult = "";

    }

    public void onBackPressed() {
        finish();
        System.exit(0);
        super.onBackPressed();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int cnt = 0;
        for (int i = 0; i < mInputNum; i++) {
            if (mEditText[i].getText().toString().length() > 0) {
                cnt++;
            }
        }
        if (cnt == mInputNum) {
            mFab.setVisibility(View.VISIBLE);
        } else {
            mFab.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mFab.getId()) {
            // 로그인 버튼 클릭

            // Email, password validation

            // Query - Select USER_CODE, USER_MAIL, USER_PSWD, USER_NAME from ACCOUNT (회원정보 intent에 저장)

            // Query - Select * from ACCOUNT-GROUPS WHERE USER_CODE = @@ (회원코드로 회원의 모임번호들 검색 / Intent 저장)

            //서버로 ID 전송
            mTask = new BackgroundTask();
            mTask.execute();
        }
        if (v.getId() == mButton.getId()) {
            startActivity(new Intent(Login.this, SignUp.class));
        }
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            mAddress = mURL;
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            // TODO Auto-generated method stub
            mResult = request(mAddress);
            return null;
        }

        protected void onPostExecute(Integer a) {
//            Toast.makeText(getApplicationContext(), mResult, Toast.LENGTH_SHORT).show();
            Log.w(TAG, mResult);

            if (progressDialog != null)
                progressDialog.dismiss();

            if (mResult != "") {
                mIntent = new Intent(Login.this, GroupList.class);
                String email = mEditText[0].getText().toString();
                String password = mEditText[1].getText().toString();
                if (isExistEmail(email) >= 0) {
                    if (isRightPassword(password, isExistEmail(email))) {
                        mIntent.putExtra(Gl.USER, Gl.getUser(isExistEmail(email)));
                        setAutoLogin(email, password);
                        startActivityForResult(mIntent, Gl.LOGIN_GROUPLIST);
                        mEditText[0].setText("");
                        mEditText[1].setText("");
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.wrong_pw, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), R.string.noexist_email, Toast.LENGTH_SHORT).show();
                }
            } else
                Toast.makeText(getApplicationContext(), R.string.login_fail_msg, Toast.LENGTH_SHORT).show();
        }
    }

    private int isExistEmail(String s) {
        for (int i = 0; i < Gl.getUserCount(); i++) {
            if (Gl.getUser(i).getEmail().equals(s)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isRightPassword(String s, int i) {
        if (Gl.getUser(i).getPassword().equals(s)) {
            return true;
        } else {
            return false;
        }
    }

    //Set email, password in Shared Preferences
    private void setAutoLogin(String email, String password) {
        mEdit.putString(Gl.USER_EMAIL, email);
        mEdit.putString(Gl.USER_PASSWORD, password);
        mEdit.commit();
    }

    private String request(String urlStr) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(mURL);
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("msg", mEditText[0].getText().toString()));
            //params.add(new BasicNameValuePair("pass", "xyz"));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(params, HTTP.UTF_8);
            post.setEntity(ent);
            HttpResponse responsePOST = client.execute(post);
            HttpEntity resEntity = responsePOST.getEntity();

            if (resEntity != null) {
                mResult = EntityUtils.toString(resEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mResult;
    }

    public Dialog onCreateDialog(int id) {
        if (id == PROGRESS_DIALOG) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.login_progress_msg));

            return progressDialog;
        }
        return null;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.LOGIN_GROUPLIST) {
            if (resultCode == Gl.RESULT_DELETE) {
                Toast.makeText(getApplicationContext(), R.string.delete_acc_msg, Toast.LENGTH_SHORT).show();
            }
            if (resultCode == RESULT_CANCELED) {
                finish();
                System.exit(0);
                super.onBackPressed();
            }
        }
    }
}
