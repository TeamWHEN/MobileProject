package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.menu.About;
import com.example.ysm0622.app_when.menu.RateView;
import com.example.ysm0622.app_when.menu.Settings;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.User;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import java.util.ArrayList;

public class GroupList extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // TAG
    private static final String TAG = GroupList.class.getName();

    // Const
    private static final int mToolBtnNum = 1;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    private ArrayList<Group> groupData = new ArrayList<Group>();
    private GroupDataAdapter adapter;
    private TextView mTextView;
    private ListView mListView;
    private RateView mRateView;

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist_drawer);

        mSharedPref = getSharedPreferences(Global.FILE_NAME_LOGIN, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mIntent = getIntent();

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_group_list);

        initToolbar(toolbarIcon, toolbarTitle);

        initNavigationView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.setClass(GroupList.this, CreateGroup.class);
                startActivityForResult(mIntent, 1000);
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mTextView = (TextView) findViewById(R.id.TextView0);

        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new GroupDataAdapter(this, R.layout.group_item, groupData, mIntent);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                mIntent.setClass(GroupList.this, GroupManage.class);
                mIntent.putExtra(Global.GROUP, groupData.get(position));
                mIntent.putExtra(Global.TAB_NUMBER, 1);
                startActivityForResult(mIntent, 1001);
            }
        });

        mRateView = new RateView(this);

        // Login Activity에서 Intent 받아서 그룹정보 search

        // Query - Select GROUP_CODE, USER_CODE, GROUP_NAME from GROUPS WHERE GROUP_CODE = @@ (Intent에서 받아온 GROUP_CODE로 그룹 Search)

        // Query - Select GROUP_CODE, COUNT(*) from ACCOUNT-GROUPS GROUP BY GROUP_CODE (그룹별 인원 추출 query)
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavView.setCheckedItem(R.id.nav_home);//nav item home으로 초기화
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

        if (id == R.id.nav_home) {

        } else if (id == R.id.nav_setting) {
            startActivity(new Intent(GroupList.this, Settings.class));
        } else if (id == R.id.nav_rate) {
            mRateView.show();
        } else if (id == R.id.nav_about) {
            startActivity(new Intent(GroupList.this, About.class));
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
                kakaoBuilder.addAppLink("편리한 시간 관리 앱 WHEN");

                kakaoBuilder.build();
            /*메시지 발송*/
                kakaoLink.sendMessage(kakaoBuilder, this);
            } catch (KakaoParameterException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_logout) {
            logout();
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                adapter.add((Group) mIntent.getSerializableExtra(Global.GROUP));
                adapter.notifyDataSetChanged();
                mTextView.setVisibility(View.INVISIBLE);
                mTextView.setEnabled(false);
                mTextView.setHeight(0);
            }
        }
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {

                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            mDrawer.openDrawer(mNavView);
        }
    }

    //Remove Shared Preferences of LOGIN_DATA
    public void logout() {
        mEdit.clear();
        mEdit.commit();
    }

}
