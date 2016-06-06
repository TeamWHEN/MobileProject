package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.object.Group;
import com.example.ysm0622.app_when.R;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<Group> {

    private static final int COUNT = 3;
    private final Context mContext;
    private ArrayList<Group> values = new ArrayList<Group>();


    public GroupDataAdapter(Context context, int resource, ArrayList<Group> values) {
        super(context, resource, values);
        this.mContext = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.group_item, null);
        }
        Group g = values.get(position);
        if (g != null) {
            TextView mTextView[] = new TextView[COUNT];
            ImageView mImageViewProfile;
            ImageView mImageViewIcon[] = new ImageView[COUNT];
            ImageView mImageViewBtn[] = new ImageView[COUNT];

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
            for (int i = 0; i < COUNT; i++) {
                mImageViewIcon[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
                mImageViewBtn[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }

            mTextView[0].setText(g.getTitle());
            //TextView[1].setText(g.getMaster().getName());
            //TextView[2].setText(g.getMemberNum());

            mTextView[1].setText("ysm0622");
            mTextView[2].setText("10");
        }
        return v;
    }
}
