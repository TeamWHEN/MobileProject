package com.teamw.ysm0622.app_when.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.login.Login;

/**
 * Created by JungHan on 2016-06-19.
 */
public class LoginTest extends ActivityInstrumentationTestCase2<Login> {
    public LoginTest() {
        super(Login.class);
    }

    public void testEmail() {
        Activity activity = getActivity();
        TextView tv = (TextView) activity.findViewById(R.id.EditText0);

        assertEquals(activity.getText(R.string.email_hint), tv.getHint());
    }

    public void testPassword() {
        Activity activity = getActivity();
        TextView tv = (TextView) activity.findViewById(R.id.EditText1);

        assertEquals(activity.getText(R.string.password_hint), tv.getHint());
    }

}
