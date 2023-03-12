package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;


public class FirstActivity extends AppCompatActivity {

    private Cursor cursor;
    @Override
    public void onDestroy(){
        super.onDestroy();
        cursor.close();
    }



    //定义共享优先数据及基础字段  用于签到功能实现
    private String MY_RMBCost ="MY_RMBCost";

    private String TodayTime ="TodayTime";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        //--------------------------------------------------------------登录
        Button btnlongin = findViewById(R.id.login);
        btnlongin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //-------------------------------------------------------------签到
        final Button bt_qiandao = (Button)findViewById(R.id.bt_qiandao);
        final TextView tv_time = (TextView)findViewById(R.id.tv_qiandaotime);

        SharedPreferences my_rmb_data = getSharedPreferences(MY_RMBCost, 0);//读取共享数据

        Time t = new Time();
        t.setToNow();
        int lastmonth = t.month + 1 ;
        final String str =  t.year + "年" + lastmonth + "月" + t.monthDay + "日";


        final String nowtime =my_rmb_data.getString(TodayTime, "").toString();

        if(nowtime.equals(str)==true)
        {
            tv_time.setText("日期："+ nowtime +"已签到！");
            bt_qiandao.setBackgroundResource(R.drawable.yqd);
            bt_qiandao.setText("✔签到");
        }
        else
        {
            tv_time.setText("日期："+ str);
            bt_qiandao.setBackgroundResource(R.drawable.qd);
        }

        //签到功能
        bt_qiandao.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                SharedPreferences my_rmb_data = getSharedPreferences(MY_RMBCost, 0);
                if(my_rmb_data.getString(TodayTime, "").toString().equals(str)==true)
                {
                    Toast.makeText(FirstActivity.this , "今日已签到！", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    my_rmb_data.edit()
                            .putString(TodayTime, str)
                            .commit();
                    tv_time.setText("日期："+ str +"已签到！");
                    bt_qiandao.setBackgroundResource(R.drawable.yqd);
                    bt_qiandao.setText("✔签到");
                    Toast.makeText( FirstActivity.this , "签到成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //-------------------------------------每日资讯
        //ArrayAdapter<News> listAdapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,News.news);


        ListView listnews = findViewById(R.id.list_news);
        //listnews.setAdapter(listAdapter);

        //设置游标适配器
        SQLiteOpenHelper starbuzzDatabaseHelper=new CompanyDatabaseHelper(this);
        //获得数据库引用
        try {
            SQLiteDatabase db =starbuzzDatabaseHelper.getReadableDatabase();
            cursor = db.query("NEWS",
                    new String[]{"_id","TITLE"},
                    null,null,null,null,null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"TITLE"},
                    new int[]{android.R.id.text1},
                    0);
            listnews.setAdapter(listAdapter);

        }catch (SQLException e){
            Log.e("sqlite",e.getMessage());
            Toast toast=Toast.makeText(this,"Database unavaiable",Toast.LENGTH_SHORT);
            toast.show();
        }
        //指定监听器 相应选项单击
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(FirstActivity.this,NewsActivity.class);
                intent.putExtra(NewsActivity.EXTRA_DRINKID,(int)id);
                startActivity(intent);

            }
        };
        listnews.setOnItemClickListener(itemClickListener);
        //-------------------------------------------------公司简介
        Button btnintroduction = findViewById(R.id.introduction);
        btnintroduction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FirstActivity.this,IntroductionActivity.class);
                startActivity(intent);
            }
        });

    }
}
