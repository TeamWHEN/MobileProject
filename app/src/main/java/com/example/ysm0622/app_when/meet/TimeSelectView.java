package com.example.ysm0622.app_when.meet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;

public class TimeSelectView extends SurfaceView implements SurfaceHolder.Callback {

    // TAG
    private static final String TAG = TimeSelectView.class.getName();

    // Const
    private static final int DIVISION = 8;

    private Context mContext;
    private SurfaceHolder mHolder;
    public DrawThread mDrawThread;

    private int mWidth;
    private int mHeight;

    private Canvas mCanvas;
    private Bitmap mBuffer;
    private Paint mPrimaryPaint;
    private Paint mWhitePaint;
    private Paint mAccentPaint;
    private int mStroke;
    private int mScale;

    private boolean mDraw;

    private boolean sw;

    private boolean mInputMode;

    private boolean mSelected[] = new boolean[DIVISION];

    public TimeSelectView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public TimeSelectView(Context context, AttributeSet attr) {
        super(context, attr);
        mContext = context;
        init();
    }

    @Override
    public void onMeasure(int width, int height) {
        setMeasuredDimension(width, width / DIVISION);
    }

    private void init() {
        mHolder = getHolder();
        mHolder.setKeepScreenOn(true);
        getHolder().addCallback(this);
        setFocusable(true);
        mDrawThread = new DrawThread();
        for (int i = 0; i < DIVISION; i++) {
            mSelected[i] = false;
        }
    }

    int lastX, lastY, currX, currY;
    boolean isDeleting;
    Point p = new Point();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        boolean del = false;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                p.x = (int) event.getX();
                p.y = (int) event.getY();
                if (getTouchIndex(p) >= 0) {
                    if (mSelected[getTouchIndex(p)]) sw = false;
                    else sw = true;
                    drawRectIndex(getTouchIndex(p));
                    mDraw = true;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                p.x = (int) event.getX();
                p.y = (int) event.getY();
                drawRectIndex(getTouchIndex(p));
                mDraw = true;
                break;
            case MotionEvent.ACTION_UP:
        }
        return true;
    }

    private int getTouchIndex(Point point) {
        for (int i = 0; i < DIVISION; i++) {
            if (point.x > (i * mScale) + mStroke / 2.0 && point.x < ((i + 1) * mScale) - mStroke / 2.0 &&
                    point.y > 0 && point.y < mHeight) {
                return i;
            }
        }
        return -1;
    }

    public void drawRectByArray(boolean arr[]) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i])
                mCanvas.drawRect(i * mScale, mStroke, (i + 1) * mScale, mHeight - mStroke, mAccentPaint);
            else
                mCanvas.drawRect(i * mScale, mStroke, (i + 1) * mScale, mHeight - mStroke, mWhitePaint);
        }
        drawBorder();
    }

    private void drawRectIndex(int i) {
        if (i < 0) return;
        if (sw)
            mCanvas.drawRect(i * mScale, mStroke, (i + 1) * mScale, mHeight - mStroke, mAccentPaint);
        else
            mCanvas.drawRect(i * mScale, mStroke, (i + 1) * mScale, mHeight - mStroke, mWhitePaint);
        mSelected[i] = sw;
        drawBorder();
    }

    private void drawBorder() {
        mStroke = (int) (2 * Global.DENSITY + 0.5f);
        mPrimaryPaint.setStrokeWidth(mStroke);
        mCanvas.drawLine(0, 0, mWidth, 0, mPrimaryPaint);
        mCanvas.drawLine(0, mHeight, mWidth, mHeight, mPrimaryPaint);
        mCanvas.drawLine(0, 0, 0, mHeight, mPrimaryPaint);
        mCanvas.drawLine(mWidth, 0, mWidth, mHeight, mPrimaryPaint);
        mStroke = (int) (1 * Global.DENSITY + 0.5f);
        mPrimaryPaint.setStrokeWidth(mStroke);
        for (int i = 0; i < DIVISION + 1; i++) {
            mCanvas.drawLine(mScale * i, 0, mScale * i, mHeight, mPrimaryPaint);
        }
    }

    public void setDraw(boolean v) {
        mDraw = v;
    }

    public void setAll(boolean v) {
        if (v) {
            mCanvas.drawRect(0, 0, mWidth, mHeight, mPrimaryPaint);
            mCanvas.drawRect(mStroke, mStroke, mWidth - mStroke, mHeight - mStroke, mAccentPaint);
            for (int i = 0; i < DIVISION; i++) {
                mCanvas.drawLine(mScale * i, 0, mScale * i, mHeight, mPrimaryPaint);
                mSelected[i] = v;
            }
        } else {
            mCanvas.drawRect(0, 0, mWidth, mHeight, mPrimaryPaint);
            mCanvas.drawRect(mStroke, mStroke, mWidth - mStroke, mHeight - mStroke, mWhitePaint);
            for (int i = 0; i < DIVISION; i++) {
                mCanvas.drawLine(mScale * i, 0, mScale * i, mHeight, mPrimaryPaint);
                mSelected[i] = v;
            }
        }
    }

    public void setSelected(boolean arr[]) {
        mSelected = arr;
        drawRectByArray(mSelected);
    }

    public boolean[] getSelected() {
        return mSelected;
    }

    public boolean isAllSelcted() {
        for (int i = 0; i < DIVISION; i++) {
            if (!mSelected[i]) return false;
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCanvas = new Canvas();

        mWidth = getWidth();
        mHeight = getHeight();

        mBuffer = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas.setBitmap(mBuffer);
        mCanvas.drawColor(getResources().getColor(R.color.colorPrimary));

        mPrimaryPaint = new Paint();
        mWhitePaint = new Paint();
        mAccentPaint = new Paint();
        mPrimaryPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mWhitePaint.setColor(getResources().getColor(R.color.white));
        mAccentPaint.setColor(getResources().getColor(R.color.colorAccent));

        mScale = mWidth / DIVISION;
        mStroke = (int) (2 * Global.DENSITY + 0.5f);
        mPrimaryPaint.setStrokeWidth(mStroke);

        mCanvas.drawColor(getResources().getColor(R.color.white));
        drawBorder();

        mDrawThread.setRunning(true);
        mDrawThread.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        mDrawThread.setRunning(false);
        mDraw = false;
        while (retry) {
            try {
                mDrawThread.join();
                retry = false;
            } catch (InterruptedException e) {
            }
        }
    }

    public class DrawThread extends Thread {

        private static final String TAG = "DrawThread";
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
