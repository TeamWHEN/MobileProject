package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CustomSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    // TAG
    private static final String TAG = CustomSurfaceView.class.getName();

    public CustomSurfaceView(Context context) {
        super(context);
    }

    public CustomSurfaceView(Context context, AttributeSet attr) {
        super(context, attr);
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

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}