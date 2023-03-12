package com.admin.staff.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.bean.Wage;

import java.util.List;

public class WageAdapter extends BaseAdapter {
    private Context mContext;
    private List<Wage> list;

    public WageAdapter(Context mContext, List<Wage> list) {
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
            convertView = View.inflate(mContext, R.layout.item_wage, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final Wage wage = list.get(position);
        holder.tv_work_num.setText(wage.getWork_num());
        holder.tv_name.setText(wage.getName());
        holder.tv_late_num.setText(wage.getLate_num());
        holder.tv_base_wage.setText(wage.getBase_wages());
        holder.tv_perform_wage.setText(wage.getPerform_wages());
        holder.tv_deduct_wage.setText(wage.getDeduct_wages());
        holder.tv_actual.setText(wage.getActual());

        return convertView;
    }

    public static
    class ViewHolder {
        public View rootView;
        public TextView tv_work_num;
        public TextView tv_name;
        public TextView tv_late_num;
        public TextView tv_base_wage;
        public TextView tv_perform_wage;
        public TextView tv_deduct_wage;
        public TextView tv_actual;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.tv_work_num = (TextView) rootView.findViewById(R.id.tv_work_num);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_late_num = (TextView) rootView.findViewById(R.id.tv_late_num);
            this.tv_base_wage = (TextView) rootView.findViewById(R.id.tv_base_wage);
            this.tv_perform_wage = (TextView) rootView.findViewById(R.id.tv_perform_wage);
            this.tv_deduct_wage = (TextView) rootView.findViewById(R.id.tv_deduct_wage);
            this.tv_actual = (TextView) rootView.findViewById(R.id.tv_actual);
        }

    }
}
