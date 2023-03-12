package com.example.double2.studentinfomanager.activity;

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

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    //控件
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;
    private RecyclerView rvMain;
    private FloatingActionButton fabtnAdd;
    //数据存储
    private StudentDateBaseHelper mStudentDateBaseHelper;
    private SQLiteDatabase mSQLiteDatabase;
    private SharedPreferences mSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        setContentView(R.layout.act_main);

        mStudentDateBaseHelper = new StudentDateBaseHelper(this, "StudentInfo.db", null, 1);
        mSQLiteDatabase = mStudentDateBaseHelper.getReadableDatabase();
        mSharedPreferences = this.getSharedPreferences("student", MODE_PRIVATE);

        setTestData();
        initView();
    }

    //预置学生信息
    private void setTestData() {

        ContentValues test = new ContentValues();
        test.put("number", "2821654652");
        test.put("gender", "男");
        test.put("name", "许爱国");
        test.put("birth", "1999年1月1日");
        test.put("native_place", "湖北");
        test.put("specialty", "光电");
        test.put("grade", "60");
        mSQLiteDatabase.insert("student", null, test);

        ContentValues test1 = new ContentValues();
        test1.put("number", "28211654652");
        test1.put("gender", "女");
        test1.put("name", "张金刚");
        test1.put("birth", "1998年12月1日");
        test1.put("native_place", "西藏");
        test1.put("specialty", "机械");
        test1.put("grade", "69");
        mSQLiteDatabase.insert("student", null, test1);

        ContentValues test2 = new ContentValues();
        test2.put("number", "28216568462");
        test2.put("gender", "男");
        test2.put("name", "许仙");
        test2.put("birth", "1997年12月1日");
        test2.put("native_place", "湖北");
        test2.put("specialty", "光电");
        test2.put("grade", "89");
        mSQLiteDatabase.insert("student", null, test2);

        ContentValues test3 = new ContentValues();
        test3.put("number", "282161652");
        test3.put("gender", "女");
        test3.put("name", "诸葛硬");
        test3.put("birth", "1999年1月11日");
        test3.put("native_place", "湖北");
        test3.put("specialty", "物理");
        test3.put("grade", "78");
        mSQLiteDatabase.insert("student", null, test3);

        ContentValues test4 = new ContentValues();
        test4.put("number", "2821646462");
        test4.put("gender", "男");
        test4.put("name", "张锋");
        test4.put("birth", "2000年1月1日");
        test4.put("native_place", "天津");
        test4.put("specialty", "光电");
        test4.put("grade", "56");
        mSQLiteDatabase.insert("student", null, test4);






        Boolean isFirstStart = mSharedPreferences.getBoolean("is_first_start", true);
        if (isFirstStart) {
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putBoolean("is_first_start", false);
            mEditor.commit();

            ArrayList<String> number = new ArrayList<>();
            ArrayList<String> name = new ArrayList<>();
            String gender = "女";
            String nativePlace = "湖南常德";
            String specialty = "物联网";
            String grade = "98";
            String birth = "1999年1月1日";

            number.add("20211120000");
            name.add("赵一");
            number.add("20211120001");
            name.add("钱二");
            number.add("20211120003");
            name.add("孙三");
            number.add("20211120005");
            name.add("李四");
            number.add("20211120007");
            name.add("周五");
            number.add("20211120006");
            name.add("吴六");
            number.add("20211120008");
            name.add("郑七");
            number.add("20211120009");
            name.add("王八");


            for (int i = 0; i < number.size(); i++) {
                ContentValues values = new ContentValues();
                values.put("number", number.get(i));
                values.put("name", name.get(i));
                values.put("gender", gender);
                values.put("native_place", nativePlace);
                values.put("specialty", specialty);
                values.put("grade", grade);
                values.put("birth", birth);
                mSQLiteDatabase.insert("student", null, values);
            }
        }
    }


    private void initView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //将状态栏颜色设置为与toolbar一致
            getWindow().setStatusBarColor(getResources().getColor(R.color.normal_blue));
        }
        setToolBar();
        setNavigationView();

        rvMain = (RecyclerView) findViewById(R.id.rv_main);
        rvMain.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        fabtnAdd = (FloatingActionButton) findViewById(R.id.fabtn_main_add);
        fabtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                intent.putExtra("type", EditActivity.TYPE_ADD);
                startActivity(intent);
            }
        });
    }

    protected void onStart() {
        super.onStart();
        refreshRecyclerView();
    }

    private void refreshRecyclerView() {
        ArrayList<String> number = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> gender = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query("student", null, null, null, null, null, null);

        int size = mCursor.getCount() < ShowAdapter.maxSize ? mCursor.getCount() : ShowAdapter.maxSize;

        while (true) {
            if (size-- == 0)
                break;
            mCursor.moveToNext();
            number.add(mCursor.getString(mCursor.getColumnIndex("number")));
            name.add(mCursor.getString(mCursor.getColumnIndex("name")));
            gender.add(mCursor.getString(mCursor.getColumnIndex("gender")));
        }
        mCursor.close();


        rvMain.setAdapter(new ShowAdapter(MainActivity.this, number, name, gender));
    }

    private void setNavigationView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mNavigationView = (NavigationView) findViewById(R.id.nv_main_menu);
        setupDrawerContent(mNavigationView);
    }

    private void setToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.id_toolbar);
        toolbar.setTitle("学生信息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                searchAction();
                return false;
            }
        });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        Intent intent;
                        switch (menuItem.getItemId()) {
                            case R.id.nav_my_info:
                                intent=new Intent(MainActivity.this,MyInfoActivity.class);
                                startActivity(intent);
                                break;
                            case R.id.nav_password:
                                changePasswordDialog();
                                break;
                            case R.id.nav_search:
                                searchAction();
                                break;
                            case R.id.nav_add:
                                intent = new Intent(MainActivity.this, EditActivity.class);
                                intent.putExtra("type", EditActivity.TYPE_ADD);
                                startActivity(intent);
                                break;
                            case R.id.log_out:
                                intent = new Intent(MainActivity.this, LogInActivity.class);
                                startActivity(intent);
                                break;
                        }
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void changePasswordDialog() {
        final TableLayout tlPassword = (TableLayout) getLayoutInflater().inflate(R.layout.dialog_main_password, null);
        final EditText etOldPassword = (EditText) tlPassword.findViewById(R.id.et_main_old_password);
        final EditText etNewPassword = (EditText) tlPassword.findViewById(R.id.et_main_new_password);
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("修改登录密码")
                .setView(tlPassword)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String oldPassword = mSharedPreferences.getString("password", "1");
                        if (oldPassword.equals(etOldPassword.getText().toString())) {
                            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
                            mEditor.putString("password", etNewPassword.getText().toString());
                            mEditor.commit();
                            Toast.makeText(MainActivity.this, "修改密码成功！", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "原密码错误！", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                //由于“取消”的button没有设置点击效果，直接设为null就可以了
                .setNegativeButton("取消", null)
                .create()
                .show();
    }

    private void searchAction() {
        final String[] arrayGender = new String[]{"学号", "姓名"};
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("搜索类型")
                .setItems(arrayGender, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                        switch (which) {
                            case 0:
                                intent.putExtra("search_type", SearchActivity.TYPE_SEARCH_NUMBER);
                                break;
                            case 1:
                                intent.putExtra("search_type", SearchActivity.TYPE_SEARCH_NAME);
                                break;
                        }
                        startActivity(intent);
                    }
                })
                .create()
                .show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}