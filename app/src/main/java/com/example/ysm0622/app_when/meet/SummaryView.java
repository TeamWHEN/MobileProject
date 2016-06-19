package com.example.ysm0622.app_when.meet;


import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Meet;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class SummaryView extends LinearLayout {

    // TAG
    private static final String TAG = SummaryView.class.getName();

    // Const
    private int mRow;
    private int mCol;
    private int mMin;
    private int mMax;

    // Data
    private Intent mIntent;
    private Context mContext;

    // Views
    private LinearLayout[] mLinearLayout = new LinearLayout[4];
    private View mView;
    private CustomSurfaceView mSurfaceView;

    // Class
    private Meet m;

    public SummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.w(TAG, "Start constructor");
        this.mContext = context;
        init();
    }

    private void init() {
        // Array allocation

        // Create instance

        // View allocation

        // Add listener

        // Default setting
    }

    public void setLayout(LinearLayout arr[], View view, CustomSurfaceView surfaceView) {
        for (int i = 0; i < arr.length; i++) {
            mLinearLayout[i] = arr[i];
        }
        mView = view;
        mSurfaceView = surfaceView;
    }

    public void drawState(Intent intent) {
        mIntent = intent;
        calculate();
        setTimeSlice();
        setDateSlice();
        mSurfaceView.drawState(m, mCol, mRow, mMin, mMax);
    }

    private String getDay(int i) {
        String[] week = {"일", "월", "화", "수", "목", "금", "토"};
        return week[i - 1];
    }

    private void setDateSlice() {
        ArrayList<Calendar> arrayList = m.getSelectedDate();
        Collections.sort(arrayList, new CalendarAscCompare());
        int width = (int) (mContext.getResources().getDisplayMetrics().widthPixels - (35 * Gl.DENSITY + 0.5f));
        int minWidth = (int) (64 * Gl.DENSITY + 0.5f);
        int divide = width / mCol;
        for (int i = 0; i < mCol; i++) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.slice_date, null);
            LayoutParams Param;
            if (divide > minWidth)
                Param = new LayoutParams(divide, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            else Param = new LayoutParams(minWidth, ViewGroup.LayoutParams.MATCH_PARENT, 1.0f);
            v.setLayoutParams(Param);
            String str1 = (arrayList.get(i).get(Calendar.MONTH) + 1) + "월 " + arrayList.get(i).get(Calendar.DATE) + "일";
            String str2 = getDay(arrayList.get(i).get(Calendar.DAY_OF_WEEK));
            TextView TextView0 = (TextView) v.findViewById(R.id.TextView0);
            TextView TextView1 = (TextView) v.findViewById(R.id.TextView1);
            if (str2.equals("일")) TextView1.setTextColor(getResources().getColor(R.color.red_dark));
            else if (str2.equals("토"))
                TextView1.setTextColor(getResources().getColor(R.color.blue));
            TextView0.setText(str1);
            TextView1.setText(str2);
            mLinearLayout[1].addView(v);
        }
    }

    private void setTimeSlice() {
        for (int i = mMin; i <= mMax; i++) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = vi.inflate(R.layout.slice_time, null);
            LayoutParams Param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);
            v.setLayoutParams(Param);
            TextView TextView = (TextView) v.findViewById(R.id.TextView0);
            ImageView ImageView = (ImageView) v.findViewById(R.id.ImageView0);
            TextView AMPM = (TextView) v.findViewById(R.id.ampm);

            if (i == mMin) {
                Param = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) (1 * Gl.DENSITY));
                ImageView.setLayoutParams(Param);
                if (mMin < 12) AMPM.setText(R.string.am);
                else AMPM.setText(R.string.pm);
            }
            if (i == 12) AMPM.setText(R.string.pm);
            if (i > 12){
                String str = (i - 12) + mContext.getString(R.string.hour);
                TextView.setText(str);
            }
            else {
                String str = i + mContext.getString(R.string.hour);
                TextView.setText(str);
            }
            mLinearLayout[0].addView(v);
        }
    }

    private void calculate() {
        m = (Meet) mIntent.getSerializableExtra(Gl.MEET);

        if (m.getDateTimeNum() > 0) {
            mMin = getMinTime(m.getDateTime());
            mMax = getMaxTime(m.getDateTime());
        } else {
            mMin = 0;
            mMax = 23;
        }
        mCol = m.getSelectedDate().size();
        mRow = mMax - mMin + 1;
    }

    private int getMinTime(ArrayList<DateTime> D) {
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < D.size(); i++) {
            ArrayList<ArrayList<Calendar>> arrayLists = D.get(i).getSelectTime();
            Collections.sort(arrayLists, new CalendarListAscCompare());
            for (int j = 0; j < arrayLists.size(); j++) {
                ArrayList<Calendar> arrayList = arrayLists.get(j);
                Collections.sort(arrayList, new CalendarAscCompare());
                for (int k = 0; k < arrayList.size(); k++) {
                    Calendar tmp = arrayList.get(0);
                    if (isEqual(tmp, arrayList.get(k)) && min > arrayList.get(k).get(Calendar.HOUR_OF_DAY)) {
                        min = arrayList.get(k).get(Calendar.HOUR_OF_DAY);
                        if (min == 0 || min == 1) return 0;
                    }
                }
            }
        }
        return min - 1;
    }

    private int getMaxTime(ArrayList<DateTime> D) {
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < D.size(); i++) {
            ArrayList<ArrayList<Calendar>> arrayLists = D.get(i).getSelectTime();
            for (int j = 0; j < arrayLists.size(); j++) {
                ArrayList<Calendar> arrayList = arrayLists.get(j);
                for (int k = 0; k < arrayList.size(); k++) {
                    Calendar tmp = arrayList.get(0);
                    if (isEqual(tmp, arrayList.get(k)) && max < arrayList.get(k).get(Calendar.HOUR_OF_DAY)) {
                        max = arrayList.get(k).get(Calendar.HOUR_OF_DAY);
                        if (max == 23) return max;
                    }
                }
            }
        }
        return max + 1;
    }

    public boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }

    static class CalendarAscCompare implements Comparator<Calendar> {
        @Override
        public int compare(Calendar lhs, Calendar rhs) {
            return lhs.compareTo(rhs);
        }
    }

    static class CalendarListAscCompare implements Comparator<ArrayList<Calendar>> {
        @Override
        public int compare(ArrayList<Calendar> lhs, ArrayList<Calendar> rhs) {
            return lhs.get(0).compareTo(rhs.get(0));
        }
    }
}
