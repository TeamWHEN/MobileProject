package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Gl;
import com.example.ysm0622.app_when.object.Group;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<Group> {

    // TAG
    private static final String TAG = GroupDataAdapter.class.getName();

    // Const
    private static final int TEXT_NUM = 4;
    private static final int ICON_NUM = 3;
    private static final int BTN_NUM = 4;

    // Intent
    private Intent mIntent;

    // Context
    private final Context mContext;

    // Data
    private ArrayList<Group> values = new ArrayList<Group>();

    //Dialog
    private AlertDialog mDialBox;

    public GroupDataAdapter(Context context, int resource, ArrayList<Group> values, Intent intent) {
        super(context, resource, values);
        this.mContext = context;
        this.values = values;
        this.mIntent = intent;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.group_item, null);
        }
        final Group g = values.get(position);
        if (g != null) {
            TextView mTextView[] = new TextView[TEXT_NUM];
            ImageView mImageViewProfile;
            ImageView mImageViewIcon[] = new ImageView[ICON_NUM];
            ImageView mImageViewBtn[] = new ImageView[BTN_NUM];

            mTextView[0] = (TextView) v.findViewById(R.id.TextView0);
            mTextView[1] = (TextView) v.findViewById(R.id.TextView1);
            mTextView[2] = (TextView) v.findViewById(R.id.TextView2);

            mImageViewProfile = (ImageView) v.findViewById(R.id.ImageView0);

            mImageViewIcon[0] = (ImageView) v.findViewById(R.id.ImageView1);
            mImageViewIcon[1] = (ImageView) v.findViewById(R.id.ImageView2);
            mImageViewIcon[2] = (ImageView) v.findViewById(R.id.ImageView3);

            mImageViewBtn[0] = (ImageView) v.findViewById(R.id.ImageView4);
            mImageViewBtn[1] = (ImageView) v.findViewById(R.id.ImageView5);
            mImageViewBtn[2] = (ImageView) v.findViewById(R.id.ImageView6);
            mImageViewBtn[3] = (ImageView) v.findViewById(R.id.ImageView7);

            mImageViewProfile.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            for (int i = 0; i < ICON_NUM; i++) {
                mImageViewIcon[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }
            for (int i = 0; i < BTN_NUM; i++) {
                mImageViewBtn[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }

            mTextView[0].setText(g.getTitle());
            mTextView[1].setText(g.getMaster().getName());
            mTextView[2].setText(String.valueOf(g.getMemberNum()));

            mImageViewBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    descriptionDialogBox(g);
                }
            });
            mImageViewBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, GroupManage.class);
                    mIntent.putExtra(Gl.GROUP, g);
                    mIntent.putExtra(Gl.TAB_NUMBER, 0);
                    ((Activity) mContext).startActivityForResult(mIntent, Gl.GROUPLIST_GROUPMANAGE);
                }
            });
            mImageViewBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, GroupManage.class);
                    mIntent.putExtra(Gl.GROUP, g);
                    mIntent.putExtra(Gl.TAB_NUMBER, 2);
                    ((Activity) mContext).startActivityForResult(mIntent, Gl.GROUPLIST_GROUPMANAGE);
                }
            });
            mImageViewBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dropDialogBox(g);
                }
            });
        }
        return v;
    }

    //탈퇴 다이어로그
    public void dropDialogBox(final Group g) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drop_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.drop_title);
        TextView Content = (TextView) view.findViewById(R.id.drop_content);
        TextView Btn1 = (TextView) view.findViewById(R.id.drop_btn1);
        TextView Btn2 = (TextView) view.findViewById(R.id.drop_btn2);

        Title.setText(R.string.Leave_the_group);
        Content.setText(R.string.Leave_group_warn_msg);
        Btn1.setText(R.string.cancel);
        Btn2.setText(R.string.leave);

        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
            }
        });//취소

        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
                Gl.remove(g);
                values.remove(g);
                notifyDataSetInvalidated();
                GroupList a = (GroupList) mContext;
                a.groupDataEmptyCheck();
            }
        });//탈퇴

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }

    //그룹 설명 다이어로그
    public void descriptionDialogBox(Group g) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.groupdescription_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.groupdescription_title);
        TextView Content = (TextView) view.findViewById(R.id.groupdescription_content);
        TextView Btn = (TextView) view.findViewById(R.id.groupdescription_btn);

        Title.setText(g.getTitle());
        Content.setText(g.getDesc());

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialBox.cancel();
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }
}
