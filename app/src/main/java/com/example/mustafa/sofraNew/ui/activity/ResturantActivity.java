package com.example.mustafa.sofraNew.ui.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.NotificationsFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.Home.RestaurantCategoriesFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.MoreFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantCommissionFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantInfoFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;

public class ResturantActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    RestaurantCategoriesFragment categoriesFragment=new RestaurantCategoriesFragment();
                    HelperMethods.replace(categoriesFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
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
                    moreFragment.Type = "RestaurantsList";
                    HelperMethods.replace(moreFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                    return true;
            }
            return false;
        }
    };
    private API ApiServices;
    private String Api_token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_food_order_cycle);
        ButterKnife.bind(this);
        RestaurantCategoriesFragment categoriesFragment=new RestaurantCategoriesFragment();
        HelperMethods.replace(categoriesFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        ApiServices=RetrofitClient.getClient().create(API.class);
        Api_token=SharedPreferencesManger.LoadData(this,RESTURANT_API_TOKEN);
        getFireBaseNotifications();

    }

    @OnClick({R.id.Activity_Resturant_home_img_notifications, R.id.Activity_Resturant_home_img_calculate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Activity_Resturant_home_img_notifications:

                NotificationsFragment notificationsFragment = new NotificationsFragment();
                notificationsFragment.Type = "RestaurantsList";
                HelperMethods.replace(notificationsFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                break;
            case R.id.Activity_Resturant_home_img_calculate:

                RestaurantCommissionFragment commissionFragment = new RestaurantCommissionFragment();
                HelperMethods.replace(commissionFragment, getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

                break;
        }
    }


    private void getFireBaseNotifications() {

        final String token = FirebaseInstanceId.getInstance().getToken();

        ApiServices.RegisterToken(token, Api_token, "android")
                .enqueue(new Callback<PublicResponse>() {
                    @Override
                    public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(ResturantActivity.this, response.body().getMsg(), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(ResturantActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PublicResponse> call, Throwable t) {

                    }
                });
    }
}
