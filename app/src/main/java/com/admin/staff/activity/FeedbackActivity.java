package com.admin.staff.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.adapter.FeedBackAdapter;
import com.admin.staff.bean.FeedBack;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.FeedBackDao;
import com.admin.staff.db.FeedBackDaoImpl;

import java.util.List;

/**
 * 反馈
 */
public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private ListView listView;
    List<FeedBack> query;
    FeedBackAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        initView();
        getData();
    }

    private void getData() {
        final FeedBackDao dao = new FeedBackDaoImpl(new DBHelper(this));
        query = dao.query(null);
        adapter = new FeedBackAdapter(this, query);
        listView.setAdapter(adapter);
        //处理
        adapter.setClListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FeedBack back = query.get(position);
                back.setState(1);
                dao.update(back);
                Toast.makeText(FeedbackActivity.this, "已处理", Toast.LENGTH_SHORT).show();
                getData();
            }
        });
        //忽略
        adapter.setDeleteListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                dao.delete(query.get(position).getId());
                Toast.makeText(FeedbackActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                getData();
            }
        });
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        listView = (ListView) findViewById(R.id.listView);

        ib_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
        }
    }
}
