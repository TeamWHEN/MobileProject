package com.example.ysm0622.app_when.group;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.global.G;
import com.example.ysm0622.app_when.meet.PollState;
import com.example.ysm0622.app_when.meet.SelectDay;
import com.example.ysm0622.app_when.object.Meet;

import java.util.ArrayList;
import java.util.Calendar;

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
    private Meet m;

    public MeetDataAdapter(Context context, int resource, ArrayList<Meet> values, Intent intent) {
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
            v = vi.inflate(R.layout.meet_item, null);
        }
        m = values.get(position);
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

            mImageViewProfile.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));
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
            Calendar cal = m.getSelectedDate().get(0);
            String str = cal.get(Calendar.YEAR) + "년 " + cal.get(Calendar.MONTH) + "월 " + cal.get(Calendar.DATE) + "일";
            if (m.getSelectedDate().size() != 1)
                str += " 외 " + (m.getSelectedDate().size() - 1) + "...";
            mTextView[4].setText(str);

            mImageViewBtn[0].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, PollState.class);
                    mIntent.putExtra(G.MEET, m);
                    ((Activity) mContext).startActivityForResult(mIntent, G.GROUPMANAGE_POLLSTATE);
                    G.Log(m);
                }
            });
            mImageViewBtn[1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mIntent.setClass(mContext, SelectDay.class);
                    mIntent.putExtra(G.MEET, m);
                    mIntent.putExtra(G.SELECT_DAY_MODE, 1);
                    ((Activity) mContext).startActivityForResult(mIntent, G.GROUPMANAGE_SELECTDAY);
                    G.Log(m);
                }
            });
            mImageViewBtn[2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            mImageViewBtn[3].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mImageViewBtn[3].setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_notifications_off));
                }
            });
        }
        return v;
    }
}
