package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;
import com.example.ysm0622.app_when.object.User;

import java.util.ArrayList;

public class UserDataAdapter extends ArrayAdapter<User> {

    private static final int COUNT = 2;
    private final Context mContext;
    private ArrayList<User> values = new ArrayList<>();


    public UserDataAdapter(Context context, int resource, ArrayList<User> values) {
        super(context, resource, values);
        this.mContext = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.member_item, null);
        }
        User u = values.get(position);
        if (u != null) {
            TextView mTextView[] = new TextView[COUNT];
            ImageView mImageViewProfile;

            mTextView[0] = (TextView) v.findViewById(R.id.TextView0);
            mTextView[1] = (TextView) v.findViewById(R.id.TextView1);

            mImageViewProfile = (ImageView) v.findViewById(R.id.ImageView0);

            mImageViewProfile.setColorFilter(mContext.getResources().getColor(R.color.colorPrimary));

            mTextView[0].setText(u.getName());
            mTextView[1].setText(u.getEmail());
        }
        return v;
    }
}
