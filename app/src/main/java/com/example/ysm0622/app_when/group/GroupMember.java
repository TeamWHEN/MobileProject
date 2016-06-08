package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.widget.ListView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;

public class GroupMember extends CoordinatorLayout {

    // TAG
    private static final String TAG = GroupMember.class.getName();

    private ArrayList<User> userData = new ArrayList<>();

    private Context mContext;
    private UserDataAdapter adapter;
    private ListView mListView;
    private FloatingActionButton mFab;


    public GroupMember(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public GroupMember(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        // Array allocation

        // Create instance

        // View allocation
        mListView = (ListView) findViewById(R.id.ListView);
        mFab = (FloatingActionButton) findViewById(R.id.fab);

        // Add listener

        // Default setting
    }

    public FloatingActionButton getFab() {
        return mFab;
    }
}
