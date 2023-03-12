package com.admin.staff.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.admin.staff.R;
import com.admin.staff.adapter.MyFeedBackAdapter;
import com.admin.staff.bean.FeedBack;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.FeedBackDao;
import com.admin.staff.db.FeedBackDaoImpl;
import com.admin.staff.util.SpUtil;

import java.util.List;

/**
 * 我的反馈记录
 */
public class MyFeedBackActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_feed_back);
        initView();
        getData();
    }

    private void getData() {
        FeedBackDao dao = new FeedBackDaoImpl(new DBHelper(this));
        List<FeedBack> query = dao.query(SpUtil.getCurrentUser().getWork_num());
        listView.setAdapter(new MyFeedBackAdapter(this, query));
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
