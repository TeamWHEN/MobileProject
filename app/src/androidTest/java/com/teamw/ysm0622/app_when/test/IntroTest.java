package com.teamw.ysm0622.app_when.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.intro.Intro;

/**
 * Created by JungHan on 2016-06-19.
 */
public class IntroTest extends ActivityInstrumentationTestCase2<Intro> {
    public IntroTest() {
        super(Intro.class);
    }

    public void testCopyright() {
        Activity activity = getActivity();
        TextView tv = (TextView) activity.findViewById(R.id.copyright);

        assertEquals(activity.getText(R.string.copyright), tv.getText());
    }
}
