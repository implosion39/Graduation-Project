package com.example.double2.studentinfomanager.activity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.double2.studentinfomanager.R;
import com.example.double2.studentinfomanager.db.UserDateBaseHelper;

import java.util.ArrayList;

public class LogInActivity extends Activity {

    //控件
    private Button btnLogIn;
    private Button reigs;
    private EditText etPassword;
    private EditText etusername;
    private String name;
    private String word;
    //数据存储

    private UserDateBaseHelper mUserDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_log_in);

        mUserDateBaseHelper = new UserDateBaseHelper(this, "UserInfo.db", null, 1);
        mSQLiteDatabase = mUserDateBaseHelper.getReadableDatabase();

        initView();

    }

    private void initView() {

        ContentValues values = new ContentValues();
        values.put("username", "许爱国");
        values.put("password", "1");
        mSQLiteDatabase.insert("user", null, values);


        etPassword = (EditText) findViewById(R.id.et_login_password);
        etusername=(EditText) findViewById(R.id.act_log_username);
        btnLogIn = (Button) findViewById(R.id.btn_login_log_in);
        reigs=(Button)findViewById(R.id.act_log_regis);

        reigs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(LogInActivity.this, Regis.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(notSameUsername(etusername.getText().toString())){

                    Toast.makeText(LogInActivity.this, "该用户不存在", Toast.LENGTH_SHORT).show();

                }else{
                    String rname=etusername.getText().toString();
                    String rpassword=etPassword.getText().toString();

                    ArrayList<User> data=mUserDateBaseHelper.getAllData2();
                    boolean match=false;
                    for(int i=0;i < data.size();i++){
                        User user = data.get(i);
                        if (rname.equals(user.getName()) && rpassword.equals(user.getPassword())) {
                            match = true;
                            break;
                        } else {
                            match = false;
                        }
                    }
                    if (match) {
                        Toast.makeText(LogInActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LogInActivity.this, "密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
                    }



                }

            }
        });
    }

    private boolean notSameUsername(String test2) {

        Cursor cursor = mSQLiteDatabase.query("user", null, "username=?", new String[]{test2}, null, null, null);
        if (cursor.getCount() == 0) {
            cursor.close();
            return true;
        } else {
            cursor.close();
            return false;
        }
    }


}
