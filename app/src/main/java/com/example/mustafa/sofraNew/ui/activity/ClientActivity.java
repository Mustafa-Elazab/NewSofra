package com.example.mustafa.sofraNew.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.MoreFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.HomeFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.OrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.YourInfoFragment;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.NotificationsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ClientActivity extends AppCompatActivity {


    @BindView(R.id.Activity_home_img_store)
    ImageView ActivityHomeImgMenu;
    @BindView(R.id.Activity_home_img_notifications)
    ImageView ActivityHomeImgNotifications;
    @BindView(R.id.Activity_home_relative)
    RelativeLayout ActivityHomeRelative;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    HomeFragment homeFragment = new HomeFragment();
                    HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
                    return true;
                case R.id.navigation_order:
                    OrderFragment orderFragment = new OrderFragment();
                    HelperMethods.replace(orderFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);

                    return true;
                case R.id.navigation_info:

                    YourInfoFragment infoFragment = new YourInfoFragment();
                    HelperMethods.replace(infoFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);

                    return true;
                case R.id.navigation_more:

                    MoreFragment moreFragment = new MoreFragment();
                    moreFragment.Type="Client";
                    HelperMethods.replace(moreFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);

                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_order_cycle);
        ButterKnife.bind(this);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        HomeFragment homeFragment = new HomeFragment();
        HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @OnClick({R.id.Activity_home_img_store, R.id.Activity_home_img_notifications})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Activity_home_img_store:

                Intent Login=new Intent(this,LoginActivity.class);
                startActivity(Login);

                break;
            case R.id.Activity_home_img_notifications:

                NotificationsFragment notificationsFragment=new NotificationsFragment();
                HelperMethods.replace(notificationsFragment, getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);

                break;
        }
    }
}
