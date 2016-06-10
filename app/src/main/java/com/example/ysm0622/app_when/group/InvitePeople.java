package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
    private static final String TAG = InvitePeople.class.getName();

    // Const
    private static final int mToolBtnNum = 2;
    private int MODE;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.invitepeople_main);

        // Receive intent
        mIntent = getIntent();
        MODE = mIntent.getIntExtra(Global.INVITE_MODE, 0);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_done_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_invite_people);

        initToolbar(toolbarIcon, toolbarTitle);

        initialize();

        testAllUser.addAll(Global.getUsers());

        if (MODE == 1) {
            mLinearLayout.removeAllViews();
            initData();
        }
        Group g = (Group) mIntent.getSerializableExtra(Global.GROUP);
        for (int i = 0; g!=null && i < g.getMemberNum(); i++) {
            Log.w(TAG, "User(" + i + ") : " + g.getMember(i));
        }
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
        if (MODE == 1)
            mToolbarAction[1].setVisibility(View.VISIBLE);
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

    private void initData() {
        Group g = (Group) mIntent.getSerializableExtra(Global.GROUP);
        Member = g.getMember();
        for (int i = 0; i < Member.size(); i++) {
            findMember(testAllUser, Member.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    private void findMember(ArrayList<User> arrayList, User user) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i).getId() == user.getId()) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View v = vi.inflate(R.layout.invitepeople_miniprofile, null);

                LinearLayout LinearLayout = (LinearLayout) v.findViewById(R.id.UserLayout);
                ImageView ImageView = (ImageView) v.findViewById(R.id.UserProfile);
                TextView TextView = (TextView) v.findViewById(R.id.UserName);

                mMemberLayout.add(0, LinearLayout);

                TextView.setText(user.getName());
                ImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));

                mLinearLayout.addView(v, 0);
                arrayList.remove(i);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mToolbarAction[0].getId() == v.getId()) {
            super.onBackPressed();
        }
        if (mToolbarAction[1].getId() == v.getId()) {
            if (MODE == 0) {
                Group G = new Group();
                G.setTitle(mIntent.getStringExtra(Global.GROUP_TITLE));
                G.setDesc(mIntent.getStringExtra(Global.GROUP_DESC));
                G.setMaster((User) mIntent.getSerializableExtra(Global.USER));
                Member.add((User) mIntent.getSerializableExtra(Global.USER));
                G.setMember(Member);
                mIntent.putExtra(Global.GROUP, G);
                setResult(RESULT_OK, mIntent);
                finish();
            } else if (MODE == 1) {
                Group g = (Group) mIntent.getSerializableExtra(Global.GROUP);
                g.setMember(Member);
                mIntent.putExtra(Global.GROUP, g);
                setResult(RESULT_OK, mIntent);
                finish();
            }
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
            updateListView(mEditText.getText());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        updateListView(s);
    }

    private void updateListView(CharSequence s) {
        mAdapter.clear();
        if (mEditText.getText().toString().length() >= 1) {
            mImageView[1].setVisibility(View.VISIBLE);
            //test
            for (int i = 0; i < testAllUser.size(); i++) {
                String keyWord = s.toString();
                String searchData = testAllUser.get(i).getName();
                boolean isData = SoundSearcher.matchString(searchData, keyWord);
                if (isData || testAllUser.get(i).getName().contains(keyWord) || testAllUser.get(i).getEmail().contains(keyWord)) {
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

    private void addMember(int position) {
        if (mMemberLayout.size() == 0) mLinearLayout.removeAllViews();
        LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = vi.inflate(R.layout.invitepeople_miniprofile, null);

        LinearLayout LinearLayout = (LinearLayout) v.findViewById(R.id.UserLayout);
        ImageView ImageView = (ImageView) v.findViewById(R.id.UserProfile);
        TextView TextView = (TextView) v.findViewById(R.id.UserName);

        mMemberLayout.add(0, LinearLayout);
        LinearLayout.setOnClickListener(this);

        TextView.setText(searchUser.get(position).getName());
        ImageView.setColorFilter(getResources().getColor(R.color.colorPrimary));

        mLinearLayout.addView(v, 0);
        Member.add(0, searchUser.get(position));
        testAllUser.remove(searchUser.get(position));
        mAdapter.remove(searchUser.get(position));
        mAdapter.notifyDataSetChanged();
        mToolbarAction[1].setVisibility(View.VISIBLE);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        addMember(position);
    }
}
