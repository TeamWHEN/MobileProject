package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;

public class InvitePeople extends AppCompatActivity implements View.OnFocusChangeListener, TextWatcher, View.OnClickListener, AdapterView.OnItemClickListener {

    // TAG
    private static final String TAG = "InvitePeople";

    // Const
    private static final int mToolBtnNum = 2;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private TextView mEmptyMsgView;
    private View mInitLayout;
    private ImageView mImageView[] = new ImageView[2];
    private EditText mEditText;
    private ListView mListView;
    private LinearLayout mLinearLayout;

    private ArrayList<LinearLayout> mMemberLayout = new ArrayList<>();

    // Adapter
    private UserDataAdapter mAdapter;


    private ArrayList<User> Member = new ArrayList<>();

    // Test Data
    private ArrayList<User> testAllUser = new ArrayList<>();
    private ArrayList<User> searchUser = new ArrayList<>();
    private static final int TEST_NUM = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitepeople_main);

        // Receive intent
        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_invite_people);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();

        //test
        test();
    }

    //test
    private void test() {
        User testuser[] = new User[TEST_NUM];
        testuser[0] = new User("양성민", "ysm0622@gmail.com", "");
        testuser[1] = new User("지정한", "ysm0622@gmail.com", "");
        testuser[2] = new User("조동현", "ysm0622@gmail.com", "");
        testuser[3] = new User("조서형", "ysm0622@gmail.com", "");
        testuser[4] = new User("김영송", "ysm0622@gmail.com", "");
        testuser[5] = new User("장영준", "ysm0622@gmail.com", "");
        testuser[6] = new User("유영준", "ysm0622@gmail.com", "");
        testuser[7] = new User("양영선", "ysm0622@gmail.com", "");
        testuser[8] = new User("이수현", "ysm0622@gmail.com", "");
        testuser[9] = new User("박정호", "ysm0622@gmail.com", "");
        for (int i = 0; i < testuser.length; i++)
            testAllUser.add(testuser[i]);
    }

    private void initialize() {

        // Array allocation

        // Create instance
        mAdapter = new UserDataAdapter(this, R.layout.member_item, searchUser);

        // View allocation
        mImageView[0] = (ImageView) findViewById(R.id.ImageView0);
        mImageView[1] = (ImageView) findViewById(R.id.ImageView1);

        mEditText = (EditText) findViewById(R.id.EditText0);

        mLinearLayout = (LinearLayout) findViewById(R.id.LinearLayout0);

        mListView = (ListView) findViewById(R.id.ListView);

        mEmptyMsgView = (TextView) findViewById(R.id.EmptyMsg);

        mInitLayout = (View) findViewById(R.id.Init);

        // Add listener
        mEditText.setOnFocusChangeListener(this);
        mEditText.addTextChangedListener(this);
        mImageView[1].setOnClickListener(this);

        // Default setting
        mInitLayout.setVisibility(View.INVISIBLE);
        mImageView[1].setColorFilter(getResources().getColor(R.color.colorPrimary));
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(this);

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
        mToolbarAction[1].setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View v) {
        if (mToolbarAction[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (mToolbarAction[1].getId() == v.getId()) {
            mIntent.putExtra(Global.GROUP_MEMBER, Member);
            Group G = new Group();
            G.setTitle(mIntent.getStringExtra(Global.GROUP_TITLE));
            G.setDesc(mIntent.getStringExtra(Global.GROUP_DESC));
            G.setMaster((User) mIntent.getSerializableExtra(Global.USER));
            Member.add((User) mIntent.getSerializableExtra(Global.USER));
            G.setMember(Member);
            mIntent.putExtra(Global.GROUP, G);
            setResult(RESULT_OK, mIntent);
            finish();
        }
        if (mImageView[1].getId() == v.getId()) {
            mEditText.setText("");
        }
        if (mMemberLayout.size() != 0) {
            for (int i = 0; i < mMemberLayout.size(); i++) {
                if (mMemberLayout.get(i).equals(v)) {
                    mMemberLayout.remove(i);
                    testAllUser.add(Member.get(i));
                    Member.remove(i);
                    mLinearLayout.removeViewAt(i);
                    mAdapter.notifyDataSetChanged();
                }
            }
            if (mMemberLayout.size() == 0) {
                mLinearLayout.addView(mEmptyMsgView);
                mLinearLayout.addView(mInitLayout);
                mToolbarAction[1].setVisibility(View.INVISIBLE);
            }
            updateListView();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateListView();
    }

    private void updateListView() {
        mAdapter.clear();
        if (mEditText.getText().toString().length() >= 1) {
            mImageView[1].setVisibility(View.VISIBLE);
            String searched = mEditText.getText().toString();
            //test
            for (int i = 0; i < testAllUser.size(); i++) {
                if (testAllUser.get(i).getName().contains(searched) || testAllUser.get(i).getEmail().contains(searched)) {
                    mAdapter.add(testAllUser.get(i));
                }
            }
        } else {
            mImageView[1].setVisibility(View.INVISIBLE);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v.getId() == mEditText.getId()) {
            mImageView[0].clearColorFilter();
            if (hasFocus) {
                mImageView[0].setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
            } else {
                mImageView[0].setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mMemberLayout.size() == 0) mLinearLayout.removeAllViews();
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.invitepeople_miniprofile, null);
        LinearLayout LinearLayout = (LinearLayout) v.findViewById(R.id.UserLayout);
        mMemberLayout.add(0, LinearLayout);
        LinearLayout.setOnClickListener(this);
        TextView TextView = (TextView) v.findViewById(R.id.UserName);
        ImageView ImageView = (ImageView) v.findViewById(R.id.UserProfile);
        TextView.setText(searchUser.get(position).getName());
        ImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));
        mLinearLayout.addView(v, 0);
        Member.add(0, searchUser.get(position));
        testAllUser.remove(searchUser.get(position));
        mAdapter.remove(searchUser.get(position));
        mAdapter.notifyDataSetChanged();
        mToolbarAction[1].setVisibility(View.VISIBLE);
    }
}
