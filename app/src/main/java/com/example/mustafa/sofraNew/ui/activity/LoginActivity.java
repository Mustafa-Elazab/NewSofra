package com.example.mustafa.sofraNew.ui.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.LoginCycle.RestaurantLoginFragment;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RestaurantLoginFragment restaurantLoginFragment = new RestaurantLoginFragment();
        restaurantLoginFragment.Type = getIntent().getStringExtra("type");
        HelperMethods.replace(restaurantLoginFragment, getSupportFragmentManager(), R.id.login_cycle, null, null);
    }
}
