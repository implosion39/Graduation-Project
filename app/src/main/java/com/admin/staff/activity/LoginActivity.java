package com.admin.staff.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.bean.User;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.UserDao;
import com.admin.staff.db.UserDaoImpl;
import com.admin.staff.util.IdentifyingCode;
import com.admin.staff.util.SpUtil;


/**
 * 登录界面
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_username;
    private EditText et_password;
    private Button btn_login;
    private EditText et_code;
    private ImageView iv_code;
    private String realCode;//图片上的验证码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }


    /**
     * 初始化
     */
    private void initView() {
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_code = (ImageView) findViewById(R.id.iv_code);
        //设置图片验证码
        iv_code.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        //获取图片上的验证码
        realCode = IdentifyingCode.getInstance().getCode().toLowerCase();

        btn_login.setOnClickListener(this);
        iv_code.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_code://图片验证码
                //设置图片验证码
                iv_code.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
                //获取图片上的验证码
                realCode = IdentifyingCode.getInstance().getCode().toLowerCase();
                break;
            case R.id.btn_login://登录
                submit();
                break;
        }
    }

    private void submit() {
        String username = et_username.getText().toString().trim();
        if (TextUtils.isEmpty(username)) {
            Toast.makeText(this, "账号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String password = et_password.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //判断验证码是否正确
        String inputCode = et_code.getText().toString().toLowerCase();
        if (!inputCode.equals(realCode)) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
            return;
        }

        UserDao userDao = new UserDaoImpl(new DBHelper(this));
        User user = userDao.login(username, password);

        if (user != null) {
            SpUtil.put("id", user.getId());
            SpUtil.put("isAdmin", user.getType().equals("管理员"));//保存登录状态
            startActivity(new Intent(this, MainActivity.class));
            Toast.makeText(this, "登录成功！", Toast.LENGTH_SHORT).show();
            SpUtil.saveUser(user);
            SpUtil.put("isLogin", true);//保存登录状态
            finish();

        } else {
            Toast.makeText(this, "账号或密码错误", Toast.LENGTH_SHORT).show();
        }

    }

}
