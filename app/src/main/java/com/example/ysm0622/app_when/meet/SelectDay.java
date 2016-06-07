package com.example.ysm0622.app_when.meet;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

import java.util.ArrayList;
import java.util.Calendar;

public class SelectDay extends AppCompatActivity implements View.OnClickListener {

    // TAG
    private static final String TAG = "SelectDay";

    // Const
    private static final int mInputNum = 2;
    private static final int mToolBtnNum = 2;
    private static final int mSelectViewNum = 3;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private ImageView mImageView[] = new ImageView[mInputNum];
    private TextView mTextView;
    private LinearLayout mLinearLayout;
    private CalendarMonthView mCalendarView;
    private Calendar mToday;
    private Calendar mCurrent;
    private int mYear;
    private int mMonth;
    private String mCtitle;

    private TimeSelectView mTimeSelectView[] = new TimeSelectView[mSelectViewNum];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeinput_main);

        // Receive intent
        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.select_day);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initialize() {

        // Array allocation

        // Create instance
        mCurrent = Calendar.getInstance();
        mToday = Calendar.getInstance();

        // View allocation
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);
        mTextView = (TextView) findViewById(R.id.TextView0);
        mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout0);
        mCalendarView = (CalendarMonthView) findViewById(R.id.CalendarView);
        mTimeSelectView[0] = (TimeSelectView) findViewById(R.id.TimeSelectView0);
        mTimeSelectView[1] = (TimeSelectView) findViewById(R.id.TimeSelectView1);
        mTimeSelectView[2] = (TimeSelectView) findViewById(R.id.TimeSelectView2);


        // Add listener
        for (int i = 0; i < mInputNum; i++) {
            mImageView[i].setOnClickListener(this);
        }
        for (int i = 0; i < 42; i++) {
            mCalendarView.getTextView(i).setOnClickListener(this);
        }

        // Default setting
        mToolbarAction[1].setVisibility(View.INVISIBLE);

        mImageView[0].setColorFilter(getResources().getColor(R.color.colorAccent));
        mImageView[1].setColorFilter(getResources().getColor(R.color.colorAccent));
        mImageView[0].setVisibility(View.INVISIBLE);
        setCalendarTitle();
        mCalendarView.reDisplay(mCurrent);
        mLinearLayout.setVisibility(View.INVISIBLE);
        mLinearLayout.setEnabled(false);
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
            ArrayList<Calendar> DateData = mCalendarView.getDateData();
            //mBundle.putSerializable("DateData", DateData);
            setResult(RESULT_OK, mIntent);
            finish();
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
        for (int i = 0; i < 42; i++) {
            if (v.equals(mCalendarView.getTextView(i))) {
                boolean mSelected[] = mCalendarView.getSelected();
                ArrayList<Calendar> mDateData = mCalendarView.getDateData();
                Calendar mCalendarArray[] = mCalendarView.getCalendarArray();
                if (mSelected[i]) {
                    for (int n = 0; n < mDateData.size(); n++) {
                        if (mCalendarView.isEqual(mCalendarArray[i], mDateData.get(n))) {
                            mDateData.remove(n);
                            break;
                        }
                    }
                    for (int n = 0; n < mDateData.size(); n++) {
                        Log.w(TAG, mDateData.get(n).get(Calendar.YEAR) + "년 " + (mDateData.get(n).get(Calendar.MONTH) + 1) + "월 " + mDateData.get(n).get(Calendar.DATE) + "일");
                    }
                    mCalendarView.reDisplay(mCurrent);
                } else {
                    mDateData.add(mCalendarView.makeClone(mCalendarArray[i]));
                    for (int n = 0; n < mDateData.size(); n++) {
                        Log.w(TAG, mDateData.get(n).get(Calendar.YEAR) + "년 " + (mDateData.get(n).get(Calendar.MONTH) + 1) + "월 " + mDateData.get(n).get(Calendar.DATE) + "일");
                    }
                    mCalendarView.reDisplay(mCurrent);
                }
                if (mCalendarView.getDateData().size() > 0) {
                    mToolbarAction[1].setVisibility(View.VISIBLE);
                } else {
                    mToolbarAction[1].setVisibility(View.INVISIBLE);
                }
            }
        }
    }
}
