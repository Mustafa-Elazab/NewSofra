package com.example.mustafa.sofraNew.ui.activity.Splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.example.mustafa.sofraNew.ui.activity.LoginActivity;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.CLIENT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTAURANT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.SaveData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_TYPE;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.Activity_Splash_order, R.id.Activity_Splash_sale})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Activity_Splash_order:

                SaveData(this, USER_TYPE, CLIENT);
                Intent intent1 = new Intent(SplashActivity.this, ClientActivity.class);
                startActivity(intent1);

                break;
            case R.id.Activity_Splash_sale:
                String Resturant_api_token = SharedPreferencesManger.LoadData(this, RESTURANT_API_TOKEN);
                SaveData(this, USER_TYPE, RESTAURANT);
                if (Resturant_api_token != null) {
                    Intent resturant = new Intent(SplashActivity.this, ResturantActivity.class);
                    startActivity(resturant);
                } else {
                    Intent login = new Intent(SplashActivity.this, LoginActivity.class);
                    login.putExtra("type", "RestaurantsList");
                    startActivity(login);
                }

                break;
        }


    }


}
