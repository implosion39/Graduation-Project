package com.admin.staff.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.admin.staff.R;
import com.admin.staff.bean.User;
import com.admin.staff.bean.Wage;
import com.admin.staff.db.DBHelper;
import com.admin.staff.db.WageDao;
import com.admin.staff.db.WageDaoImpl;

/**
 * 更新或添加工资
 */
public class AddUpdateWageActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_title;
    private ImageButton ib_back;
    private EditText et_work_num;
    private EditText et_name;
    //private Spinner sp_department;
    private EditText et_late_num;
    private EditText et_base_wage;
    private EditText et_perform_wage;
    private EditText et_deduct_wage;
    private EditText et_actual;
    private Button btn_save;
    private Wage updateWage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update_wage);
        initView();
        initData();
    }

    private void initView() {
        tv_title = (TextView) findViewById(R.id.tv_title);
        ib_back = (ImageButton) findViewById(R.id.ib_back);
        et_work_num = (EditText) findViewById(R.id.et_work_num);
        et_name = (EditText) findViewById(R.id.et_name);
        //sp_department = (Spinner) findViewById(R.id.sp_department);
        et_late_num = (EditText) findViewById(R.id.et_late_num);
        et_base_wage = (EditText) findViewById(R.id.et_base_wage);
        et_perform_wage = (EditText) findViewById(R.id.et_perform_wage);
        et_deduct_wage = (EditText) findViewById(R.id.et_deduct_wage);
        et_actual = (EditText) findViewById(R.id.et_actual);
        btn_save = (Button) findViewById(R.id.btn_save);

        ib_back.setOnClickListener(this);
        btn_save.setOnClickListener(this);
    }

    /**
     * 是否是修改信息，赋值
     */
    private void initData() {
        updateWage = (Wage) getIntent().getSerializableExtra("wage");
        if (updateWage != null) {
            tv_title.setText("修改工作工资信息");
            et_work_num.setText(updateWage.getWork_num());
            et_name.setText(updateWage.getName());
            et_late_num.setText(updateWage.getLate_num());
            et_base_wage.setText(updateWage.getBase_wages());
            et_deduct_wage.setText(updateWage.getDeduct_wages());
            et_perform_wage.setText(updateWage.getPerform_wages());
            et_actual.setText(updateWage.getActual());
            //部门
            /*String department = updateWage.getDepartment();
            int d_index = 0;
            String[] departments = getResources().getStringArray(R.array.department_array);
            for (int i = 0; i < departments.length; i++) {
                if (department.equals(departments[i])){
                    d_index = i;
                    break;
                }
            }
            sp_department.setSelection(d_index);*/


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_back:
                finish();
                break;
            case R.id.btn_save:
                submit();
                break;
        }
    }

    private void submit() {
        // validate
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

        String late_num = et_late_num.getText().toString().trim();
        if (TextUtils.isEmpty(late_num)) {
            Toast.makeText(this, "迟到次数不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String base_wage = et_base_wage.getText().toString().trim();
        if (TextUtils.isEmpty(base_wage)) {
            Toast.makeText(this, "基本工资不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String perform_wage = et_perform_wage.getText().toString().trim();
        if (TextUtils.isEmpty(perform_wage)) {
            Toast.makeText(this, "绩效奖励不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String deduct_wage = et_deduct_wage.getText().toString().trim();
        if (TextUtils.isEmpty(deduct_wage)) {
            Toast.makeText(this, "扣除工资不能为空", Toast.LENGTH_SHORT).show();
            return;
        }

        String actual = et_actual.getText().toString().trim();
        if (TextUtils.isEmpty(actual)) {
            Toast.makeText(this, "实发工资不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        //String department = sp_department.getSelectedItem().toString();
        WageDao dao = new WageDaoImpl(new DBHelper(this));

        if (updateWage==null){
            //TODO 判断工号是否存在
            boolean existUser = dao.isExistUser(num);
            if (existUser){
                Toast.makeText(this, "工号已存在", Toast.LENGTH_SHORT).show();
                return;
            }
            Wage wage = new Wage(num,name,late_num,base_wage,perform_wage,deduct_wage,actual);
            //插入保存到数据库
            long l = dao.insert(wage);
            if (l > 0) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }else {
            updateWage.setWork_num(num);
            updateWage.setName(name);
            updateWage.setLate_num(late_num);
            updateWage.setBase_wages(base_wage);
            updateWage.setPerform_wages(perform_wage);
            updateWage.setDeduct_wages(deduct_wage);
            //updateWage.setDepartment(department);
            updateWage.setActual(actual);
            int update = dao.update(updateWage);
            if (update > 0) {
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
