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
import com.admin.staff.bean.FeedBack;
import com.admin.staff.bean.User;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.FeedBackDao;
import com.admin.staff.db.FeedBackDaoImpl;
import com.admin.staff.util.SpUtil;

/**
 * 添加反馈
 */
public class FeedBackAddActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private ImageButton ib_ok;
    private EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_add);
        initView();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_ok = (ImageButton) findViewById(R.id.ib_ok);

        ib_back.setOnClickListener(this);
        ib_ok.setOnClickListener(this);
        et_content = (EditText) findViewById(R.id.et_content);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.ib_ok:
                save();
                break;
        }
    }

    private void save() {
        String content = et_content.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "您的反馈...", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = SpUtil.getCurrentUser();
        FeedBack feedBack = new FeedBack();
        feedBack.setWork_num(user.getWork_num());
        feedBack.setName(user.getName());
        feedBack.setTime(System.currentTimeMillis());
        feedBack.setContent(content);
        feedBack.setState(0);

        FeedBackDao feedBackDao = new FeedBackDaoImpl(new DBHelper(this));
        feedBackDao.insert(feedBack);

        Toast.makeText(this, "反馈成功", Toast.LENGTH_SHORT).show();
        finish();

    }
}
