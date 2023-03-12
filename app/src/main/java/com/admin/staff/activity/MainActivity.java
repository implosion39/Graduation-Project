package com.admin.staff.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.admin.staff.R;
import com.admin.staff.fragment.HomeFragment;
import com.admin.staff.fragment.MeFragment;
import com.admin.staff.fragment.WagesFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView navigation;
    private Fragment[] fragments;
    private int currentIndex, index;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        fragments = new Fragment[]{new HomeFragment(), new WagesFragment(), new MeFragment()};
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, fragments[0])
                .show(fragments[0]).commit();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                index = 0;
                break;
            case R.id.navigation_dashboard:
                index = 1;
                break;
            case R.id.navigation_notifications:
                index = 2;
                break;
        }
        showFragment(index);

        return true;
    }

    /**
     * 切换fragment
     *
     * @param index：0-2
     */
    public void showFragment(int index) {
        if (currentIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager().beginTransaction();
            trx.hide(fragments[currentIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fl_main, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }

        currentIndex = index;
    }

}
