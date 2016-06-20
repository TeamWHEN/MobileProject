package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.menu.About;
import com.example.ysm0622.app_when.menu.Settings;
import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.object.User;
import com.example.ysm0622.app_when.server.ServerConnection;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class GroupList extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // TAG
    private static final String TAG = GroupList.class.getName();

    // Const
    private static final int mToolBtnNum = 2;
    public static final int PROGRESS_DIALOG = 1001;
    public static final int DELETEGROUP_DIALOG = 1002;

    // Intent
    private Intent mIntent;

    // Toolbar
    private ImageView mToolbarAction[];
    private TextView mToolbarTitle;

    private DrawerLayout mDrawer;
    private NavigationView mNavView;
    public static ArrayList<Group> groupData = new ArrayList<>();
    private GroupDataAdapter adapter;
    private static LinearLayout mEmptyView;
    private ListView mListView;

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    private AlertDialog mDialBox;
    public ProgressDialog progressDialog;

    private User u;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grouplist_drawer);

//        new ServerConnection().execute(Gl.SELECT_ALL_GROUP);
//        new ServerConnection().execute(Gl.SELECT_ALL_USERGROUP);
        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LOGIN, MODE_PRIVATE);
        mEdit = mSharedPref.edit();
        mIntent = getIntent();

        // Init group data
        u = (User) mIntent.getSerializableExtra(Gl.USER);
        Gl.MyUser = u;

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_refresh_white_24dp);
        String toolbarTitle = getResources().getString(R.string.title_activity_group_list);

        initEmptyScreen();

        initToolbar(toolbarIcon, toolbarTitle);

        initNavigationView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIntent.setClass(GroupList.this, CreateGroup.class);
                mIntent.putExtra(Gl.INVITE_MODE, 0);
                startActivityForResult(mIntent, Gl.GROUPLIST_CREATEGROUP);
            }
        });

        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.setDrawerListener(toggle);
        toggle.syncState();

        mListView = (ListView) findViewById(R.id.ListView);
        adapter = new GroupDataAdapter(this, R.layout.group_item, groupData, mIntent);
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Log.e("TAG", "" + position);
                mIntent.setClass(GroupList.this, GroupManage.class);
                mIntent.putExtra(Gl.GROUP, groupData.get(position));
                mIntent.putExtra(Gl.TAB_NUMBER, 1);
                startActivityForResult(mIntent, Gl.GROUPLIST_GROUPMANAGE);

            }
        });
        BackgroundTask mTask = new BackgroundTask();
        mTask.execute();
    }

    public static void groupDataEmptyCheck() {
        if (groupData.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setEnabled(true);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mEmptyView.setLayoutParams(param);
        } else {
            mEmptyView.setEnabled(false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            mEmptyView.setLayoutParams(param);
        }
        Log.w(TAG, "GroupData size : " + groupData.size());
    }

    class BackgroundTask extends AsyncTask<Integer, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(Integer... arg0) {
            String result1 = ServerConnection.getStringFromServer(new ArrayList<NameValuePair>(), Gl.SELECT_ALL_GROUP);
            String result2 = ServerConnection.getStringFromServer(new ArrayList<NameValuePair>(), Gl.SELECT_ALL_USERGROUP);
            ServerConnection.SelectAllGroup(result1);
            ServerConnection.SelectAllUserGroup(result2);
            return null;
        }

        protected void onPostExecute(Integer a) {
            groupData = Gl.getGroupsByUserId(Gl.MyUser.getId());
            adapter.clear();
            adapter.addAll(groupData);
            groupDataEmptyCheck();
            adapter.notifyDataSetChanged();
            if (progressDialog != null)
                dismissDialog(PROGRESS_DIALOG);
        }
    }

    public Dialog onCreateDialog(int id) {
        progressDialog = new ProgressDialog(this);
        if (id == PROGRESS_DIALOG) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.groupinfo_progress));
            progressDialog.setCancelable(false);
            return progressDialog;
        }
        if (id == DELETEGROUP_DIALOG) {
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.groupleave_progress));
            progressDialog.setCancelable(false);
            return progressDialog;
        }
        return null;
    }

    private void initEmptyScreen() {
        mEmptyView = (LinearLayout) findViewById(R.id.EmptyView);

        ImageView image[] = new ImageView[3];
        TextView text;
        image[0] = (ImageView) findViewById(R.id.emptyImageView0);
        image[1] = (ImageView) findViewById(R.id.emptyImageView1);
        image[2] = (ImageView) findViewById(R.id.emptyImageView2);

        text = (TextView) findViewById(R.id.emptyTextView0);

        for (int i = 0; i < 2; i++) {
            image[i].setColorFilter(getResources().getColor(R.color.colorPrimary));
        }
        for (int i = 0; i < image.length; i++) {
            image[i].setAlpha((float) 0.4);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNavView.setCheckedItem(R.id.nav_group);//nav item home으로 초기화
    }

    private void initNavigationView() {
        DisplayMetrics dm = getApplicationContext().getResources().getDisplayMetrics();
        float mScale = getResources().getDisplayMetrics().density;
        int width = (int) (dm.widthPixels - (56 * mScale + 0.5f));
        mNavView = (NavigationView) findViewById(R.id.nav_view);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0);
        param.height = (int) (width * 9.0 / 16.0);
        mNavView.getHeaderView(0).setLayoutParams(param);
        setRandomNavHeader((int) (Math.random() * 4));
        ImageView ImageView0 = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.MyProfile);
        ImageView0.setColorFilter(getResources().getColor(R.color.white));
        TextView TextView0 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyName);
        TextView TextView1 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyEmail);

        User user = (User) mIntent.getSerializableExtra(Gl.USER);

        if (user.ImageFilePath != null && !user.ImageFilePath.equals("")) {//프로필 이미지가 존재
            ImageView0.clearColorFilter();
            ImageView0.setImageBitmap(Gl.getCircleBitmap(Gl.PROFILES.get(String.valueOf(u.getId()))));
        }

        TextView0.setText(user.getName());
        TextView1.setText(user.getEmail());
        mNavView.setNavigationItemSelectedListener(this);
    }

    private void setRandomNavHeader(int i) {
        if (i == 0)
            mNavView.getHeaderView(0).setBackground(getResources().getDrawable(R.drawable.wallpaper1_resize));
        if (i == 1)
            mNavView.getHeaderView(0).setBackground(getResources().getDrawable(R.drawable.wallpaper2_resize));
        if (i == 2)
            mNavView.getHeaderView(0).setBackground(getResources().getDrawable(R.drawable.wallpaper3_resize));
        if (i == 3)
            mNavView.getHeaderView(0).setBackground(getResources().getDrawable(R.drawable.wallpaper4_resize));
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
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_group) {

        } else if (id == R.id.nav_setting) {
            mIntent.setClass(GroupList.this, Settings.class);
            startActivityForResult(mIntent, Gl.GROUPLIST_SETTINGS);
        } else if (id == R.id.nav_rate) {
            createDialogBox();
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
                kakaoBuilder.addAppLink("편리한 그룹 일정 관리 앱 WHEN");

                kakaoBuilder.build();
            /*메시지 발송*/
                kakaoLink.sendMessage(kakaoBuilder, this);
            } catch (KakaoParameterException e) {
                e.printStackTrace();
            }
        } else if (id == R.id.nav_logout) {
            logout();
            setResult(Gl.RESULT_LOGOUT);
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.GROUPLIST_CREATEGROUP) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                Group g = (Group) mIntent.getSerializableExtra(Gl.GROUP);
                groupData.add(g);
                adapter.add(g);
                groupDataEmptyCheck();
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == Gl.GROUPLIST_GROUPMANAGE) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                initNavigationView();
            }
            if (resultCode == Gl.RESULT_LOGOUT) {
                finish();
            }
            if (resultCode == Gl.RESULT_DELETE) {
                setResult(Gl.RESULT_DELETE);
                finish();
            }
        }
        if (requestCode == Gl.GROUPLIST_SETTINGS) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                initNavigationView();
            }
            if (resultCode == Gl.RESULT_DELETE) {
                setResult(Gl.RESULT_DELETE);
                finish();
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            mDrawer.openDrawer(mNavView);
        }
        if (v.getId() == mToolbarAction[1].getId()) {
            BackgroundTask mTask = new BackgroundTask();
            mTask.execute();
        }
    }

    //Remove Shared Preferences of LOGIN_DATA
    public void logout() {
        mEdit.clear();
        mEdit.commit();
    }

    //평가 다이어로그
    public void createDialogBox() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.rate_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.drop_title);
        TextView Content = (TextView) view.findViewById(R.id.drop_content);
        ImageView Btn[] = new ImageView[3];
        Btn[0] = (ImageView) view.findViewById(R.id.drop_btn1);
        Btn[1] = (ImageView) view.findViewById(R.id.drop_btn2);
        Btn[2] = (ImageView) view.findViewById(R.id.drop_btn3);

        for (int i = 0; i < 3; i++) {
            Btn[i].setColorFilter(getResources().getColor(R.color.colorAccent));
        }

        Btn[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
                mNavView.setCheckedItem(R.id.nav_group);
            }
        });
        Btn[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
                mNavView.setCheckedItem(R.id.nav_group);
            }
        });
        Btn[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
                mNavView.setCheckedItem(R.id.nav_group);
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }
}
