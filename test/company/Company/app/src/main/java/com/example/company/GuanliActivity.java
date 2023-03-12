package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GuanliActivity extends AppCompatActivity implements View.OnClickListener {

    private Cursor cursor;

    private CompanyDatabaseHelper mDBOpenHelper;
    private Button mbtadd;
    private Button mbtdelete;
    private  Button mbtupdate;
    private Button mbtsearch;
    private Button mbtback;

    private EditText medtname;
    private EditText medtsex;
    private EditText medttel;
    private EditText medtposition;
    private EditText medttime;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guanli);
        initView();

        mDBOpenHelper = new CompanyDatabaseHelper(this);
    }

    private void initView(){
        mbtadd = findViewById(R.id.bt_add);
        mbtdelete = findViewById(R.id.bt_delete);
        mbtupdate = findViewById(R.id.bt_update);
        mbtsearch = findViewById(R.id.bt_search);
        mbtback = findViewById(R.id.bt_guanliback);

        medtname = findViewById(R.id.edt_name);
        medtsex = findViewById(R.id.edt_sex);
        medttel = findViewById(R.id.edt_tel);
        medtposition = findViewById(R.id.edt_position);
        medttime = findViewById(R.id.edt_time);

        mbtadd.setOnClickListener(this);
        mbtdelete.setOnClickListener(this);
        mbtupdate.setOnClickListener(this);
        mbtsearch.setOnClickListener(this);
        mbtback.setOnClickListener(this);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.bt_add:
//                int mid =Integer.parseInt (medtid.getText().toString());
                String name = medtname.getText().toString().trim();
                String sex = medtsex.getText().toString().trim();
                String tel = medttel.getText().toString().trim();
                String position = medtposition.getText().toString().trim();
                String time = medttime.getText().toString().trim();
                mDBOpenHelper.yuangong_add(name,sex,tel,position,time);
                Toast.makeText(this,  "添加成功", Toast.LENGTH_SHORT).show();
                medtname.setText("");
                medtsex.setText("");
                medttel.setText("");
                medtposition.setText("");
                medttime.setText("");
                break;
            case R.id.bt_delete:
                AlertDialog alertDialog = new AlertDialog.Builder(this)
                        .setTitle("删除员工信息")
                        .setMessage("是否删除")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String name = medtname.getText().toString().trim();
                                String position = medtposition.getText().toString().trim();
                                mDBOpenHelper.yuangong_delete(name);
                                medtname.setText("");
                                medtsex.setText("");
                                medttel.setText("");
                                medtposition.setText("");
                                medttime.setText("");

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                return;
                            }
                        }).create();
                alertDialog.show();
                break;
            case R.id.bt_update:
                String uname = medtname.getText().toString().trim();
                String utel = medttel.getText().toString().trim();
                String uposition = medtposition.getText().toString().trim();
                mDBOpenHelper.yuangong_updata(uname,uposition,utel);
                Toast.makeText(this,"更新成功",Toast.LENGTH_SHORT).show();
                medtname.setText("");
                medtsex.setText("");
                medttel.setText("");
                medtposition.setText("");
                medttime.setText("");
                break;
            case R.id.bt_search:
                startActivity(new Intent(GuanliActivity.this, SearchActivity.class));
                finish();//销毁此Activity

                break;

            case R.id.bt_guanliback:
                startActivity(new Intent(GuanliActivity.this, GloginActivity.class));
                finish();//销毁此Activity
                break;
        }

    }
}
