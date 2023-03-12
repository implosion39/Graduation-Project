package com.admin.staff.activity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.bean.User;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.UserDao;
import com.admin.staff.db.UserDaoImpl;

import java.util.Calendar;

/**
 * 添加、修改用户
 */
public class AddUpdateUserActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private EditText et_work_num;
    private EditText et_name;
    private RadioButton rb_man;
    private RadioButton rb_woman;
    private EditText et_birthday;
    private EditText et_work_years;
    private Spinner sp_post;
    private Spinner sp_department;
    private EditText et_phone;
    private Spinner sp_education;
    private Button btn_save;
    private User updateUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_user);
        initView();

        initData();
    }

    /**
     * 是否是修改信息，赋值
     */
    private void initData() {
        updateUser = (User) getIntent().getSerializableExtra("user");
        if (updateUser != null) {
            tv_title.setText("修改信息");
            et_work_num.setText(updateUser.getWork_num());
            et_name.setText(updateUser.getName());
            et_birthday.setText(updateUser.getBirthday());
            et_phone.setText(updateUser.getPhone());
            et_work_years.setText(updateUser.getWork_years());
            //部门
            String department = updateUser.getDepartment();
            int d_index = 0;
            String[] departments = getResources().getStringArray(R.array.department_array);
            for (int i = 0; i < departments.length; i++) {
                if (department.equals(departments[i])){
                    d_index = i;
                    break;
                }
            }
            sp_department.setSelection(d_index);

            //职务
            String post = updateUser.getPost();
            int p_index = 0;
            String[] posts = getResources().getStringArray(R.array.post_array);
            for (int i = 0; i < posts.length; i++) {
                if (post.equals(posts[i])){
                    p_index = i;
                    break;
                }
            }
            sp_post.setSelection(p_index);

            //学历
            String education = updateUser.getEducation();
            int e_index = 0;
            String[] educations = getResources().getStringArray(R.array.education_array);
            for (int i = 0; i < educations.length; i++) {
                if (education.equals(educations[i])){
                    e_index = i;
                    break;
                }
            }
            sp_education.setSelection(e_index);

            //性别
            String sex = updateUser.getSex();
            if ("男".equals(sex)){
                rb_man.setChecked(true);
            }else {
                rb_woman.setChecked(true);
            }

        }
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        et_work_num = (EditText) findViewById(R.id.et_work_num);
        et_name = (EditText) findViewById(R.id.et_name);
        rb_man = (RadioButton) findViewById(R.id.rb_man);
        rb_woman = (RadioButton) findViewById(R.id.rb_woman);
        et_birthday = (EditText) findViewById(R.id.et_birthday);
        et_work_years = (EditText) findViewById(R.id.et_work_years);
        sp_post = (Spinner) findViewById(R.id.sp_post);
        sp_department = (Spinner) findViewById(R.id.sp_department);
        et_phone = (EditText) findViewById(R.id.et_phone);
        sp_education = (Spinner) findViewById(R.id.sp_education);
        btn_save = (Button) findViewById(R.id.btn_save);

        ib_back.setOnClickListener(this);
        et_birthday.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.et_birthday:
                selectDate();
                break;
            case R.id.btn_save:
                save();
                break;
        }
    }


    /**
     * 选择日期
     */
    private void selectDate() {
        Calendar cal = Calendar.getInstance();
        //获取年月日时分秒
        int year = cal.get(Calendar.YEAR);
        //获取到的月份是从0开始计数
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        //后边三个参数为显示dialog时默认的日期，月份从0开始，0-11对应1-12个月
        DatePickerDialog dialog = new DatePickerDialog(this, 0, listener, year, month, day);
        dialog.show();
    }

    /**
     * 日期选择器监听
     */
    private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            //将选择的日期显示到TextView中,因为之前获取month直接使用，所以不需要+1，这个地方需要显示，所以+1
            et_birthday.setText(year + "/" + (++month) + "/" + day);

        }
    };

    /**
     * 保存
     */
    private void save() {
        String num = et_work_num.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(this, "工号不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String name = et_name.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String birthday = et_birthday.getText().toString().trim();
        if (TextUtils.isEmpty(birthday)) {
            Toast.makeText(this, "请选择生日", Toast.LENGTH_SHORT).show();
            return;
        }

        String years = et_work_years.getText().toString().trim();
        if (TextUtils.isEmpty(years)) {
            Toast.makeText(this, "请输入工龄", Toast.LENGTH_SHORT).show();
            return;
        }

        String phone = et_phone.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            Toast.makeText(this, "请输入电话", Toast.LENGTH_SHORT).show();
            return;
        }
        String sex = rb_man.isChecked() ? "男" : "女";
        String post = sp_post.getSelectedItem().toString();
        String department = sp_department.getSelectedItem().toString();
        String education = sp_education.getSelectedItem().toString();

        //构造User对象
        User user = new User(num, name, sex, birthday, years, post, department, phone, education, "123456", "普通员工");
        UserDao userDao = new UserDaoImpl(new DBHelper(this));
        if (updateUser==null){
            //TODO 判断工号是否存在
            boolean existUser = userDao.isExistUser(num);
            if (existUser){
                Toast.makeText(this, "工号已存在", Toast.LENGTH_SHORT).show();
                return;
            }
            //插入保存到数据库
            long l = userDao.insertUser(user);
            if (l > 0) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            updateUser.setWork_num(num);
            updateUser.setName(name);
            updateUser.setSex(sex);
            updateUser.setBirthday(birthday);
            updateUser.setWork_years(years);
            updateUser.setPost(post);
            updateUser.setDepartment(department);
            updateUser.setPhone(phone);
            updateUser.setEducation(education);
            int update = userDao.update(updateUser);
            if (update > 0) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }



    }
}
