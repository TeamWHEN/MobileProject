package com.example.ysm0622.app_when.login;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
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

    public static final int PROGRESS_DIALOG = 1001;
    public ProgressDialog progressDialog;
    private static final String TAG = "Login";
    private static final int mInputNum = 2;
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

        initialize();
    }

    private void initialize() {

        // Array allocation
        mEditText = new EditText[mInputNum];

        // Create instance
        mTask = new BackgroundTask();

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
            Toast.makeText(getApplicationContext(), mResult, Toast.LENGTH_SHORT).show();
            Log.w(TAG, mResult);

            if (progressDialog != null)
                progressDialog.dismiss();

            if (mResult != "")
                startActivity(new Intent(Login.this, GroupList.class));
            else
                Toast.makeText(getApplicationContext(), "로그인이 실패했습니다", Toast.LENGTH_SHORT).show();
        }
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
            progressDialog.setMessage("로그인 중입니다.");

            return progressDialog;
        }
        return null;
    }
}
