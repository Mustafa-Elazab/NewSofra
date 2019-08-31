package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.USER_ORDER_ADAPTER;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {


    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.ViewPager)
    android.support.v4.view.ViewPager ViewPager;
    Unbinder unbinder;

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_order, container, false);


        unbinder = ButterKnife.bind(this, view);

        ViewPager.setAdapter(new USER_ORDER_ADAPTER(getChildFragmentManager(), getActivity()));
        tabLayout.setupWithViewPager(ViewPager);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
