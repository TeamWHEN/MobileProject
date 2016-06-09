package com.example.ysm0622.app_when.menu;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Toast;

/**
 * Created by JungHan on 2016-06-07.
 */
public class RateView extends AlertDialog {

    // TAG
    private static final String TAG = RateView.class.getName();


    private RatingBar rateBar;
    private LinearLayout linear;
    private Button btn;

    public RateView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        setTitle("어플리케이션 평가");

        setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                }
                return false;
            }
        });

        rateBar = new RatingBar(context);
        rateBar.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        rateBar.setNumStars(5);
        rateBar.setStepSize(0.5f);

        linear = new LinearLayout(context);
        linear.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        linear.setGravity(Gravity.CENTER);
        linear.setOrientation(LinearLayout.VERTICAL);

        btn = new Button(context);
        btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        btn.setText("전송");
        btn.setGravity(Gravity.CENTER);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //서버로 전송
                Toast.makeText(getContext(), "" + rateBar.getRating(), Toast.LENGTH_SHORT).show();

                cancel();
            }
        });

        linear.addView(rateBar);
        linear.addView(btn);
        setView(linear);
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
    }

    @Override
    public void setOnKeyListener(OnKeyListener onKeyListener) {
        super.setOnKeyListener(onKeyListener);
    }

    @Override
    public void setView(View view) {
        super.setView(view);
    }

    @Override
    protected void onStop(){
        super.onStop();
    }
}
