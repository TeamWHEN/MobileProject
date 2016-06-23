package com.teamw.ysm0622.app_when.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamw.ysm0622.app_when.R;
import com.teamw.ysm0622.app_when.global.Gl;
import com.teamw.ysm0622.app_when.meet.PollState;
import com.teamw.ysm0622.app_when.meet.SelectDay;
import com.teamw.ysm0622.app_when.object.Group;
import com.teamw.ysm0622.app_when.object.Meet;
import com.teamw.ysm0622.app_when.object.MeetDate;
import com.teamw.ysm0622.app_when.object.User;

import java.util.ArrayList;
import java.util.Date;

public class MeetDataAdapter extends ArrayAdapter<Meet> {

    // TAG
    private static final String TAG = MeetDataAdapter.class.getName();

    // Const
    private static final int TEXT_NUM = 5;
    private static final int ICON_NUM = 4;
    private static final int BTN_NUM = 4;

    // Intent
    private Intent mIntent;

    // Context
    private final Context mContext;

    // Data
    private ArrayList<Meet> values = new ArrayList<>();

    private AlertDialog mDialBox;
    //Shared Preferences
    SharedPreferences mSharedPref;
    SharedPreferences.Editor mEdit;

    public Bitmap temp;

    public MeetDataAdapter(Context context, int resource, ArrayList<Meet> values, Intent intent) {
        super(context, resource, values);
        this.mContext = context;
        this.values = values;
        this.mIntent = intent;

        mSharedPref = mContext.getSharedPreferences(Gl.FILE_NAME_MEET, mContext.MODE_PRIVATE);
        mEdit = mSharedPref.edit();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.meet_item, null);
        }
        final Meet m = values.get(position);
        if (m != null) {
            TextView mTextView[] = new TextView[TEXT_NUM];
            ImageView mImageViewProfile;
            ImageView mImageViewIcon[] = new ImageView[ICON_NUM];
            final ImageView mImageViewBtn[] = new ImageView[BTN_NUM];

            mTextView[0] = (TextView) v.findViewById(R.id.TextView0);
            mTextView[1] = (TextView) v.findViewById(R.id.TextView1);
            mTextView[2] = (TextView) v.findViewById(R.id.TextView2);
            mTextView[3] = (TextView) v.findViewById(R.id.TextView3);
            mTextView[4] = (TextView) v.findViewById(R.id.TextView4);

            mImageViewProfile = (ImageView) v.findViewById(R.id.ImageView0);

            mImageViewIcon[0] = (ImageView) v.findViewById(R.id.ImageView1);
            mImageViewIcon[1] = (ImageView) v.findViewById(R.id.ImageView2);
            mImageViewIcon[2] = (ImageView) v.findViewById(R.id.ImageView3);
            mImageViewIcon[3] = (ImageView) v.findViewById(R.id.ImageView4);

            mImageViewBtn[0] = (ImageView) v.findViewById(R.id.ImageView5);
            mImageViewBtn[1] = (ImageView) v.findViewById(R.id.ImageView6);
            mImageViewBtn[2] = (ImageView) v.findViewById(R.id.ImageView7);
            mImageViewBtn[3] = (ImageView) v.findViewById(R.id.ImageView8);

            LinearLayout mLocationLayout = (LinearLayout) v.findViewById(R.id.LocationLayout);

            if (!Gl.getUser(m.getMasterId()).getImageFilePath().equals("")) {//유저 프로필 이미지 존재
                mImageViewProfile.clearColorFilter();
                temp = BitmapFactory.decodeFile(Gl.ImageFilePath + m.getMasterId() + ".jpg");
                mImageViewProfile.setImageBitmap(Gl.getCircleBitmap(temp));
            } else {//default 이미지 사용
                mImageViewProfile.clearColorFilter();
                temp = Gl.getDefaultImage(m.getMasterId());
                mImageViewProfile.setImageBitmap(Gl.getCircleBitmap(temp));
            }
            for (int i = 0; i < ICON_NUM; i++) {
                mImageViewIcon[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }
            for (int i = 0; i < BTN_NUM; i++) {
                mImageViewBtn[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }

            mTextView[0].setText(m.getTitle());
            mTextView[1].setText(m.getMaster().getName());
            mTextView[2].setText(m.getDateTimeNum() + " / " + m.getGroup().getMemberNum());
            mTextView[3].setText(m.getLocation());

            //년, 월, 일 시간 표시
            if (m.MeetDate != null && m.getMeetDate().size() > 0) {
                MeetDate md = m.getMeetDate().get(0);
                Date d = new Date(md.getDate());
                String str = (d.getYear() + 1900) + "년 " + (d.getMonth() + 1) + "월 " + d.getDate() + "일";
                if (m.getMeetDate().size() != 1)
                    str += " 외 " + (m.getMeetDate().size() - 1) + "일";
                mTextView[4].setText(str);
            }

            if (m.getLocation().equals("")) mLocationLayout.setVisibility(View.GONE);

            //투표 현황
            mImageViewBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((Gl.MyUser.getId() == m.getMasterId()) || (m.getGroup().Member.size() == m.getDateTimeNum())) {//Complete vote or master user
                        mIntent.setClass(mContext, PollState.class);
                        mIntent.putExtra(Gl.MEET, m);
                        ((Activity) mContext).startActivityForResult(mIntent, Gl.GROUPMANAGE_POLLSTATE);
                        Gl.LogAllMeet();
                    } else {//Incomplete vote
                        Toast.makeText(mContext, R.string.incomplete_vote_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            //일정 날짜 선택
            mImageViewBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, SelectDay.class);
                    mIntent.putExtra(Gl.MEET, m);
                    mIntent.putExtra(Gl.SELECT_DAY_MODE, 1);
                    ((Activity) mContext).startActivityForResult(mIntent, Gl.GROUPMANAGE_SELECTDAY);
                    Gl.LogAllMeet();
                }
            });

            //투표한 그룹원 표시
            mImageViewBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    pollStateDialogBox(m);
                }
            });

            //Meeting 알람 설정
            mImageViewBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mSharedPref.contains(Gl.MEET_NOTICE + m.getId())) {
                        if (mSharedPref.getBoolean(Gl.MEET_NOTICE + m.getId(), false)) {//OFF
                            mEdit.putBoolean(Gl.MEET_NOTICE + m.getId(), false);
                            mImageViewBtn[3].setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
                            Toast.makeText(mContext, R.string.noti_off, Toast.LENGTH_SHORT).show();
                        } else {//ON
                            mEdit.putBoolean(Gl.MEET_NOTICE + m.getId(), true);
                            mImageViewBtn[3].setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications));
                            Toast.makeText(mContext, R.string.noti_on, Toast.LENGTH_SHORT).show();
                        }
                    } else {//가장 처음 알림 설정 OFF 하면 발생        설정 안했을시 기본은 알림 설정 ON
                        mEdit.putBoolean(Gl.MEET_NOTICE + m.getId(), false);
                        mImageViewBtn[3].setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
                        Toast.makeText(mContext, R.string.noti_off, Toast.LENGTH_SHORT).show();
                    }
                    mEdit.commit();
                }
            });
        }
        return v;
    }

    //투표한 유저 확인
    public void pollStateDialogBox(final Meet m) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.member_alert, null);

        PollStateAdapter mAdapter;
        ArrayList<User> pollUserData = new ArrayList<>();
        TextView Title = (TextView) view.findViewById(R.id.title);
        ListView ListView = (ListView) view.findViewById(R.id.ListView);
        TextView Btn = (TextView) view.findViewById(R.id.btn);

        Group g = (Group) mIntent.getSerializableExtra(Gl.GROUP);
        pollUserData.addAll(g.getMember());

        mAdapter = new PollStateAdapter(mContext, R.layout.member_alert_item, pollUserData, m);
        ListView.setAdapter(mAdapter);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
            }
        });//취소

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }

    public class PollStateAdapter extends ArrayAdapter<User> {

        private Context mContext;
        private ArrayList<User> values = new ArrayList<>();
        private Meet m;

        public PollStateAdapter(Context context, int resource, ArrayList<User> values, Meet m) {
            super(context, resource, values);
            this.mContext = context;
            this.values = values;
            this.m = m;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.member_alert_item, null);
            }
            final User u = values.get(position);
            if (u != null) {
                ImageView mImageView[] = new ImageView[2];
                TextView mTextView;

                mImageView[0] = (ImageView) v.findViewById(R.id.ImageView0);
                mImageView[1] = (ImageView) v.findViewById(R.id.ImageView1);
                mTextView = (TextView) v.findViewById(R.id.TextView0);

                if (!u.getImageFilePath().equals("")) {//유저 이미지가 존재
                    mImageView[0].clearColorFilter();
                    temp = BitmapFactory.decodeFile(Gl.ImageFilePath + u.getId() + ".jpg");
                    mImageView[0].setImageBitmap(Gl.getCircleBitmap(temp));
                } else {//default 이미지 사용
                    mImageView[0].clearColorFilter();
                    temp = Gl.getDefaultImage(u.getId());
                    mImageView[0].setImageBitmap(Gl.getCircleBitmap(temp));
                }
                mImageView[1].setColorFilter(mContext.getResources().getColor(R.color.colorAccent));
                mImageView[1].setVisibility(View.INVISIBLE);
                for (int i = 0; i < m.getDateTimeNum(); i++) {
                    if (u.getId() == m.getDateTime().get(i).getUser().getId()) {
                        mImageView[1].setVisibility(View.VISIBLE);
                        break;
                    }
                }

                mTextView.setText(u.getName());
            }
            return v;
        }

    }
}
