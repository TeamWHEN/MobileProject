package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.menu.About;
import com.example.ysm0622.app_when.menu.Settings;
import com.example.ysm0622.app_when.object.Group;

import java.util.ArrayList;

public class GroupList extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private static final String TAG = "GroupList";
    private static final int mToolBtnNum = 1;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    private ArrayList<Group> groupData = new ArrayList<Group>();
    private GroupDataAdapter adapter;
    private TextView mTextView;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist_drawer);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        String toolbarTitle = getResources().getString(R.string.title_activity_group_list);

        initToolbar(toolbarIcon, toolbarTitle);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(GroupList.this, CreateGroup.class), 1000);
            }
        });

        mNavView = (NavigationView) findViewById(R.id.nav_view);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavView.setNavigationItemSelectedListener(this);

        mTextView = (TextView) findViewById(R.id.TextView0);

        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new GroupDataAdapter(this, R.layout.group_item, groupData);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Intent mIntent = new Intent(GroupList.this, GroupManage.class);
                Bundle mBundle = new Bundle();
                TextView mTextView = (TextView) v.findViewById(R.id.TextView0);
                String mTitle = mTextView.getText().toString();
                mBundle.putString("Title", mTitle);
                mIntent.putExtras(mBundle);
                startActivity(mIntent);
            }
        });

        // Login Activity에서 Intent 받아서 그룹정보 search

        // Query - Select GROUP_CODE, USER_CODE, GROUP_NAME from GROUPS WHERE GROUP_CODE = @@ (Intent에서 받아온 GROUP_CODE로 그룹 Search)

        // Query - Select GROUP_CODE, COUNT(*) from ACCOUNT-GROUPS GROUP BY GROUP_CODE (그룹별 인원 추출 query)
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

        } else if (id == R.id.nav_about) {
            startActivity(new Intent(GroupList.this, About.class));
        } else if (id == R.id.nav_share) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1000) {
            if (resultCode == RESULT_OK) {
                Group G = new Group();
                Bundle mBundle = intent.getExtras();
                G.setTitle(mBundle.getString("Title"));
                G.setDesc(mBundle.getString("Desc"));
                adapter.add(G);
                adapter.notifyDataSetChanged();
                mTextView.setVisibility(View.INVISIBLE);
                mTextView.setEnabled(false);
                mTextView.setHeight(0);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            mDrawer.openDrawer(mNavView);
        }
    }
}
