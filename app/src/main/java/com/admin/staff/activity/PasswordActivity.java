package com.admin.staff.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.bean.User;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.UserDao;
import com.admin.staff.db.UserDaoImpl;
import com.admin.staff.util.SpUtil;

/**
 * 修改密码
 */
public class PasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private ImageButton ib_ok;
    private EditText et_old_pwd;
    private EditText et_new_pwd;
    private EditText et_new_pwd_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_ok = (ImageButton) findViewById(R.id.ib_ok);
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);
        et_new_pwd_ok = (EditText) findViewById(R.id.et_new_pwd_ok);

        ib_back.setOnClickListener(this);
        ib_ok.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_ok:
                submit();
                break;
        }
    }

    private void submit() {

        String pwd = et_old_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "原密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd_new = et_new_pwd.getText().toString().trim();
        if (TextUtils.isEmpty(pwd)) {
            Toast.makeText(this, "新密码", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd_new_ok = et_new_pwd_ok.getText().toString().trim();
        if (!pwd_new.equals(pwd_new_ok)) {
            Toast.makeText(this, "密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        User currentUser = SpUtil.getCurrentUser();
        if (!currentUser.getPassword().equals(pwd)) {
            Toast.makeText(this, "原密码错误", Toast.LENGTH_SHORT).show();
            return;
        }
        currentUser.setPassword(pwd_new);
        UserDao userDao = new UserDaoImpl(new DBHelper(this));
        userDao.update(currentUser);
        SpUtil.saveUser(currentUser);
        Toast.makeText(this, "密码修改成功", Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }
}
