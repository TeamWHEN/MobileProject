package com.example.ysm0622.app_when.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.ysm0622.app_when.R;

import java.util.ArrayList;

public class GroupDataAdapter extends ArrayAdapter<Group> {
    private final Context context;
    private ArrayList<Group> values = new ArrayList<Group>();


    public GroupDataAdapter(Context context, int resource, ArrayList<Group> values) {
        super(context, resource, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.group_info, null);
        }
        Group g = values.get(position);
        if (g != null) {
            TextView TextView[] = new TextView[3];
            TextView[0]=(TextView) v.findViewById(R.id.tv_GroupTitle);
            TextView[1]=(TextView) v.findViewById(R.id.tv_GroupMaster);
            TextView[2]=(TextView) v.findViewById(R.id.tv_GroupMemberNum);

            TextView[0].setText(g.getTitle());
            //TextView[1].setText(g.getMaster().getName());
            //TextView[2].setText(g.getMemberNum());

            TextView[1].setText("ysm0622");
            TextView[2].setText("10");
        }
        return v;
    }
}
