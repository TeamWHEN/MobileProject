package com.example.ysm0622.app_when.meet;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.MeetDate;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.server.ServerConnection;

import org.apache.http.NameValuePair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class SelectDay extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    // TAG
    private static final String TAG = SelectDay.class.getName();

    // Const
    private static final int mInputNum = 2;
    private static final int mToolBtnNum = 2;
    private static final int mSelectViewNum = 3;
    private static int MODE;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private ImageView mImageView[] = new ImageView[mInputNum];
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private CalendarMonthView mCalendarView;
    private TextView mCurrentDate;
    private Switch mSwitch;
    private View mInclude[];
    private Calendar mToday;
    private Calendar mCurrent;
    private Calendar mSelect;
    private int mYear;
    private int mMonth;
    private String mCtitle;

    private TimeSelectView mTimeSelectView[] = new TimeSelectView[mSelectViewNum];

    private boolean time[] = new boolean[24];

    public ArrayList<ArrayList<Calendar>> allData = new ArrayList<>();

    public static final int PROGRESS_DIALOG = 1001;
    public ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeinput_main);

        // Receive intent
        mIntent = getIntent();

        MODE = mIntent.getIntExtra(Gl.SELECT_DAY_MODE, 0);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.select_day);
        if (MODE == 1) toolbarTitle = getString(R.string.input_time);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initialize() {

        // Array allocation
        mInclude = new View[mSelectViewNum];

        // Create instance
        mCurrent = Calendar.getInstance();
        mToday = Calendar.getInstance();
        mSelect = Calendar.getInstance();

        // View allocation
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mTextView = (TextView) findViewById(R.id.TextView0);
        mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout0);
        mCalendarView = (CalendarMonthView) findViewById(R.id.CalendarView);
        mCurrentDate = (TextView) findViewById(R.id.CurrentDate);
        mSwitch = (Switch) findViewById(R.id.Switch0);
        mTimeSelectView[0] = (TimeSelectView) findViewById(R.id.TimeSelectView0);
        mTimeSelectView[1] = (TimeSelectView) findViewById(R.id.TimeSelectView1);
        mTimeSelectView[2] = (TimeSelectView) findViewById(R.id.TimeSelectView2);
        mInclude[0] = (View) findViewById(R.id.Include0);
        mInclude[1] = (View) findViewById(R.id.Include1);
        mInclude[2] = (View) findViewById(R.id.Include2);


        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mImageView[i].setOnClickListener(this);
        }
        for (int i = 0; i < 42; i++) {
            mCalendarView.getTextView(i).setOnClickListener(this);
        }
        for (int i = 0; i < mSelectViewNum; i++) {
            mTimeSelectView[i].setOnClickListener(this);
        }
        mSwitch.setOnCheckedChangeListener(this);

        // Default setting
        mToolbarAction[1].setVisibility(View.INVISIBLE);

        mImageView[0].setColorFilter(getResources().getColor(R.color.colorAccent));
        mImageView[1].setColorFilter(getResources().getColor(R.color.colorAccent));
        mImageView[0].setVisibility(View.INVISIBLE);
        setCalendarTitle();

        mCalendarView.setMODE(MODE);
        mCalendarView.setIntent(mIntent);

        mLinearLayout.setVisibility(View.INVISIBLE);
        mLinearLayout.setEnabled(false);

        TextView Scale[] = new TextView[27];
        for (int i = 0; i < 3; i++) {
            Scale[i * 9 + 0] = (TextView) mInclude[i].findViewById(R.id.TextView0);
            Scale[i * 9 + 1] = (TextView) mInclude[i].findViewById(R.id.TextView1);
            Scale[i * 9 + 2] = (TextView) mInclude[i].findViewById(R.id.TextView2);
            Scale[i * 9 + 3] = (TextView) mInclude[i].findViewById(R.id.TextView3);
            Scale[i * 9 + 4] = (TextView) mInclude[i].findViewById(R.id.TextView4);
            Scale[i * 9 + 5] = (TextView) mInclude[i].findViewById(R.id.TextView5);
            Scale[i * 9 + 6] = (TextView) mInclude[i].findViewById(R.id.TextView6);
            Scale[i * 9 + 7] = (TextView) mInclude[i].findViewById(R.id.TextView7);
            Scale[i * 9 + 8] = (TextView) mInclude[i].findViewById(R.id.TextView8);
        }
        for (int i = 0; i < 9; i++) {
            Scale[i].setText(String.valueOf(i));
        }
        for (int i = 8; i < 18; i++) {
            Scale[i + 1].setText(String.valueOf(i));
        }
        for (int i = 16; i < 25; i++) {
            Scale[i + 2].setText(String.valueOf(i));
        }

        if (MODE == 1) {
            User U = (User) mIntent.getSerializableExtra(Gl.USER);
            Meet M = (Meet) mIntent.getSerializableExtra(Gl.MEET);
            if (M.getDateTime() != null && getDataIndex(M.getDateTime(), U) >= 0) {
                allData = M.getDateTime().get(getDataIndex(M.getDateTime(), U)).getSelectTime();
            }
            mCalendarView.setAllData(allData);
            for (int i = 0; i < M.getDateTime().size(); i++) {
                Log.w(TAG, "allData[" + i + "]");
                Log.w(TAG, "allData[" + i + "] Size = " + M.getDateTime().get(i).getSelectTime().size());
            }
        }
        mCalendarView.reDisplay(mCurrent);
    }

    private void setCalendarTitle() {
        mYear = mCurrent.get(Calendar.YEAR);
        mMonth = mCurrent.get(Calendar.MONTH) + 1;
        mCtitle = mYear + "년 " + mMonth + "월";
        mTextView.setText(mCtitle);
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
            if (MODE == 0) {
                Group G = (Group) mIntent.getSerializableExtra(Gl.GROUP);
                int groupid = G.getId();
                int masterid = Gl.MyUser.getId();
                String title = mIntent.getStringExtra(Gl.MEET_TITLE);
                String desc = mIntent.getStringExtra(Gl.MEET_DESC);
                String location = mIntent.getStringExtra(Gl.MEET_LOCATION);
                Meet M = new Meet(groupid, masterid, title, desc, location);
                ArrayList<Calendar> calendars = mCalendarView.getDateData();
                ArrayList<MeetDate> dates = new ArrayList<>();
                for (int i = 0; i < calendars.size(); i++) {
                    Date d = calendars.get(i).getTime();
                    MeetDate md = new MeetDate(groupid, M.getId(),d.getTime());
                    dates.add(md);
                }
                M.setMeetDate(dates);
                M.setSelectedDate(mCalendarView.getDateData());
                BackgroundTask mTask = new BackgroundTask();
                mTask.execute(M);
                mIntent.putExtra(Gl.MEET, M);
                setResult(RESULT_OK, mIntent);
                finish();
            } else if (MODE == 1) {
                DateTime D = new DateTime();
                D.setUser((User) mIntent.getSerializableExtra(Gl.USER));
                D.setSelectTime(allData);
                Meet M = (Meet) mIntent.getSerializableExtra(Gl.MEET);
                M = M.addDateTime(D);
                mIntent.putExtra(Gl.MEET, M);
                setResult(RESULT_OK, mIntent);
                finish();
            }
        }
        if (mImageView[0].getId() == v.getId()) {
            mCurrent.add(Calendar.MONTH, -1);
            mCalendarView.reDisplay(mCurrent);
            setCalendarTitle();
            if (mCurrent.compareTo(mToday) == 0) {
                mImageView[0].setVisibility(View.INVISIBLE);
            }
        }
        if (mImageView[1].getId() == v.getId()) {
            mCurrent.add(Calendar.MONTH, 1);
            mCalendarView.reDisplay(mCurrent);
            setCalendarTitle();
            if (mCurrent.compareTo(mToday) != 0) {
                mImageView[0].setVisibility(View.VISIBLE);
            }
        }
        for (int i = 0; i < mSelectViewNum; i++) {
            if (v.equals(mTimeSelectView[i])) {
                if (mTimeSelectView[0].isAllSelcted() && mTimeSelectView[1].isAllSelcted() && mTimeSelectView[2].isAllSelcted()) {
                    mSwitch.setChecked(true);
                } else {
                    mSwitch.setOnCheckedChangeListener(null);
                    mSwitch.setChecked(false);
                    mSwitch.setOnCheckedChangeListener(this);
                }
                saveData();
            }
        }
        for (int i = 0; i < 42; i++) {
            if (v.equals(mCalendarView.getTextView(i))) {
                dateClick(i);
            }
        }
    }

    class BackgroundTask extends AsyncTask<Meet, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(Meet... args) {
            ArrayList<NameValuePair> param1 = ServerConnection.InsertMeet(args[0]);
            ArrayList<NameValuePair> param2 = ServerConnection.InsertMeetDate(args[0]);
            ServerConnection.getStringFromServer(param1, Gl.INSERT_MEET);
            ServerConnection.getStringFromServer(param2, Gl.INSERT_MEETDATE);
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
            progressDialog.setMessage(getString(R.string.createmeet_progress));

            return progressDialog;
        }
        return null;
    }

    private void dateClick(int i) {
        boolean mSelected[] = mCalendarView.getSelected();
        ArrayList<Calendar> mDateData = mCalendarView.getDateData();
        Calendar mCalendarArray[] = mCalendarView.getCalendarArray();
        if (MODE == 0) {
            if (mSelected[i]) {
                for (int n = 0; n < mDateData.size(); n++) {
                    if (mCalendarView.isEqual(mCalendarArray[i], mDateData.get(n))) {
                        mDateData.remove(n);
                        break;
                    }
                }
                mCalendarView.reDisplay(mCurrent);
            } else {
                mDateData.add(mCalendarView.makeClone(mCalendarArray[i]));
                mCalendarView.reDisplay(mCurrent);
            }
            if (mCalendarView.getDateData().size() > 0) {
                mToolbarAction[1].setVisibility(View.VISIBLE);
            } else {
                mToolbarAction[1].setVisibility(View.INVISIBLE);
            }
        } else if (MODE == 1) {
            mDateData.clear();
            if (mSelected[i]) {
                mLinearLayout.setVisibility(View.INVISIBLE);
                mLinearLayout.setEnabled(false);
                for (int j = 0; j < mSelectViewNum; j++) {
                    mTimeSelectView[j].setDraw(false);
                }
            } else {
                mLinearLayout.setVisibility(View.VISIBLE);
                mLinearLayout.setEnabled(true);
                mDateData.add(mCalendarView.makeClone(mCalendarArray[i]));
                String s = mCalendarArray[i].get(Calendar.YEAR) + "년 " + (mCalendarArray[i].get(Calendar.MONTH) + 1) + "월 " + mCalendarArray[i].get(Calendar.DATE) + "일";
                mCurrentDate.setText(s);
                for (int j = 0; j < mSelectViewNum; j++) {
                    mTimeSelectView[j].setDraw(true);
                }

                saveData();
                mSelect = makeClone(mCalendarArray[i]);
                Log.w(TAG, "Current Select : " + mSelect.get(Calendar.YEAR) + "/" + mSelect.get(Calendar.MONTH) + "/" + mSelect.get(Calendar.DATE));
                loadData();
                if (isAllSelected(time)) {
                    mSwitch.setOnCheckedChangeListener(null);
                    mSwitch.setChecked(true);
                    mSwitch.setOnCheckedChangeListener(this);
                } else {
                    mSwitch.setOnCheckedChangeListener(null);
                    mSwitch.setChecked(false);
                    mSwitch.setOnCheckedChangeListener(this);
                }
            }
            mCalendarView.reDisplay(mCurrent);
        }
    }

    private int getDataIndex(ArrayList<DateTime> arrayList, User U) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getUser().getId() == U.getId()) {
                return i;
            }
        }
        return -1;
    }

    public ArrayList<ArrayList<Calendar>> getAllData() {
        return allData;
    }

    private boolean isAllSelected(boolean arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i]) return false;
        }
        return true;
    }

    private boolean[] calendarToArray(ArrayList<Calendar> arrayList) {
        boolean result[] = new boolean[24];

        Arrays.fill(result, false);
        for (int i = 0; i < arrayList.size(); i++) {
            result[arrayList.get(i).get(Calendar.HOUR_OF_DAY)] = true;
        }
        return result;
    }

    private ArrayList<Calendar> arrayToCalendar(Calendar c, boolean arr[]) {
        ArrayList<Calendar> result = new ArrayList<>();

        for (int i = 0; i < arr.length; i++) {
            Calendar n = makeClone(c);
            if (arr[i]) {
                n.set(Calendar.HOUR_OF_DAY, i);
                result.add(n);
            }
        }
        Log.w(TAG, "Call arrayToCalendar >>> Calendar size : " + result.size());

        return result;
    }

    private int getContainIndex(ArrayList<ArrayList<Calendar>> arrayList, Calendar c) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (isEqual(arrayList.get(i).get(0), c)) {
                return i;
            }
        }
        return -1;
    }

    private void saveData(Calendar c, ArrayList<Calendar> data) {
        // 있으면 수정, 없으면 저장

        if (data.size() == 0) {
            int index = getContainIndex(allData, c);
            if (index >= 0) allData.remove(index);
        } else if (getContainIndex(allData, data.get(0)) >= 0) {
            int index = getContainIndex(allData, data.get(0));
            allData.remove(index);
            allData.add(data);
        } else {
            allData.add(data);
        }

    }

    private void saveData() {

        for (int i = 0; i < mSelectViewNum; i++) {
            System.arraycopy(mTimeSelectView[i].getSelected(), 0, time, i * 8, 8);
        }

        saveData(mSelect, arrayToCalendar(mSelect, time));

        mCalendarView.setAllData(allData);

        if (allData.size() > 0) {
            mToolbarAction[1].setVisibility(View.VISIBLE);
        } else {
            mToolbarAction[1].setVisibility(View.INVISIBLE);
        }

        Log.w(TAG, "Save Data(" + mSelect.get(Calendar.YEAR) + "/" + mSelect.get(Calendar.MONTH) + "/" + mSelect.get(Calendar.DATE) + ")");

    }

    private void loadData() {

        if (getContainIndex(allData, mSelect) >= 0)
            time = calendarToArray(allData.get(getContainIndex(allData, mSelect)));
        else
            Arrays.fill(time, false);

        for (int i = 0; i < mSelectViewNum; i++) {
            boolean arr[] = new boolean[8];
            System.arraycopy(time, i * 8, arr, 0, 8);
            mTimeSelectView[i].setSelected(arr);
            Log.w(TAG, arr[0] + " " + arr[1] + " " + arr[2] + " " + arr[3] + " " + arr[4] + " " + arr[5] + " " + arr[6] + " " + arr[7]);
        }
        Log.w(TAG, "Load Data(" + mSelect.get(Calendar.YEAR) + "/" + mSelect.get(Calendar.MONTH) + "/" + mSelect.get(Calendar.DATE) + ")");
    }

    private boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }

    private Calendar makeClone(Calendar A) {
        Calendar New = Calendar.getInstance();
        New.set(A.get(Calendar.YEAR), A.get(Calendar.MONTH), A.get(Calendar.DATE), A.get(Calendar.HOUR_OF_DAY), 0);
        return New;
    }

    private ArrayList<Calendar> makeClone(ArrayList<Calendar> A) {
        ArrayList<Calendar> New = new ArrayList<>();
        New.addAll(A);
        return New;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.equals(mSwitch)) {
            if (isChecked) {
                for (int i = 0; i < mSelectViewNum; i++) {
                    mTimeSelectView[i].setDraw(true);
                    mTimeSelectView[i].setAll(true);
                }
            } else {
                for (int i = 0; i < mSelectViewNum; i++) {
                    mTimeSelectView[i].setDraw(true);
                    mTimeSelectView[i].setAll(false);
                }
            }
            saveData();
        }
    }

    @Override
    public void onPause() {
        for (int i = 0; i < mSelectViewNum; i++) {
            mTimeSelectView[i].mDrawThread.setRunning(false);
        }
        super.onPause();
    }
}
