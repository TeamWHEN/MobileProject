package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.object.Group;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<Group> {

    // TAG
    private static final String TAG = GroupDataAdapter.class.getName();

    // Const
    private static final int TEXT_NUM = 4;
    private static final int ICON_NUM = 3;
    private static final int BTN_NUM = 3;

    // Intent
    private Intent mIntent;

    // Context
    private final Context mContext;

    // Data
    private ArrayList<Group> values = new ArrayList<Group>();
    private Group g;

    //Dialog
    private AlertDialog mDialBox;
    private String msg;

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
        g = values.get(position);
        if (g != null) {
            TextView mTextView[] = new TextView[TEXT_NUM];
            ImageView mImageViewProfile;
            ImageView mImageViewIcon[] = new ImageView[ICON_NUM];
            final ImageView mImageViewBtn[] = new ImageView[BTN_NUM];

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
                    mIntent.setClass(mContext, GroupManage.class);
                    mIntent.putExtra(Global.GROUP, g);
                    mIntent.putExtra(Global.TAB_NUMBER, 0);
                    ((Activity) mContext).startActivityForResult(mIntent, Global.GROUPLIST_GROUPMANAGE);
                }
            });
            mImageViewBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, GroupManage.class);
                    mIntent.putExtra(Global.GROUP, g);
                    mIntent.putExtra(Global.TAB_NUMBER, 2);
                    ((Activity) mContext).startActivityForResult(mIntent, Global.GROUPLIST_GROUPMANAGE);
                }
            });
            mImageViewBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createDialogBox();
                }
            });
        }
        return v;
    }

    //탈퇴 다이어로그
    public void createDialogBox() {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.drop_alert, null);

        TextView Title = (TextView) view.findViewById(R.id.drop_title);
        Title.setText("그룹을 탈퇴하시겠습니까?");
        Title.setTextColor(Color.BLACK);
        TextView Content = (TextView) view.findViewById(R.id.drop_content);
        Content.setText("그룹 탈퇴시 모든 입력 기록이 삭제되며, 구성원에게 초대 받기 전까지 다시 그룹에 가입할 수 없습니다.");

        Button Btn1 = (Button) view.findViewById(R.id.drop_btn1);
        Btn1.setText("탈퇴");
        Btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "탈퇴", Toast.LENGTH_SHORT).show();
                mDialBox.cancel();
            }
        });//탈퇴
        Button Btn2 = (Button) view.findViewById(R.id.drop_btn2);
        Btn2.setText("취소");
        Btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "취소", Toast.LENGTH_SHORT).show();
                mDialBox.cancel();
            }
        });//취소

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(view);

        mDialBox = builder.create();
        mDialBox.show();
    }
}
