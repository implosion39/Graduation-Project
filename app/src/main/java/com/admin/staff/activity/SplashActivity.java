package com.admin.staff.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.admin.staff.R;
import com.admin.staff.util.SpUtil;


/**
 * @author DaQiang
 * @Date 2019/6/5 7:27
 * @Desc 欢迎页面
 */

public class SplashActivity extends AppCompatActivity {

    private ImageView iv_welcome;//背景图片

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);
        initView();

        //添加一个3秒的透明渐变的动画
        Animation animation = new AlphaAnimation(0.0f, 1.0f);//透明度
        animation.setDuration(3000);//设置动画时间
        //动画监听
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //动画结束，判断是否登录，登录过就跳转到主页，未登录过跳转到登录页面
                boolean isLogin = SpUtil.getBoolean("isLogin", false);
                if (isLogin){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动动画
        iv_welcome.startAnimation(animation);

    }

    /**
     * 初始化控件
     */
    private void initView() {
        iv_welcome = (ImageView) findViewById(R.id.iv_welcome);
    }
}
