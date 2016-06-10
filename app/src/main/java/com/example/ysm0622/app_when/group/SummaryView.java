package com.example.ysm0622.app_when.group;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;

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

    public SummaryView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.w(TAG, "Start constructor");
        this.mContext = context;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.setElevation((float)4.0);
        }
        init();
    }

    private void init() {

        // Array allocation

        // Create instance

        // View allocation

        // Add listener

        // Default setting
//        mLinearLayout.addView(mSurfaceView);

    }

}
