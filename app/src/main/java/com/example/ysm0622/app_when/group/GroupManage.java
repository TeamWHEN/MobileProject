package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.meet.CreateMeet;
import com.example.ysm0622.app_when.menu.About;
import com.example.ysm0622.app_when.menu.RateView;
import com.example.ysm0622.app_when.menu.Settings;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.Meet;
import com.example.ysm0622.app_when.object.User;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.util.ArrayList;
import java.util.Calendar;

public class GroupManage extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // TAG
    private static final String TAG = GroupManage.class.getName();

    // Const
    private static final int mToolBtnNum = 1;
    private static final int mTabBtnNum = 3;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    // Tabbar
    private LinearLayout mTabbarAction[];
    private ImageView mTabbarImage[];
    private View mTabbarLine[];

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    private View mTabContent[];
    private RateView mRateView;

    private FloatingActionButton mFab[];

    private GroupMember mGroupMember;

    // List View
    private ListView mListView[];

    // Adapter
    private UserDataAdapter UserAdapter;
    private MeetDataAdapter MeetAdapter;

    // Data
    private Group G;
    private ArrayList<User> userData = new ArrayList<>();
    private ArrayList<Meet> meetData = new ArrayList<>();

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupmanage_drawer);

        mSharedPref = getSharedPreferences(Global.FILE_NAME_LOGIN, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        String toolbarTitle = "";
        if (mIntent.getIntExtra(Global.TAB_NUMBER, 1) == 0)
            toolbarTitle = getResources().getString(R.string.meet_list);
        if (mIntent.getIntExtra(Global.TAB_NUMBER, 1) == 1)
            toolbarTitle = getResources().getString(R.string.meet_info);
        if (mIntent.getIntExtra(Global.TAB_NUMBER, 1) == 2)
            toolbarTitle = getResources().getString(R.string.member);

        mRateView = new RateView(this);

        initToolbar(toolbarIcon, toolbarTitle);

        initTabbar(mIntent.getIntExtra(Global.TAB_NUMBER, 1));

        initNavigationView();

        initialize();

        // Login Activity에서 Intent 받아서 그룹정보 search

        // Query - Select GROUP_CODE, USER_CODE, GROUP_NAME from GROUPS WHERE GROUP_CODE = @@ (Intent에서 받아온 GROUP_CODE로 그룹 Search)

        // Query - Select GROUP_CODE, COUNT(*) from ACCOUNT-GROUPS GROUP BY GROUP_CODE (그룹별 인원 추출 query)
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavView.setCheckedItem(R.id.nav_group);//nav item home으로 초기화
    }

    private void initialize() {

        // Array allocation
        mFab = new FloatingActionButton[2];
        mListView = new ListView[2];

        // Create instance
        UserAdapter = new UserDataAdapter(this, R.layout.member_item, userData);
        MeetAdapter = new MeetDataAdapter(this, R.layout.meet_item, meetData, mIntent);

        // View allocation
        mFab[0] = (FloatingActionButton) mTabContent[0].findViewById(R.id.fab);
        mFab[1] = (FloatingActionButton) mTabContent[2].findViewById(R.id.fab);

        mListView[0] = (ListView) mTabContent[0].findViewById(R.id.ListView);
        mListView[1] = (ListView) mTabContent[2].findViewById(R.id.ListView);

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        // Add listener
        for (int i = 0; i < 2; i++)
            mFab[i].setOnClickListener(this);

        // Default setting
        mListView[0].setAdapter(MeetAdapter);
        mListView[0].setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        mListView[1].setAdapter(UserAdapter);
        mListView[1].setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        G = (Group) mIntent.getSerializableExtra(Global.GROUP);
        userData = G.getMember();
        for (int i = 0; i < userData.size(); i++) {
            UserAdapter.add(G.getMember(i));
        }
        UserAdapter.notifyDataSetChanged();
    }

    private void initNavigationView() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        float mScale = getResources().getDisplayMetrics().density;
        int width = (int) (dm.widthPixels - (56 * mScale + 0.5f));
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        param.height = (int) (width * 9.0 / 16.0);
        mNavView.getHeaderView(0).setLayoutParams(param);
        ImageView ImageView0 = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.MyProfile);
        ImageView0.setColorFilter(getResources().getColor(R.color.white));
        TextView TextView0 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyName);
        TextView TextView1 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyEmail);
        User user = (User) mIntent.getSerializableExtra(Global.USER);
        TextView0.setText(user.getName());
        TextView1.setText(user.getEmail());
        mNavView.setNavigationItemSelectedListener(this);
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

    private void initTabbar(int v) {
        mTabbarAction = new LinearLayout[mTabBtnNum];
        mTabbarImage = new ImageView[mTabBtnNum];
        mTabbarLine = new View[mTabBtnNum];
        mTabContent = new View[mTabBtnNum];

        mTabbarAction[0] = (LinearLayout) findViewById(R.id.Tabbar_Tab0);
        mTabbarAction[1] = (LinearLayout) findViewById(R.id.Tabbar_Tab1);
        mTabbarAction[2] = (LinearLayout) findViewById(R.id.Tabbar_Tab2);

        mTabbarImage[0] = (ImageView) findViewById(R.id.Tabbar_Image0);
        mTabbarImage[1] = (ImageView) findViewById(R.id.Tabbar_Image1);
        mTabbarImage[2] = (ImageView) findViewById(R.id.Tabbar_Image2);

        mTabbarLine[0] = (View) findViewById(R.id.Tabbar_Line0);
        mTabbarLine[1] = (View) findViewById(R.id.Tabbar_Line1);
        mTabbarLine[2] = (View) findViewById(R.id.Tabbar_Line2);

        mTabContent[0] = (View) findViewById(R.id.Include0);
        mTabContent[1] = (View) findViewById(R.id.Include1);
        mTabContent[2] = (View) findViewById(R.id.Include2);

        for (int i = 0; i < mTabBtnNum; i++) {
            mTabbarAction[i].setOnClickListener(this);
            mTabbarImage[i].setColorFilter(getResources().getColor(R.color.grey4));
            mTabContent[i].setVisibility(View.GONE);
        }

        mTabbarImage[v].setColorFilter(getResources().getColor(R.color.white));
        mTabbarLine[v].setBackgroundColor(getResources().getColor(R.color.white));
        mTabContent[v].setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_group) {

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(GroupManage.this, Settings.class));
        } else if (id == R.id.nav_rate) {
            mRateView.show();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(GroupManage.this, About.class));
        } else if (id == R.id.nav_share) {
            try {
                final KakaoLink kakaoLink = KakaoLink.getKakaoLink(this);
                final KakaoTalkLinkMessageBuilder kakaoBuilder = kakaoLink.createKakaoTalkLinkMessageBuilder();

            /*메시지 추가*/
                //kakaoBuilder.addText("편리한 시간 관리 앱 WHEN");

            /*이미지 가로/세로 사이즈는 80px 보다 커야하며, 이미지 용량은 500kb 이하로 제한된다.*/
                String url = "http://upload2.inven.co.kr/upload/2015/09/27/bbs/i12820605286.jpg";
                kakaoBuilder.addImage(url, 1080, 1920);
            /*앱 실행버튼 추가*/
                kakaoBuilder.addAppButton("설치");

            /*앱 링크 추가*/
                kakaoBuilder.addAppLink("편리한 그룹 일정 관리 앱 WHEN");

                kakaoBuilder.build();
            /*메시지 발송*/
                kakaoLink.sendMessage(kakaoBuilder, this);
            } catch (KakaoParameterException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_logout) {
            logout();
            setResult(RESULT_CANCELED);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Global.GROUPMANAGE_CREATEMEET) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                MeetAdapter.add((Meet) intent.getSerializableExtra(Global.MEET));
                MeetAdapter.notifyDataSetChanged();
                TextView mTextView = (TextView) mTabContent[0].findViewById(R.id.TextView0);
                mTextView.setVisibility(View.INVISIBLE);
                mTextView.setEnabled(false);
                mTextView.setHeight(0);
            }
        }
        if (requestCode == Global.GROUPMANAGE_INVITEPEOPLE) {
            mIntent = intent;
        }
        if (requestCode == Global.GROUPMANAGE_SELECTDAY) {
            mIntent = intent;
            Meet m = (Meet) mIntent.getSerializableExtra(Global.MEET);
            ArrayList<Calendar> startTime = m.getDateTime().getStartTime();
            ArrayList<Calendar> endTime = m.getDateTime().getEndTime();
            for (int i = 0; i < startTime.size(); i++) {
                Log.w(TAG, startTime.get(i).get(Calendar.MONTH) + "/" + startTime.get(i).get(Calendar.DATE) + " " + startTime.get(i).get(Calendar.HOUR_OF_DAY) + "시 ~ " + endTime.get(i).get(Calendar.MONTH) + "/" + endTime.get(i).get(Calendar.DATE) + "일 " + endTime.get(i).get(Calendar.HOUR_OF_DAY) + "시");
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            mDrawer.openDrawer(mNavView);
        }
        if (v.getId() == mTabbarAction[0].getId() || v.getId() == mTabbarAction[1].getId() || v.getId() == mTabbarAction[2].getId()) {
            for (int i = 0; i < mTabBtnNum; i++) {
                mTabbarImage[i].clearColorFilter();
                mTabbarImage[i].setColorFilter(getResources().getColor(R.color.grey4), PorterDuff.Mode.SRC_ATOP);
                mTabbarLine[i].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                mTabContent[i].setVisibility(View.GONE);
                if (v.getId() == mTabbarAction[i].getId()) {
                    mTabbarImage[i].setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                    mTabbarLine[i].setBackgroundColor(getResources().getColor(R.color.white));
                    mTabContent[i].setVisibility(View.VISIBLE);
                    if (i == 0) mToolbarTitle.setText(getResources().getString(R.string.meet_list));
                    if (i == 1) mToolbarTitle.setText(getResources().getString(R.string.meet_info));
                    if (i == 2) mToolbarTitle.setText(getResources().getString(R.string.member));
                }
            }
        }
        if (v.equals(mFab[0])) {
            mIntent.setClass(GroupManage.this, CreateMeet.class);
            mIntent.putExtra(Global.SELECT_DAY_MODE, 0);
            startActivityForResult(mIntent, Global.GROUPMANAGE_CREATEMEET);
        }
        if (v.equals(mFab[1])) {
            mIntent.setClass(GroupManage.this, InvitePeople.class);
            startActivityForResult(mIntent, Global.GROUPMANAGE_INVITEPEOPLE);
        }
    }
    //Remove Shared Preferences of LOGIN_DATA
    public void logout(){
        mEdit.clear();
        mEdit.commit();
    }
}
