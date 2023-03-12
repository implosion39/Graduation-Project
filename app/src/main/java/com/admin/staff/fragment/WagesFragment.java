package com.admin.staff.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.activity.AddUpdateUserActivity;
import com.admin.staff.activity.AddUpdateWageActivity;
import com.admin.staff.adapter.WageAdapter;
import com.admin.staff.bean.User;
import com.admin.staff.bean.Wage;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.WageDao;
import com.admin.staff.db.WageDaoImpl;
import com.admin.staff.util.SpUtil;

import java.util.List;

/**
 * 1工资
 */
public class WagesFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ListView listView;
    private WageDao wageDao;
    private FloatingActionButton fab_add;

    private boolean isAdmin = SpUtil.getBoolean("isAdmin", false);//是否是管理员
    private int id = (int) SpUtil.getData("id", -1);//员工id
    private List<Wage> wages;
    private WageAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wages, container, false);
        initView(view);
        getData();
        return view;
    }

    private void getData() {
        wageDao = new WageDaoImpl(new DBHelper(getContext()));
        if (isAdmin) {
            wages = wageDao.selectById(null);
        } else {
            wages = wageDao.selectById(SpUtil.getCurrentUser().getWork_num());
        }
        adapter = new WageAdapter(getContext(), wages);
        listView.setAdapter(adapter);
    }

    @SuppressLint("RestrictedApi")
    private void initView(View view) {
        listView = (ListView) view.findViewById(R.id.listView);
        fab_add = view.findViewById(R.id.fab_add);
        fab_add.setOnClickListener(this);



        if (isAdmin){
            listView.setOnItemClickListener(this);
            fab_add.setVisibility(View.VISIBLE);
        }else {
            fab_add.setVisibility(View.GONE);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Wage wage = wages.get(position);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] items = {"修改", "删除"};

        builder.setTitle("操作");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//修改
                    Intent intent = new Intent(getContext(), AddUpdateWageActivity.class);
                    intent.putExtra("wage", wage);
                    startActivity(intent);
                } else if (which == 1) {//删除
                    if (isAdmin) {
                        delete(wage);
                    } else {
                        Toast.makeText(getContext(), "不能删除", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.create().show();
    }


    /**
     * 删除
     *
     * @param wage
     */
    private void delete(final Wage wage) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除")
                .setMessage("是否删除此记录（" + wage.getName() + "）?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        wageDao.delete(wage.getId());
                        Toast.makeText(getContext(), "删除成功", Toast.LENGTH_SHORT).show();
                        getData();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getContext(), AddUpdateWageActivity.class);
        startActivity(intent);
    }
}
