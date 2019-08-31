package com.example.mustafa.sofraNew.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.order.RestaurantCurrentOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.order.RestaurantNewOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.order.RestaurantPreviousOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class RestaurantOrderAdapter extends FragmentPagerAdapter {


    Fragment fragment[]={new RestaurantNewOrderFragment(),new RestaurantCurrentOrderFragment(),new RestaurantPreviousOrderFragment()};

    List<String> title=new ArrayList<>();

    public RestaurantOrderAdapter(FragmentManager fm, Context context) {
        super(fm);
        title.add(context.getString(R.string.new_order));
        title.add(context.getString(R.string.current_orders));
        title.add(context.getString(R.string.previos_orders));


    }

    @Override
    public Fragment getItem(int i) {
        return fragment[i];
    }

    @Override
    public int getCount() {
        return fragment.length;
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return title.get(position);
    }
}
