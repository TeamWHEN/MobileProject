package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.object.Meet;

import java.util.ArrayList;

public class MeetDataAdapter extends ArrayAdapter<Meet> {

    private static final int TEXT_NUM = 5;
    private static final int ICON_NUM = 4;
    private static final int BTN_NUM = 2;
    private final Context mContext;
    private ArrayList<Meet> values = new ArrayList<>();

    public MeetDataAdapter(Context context, int resource, ArrayList<Meet> values) {
        super(context, resource, values);
        this.mContext = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.meet_item, null);
        }
        Meet m = values.get(position);
        if (m != null) {
            TextView mTextView[] = new TextView[TEXT_NUM];
            ImageView mImageViewProfile;
            ImageView mImageViewIcon[] = new ImageView[ICON_NUM];
            ImageView mImageViewBtn[] = new ImageView[BTN_NUM];

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

            mImageViewProfile.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            for (int i = 0; i < ICON_NUM; i++) {
                mImageViewIcon[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }
            for (int i = 0; i < BTN_NUM; i++) {
                mImageViewBtn[i].setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
            }

            mTextView[0].setText(m.getTitle());
            //TextView[1].setText(g.getMaster().getName());
            //TextView[2].setText(g.getMemberNum());

            mTextView[1].setText("ysm0622");
            mTextView[2].setText("1 / 10");
            mTextView[3].setText(m.getLocation());
        }
        return v;
    }
}
