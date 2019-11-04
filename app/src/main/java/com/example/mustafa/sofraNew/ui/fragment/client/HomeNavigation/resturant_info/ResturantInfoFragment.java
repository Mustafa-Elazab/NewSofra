package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantHomeFragment.Restaurant_Id;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantInfoFragment extends Fragment {


    @BindView(R.id.Fragment_food_order_cycle_home_resturantinfo_info_tv_status)
    TextView FragmentFoodOrderCycleHomeResturantinfoInfoTvStatus;
    @BindView(R.id.Fragment_food_order_cycle_home_resturantinfo_info_tv_city)
    TextView FragmentFoodOrderCycleHomeResturantinfoInfoTvCity;
    @BindView(R.id.Fragment_food_order_cycle_home_resturantinfo_info_tv_block)
    TextView FragmentFoodOrderCycleHomeResturantinfoInfoTvBlock;
    @BindView(R.id.Fragment_food_order_cycle_home_resturantinfo_info_tv_minmum)
    TextView FragmentFoodOrderCycleHomeResturantinfoInfoTvMinmum;
    @BindView(R.id.Fragment_food_order_cycle_home_resturantinfo_info_tv_delivery)
    TextView FragmentFoodOrderCycleHomeResturantinfoInfoTvDelivery;
    Unbinder unbinder;
    private API ApiServices;

    public ResturantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_resturant_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getInfoData();
        return view;
    }

    private void getInfoData() {

        ApiServices.onInfo(Restaurant_Id).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                try {
                    if (response.body().getStatus()==1) {
                        FragmentFoodOrderCycleHomeResturantinfoInfoTvBlock.setText("الحي :"+response.body().getData().getData().getRegion().getName());
                        FragmentFoodOrderCycleHomeResturantinfoInfoTvDelivery.setText("رسوم التوصيل :"+response.body().getData().getData().getDeliveryCost());
                        FragmentFoodOrderCycleHomeResturantinfoInfoTvMinmum.setText("الحد الادني :"+response.body().getData().getData().getMinimumCharger());
                        FragmentFoodOrderCycleHomeResturantinfoInfoTvStatus.setText("الحالة :"+response.body().getData().getData().getAvailability());
                        FragmentFoodOrderCycleHomeResturantinfoInfoTvCity.setText("المدينة :"+response.body().getData().getData().getRegion().getCity().getName());

                    }
                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
