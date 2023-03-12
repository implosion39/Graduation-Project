package com.admin.staff.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.activity.FeedBackAddActivity;
import com.admin.staff.activity.FeedbackActivity;
import com.admin.staff.activity.LoginActivity;
import com.admin.staff.activity.MyFeedBackActivity;
import com.admin.staff.activity.PasswordActivity;
import com.admin.staff.util.SpUtil;
import com.makeramen.roundedimageview.RoundedImageView;

/**
 * 我的
 */
public class MeFragment extends Fragment implements View.OnClickListener {


    private RoundedImageView riv_head;
    private TextView tv_name;
    private LinearLayout ll_publish;
    private LinearLayout ll_logout;
    private LinearLayout ll_password, ll_my_back;
    private boolean isAdmin = SpUtil.getBoolean("isAdmin", false);//是否是管理员
    private int id = (int) SpUtil.getData("id", -1);//员工id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        tv_name.setText(SpUtil.getCurrentUser().getName());
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    private void initView(View view) {
        riv_head = (RoundedImageView) view.findViewById(R.id.riv_head);
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        ll_password = (LinearLayout) view.findViewById(R.id.ll_password);
        ll_publish = (LinearLayout) view.findViewById(R.id.ll_publish);
        ll_logout = (LinearLayout) view.findViewById(R.id.ll_logout);
        ll_my_back = (LinearLayout) view.findViewById(R.id.ll_my_back);

        ll_publish.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        ll_password.setOnClickListener(this);
        ll_my_back.setOnClickListener(this);

        if (isAdmin) {
            ll_my_back.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_password:
                startActivityForResult(new Intent(getContext(), PasswordActivity.class), 11);
                break;
            case R.id.ll_publish:
                //反馈
                if (isAdmin) {
                    startActivity(new Intent(getContext(), FeedbackActivity.class));
                } else {
                    startActivity(new Intent(getContext(), FeedBackAddActivity.class));
                }
                break;
            case R.id.ll_my_back:
                startActivity(new Intent(getContext(), MyFeedBackActivity.class));
                break;
            case R.id.ll_logout:
                logout();
                break;
        }
    }

    /**
     * 退出登录
     */
    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder
                .setTitle("退出登录")
                .setMessage("您是否要退出登录?")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivity(new Intent(getContext(), LoginActivity.class));
                        SpUtil.put("isLogin", false);//保存登录状态
                        getActivity().finish();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11 && resultCode == Activity.RESULT_OK) {
            SpUtil.put("isLogin", false);//保存登录状态
            startActivity(new Intent(getContext(), LoginActivity.class));
            getActivity().finish();
        }
    }
}
