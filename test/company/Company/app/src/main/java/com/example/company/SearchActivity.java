package com.example.company;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SearchActivity extends AppCompatActivity  implements View.OnClickListener  {
    private CompanyDatabaseHelper mDBOpenHelper;


    private EditText medts;
    private Button mbtok;
    private  Button mbtback;
    private TextView mtvid;
    private TextView mtvname;
    private TextView mtvsex;
    private TextView mtvtel;
    private TextView mtvposition;
    private TextView mtvtime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mtvid = findViewById(R.id.s_id);
        mtvname = findViewById(R.id.s_name);
        mtvsex = findViewById(R.id.s_sex);
        mtvtel= findViewById(R.id.s_tel);
        mtvposition= findViewById(R.id.s_position);
        mtvtime = findViewById(R.id.s_time);
        medts = findViewById(R.id.edt_search);
        mbtok = findViewById(R.id.bt_ok);
        mbtback =findViewById(R.id.bt_searchback);

        mbtok.setOnClickListener(this);
        mbtback.setOnClickListener(this);



        mDBOpenHelper = new CompanyDatabaseHelper(this);


    }



    public void  onClick(View view){
        switch (view.getId()){
            case R.id.bt_searchback:
                startActivity(new Intent(SearchActivity.this,GuanliActivity.class));
                finish();
                break;
            case R.id.bt_ok:
                String name = medts.getText().toString().trim();//获取姓名编辑框的内容
                if(name==null){
                    Toast.makeText(this, "请输入姓名", Toast.LENGTH_SHORT).show();
                }

                    SQLiteDatabase db =mDBOpenHelper.getReadableDatabase();
                    Cursor cursor = db.query("yuangong",new String[]{"_id","name","sex","tel","position","time"},
                            "name=?",
                            new String[]{name},null,null,null);

                       while (cursor.moveToNext()) {
                           String s_name = cursor.getString(cursor.getColumnIndex("name"));
                           String  s_sex= cursor.getString(cursor.getColumnIndex("sex"));
                           String s_tel = cursor.getString(cursor.getColumnIndex("tel"));
                           String s_position = cursor.getString(cursor.getColumnIndex("position"));
                           String s_time = cursor.getString(cursor.getColumnIndex("TIME"));
                           String s_id = cursor.getString(cursor.getColumnIndex("_id"));


                           mtvid.setText(s_id);
                           mtvsex.setText(s_sex);
                           mtvname.setText(s_name);
                           mtvtel.setText(s_tel);
                           mtvposition.setText(s_position);
                           mtvtime.setText(s_time);
                       }
                    cursor.close();


                break;
        }
    }

}
