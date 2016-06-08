package com.example.ysm0622.app_when.menu;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

public class About extends Activity implements View.OnClickListener {

    // TAG
    private static final String TAG = About.class.getName();

    // Const
    private static final int COUNT = 3;
    private static final int mToolBtnNum = 1;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private View mInclude[];
    private ImageView mImageView[];
    private TextView mTextViewName[];
    private TextView mTextViewEmail[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_main);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        String toolbarTitle = getResources().getString(R.string.about);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();
    }

    private void initToolbar(Drawable Icon[], String Title) {
        mToolbarAction = new ImageView[2];
        mToolbarAction[0] = (ImageView) findViewById(R.id.Toolbar_Action0);
        mToolbarAction[1] = (ImageView) findViewById(R.id.Toolbar_Action1);
        mToolbarTitle = (TextView) findViewById(R.id.Toolbar_Title);

        for (int i = 0; i < mToolBtnNum; i++) {
            mToolbarAction[i].setOnClickListener(this);
            mToolbarAction[i].setImageDrawable(Icon[i]);
            mToolbarAction[i].setBackground(getResources().getDrawable(R.drawable.selector_btn));
        }
        mToolbarTitle.setText(Title);
    }

    private void initialize() {

        // Array allocation
        mInclude = new View[COUNT];
        mImageView = new ImageView[COUNT];
        mTextViewName = new TextView[COUNT];
        mTextViewEmail = new TextView[COUNT];

        // Create instance

        // View allocation
        mInclude[0] = (View) findViewById(R.id.Include0);
        mInclude[1] = (View) findViewById(R.id.Include1);
        mInclude[2] = (View) findViewById(R.id.Include2);

        mImageView[0] = (ImageView) mInclude[0].findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) mInclude[1].findViewById(R.id.ImageView0);
        mImageView[2] = (ImageView) mInclude[2].findViewById(R.id.ImageView0);

        mTextViewName[0] = (TextView) mInclude[0].findViewById(R.id.TextView0);
        mTextViewName[1] = (TextView) mInclude[1].findViewById(R.id.TextView0);
        mTextViewName[2] = (TextView) mInclude[2].findViewById(R.id.TextView0);

        mTextViewEmail[0] = (TextView) mInclude[0].findViewById(R.id.TextView1);
        mTextViewEmail[1] = (TextView) mInclude[1].findViewById(R.id.TextView1);
        mTextViewEmail[2] = (TextView) mInclude[2].findViewById(R.id.TextView1);


        // Add listener

        // Default setting
        for (int i = 0; i < COUNT; i++) {
            mImageView[i].setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        mTextViewName[0].setText(R.string.YSM);
        mTextViewName[1].setText(R.string.JDH);
        mTextViewName[2].setText(R.string.JJH);

        mTextViewEmail[0].setText(R.string.ysm_email);
        mTextViewEmail[1].setText(R.string.jdh_email);
        mTextViewEmail[2].setText(R.string.jjh_email);
    }

    @Override
    public void onClick(View v) {
        if (v.equals(mToolbarAction[0])) {
            super.onBackPressed();
        }
    }
}
