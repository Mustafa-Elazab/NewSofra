package com.example.mustafa.sofraNew.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantComment_RatingFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantInfoFragment;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantMenuFragment;

import java.util.ArrayList;

public class VIEW_PAGER_RESTURANT_ADAPTER extends FragmentPagerAdapter {


    Fragment fragment[]={new ResturantMenuFragment(),new ResturantComment_RatingFragment(),new
            ResturantInfoFragment()
    };

    ArrayList<String> title=new ArrayList<>();

    public VIEW_PAGER_RESTURANT_ADAPTER(FragmentManager fm, Context activity) {
        super(fm);

        title.add(activity.getString(R.string.menu));
        title.add(activity.getString(R.string.comment));
        title.add(activity.getString(R.string.info));

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
