package com.example.mustafa.sofraNew.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.NotificationsFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.MoreFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantCommissionFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantInfoFragment;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResturantActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    RestaurantHomeFragment homeFragment = new RestaurantHomeFragment();
                    HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
                    return true;
                case R.id.navigation_order:
                    RestaurantOrderFragment orderFragment = new RestaurantOrderFragment();
                    HelperMethods.replace(orderFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                    return true;
                case R.id.navigation_info:

                    RestaurantInfoFragment infoFragment = new RestaurantInfoFragment();
                    HelperMethods.replace(infoFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                    return true;
                case R.id.navigation_more:

                    MoreFragment moreFragment = new MoreFragment();
                    moreFragment.Type="Restaurant";
                    HelperMethods.replace(moreFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_food_order_cycle);
        ButterKnife.bind(this);
        RestaurantHomeFragment homeFragment = new RestaurantHomeFragment();
        HelperMethods.replace(homeFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @OnClick({R.id.Activity_Resturant_home_img_notifications, R.id.Activity_Resturant_home_img_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Activity_Resturant_home_img_notifications:

                NotificationsFragment notificationsFragment=new NotificationsFragment();
                HelperMethods.replace(notificationsFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);


                break;
            case R.id.Activity_Resturant_home_img_calculate:

                RestaurantCommissionFragment commissionFragment=new RestaurantCommissionFragment();
                HelperMethods.replace(commissionFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                break;
        }
    }
}
