package com.admin.staff.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.bean.User;
import com.makeramen.roundedimageview.RoundedImageView;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 员工适配器
 */
public class UserListAdapter extends BaseAdapter {
    private Context mContext;//上下文
    List<User> list;//员工列表

    /**
     * 构造方法
     *
     * @param mContext 上下文
     * @param list     员工列表
     */
    public UserListAdapter(Context mContext, List<User> list) {
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
            convertView = View.inflate(mContext, R.layout.item_users, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        //姓名
        holder.tv_name.setText(list.get(position).getName());
        //性别
        holder.tv_sex.setText(list.get(position).getSex());

        //年龄
        //1984/2/16 转换格式，计算年龄
        String birthday = list.get(position).getBirthday();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        try {
            Date date = sdf.parse(birthday);
            //计算年龄
            int age = new Date().getYear() - date.getYear();
            holder.tv_age.setText(age + "岁");
        } catch (ParseException e) {
            e.printStackTrace();
            holder.tv_age.setText(birthday);
        }
        //职务
        holder.tv_post.setText("职务：" + list.get(position).getPost());
        //部门
        holder.tv_department.setText("部门：" + list.get(position).getDepartment());
        //学历
        holder.tv_education.setText("学历：" + list.get(position).getEducation());
        //电话
        holder.tv_phone.setText("电话：" + list.get(position).getPhone());
        //工号
        holder.tv_work_num.setText("工号：" + list.get(position).getWork_num());

        //工龄
        holder.tv_years.setText("工龄：" + list.get(position).getWork_years());

        int[] res = new int[]{R.mipmap.ic_head1, R.mipmap.ic_head2, R.mipmap.ic_head3,
                R.mipmap.ic_head4, R.mipmap.ic_head5, R.mipmap.ic_head6,
                R.mipmap.ic_head7, R.mipmap.ic_head8, R.mipmap.ic_head9, R.mipmap.ic_head10};
        Random random = new Random();

        holder.riv_head.setImageResource(res[random.nextInt(100) % 10]);

        return convertView;
    }

    public static class ViewHolder {
        public View rootView;
        public RoundedImageView riv_head;
        public TextView tv_name;
        public TextView tv_sex;
        public TextView tv_age;
        public TextView tv_post;
        public TextView tv_department;
        public TextView tv_education;
        public TextView tv_phone;
        public TextView tv_work_num;
        public TextView tv_years;

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            this.riv_head = (RoundedImageView) rootView.findViewById(R.id.riv_head);
            this.tv_name = (TextView) rootView.findViewById(R.id.tv_name);
            this.tv_sex = (TextView) rootView.findViewById(R.id.tv_sex);
            this.tv_age = (TextView) rootView.findViewById(R.id.tv_age);
            this.tv_post = (TextView) rootView.findViewById(R.id.tv_post);
            this.tv_department = (TextView) rootView.findViewById(R.id.tv_department);
            this.tv_education = (TextView) rootView.findViewById(R.id.tv_education);
            this.tv_phone = (TextView) rootView.findViewById(R.id.tv_phone);
            this.tv_work_num = (TextView) rootView.findViewById(R.id.tv_work_num);
            this.tv_years = (TextView) rootView.findViewById(R.id.tv_years);
        }

    }
}
