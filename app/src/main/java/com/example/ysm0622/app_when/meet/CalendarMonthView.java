package com.example.ysm0622.app_when.meet;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

import java.util.ArrayList;
import java.util.Calendar;

public class CalendarMonthView extends LinearLayout implements View.OnClickListener {

    // TAG
    private static final String TAG = "CalendarMonthView";

    // Const
    private static final int mRow = 6;
    private static final int mCol = 7;
    private static float mScale;

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

    @Override
    public void onMeasure(int width, int height) {
        super.onMeasure(width, height);
        mWidth = getWidth();
        mHeight = getHeight();
        float h = mHeight / mRow;
        float w = mWidth / mCol;
        Log.w(TAG, "W = " + w + " H = " + h);
        h -= (int) (2 * mScale + 0.5f);
        w = w - h;
        w /= 2;
        mParam[1].setMargins((int) w, (int) (1 * mScale + 0.5f), (int) w, (int) (1 * mScale + 0.5f));
        Log.w(TAG, "Height : " + (mTextView[0].getHeight() - (2 * mScale + 0.5f)) + " Width : " + (mTextView[0].getWidth() - w * 2));
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
        for (int i = 0; i < mRow * mCol; i++) {
            mTextView[i].setOnClickListener(this);
        }

        // Default setting
        mScale = mContext.getResources().getDisplayMetrics().density;
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

    private void selectTextView(int i, int n) { // n -> 0 normal , 1 -> selected , 2 -> today
        if (n == 0) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
        } else if (n == 1) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_selected));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.white));
        } else if (n == 2) {
            mTextView[i].setBackground(mContext.getResources().getDrawable(R.drawable.selector_circle_today));
            mTextView[i].setTextColor(mContext.getResources().getColor(R.color.white));
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
            if(mCalendarArray[i].compareTo(mToday)<0){
                mTextView[i].setTextColor(mContext.getResources().getColor(R.color.alpha2));
                mTextView[i].setEnabled(false);
            }
            if (isEqual(mToday, mCalendarArray[i])) {
                selectTextView(i, 2);
            }
            for (int j = 0; j < mDateData.size(); j++) {
                if (isEqual(mCalendarArray[i], mDateData.get(j))) {
                    selectTextView(i, 1);
                    mSelected[i] = true;
                    break;
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

    public ArrayList<Calendar> getDateData() {
        return mDateData;
    }

    public TextView getTextView(int i) {
        return mTextView[i];
    }

    public boolean[] getSelected() {
        return mSelected;
    }

    public Calendar[] getCalendarArray() {
        return mCalendarArray;
    }

    @Override
    public void onClick(View v) {
        for (int i = 0; i < mRow * mCol; i++) {
            if (v.equals(mTextView[i])) {
                if (mSelected[i]) {
                    for (int n = 0; n < mDateData.size(); n++) {
                        if (isEqual(mCalendarArray[i], mDateData.get(n))) {
                            mDateData.remove(n);
                            break;
                        }
                    }
                    for (int n = 0; n < mDateData.size(); n++) {
                        Log.w(TAG, mDateData.get(n).get(Calendar.YEAR) + "년 " + (mDateData.get(n).get(Calendar.MONTH) + 1) + "월 " + mDateData.get(n).get(Calendar.DATE) + "일");
                    }
                    reDisplay(mCurrent);
                } else {
                    mDateData.add(makeClone(mCalendarArray[i]));
                    for (int n = 0; n < mDateData.size(); n++) {
                        Log.w(TAG, mDateData.get(n).get(Calendar.YEAR) + "년 " + (mDateData.get(n).get(Calendar.MONTH) + 1) + "월 " + mDateData.get(n).get(Calendar.DATE) + "일");
                    }
                    reDisplay(mCurrent);
                }
            }
        }
    }
}
