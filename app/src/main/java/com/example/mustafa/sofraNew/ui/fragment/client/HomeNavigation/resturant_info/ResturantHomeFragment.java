package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.VIEW_PAGER_RESTURANT_ADAPTER;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurants.Restaurants_Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantHomeFragment extends Fragment {


    public Restaurants_Data restaurantsdata;
    @BindView(R.id.Fragment_resturantinfo_resturant_image)
    CircleImageView FragmentResturantinfoResturantImage;
    @BindView(R.id.Fragment_resturantinfo_resturant_name)
    TextView FragmentResturantinfoResturantName;
    @BindView(R.id.Fragment_resturantinfo_resturant_tablayout)
    TabLayout FragmentResturantinfoResturantTablayout;
    @BindView(R.id.Fragment_resturantinfo_resturant_viewpager)
    ViewPager FragmentResturantinfoResturantViewpager;
    Unbinder unbinder;
    public static Integer Restaurant_Id;

    public ResturantHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_resturantinfo_home, container, false);

        unbinder = ButterKnife.bind(this, view);

        FragmentResturantinfoResturantViewpager.setAdapter(

                new VIEW_PAGER_RESTURANT_ADAPTER(getChildFragmentManager(), getActivity()));

        FragmentResturantinfoResturantTablayout.setupWithViewPager(FragmentResturantinfoResturantViewpager);

        Glide.with(getActivity()).load(restaurantsdata.getPhotoUrl()).into(FragmentResturantinfoResturantImage);
        FragmentResturantinfoResturantName.setText(restaurantsdata.getName());
        Restaurant_Id = restaurantsdata.getId();
      //  SharedPreferencesManger.RESTURANT_ID=Restaurant_Id;

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
