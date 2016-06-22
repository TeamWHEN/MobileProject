package com.example.ysm0622.app_when.meet;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.Meet;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarMonthView extends LinearLayout {

    // TAG
    private static final String TAG = CalendarMonthView.class.getName();

    // Const
    private static final int mRow = 6;
    private static final int mCol = 7;
    private int MODE;

    // Intent
    private Intent mIntent;

    private Context mContext;
    private LinearLayout mLinearLayout[];
    private LinearLayout.LayoutParams mParam[];
    private TextView mTextView[];
    private boolean mSelected[];

    private int mWidth;
    private int mHeight;

    private Calendar mToday;
    private Calendar mCurrent;
    private int curYear;
    private int curMonth;
    private int curDate;
    private Calendar startDay;
    private Calendar endDay;

    private int firstDay;
    private int lastDay;

    private ArrayList<Calendar> mDateData;
    private Calendar mCalendarArray[];

    private ArrayList<ArrayList<Calendar>> allData;


    public CalendarMonthView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public CalendarMonthView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public int getMODE() {
        return MODE;
    }

    public void setMODE(int MODE) {
        this.MODE = MODE;
    }

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        mWidth = getWidth();
        mHeight = getHeight();
        float h = mHeight / mRow;
        float w = mWidth / mCol;
        h -= (int) (2 * Gl.DENSITY + 0.5f);
        w = w - h;
        w /= 2;
        mParam[1].setMargins((int) w, (int) (1 * Gl.DENSITY + 0.5f), (int) w, (int) (1 * Gl.DENSITY + 0.5f));
    }


    private void init() {

        // Array allocation
        mLinearLayout = new LinearLayout[mRow];
        mParam = new LinearLayout.LayoutParams[2];
        mTextView = new TextView[mRow * mCol];
        mSelected = new boolean[mRow * mCol];

        // Create instance
        mToday = Calendar.getInstance();
        startDay = Calendar.getInstance();
        endDay = Calendar.getInstance();
        mCurrent = Calendar.getInstance();
        mDateData = new ArrayList<Calendar>();
        mCalendarArray = new Calendar[mRow * mCol];
        for (int i = 0; i < mRow; i++) {
            mLinearLayout[i] = new LinearLayout(mContext);
        }
        mParam[0] = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, 0, 1.0f);
        mParam[1] = new LinearLayout.LayoutParams(0, LayoutParams.MATCH_PARENT, 1.0f);
        for (int i = 0; i < mRow * mCol; i++) {
            mTextView[i] = new TextView(mContext);
            mCalendarArray[i] = Calendar.getInstance();
        }

        // View allocation

        // Add listener

        // Default setting
        Gl.DENSITY = mContext.getResources().getDisplayMetrics().density;
        for (int i = 0; i < mRow; i++) {
            mLinearLayout[i].setLayoutParams(mParam[0]);
        }
        for (int i = 0; i < mRow * mCol; i++) {
            mTextView[i].setLayoutParams(mParam[1]);
            mTextView[i].setGravity(Gravity.CENTER);
            mTextView[i].setText(String.valueOf(i));
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle));
            mTextView[i].setTextSize(16);
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            mSelected[i] = false;
        }

        // Set Layout
        for (int i = 0; i < mRow; i++) {
            addView(mLinearLayout[i]);
            for (int j = 0; j < mCol; j++) {
                mLinearLayout[i].addView(mTextView[i * mCol + j]);
            }
        }
    }

    private void selectTextView(int i, int n) { // n -> 0 normal , 1 today , 2 selected , 3 now   //    MODE 1   4 ring
        if (n == 0) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (n == 1) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_today));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (n == 2) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_selected));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (n == 3) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_now));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (n == 4) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_ring));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        }
    }

    public boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }

    public Calendar makeClone(Calendar A) {
        Calendar New = Calendar.getInstance();
        New.set(A.get(Calendar.YEAR), A.get(Calendar.MONTH), A.get(Calendar.DATE));
        return New;
    }


    //사용자의 선택에 따라서 유동적으로 달력 화면 출력
    public void reDisplay(Calendar C) {
        curYear = C.get(Calendar.YEAR);
        curMonth = C.get(Calendar.MONTH);
        curDate = C.get(Calendar.DATE);
        mCurrent.set(curYear, curMonth, curDate);
        startDay.set(curYear, curMonth, 1);
        endDay.set(curYear, curMonth, startDay.getActualMaximum(Calendar.DATE));
        firstDay = startDay.get(Calendar.DAY_OF_WEEK);
        lastDay = endDay.get(Calendar.DATE);

        int cst = -firstDay + 1;
        for (int i = 0; i < mRow * mCol; i++) {
            mCalendarArray[i].set(curYear, curMonth, 1 + cst++);
        }

        for (int i = 0; i < mRow * mCol; i++) {
            mTextView[i].setEnabled(true);
            mSelected[i] = false;
            selectTextView(i, 0);
            if (mCalendarArray[i].get(Calendar.MONTH) != curMonth) {
                mTextView[i].setTextColor(mContext.getResources().getColor(R.color.alpha2));
            }
            if (mCalendarArray[i].compareTo(mToday) < 0) {
                mTextView[i].setTextColor(mContext.getResources().getColor(R.color.alpha2));
                mTextView[i].setEnabled(false);
            }
            if (isEqual(mToday, mCalendarArray[i])) {
                selectTextView(i, 1);
            }
            if (MODE == 0) {
                for (int j = 0; j < mDateData.size(); j++) {
                    if (isEqual(mCalendarArray[i], mDateData.get(j))) {
                        if (j == mDateData.size() - 1) selectTextView(i, 3);
                        else selectTextView(i, 2);
                        mSelected[i] = true;
                        break;
                    }
                }
            } else if (MODE == 1) {
                mTextView[i].setTextColor(mContext.getResources().getColor(R.color.alpha2));
                mTextView[i].setEnabled(false);
                Meet m = (Meet) mIntent.getSerializableExtra(Gl.MEET);
                for (int j = 0; j < m.getSelectedDate().size(); j++) {
                    if (isEqual(mCalendarArray[i], m.getSelectedDate().get(j))) {
                        selectTextView(i, 4);
                        mTextView[i].setEnabled(true);
                        break;
                    }
                }
                for (int j = 0; allData != null && j < allData.size(); j++) {
                    if (isEqual(mCalendarArray[i], allData.get(j).get(0))) {
                        selectTextView(i, 2);
                    }
                }
                if (mDateData.size() != 0 && isEqual(mCalendarArray[i], mDateData.get(0))) {
                    selectTextView(i, 3);
                    mSelected[i] = true;
                }
            }
            mTextView[i].setText(String.valueOf(mCalendarArray[i].get(Calendar.DATE)));
        }

        if (curMonth != mCalendarArray[35].get(Calendar.MONTH)) {
            for (int i = 35; i < mRow * mCol; i++) {
                mTextView[i].setText("");
                mTextView[i].setEnabled(false);
                selectTextView(i, 0);
            }
        } else {
            for (int i = 35; i < mRow * mCol; i++) {
                mTextView[i].setEnabled(true);
            }
        }
    }

    public void setAllData(ArrayList<ArrayList<Calendar>> arrayLists) {
        this.allData = arrayLists;
    }

    public ArrayList<Calendar> getDateData() {
        return mDateData;
    }

    public TextView getTextView(int i) {
        return mTextView[i];
    }

    public boolean[] getSelected() {
        return mSelected;
    }

    public void setSelected(boolean[] b) {
        mSelected = b;
    }

    public Calendar[] getCalendarArray() {
        return mCalendarArray;
    }

    public void setIntent(Intent mIntent) {
        this.mIntent = mIntent;
    }

}
