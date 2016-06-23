package com.teamw.ysm0622.app_when.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
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

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.global.Gl;
import com.teamw.ysm0622.app_when.meet.CreateMeet;
import com.teamw.ysm0622.app_when.menu.About;
import com.teamw.ysm0622.app_when.menu.Settings;
import com.teamw.ysm0622.app_when.object.Group;
import com.teamw.ysm0622.app_when.object.Meet;
import com.teamw.ysm0622.app_when.object.User;
import com.teamw.ysm0622.app_when.server.ServerConnection;
import com.kakao.kakaolink.KakaoLink;
import com.kakao.kakaolink.KakaoTalkLinkMessageBuilder;
import com.kakao.util.KakaoParameterException;

import org.apache.http.NameValuePair;

import java.util.ArrayList;

public class GroupManage extends Activity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    // TAG
    private static final String TAG = GroupManage.class.getName();

    // Const
    private static final int mToolBtnNum = 2;
    private static final int mTabBtnNum = 2;

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
    private LinearLayout mEmptyView;
    private FloatingActionButton mFab[];

    // List View
    private ListView mListView[];

    // Adapter
    private UserDataAdapter UserAdapter;
    private MeetDataAdapter MeetAdapter;

    // Data
    private Group g;
    private ArrayList<User> userData = new ArrayList<>();
    private ArrayList<Meet> meetData = new ArrayList<>();

    //Shared Preferences
    private SharedPreferences mSharedPref;
    private SharedPreferences.Editor mEdit;

    private AlertDialog mDialBox;

    public static final int PROGRESS_DIALOG = 1001;
    public ProgressDialog progressDialog;

    public Bitmap temp;
    ImageView ImageView0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groupmanage_drawer);

        mSharedPref = getSharedPreferences(Gl.FILE_NAME_LOGIN, MODE_PRIVATE);
        mEdit = mSharedPref.edit();

        mIntent = getIntent();

        g = (Group) mIntent.getSerializableExtra(Gl.GROUP);
        Log.d("Gl", "GroupId : " + g.getId() + "Enter");
        userData = Gl.getUsers(g);

        Drawable[] toolbarIcon = new Drawable[2];
        toolbarIcon[0] = getResources().getDrawable(R.drawable.ic_menu_white);
        toolbarIcon[1] = getResources().getDrawable(R.drawable.ic_refresh_white_24dp);
        String toolbarTitle = "";
        if (mIntent.getIntExtra(Gl.TAB_NUMBER, 1) == 0)
            toolbarTitle = getResources().getString(R.string.meet_list);
        if (mIntent.getIntExtra(Gl.TAB_NUMBER, 1) == 1)
            toolbarTitle = getResources().getString(R.string.member);


        initEmptyScreen();

        initToolbar(toolbarIcon, toolbarTitle);

        initTabbar(mIntent.getIntExtra(Gl.TAB_NUMBER, 1));

        initNavigationView();

        initialize();

        BackgroundTask mTask = new BackgroundTask();
        mTask.execute(g);


    }

    class BackgroundTask extends AsyncTask<Group, Integer, Integer> {
        protected void onPreExecute() {
            showDialog(PROGRESS_DIALOG);
        }

        @Override
        protected Integer doInBackground(Group... args) {
            String result1 = ServerConnection.getStringFromServer(new ArrayList<NameValuePair>(), Gl.SELECT_MEET_BY_GROUP);//Meeting 정보 가져오기
            String result2 = ServerConnection.getStringFromServer(new ArrayList<NameValuePair>(), Gl.SELECT_MEETDATE_BY_GROUP);//Meeting 정보 가져오기
            ArrayList<NameValuePair> param1 = ServerConnection.SelectTimeByMeet(args[0]);
            String result = ServerConnection.getStringFromServer(param1, Gl.SELECT_TIME_BY_MEET);
            ServerConnection.SelectMeetByGroup(result1);
            ServerConnection.SelectMeetDateByGroup(result2);
            ServerConnection.SelectTimeByMeet(result);
            return null;
        }

        protected void onPostExecute(Integer a) {
            if (progressDialog != null)
                progressDialog.dismiss();
            meetData = Gl.getMeets(g);
            MeetAdapter.clear();
            MeetAdapter.addAll(meetData);
            meetDataEmptyCheck();
            MeetAdapter.notifyDataSetChanged();
        }
    }

    //로딩 다이어로그
    public Dialog onCreateDialog(int id) {
        if (id == PROGRESS_DIALOG) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMessage(getString(R.string.meetInfo_progress));

            return progressDialog;
        }
        return null;
    }

    //Meeting 데이터 확인
    public void meetDataEmptyCheck() {
        if (meetData.size() == 0) {
            mEmptyView.setVisibility(View.VISIBLE);
            mEmptyView.setEnabled(true);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            mEmptyView.setLayoutParams(param);
        } else {
            mEmptyView.setEnabled(false);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
            mEmptyView.setLayoutParams(param);
            Log.w(TAG, "GroupData size : " + meetData.size());
        }
    }

    private void initEmptyScreen() {
        mEmptyView = (LinearLayout) findViewById(R.id.EmptyView);

        ImageView image[] = new ImageView[3];
        TextView text;
        image[0] = (ImageView) findViewById(R.id.emptyImageView0);
        image[1] = (ImageView) findViewById(R.id.emptyImageView1);
        image[2] = (ImageView) findViewById(R.id.emptyImageView2);

        text = (TextView) findViewById(R.id.emptyTextView0);
        image[1].setImageDrawable(getResources().getDrawable(R.drawable.ic_date_range_white_24dp));
        text.setText(R.string.nomeet_msg);
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

    @Override
    protected void onPause() {
        super.onPause(); //save state data (background color) for future use
        //Bitmap이 차지하는 Heap Memory 를 반환한다.
        ImageView0.setImageBitmap(null);
        temp.recycle();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initNavigationView();
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
        mFab[1] = (FloatingActionButton) mTabContent[1].findViewById(R.id.fab);

        mListView[0] = (ListView) mTabContent[0].findViewById(R.id.ListView);
        mListView[1] = (ListView) mTabContent[1].findViewById(R.id.ListView);

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


        MeetAdapter.notifyDataSetChanged();
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
        setRandomNavHeader((int) (Math.random() * 4));
        ImageView0 = (ImageView) mNavView.getHeaderView(0).findViewById(R.id.MyProfile);
        ImageView0.setColorFilter(getResources().getColor(R.color.white));
        TextView TextView0 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyName);
        TextView TextView1 = (TextView) mNavView.getHeaderView(0).findViewById(R.id.MyEmail);

        User user = (User) mIntent.getSerializableExtra(Gl.USER);

        if (user.ImageFilePath != null && !user.ImageFilePath.equals("") && Gl.PROFILES.get(String.valueOf(user.getId())) != null) {//프로필 이미지가 존재
            ImageView0.clearColorFilter();
            temp = BitmapFactory.decodeFile(Gl.ImageFilePath + user.getId() + ".jpg");
            ImageView0.setImageBitmap(Gl.getCircleBitmap(temp));
        } else {
            ImageView0.clearColorFilter();
            temp = Gl.getDefaultImage(user.getId());
            ImageView0.setImageBitmap(Gl.getCircleBitmap(temp));
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

    private void initTabbar(int v) {
        mTabbarAction = new LinearLayout[mTabBtnNum];
        mTabbarImage = new ImageView[mTabBtnNum];
        mTabbarLine = new View[mTabBtnNum];
        mTabContent = new View[mTabBtnNum];

        mTabbarAction[0] = (LinearLayout) findViewById(R.id.Tabbar_Tab0);
        mTabbarAction[1] = (LinearLayout) findViewById(R.id.Tabbar_Tab2);

        mTabbarImage[0] = (ImageView) findViewById(R.id.Tabbar_Image0);
        mTabbarImage[1] = (ImageView) findViewById(R.id.Tabbar_Image2);

        mTabbarLine[0] = (View) findViewById(R.id.Tabbar_Line0);
        mTabbarLine[1] = (View) findViewById(R.id.Tabbar_Line2);

        mTabContent[0] = (View) findViewById(R.id.Include0);
        mTabContent[1] = (View) findViewById(R.id.Include2);

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
            setResult(RESULT_OK, mIntent);//인텐트 공유를 위한 부분
            finish();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_group) {

        } else if (id == R.id.nav_setting) {//환경설정
            mIntent.setClass(GroupManage.this, Settings.class);
            startActivityForResult(mIntent, Gl.GROUPMANAGE_SETTINGS);
        } else if (id == R.id.nav_rate) {//앱 평가
            createDialogBox();
        } else if (id == R.id.nav_about) {//개발자 정보
            startActivity(new Intent(GroupManage.this, About.class));
        } else if (id == R.id.nav_share) {//카카오톡 공유
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
        } else if (id == R.id.nav_logout) {//로그아웃
            logout();
            setResult(Gl.RESULT_LOGOUT);
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == Gl.GROUPMANAGE_CREATEMEET) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                Gl.add((Meet) mIntent.getSerializableExtra(Gl.MEET));
                meetData.add((Meet) mIntent.getSerializableExtra(Gl.MEET));
                MeetAdapter.add((Meet) mIntent.getSerializableExtra(Gl.MEET));
                meetDataEmptyCheck();
                MeetAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == Gl.GROUPMANAGE_INVITEPEOPLE) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                Group g = (Group) mIntent.getSerializableExtra(Gl.GROUP);
                userData.clear();
                userData.addAll(g.getMember());
                UserAdapter.notifyDataSetChanged();
                for (int i = 0; g != null && i < g.getMemberNum(); i++) {
                    Log.w(TAG, "User(" + i + ") : " + g.getMember(i).getName());
                }
                Log.w(TAG, "UserAdapter count  : " + UserAdapter.getCount());
            }
        }
        if (requestCode == Gl.GROUPMANAGE_SELECTDAY) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                Meet m = (Meet) mIntent.getSerializableExtra(Gl.MEET);
                Gl.add(m);
                MeetAdapter.clear();
                MeetAdapter.addAll(Gl.getMeets(g));
                MeetAdapter.notifyDataSetChanged();
            }
        }
        if (requestCode == Gl.GROUPMANAGE_SETTINGS) {
            if (resultCode == RESULT_OK) {
                mIntent = intent;
                initNavigationView();
            }
            if (resultCode == com.teamw.ysm0622.app_when.global.Gl.RESULT_DELETE) {
                setResult(com.teamw.ysm0622.app_when.global.Gl.RESULT_DELETE);
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == mToolbarAction[0].getId()) { // back button
            mDrawer.openDrawer(mNavView);
        }
        if (v.getId() == mToolbarAction[1].getId()) {//Meeting 데이터 새로고침
            BackgroundTask mTask = new BackgroundTask();
            mTask.execute(g);
        }
        if (v.getId() == mTabbarAction[0].getId() || v.getId() == mTabbarAction[1].getId()) {//일정 목록, 구성원 선택
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
                    if (i == 1) {
                        mToolbarTitle.setText(getResources().getString(R.string.member));
                    }
//                    if (i == 2)mToolbarTitle.setText(getResources().getString(R.string.meet_info));
                }
            }
        }
        if (v.equals(mFab[0])) {//일정 생성
            mIntent.setClass(GroupManage.this, CreateMeet.class);
            mIntent.putExtra(Gl.SELECT_DAY_MODE, 0);
            startActivityForResult(mIntent, Gl.GROUPMANAGE_CREATEMEET);
        }
        if (v.equals(mFab[1])) {//그룹으로 새로운 유저 초대
            mIntent.setClass(GroupManage.this, InvitePeople.class);
            mIntent.putExtra(Gl.INVITE_MODE, 1);
            startActivityForResult(mIntent, Gl.GROUPMANAGE_INVITEPEOPLE);
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
