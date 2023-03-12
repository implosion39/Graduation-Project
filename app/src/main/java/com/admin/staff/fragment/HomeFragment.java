package com.admin.staff.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.activity.AddUpdateUserActivity;
import com.admin.staff.adapter.UserListAdapter;
import com.admin.staff.bean.User;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.UserDao;
import com.admin.staff.db.UserDaoImpl;
import com.admin.staff.util.SpUtil;

import java.util.Arrays;
import java.util.List;

/**
 * 员工
 */
public class HomeFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {

    private AutoCompleteTextView et_id;
    private ListView listView;
    private UserListAdapter adapter;
    private FloatingActionButton fab_add;
    private Button btn_all;

    private UserDao userDao;
    private List<User> users;


    private boolean isAdmin = SpUtil.getBoolean("isAdmin", false);//是否是管理员
    private int id = (int) SpUtil.getData("id", -1);//员工id

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userDao = new UserDaoImpl(new DBHelper(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        getData();
        return view;
    }

    /**
     * 从数据库查询员工数据
     */
    @SuppressLint("RestrictedApi")
    private void getData() {
        et_id.setText("");
        //用户数据库操作类
        userDao = new UserDaoImpl(new DBHelper(getContext()));
        if (isAdmin) {
            users = userDao.selectById(-1);//管理员传-1，表示查询所有员工信息
        } else {
            users = userDao.selectById(id);//管理员传-1，表示查询所有员工信息
        }
        adapter = new UserListAdapter(getContext(), users);
        listView.setAdapter(adapter);


    }

    @SuppressLint("RestrictedApi")
    private void initView(View view) {
        et_id = (AutoCompleteTextView) view.findViewById(R.id.et_id);
        listView = (ListView) view.findViewById(R.id.listView);
        fab_add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        btn_all = view.findViewById(R.id.btn_all);
        fab_add.setOnClickListener(this);
        listView.setOnItemClickListener(this);
        btn_all.setOnClickListener(this);

        final List<String> strings;
        if (isAdmin) {
            strings = userDao.selectAllWorkIds(-1);
        } else {
            strings = userDao.selectAllWorkIds(id);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, strings);
        et_id.setAdapter(arrayAdapter);
        et_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s = (String) parent.getItemAtPosition(position);
                Log.d("HomeFragment", s);
                boolean isExist = false;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getWork_num().equals(s)) {
                        isExist = true;
                        User qUser = users.get(i);
                        users.clear();
                        users.add(qUser);
                        listView.setAdapter(new UserListAdapter(getContext(), users));
                        break;
                    }
                }

                if (!isExist) {
                    Toast.makeText(getContext(), "没有此员工", Toast.LENGTH_SHORT).show();
                    users.clear();
                    listView.setAdapter(new UserListAdapter(getContext(), users));
                }
            }
        });

        if (isAdmin) {
            fab_add.setVisibility(View.VISIBLE);
            btn_all.setVisibility(View.VISIBLE);
        } else {
            et_id.setVisibility(View.GONE);
            btn_all.setVisibility(View.GONE);
            fab_add.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_all://显示所有
                getData();
                break;
            case R.id.fab_add:
                addUser();
                break;
        }
    }

    /**
     * 添加用户
     */
    private void addUser() {
        Intent intent = new Intent(getContext(), AddUpdateUserActivity.class);
        startActivity(intent);
    }

    /**
     * ListView 点击事件
     *
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
        final User user = (User) parent.getItemAtPosition(position);
        if (!isAdmin){
            Intent intent = new Intent(getContext(), AddUpdateUserActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
            return;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        String[] items = {"修改", "删除"};

        builder.setTitle("操作");
        builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (which == 0) {//修改
                    Intent intent = new Intent(getContext(), AddUpdateUserActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else if (which == 1) {//删除
                    if (isAdmin) {
                        delete(user);
                    } else {
                        Toast.makeText(getContext(), "不能删除自己", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        builder.create().show();

    }

    /**
     * 删除
     *
     * @param user
     */
    private void delete(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("删除员工")
                .setMessage("是否删除此员工（" + user.getName() + "）?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        userDao.delete(user.getId());
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
}
