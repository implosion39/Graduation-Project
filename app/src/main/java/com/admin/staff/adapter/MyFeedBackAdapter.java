package com.admin.staff.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.bean.FeedBack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyFeedBackAdapter extends BaseAdapter {
    private Context mContext;
    private List<FeedBack> list;

    public MyFeedBackAdapter(Context mContext, List<FeedBack> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_my_feedback, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_content.setText(list.get(position).getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(list.get(position).getTime()));
        holder.tv_time.setText(time);
        int state = list.get(position).getState();
        if (state == 0) {
            holder.tv_state.setText("未处理");
        } else if (state == 1) {
            holder.tv_state.setText("已处理");
        }
        return convertView;
    }

    public static
    class ViewHolder {
        public View rootView;
        public TextView tv_content;
        public TextView tv_state;
        public TextView tv_time;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.tv_state = (TextView) rootView.findViewById(R.id.tv_state);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
        }

    }
}
