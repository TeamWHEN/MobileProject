package com.example.ysm0622.app_when.meet;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.ysm0622.app_when.R;

public class TimeSelectView extends SurfaceView implements SurfaceHolder.Callback {

    // TAG
    private static final String TAG = "TimeSelectView";

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

    private boolean mDraw;

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        int action = event.getAction();
        boolean del = false;
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                mDraw = true;
                break;
            case MotionEvent.ACTION_MOVE:
                if (isDeleting) break;
                mDraw = true;
                break;
            case MotionEvent.ACTION_UP:
                mDraw = false;
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
        mPrimaryPaint.setColor(getResources().getColor(R.color.colorPrimary));
        mWhitePaint.setColor(getResources().getColor(R.color.white));

        int scale = mWidth / DIVISION;
        int stroke = 3;
        mPrimaryPaint.setStrokeWidth(stroke);

        mCanvas.drawRect(0, 0, mWidth, mHeight, mPrimaryPaint);
        mCanvas.drawRect(stroke, stroke, mWidth - stroke, mHeight - stroke, mWhitePaint);
        for (int i = 0; i < DIVISION; i++) {
            mCanvas.drawLine(scale * i, 0, scale * i, mHeight, mPrimaryPaint);
        }
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
