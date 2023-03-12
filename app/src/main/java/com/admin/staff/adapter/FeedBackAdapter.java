package com.admin.staff.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.bean.FeedBack;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FeedBackAdapter extends BaseAdapter {
    private Context mContext;
    private List<FeedBack> list;

    private AdapterView.OnItemClickListener clListener;

    private AdapterView.OnItemClickListener deleteListener;

    public FeedBackAdapter(Context mContext, List<FeedBack> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setClListener(AdapterView.OnItemClickListener clListener) {
        this.clListener = clListener;
    }

    public void setDeleteListener(AdapterView.OnItemClickListener deleteListener) {
        this.deleteListener = deleteListener;
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_feedback, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final FeedBack feedBack = list.get(position);
        holder.tv_content.setText(feedBack.getContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(new Date(feedBack.getTime()));
        holder.tv_time.setText(time);
        int state = feedBack.getState();
        if (state == 0) {
            holder.tv_state.setText("未处理");
            holder.btn_cl.setVisibility(View.VISIBLE);
            holder.tv_state.setVisibility(View.GONE);
        } else if (state == 1) {
            holder.btn_cl.setVisibility(View.GONE);
            holder.tv_state.setText("已处理");
        }
        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (deleteListener != null) {
                    deleteListener.onItemClick(null, v, position,0 );
                }
            }
        });

        holder.btn_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clListener != null) {
                    clListener.onItemClick(null, v, position,0 );
                }
            }
        });
        return convertView;
    }

    public static
    class ViewHolder {
        public View rootView;
        public TextView tv_name;
        public TextView tv_time;
        public TextView tv_content;
        public Button btn_delete;
        public Button btn_cl;
        public TextView tv_state;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_time = (TextView) rootView.findViewById(R.id.tv_time);
            this.tv_content = (TextView) rootView.findViewById(R.id.tv_content);
            this.btn_delete = (Button) rootView.findViewById(R.id.btn_delete);
            this.btn_cl = (Button) rootView.findViewById(R.id.btn_cl);
            this.tv_state = (TextView) rootView.findViewById(R.id.tv_state);
        }

    }
}
