package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.Global;
import com.example.ysm0622.app_when.object.Group;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<Group> {

    // TAG
    private static final String TAG = "GroupDataAdapter";

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
                    mContext.startActivity(mIntent);
                }
            });
            mImageViewBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, GroupManage.class);
                    mIntent.putExtra(Global.GROUP, g);
                    mIntent.putExtra(Global.TAB_NUMBER, 2);
                    mContext.startActivity(mIntent);
                }
            });
            mImageViewBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    
                }
            });
        }
        return v;
    }
}
