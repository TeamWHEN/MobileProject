package com.example.ysm0622.app_when.meet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;
import java.util.Calendar;

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

    private ArrayList<Calendar> startTime = new ArrayList<>();
    private ArrayList<Calendar> endTime = new ArrayList<>();

    private boolean time[] = new boolean[24];

    public ArrayList<ArrayList<Calendar>> savedData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeinput_main);

        // Receive intent
        mIntent = getIntent();

        MODE = mIntent.getIntExtra(Global.SELECT_DAY_MODE, 0);

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
        mCalendarView.setmIntent(mIntent);
        mCalendarView.reDisplay(mCurrent);

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
                mIntent.putExtra(Global.MEET_SELECTEDDATE, mCalendarView.getDateData());
                Meet M = new Meet();
                M.setGroup((Group) mIntent.getSerializableExtra(Global.GROUP));
                M.setMaster((User) mIntent.getSerializableExtra(Global.USER));
                M.setTitle(mIntent.getStringExtra(Global.MEET_TITLE));
                M.setDesc(mIntent.getStringExtra(Global.MEET_DESC));
                M.setLocation(mIntent.getStringExtra(Global.MEET_LOCATION));
                M.setSelectedDate(mCalendarView.getDateData());
                mIntent.putExtra(Global.MEET, M);
                setResult(RESULT_OK, mIntent);
                finish();
            } else if (MODE == 1) {
                startTime.clear();
                endTime.clear();
                for (int i = 0; i < savedData.size(); i += 2) {
                    startTime.addAll(savedData.get(i));
                    endTime.addAll(savedData.get(i + 1));
                }
                DateTime D = new DateTime();
                D.setUser((User) mIntent.getSerializableExtra(Global.USER));
                D.setStartTime(startTime);
                D.setEndTime(endTime);
                Meet M = (Meet) mIntent.getSerializableExtra(Global.MEET);
                M.setDateTime(D);
                mIntent.putExtra(Global.MEET, M);
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
                boolean mSelected[] = mCalendarView.getSelected();
                ArrayList<Calendar> mDateData = mCalendarView.getDateData();
                Calendar mCalendarArray[] = mCalendarView.getCalendarArray();
                mSelect = makeClone(mCalendarArray[i]);
                mSelect.set(Calendar.DATE, mCalendarArray[i].get(Calendar.DATE));
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
                    }
                    // Clear field
                    for (int j = 0; j < mSelectViewNum; j++) {
                        mTimeSelectView[j].setAll(false);
                    }
                    // Data save
                    startTime.clear();
                    endTime.clear();
                    for (int j = 0; j < savedData.size(); j += 2) {
                        if (isEqual(savedData.get(j).get(0), mSelect)) {
                            startTime = makeClone(savedData.get(j));
                            endTime = makeClone(savedData.get(j + 1));
                            savedData.remove(j);
                            savedData.remove(j);
                        }
                    }
                    mCalendarView.setSavedData(savedData);
                    // Draw
                    if (startTime.size() > 0) {
                        ArrayList<Integer> timeInfo = new ArrayList<>();
                        for (int j = 0; j < startTime.size(); j++) {
                            timeInfo.add(startTime.get(j).get(Calendar.HOUR_OF_DAY));
                            if (endTime.get(j).get(Calendar.HOUR_OF_DAY) == 0) timeInfo.add(24);
                            else timeInfo.add(endTime.get(j).get(Calendar.HOUR_OF_DAY));
                        }
                        for (int j = 0; j < time.length; j++) {
                            time[j] = false;
                        }
                        for (int j = 0; j < timeInfo.size() - 1; j += 2) {
                            for (int k = timeInfo.get(j); k < timeInfo.get(j + 1); k++) {
                                time[k] = true;
                            }
                        }
                        if (isAllSelected(time)) {
                            mSwitch.setOnCheckedChangeListener(null);
                            mSwitch.setChecked(true);
                            mSwitch.setOnCheckedChangeListener(this);
                        } else {
                            mSwitch.setOnCheckedChangeListener(null);
                            mSwitch.setChecked(false);
                            mSwitch.setOnCheckedChangeListener(this);
                        }
                        boolean tmp[] = new boolean[8];
                        for (int j = 0; j < time.length; j++) {
                            tmp[j % 8] = time[j];
                            if (j % 8 == 7) {
                                mTimeSelectView[j / 8 % 3].drawRectByArray(tmp);
                            }
                        }

                    } else {
                        mSwitch.setOnCheckedChangeListener(null);
                        mSwitch.setChecked(false);
                        mSwitch.setOnCheckedChangeListener(this);
                    }

                    mCalendarView.reDisplay(mCurrent);
                }
            }
        }
    }

    private boolean isAllSelected(boolean arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (!arr[i]) return false;
        }
        return true;
    }

    private void saveData() {
        for (int j = 0; j < mSelectViewNum; j++) {
            for (int k = 0; k < 8; k++) {
                time[j * 8 + k] = mTimeSelectView[j].getSelected()[k];
            }
        }
        ArrayList<Integer> timeInfo = new ArrayList<>();
        if (time[0]) {
            timeInfo.add(0);
        }
        for (int j = 0; j < time.length - 1; j++) {
            if (time[j] != time[j + 1]) {
                timeInfo.add(j + 1);
            }
        }
        if (time[23]) {
            timeInfo.add(24);
        }
        Calendar c = makeClone(mSelect);
        startTime.clear();
        endTime.clear();
        for (int j = 0; j < timeInfo.size(); j++) {
            if (j % 2 == 0) {
                c.set(Calendar.HOUR_OF_DAY, timeInfo.get(j));
                startTime.add(makeClone(c));
            } else {
                c.set(Calendar.HOUR_OF_DAY, timeInfo.get(j));
                endTime.add(makeClone(c));
            }
        }
        for (int j = 0; j < savedData.size(); j += 2) {
            if (isEqual(savedData.get(j).get(0), mSelect)) {
                savedData.remove(j);
                savedData.remove(j);
            }
        }
        if (startTime.size() > 0) {
            savedData.add(makeClone(startTime));
            savedData.add(makeClone(endTime));
        }
        if (savedData.size() > 0) {
            mToolbarAction[1].setVisibility(View.VISIBLE);
        } else {
            mToolbarAction[1].setVisibility(View.INVISIBLE);
        }
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
    public void onResume() {
        for (int i = 0; i < mSelectViewNum; i++) {
            mTimeSelectView[i].mDrawThread.setRunning(false);
        }
        super.onResume();
    }
}
