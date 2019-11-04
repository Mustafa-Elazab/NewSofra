package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.RestaurantOrderAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantOrderFragment extends Fragment {


    @BindView(R.id.View_Pager)
    android.support.v4.view.ViewPager ViewPager;
    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    Unbinder unbinder;

    public RestaurantOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_order_container, container, false);
        unbinder = ButterKnife.bind(this, view);
        ViewPager.setAdapter(new RestaurantOrderAdapter(getChildFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(ViewPager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
