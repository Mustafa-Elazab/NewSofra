package com.example.mustafa.sofraNew.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order.CurrentOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order.NewOrderFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order.PreviousOrderFragment;

import java.util.ArrayList;
import java.util.List;

public class USER_ORDER_ADAPTER extends FragmentPagerAdapter {


    Fragment fragment[] = {new NewOrderFragment(),new CurrentOrderFragment(), new PreviousOrderFragment()};
    List<String> title = new ArrayList<>();

    public USER_ORDER_ADAPTER(FragmentManager fm,Context activity) {
        super(fm);
        title.add(activity.getResources().getString(R.string.new_order));
        title.add(activity.getString(R.string.current_order));
        title.add(activity.getString(R.string.previos_order));
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
