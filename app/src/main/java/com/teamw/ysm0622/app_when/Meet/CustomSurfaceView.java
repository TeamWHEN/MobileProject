package com.teamw.ysm0622.app_when.meet;

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

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.global.Gl;
import com.teamw.ysm0622.app_when.object.DateTime;
import com.teamw.ysm0622.app_when.object.Meet;

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

    //유저가 드래그한 시간 색 칠하기
    private void duplicateDelete(ArrayList<ArrayList<Calendar>> A, ArrayList<ArrayList<Calendar>> B) {
        for (int i = 0; i < B.size(); i++) {
            for (int j = 0; j < A.size(); j++) {
                B.get(i).removeAll(A.get(j));
            }
        }
    }

    private void drawInputs() {
        ArrayList<DateTime> D = m.getDateTime();

        for (int i = 0; i < D.size(); i++) {
            for (int j = 0; j < i; j++) {
                duplicateDelete(D.get(i).getSelectTime(), D.get(j).getSelectTime());
            }
        }

        Paint grd = new Paint();
        grd.setColor(getResources().getColor(R.color.colorAccent));
        grd.setAlpha(128);
        int cnt[][] = new int[100][100];
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 100; j++) {
                cnt[i][j] = 0;
            }
        }
        Paint paintArr[] = null;
        if (D.size() > 0) paintArr = getPaintArr(D.size());


        for (int i = 0; i < D.size(); i++) {
            ArrayList<ArrayList<Calendar>> arrayLists = D.get(i).getSelectTime();
            int n = 0;
            int h = 0;
            for (int j = 0; n < arrayLists.size() && j < m.getSelectedDate().size(); j++) {
                Calendar A = m.getSelectedDate().get(j);
                Calendar B = null;
                if (n < arrayLists.size() && arrayLists.get(n).size() != 0)
                    B = arrayLists.get(n).get(0);
                if (A != null && B != null && isEqual(A, B)) {
                    ArrayList<Calendar> arrayList = arrayLists.get(n);
                    for (int k = 0; k < arrayList.size(); k++) {
                        int v = arrayList.get(k).get(Calendar.HOUR_OF_DAY);
                        v -= mMin;
                        cnt[h][v]++;
                    }
                    n++;
                    j--;
                } else {
                    h++;
                }
            }
        }

        int maxCount = -1;
        Calendar start, end;
        start = Calendar.getInstance();
        end = Calendar.getInstance();
        boolean sw = false;
        for (int i = 0; i < D.size(); i++) {
            Log.d("TEST", "D.size : " + D.size());
            ArrayList<ArrayList<Calendar>> arrayLists = D.get(i).getSelectTime();
            int n = 0;
            int h = 0;
            for (int j = 0; n < arrayLists.size() && j < m.getSelectedDate().size(); j++) {
                Calendar A = m.getSelectedDate().get(j);
                Calendar B = null;
                if (n < arrayLists.size() && arrayLists.get(n).size() != 0)
                    B = arrayLists.get(n).get(0);
                if (A != null && B != null && isEqual(A, B)) {
                    ArrayList<Calendar> arrayList = arrayLists.get(n);
                    for (int k = 0; k < arrayList.size(); k++) {
                        int v = arrayList.get(k).get(Calendar.HOUR_OF_DAY);
                        v -= mMin;
                        Log.d("TEST", "cnt[" + h + "][" + v + "] = " + cnt[h][v]);
                        mCanvas.drawRect(mWunit * h, mHunit * v, mWunit * (h + 1), mHunit * (v + 1), paintArr[cnt[h][v] - 1]);
                        if (cnt[h][v] > maxCount) {
                            maxCount = cnt[h][v];
                            start = arrayList.get(k);
                            sw = true;
                        }
                        if (sw && cnt[h][v] == maxCount) {
                            end = arrayList.get(k);
                        }
                    }
                    n++;
                    j--;
                } else {
                    h++;
                }
            }
        }

        String str = start.get(Calendar.YEAR) + "/" + (start.get(Calendar.MONTH)+1) + "/" + start.get(Calendar.DATE) + " " + start.get(Calendar.HOUR_OF_DAY) + ":00 ~ ";
        str += end.get(Calendar.YEAR) + "/" + (end.get(Calendar.MONTH)+1) + "/" + end.get(Calendar.DATE) + " " + (end.get(Calendar.HOUR_OF_DAY)+1) + ":00" + "\n총 " + maxCount + "명입니다";
        if(maxCount!=-1)Gl.setRecommmendationTime(str);
    }

    //누적 인원 비교를 위한 색 톤 설정
    private Paint[] getPaintArr(int len) {
        Paint arr[] = new Paint[len];
        for (int i = 0; i < len; i++)
            arr[i] = new Paint();

        if (len == 1) {
            arr[0].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 2) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 3) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis3));
            arr[2].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 4) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis2));
            arr[2].setColor(getResources().getColor(R.color.dis5));
            arr[3].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 5) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis2));
            arr[2].setColor(getResources().getColor(R.color.dis4));
            arr[3].setColor(getResources().getColor(R.color.dis5));
            arr[4].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 6) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis1));
            arr[2].setColor(getResources().getColor(R.color.dis2));
            arr[3].setColor(getResources().getColor(R.color.dis4));
            arr[4].setColor(getResources().getColor(R.color.dis5));
            arr[5].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 7) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis1));
            arr[2].setColor(getResources().getColor(R.color.dis2));
            arr[3].setColor(getResources().getColor(R.color.dis3));
            arr[4].setColor(getResources().getColor(R.color.dis4));
            arr[5].setColor(getResources().getColor(R.color.dis5));
            arr[6].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 8) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis1));
            arr[2].setColor(getResources().getColor(R.color.dis2));
            arr[3].setColor(getResources().getColor(R.color.dis3));
            arr[4].setColor(getResources().getColor(R.color.dis4));
            arr[5].setColor(getResources().getColor(R.color.dis5));
            arr[6].setColor(getResources().getColor(R.color.dis6));
            arr[7].setColor(getResources().getColor(R.color.dis9));
        }
        if (len == 9) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis1));
            arr[2].setColor(getResources().getColor(R.color.dis2));
            arr[3].setColor(getResources().getColor(R.color.dis3));
            arr[4].setColor(getResources().getColor(R.color.dis4));
            arr[5].setColor(getResources().getColor(R.color.dis5));
            arr[6].setColor(getResources().getColor(R.color.dis6));
            arr[7].setColor(getResources().getColor(R.color.dis7));
            arr[8].setColor(getResources().getColor(R.color.dis9));
        }
        if (len >= 10) {
            arr[0].setColor(getResources().getColor(R.color.dis0));
            arr[1].setColor(getResources().getColor(R.color.dis1));
            arr[2].setColor(getResources().getColor(R.color.dis2));
            arr[3].setColor(getResources().getColor(R.color.dis3));
            arr[4].setColor(getResources().getColor(R.color.dis4));
            arr[5].setColor(getResources().getColor(R.color.dis5));
            arr[6].setColor(getResources().getColor(R.color.dis6));
            arr[7].setColor(getResources().getColor(R.color.dis7));
            arr[8].setColor(getResources().getColor(R.color.dis8));
            arr[9].setColor(getResources().getColor(R.color.dis9));
        }

        return arr;
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

    private boolean isEqual(ArrayList<Calendar> A, ArrayList<Calendar> B) {
        for (int i = 0; i < A.size(); i++) {
            if (!isEqualH(A.get(i), B.get(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean isEqualH(Calendar A, Calendar B) {
        if (A.get(Calendar.YEAR) == B.get(Calendar.YEAR) && A.get(Calendar.MONTH) == B.get(Calendar.MONTH) && A.get(Calendar.DATE) == B.get(Calendar.DATE) && A.get(Calendar.HOUR) == B.get(Calendar.HOUR)) {
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


    //Surfaceview를 이용하기 위한 서브 쓰레드
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