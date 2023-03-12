package com.example.double2.studentinfomanager.activity;

import android.app.Activity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.double2.studentinfomanager.R;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.double2.studentinfomanager.R;
import com.example.double2.studentinfomanager.adapter.ShowAdapter;
import com.example.double2.studentinfomanager.db.StudentDateBaseHelper;
import com.example.double2.studentinfomanager.db.UserDateBaseHelper;

import java.util.ArrayList;


import java.security.PrivateKey;

public class Regis extends Activity {

    private Button Back;
    private Button register;
    private EditText user;
    private EditText password;

    private UserDateBaseHelper mUserDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_regis);

        register=(Button) findViewById(R.id.act_regis_regis);
        user=(EditText) findViewById(R.id.act_reigs_user);
        password=(EditText)findViewById(R.id.act_regis_password) ;

        mUserDateBaseHelper = new UserDateBaseHelper(this, "UserInfo.db", null, 1);
        mSQLiteDatabase = mUserDateBaseHelper.getReadableDatabase();

        initView();
        btnSureAction();
    }

    private void initView() {
        Back=(Button) findViewById(R.id.act_regis_back);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                intent = new Intent(Regis.this, LogInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void btnSureAction() {

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();
                values.put("username", user.getText().toString());
                values.put("password", password.getText().toString());

                if(notNull(user.getText().toString(),password.getText().toString())) {
                    if (notSameUsername(user.getText().toString())){

                        mSQLiteDatabase.insert("user", null, values);
                        Toast.makeText(Regis.this, "注册成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Regis.this, "该用户已经存在", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(Regis.this, "请填写完整的信息", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean notNull(String test, String test1) {
        if (test.equals(""))
            return false;
        if (test1.equals(""))
            return false;
        return true;
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
