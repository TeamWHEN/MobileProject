package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.meet.CreateMeet;
import com.example.ysm0622.app_when.menu.About;
import com.example.ysm0622.app_when.menu.Settings;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

public class GroupManage extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "GroupList";
    private static final int mToolBtnNum = 1;
    private static final int mTabBtnNum = 3;

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

    private FloatingActionButton mFab[];

    private GroupMember mGroupMember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupmanage_drawer);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        String toolbarTitle = getResources().getString(R.string.meet_info);

        initToolbar(toolbarIcon, toolbarTitle);

        initTabbar();

        initialize();

        // Login Activity에서 Intent 받아서 그룹정보 search

        // Query - Select GROUP_CODE, USER_CODE, GROUP_NAME from GROUPS WHERE GROUP_CODE = @@ (Intent에서 받아온 GROUP_CODE로 그룹 Search)

        // Query - Select GROUP_CODE, COUNT(*) from ACCOUNT-GROUPS GROUP BY GROUP_CODE (그룹별 인원 추출 query)
    }

    private void initialize() {

        // Array allocation
        mFab = new FloatingActionButton[2];

        // Create instance

        // View allocation
        mFab[0] = (FloatingActionButton) mTabContent[0].findViewById(R.id.fab);
        mFab[1] = (FloatingActionButton) mTabContent[2].findViewById(R.id.fab);

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        // Add listener
        for (int i = 0; i < 2; i++)
            mFab[i].setOnClickListener(this);

        mNavView.setNavigationItemSelectedListener(this);

        // Default setting
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

    private void initTabbar() {
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
            mTabbarImage[i].setColorFilter(getResources().getColor(R.color.grey4), PorterDuff.Mode.SRC_ATOP);
            mTabContent[i].setVisibility(View.GONE);
        }

        mTabbarLine[1].setBackgroundColor(getResources().getColor(R.color.white));
        mTabContent[1].setVisibility(View.VISIBLE);
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
            startActivity(new Intent(GroupManage.this, Settings.class));
        } else if (id == R.id.nav_rate) {

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
                kakaoBuilder.addAppLink("편리한 시간 관리 앱 WHEN");

                kakaoBuilder.build();
            /*메시지 발송*/
                kakaoLink.sendMessage(kakaoBuilder, this);
            } catch (KakaoParameterException e) {
                e.printStackTrace();
            }
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

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
            startActivityForResult(new Intent(GroupManage.this, CreateMeet.class), 1000);
        }
        if (v.equals(mFab[1])) {
            startActivityForResult(new Intent(GroupManage.this, InvitePeople.class), 1001);
        }
    }
}
