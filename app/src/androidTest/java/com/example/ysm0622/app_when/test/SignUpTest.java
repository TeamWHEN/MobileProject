package com.example.ysm0622.app_when.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.login.SignUp;

/**
 * Created by JungHan on 2016-06-19.
 */
public class SignUpTest extends ActivityInstrumentationTestCase2<SignUp> {
    public SignUpTest() {
        super(SignUp.class);
    }

    public void testCopyright() {
        Activity activity = getActivity();
        TextView tv = (TextView) activity.findViewById(R.id.copyright);

        assertEquals(activity.getText(R.string.copyright), tv.getText());
    }
}
