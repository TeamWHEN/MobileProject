package com.example.ysm0622.app_when.meet;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.DateTime;
import com.example.ysm0622.app_when.object.Meet;

import java.util.ArrayList;
import java.util.Calendar;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    // TAG
    private static final String TAG = CustomSurfaceView.class.getName();

    // Const
    private int mRow;
    private int mCol;
    private int mMin;
    private int mMax;

    // Data
    private Intent mIntent;
    private Context mContext;

    //
    private Meet m;
    private SurfaceHolder mHolder;
    private Canvas mCanvas;
    private Bitmap mBuffer;
    private Paint mPrimaryPaint;
    private Paint mWhitePaint;
    private Paint mAccentPaint;

    private float mWidth;
    private float mHeight;
    private float mStroke;

    private float mWunit;
    private float mHunit;

    // Thread
    private CThread mThread;
    private boolean mDraw;

    public CustomSurfaceView(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    public CustomSurfaceView(Context context, AttributeSet attr) {
        super(context, attr);
        this.mContext = context;
        init();
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setKeepScreenOn(true);
        getHolder().addCallback(this);
        setFocusable(true);
        mThread = new CThread();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Point p = new Point();
        super.onTouchEvent(event);
        int action = event.getAction();
        boolean del = false;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                p.x = (int) event.getX();
                p.y = (int) event.getY();
                Log.w(TAG, "X : " + p.x + " Y : " + p.y);
            case MotionEvent.ACTION_MOVE:
                p.x = (int) event.getX();
                p.y = (int) event.getY();
                Log.w(TAG, "X : " + p.x + " Y : " + p.y);
            case MotionEvent.ACTION_UP:
        }
        return true;
    }

    public void drawState(Meet m, int col, int row, int min, int max) {
        this.m = m;
        mCol = col;
        mRow = row;
        mMin = min;
        mMax = max;
    }

    private void drawInputs() {
        ArrayList<DateTime> D = m.getDateTime();
        Paint grd = new Paint();

        grd.setColor(getResources().getColor(R.color.colorAccent));
        grd.setAlpha(255 / D.size());


        for (int i = 0; i < D.size(); i++) {
            ArrayList<ArrayList<Calendar>> arrayLists = D.get(i).getSelectTime();
            int n = 0;
            int h = 0;
            for (int j = 0; n < arrayLists.size() && j < m.getSelectedDate().size(); j++) {
                Calendar A = m.getSelectedDate().get(j);
                Calendar B = arrayLists.get(n).get(0);
                if (isEqual(A, B)) {
                    ArrayList<Calendar> arrayList = arrayLists.get(n);
                    for (int k = 0; k < arrayList.size(); k++) {
                        int v = arrayList.get(k).get(Calendar.HOUR_OF_DAY);
                        v -= mMin;
                        mCanvas.drawRect(mWunit * h, mHunit * v, mWunit * (h + 1), mHunit * (v + 1), grd);
                    }
                    n++;
                    j--;
                } else {
                    h++;
                }
            }
        }
    }


    private void drawLines() {
        for (int i = 0; i < mCol; i++) {
            float c = mWunit * (i + 1) - mStroke / 2;
            mCanvas.drawLine(c, 0, c, mHeight, mPrimaryPaint);
        }
        for (int i = 0; i < mRow; i++) {
            float c = mHunit * (i + 1) - mStroke / 2;
            mCanvas.drawLine(0, c, mWidth, c, mPrimaryPaint);
        }
    }

    private boolean isEqual(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE)) {
            return true;
        } else return false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCanvas = new Canvas();

        mWidth = getWidth();
        mHeight = getHeight();

        mWunit = mWidth / mCol;
        mHunit = mHeight / mRow;

        mBuffer = Bitmap.createBitmap((int) mWidth, (int) mHeight, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBuffer);
        mCanvas.drawColor(getResources().getColor(R.color.white));

        mPrimaryPaint = new Paint();
        mWhitePaint = new Paint();
        mAccentPaint = new Paint();
        mPrimaryPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mWhitePaint.setColor(getResources().getColor(R.color.white));
        mAccentPaint.setColor(getResources().getColor(R.color.colorAccent));

        mStroke = Gl.dpToPx(mContext, (float) 1.0);
        mPrimaryPaint.setStrokeWidth(mStroke);

        drawInputs();

        drawLines();

        mDraw = true;
        mThread.setRunning(true);
        mThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mThread.setRunning(false);
        mDraw = false;
        while (retry) {
            try {
                mThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public class CThread extends Thread {

        private final String TAG = CThread.class.getName();
        private boolean mRunning = false;

        public void setRunning(boolean v) {
            mRunning = v;
        }

        @Override
        public void run() {
            while (mRunning) {
                Canvas Canvas = null; // Swap buffer
                try {
                    Canvas = mHolder.lockCanvas();
                    synchronized (mHolder) {
                        if (mDraw) Canvas.drawBitmap(mBuffer, 0, 0, null);
                    }
                } finally {
                    if (Canvas != null) {
                        mHolder.unlockCanvasAndPost(Canvas);
                    }
                }
            }
        }
    }
}