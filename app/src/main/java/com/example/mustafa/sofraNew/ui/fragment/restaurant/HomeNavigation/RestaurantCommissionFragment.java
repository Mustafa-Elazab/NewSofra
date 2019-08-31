package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantcommissions.RestaurantCommissions;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantCommissionFragment extends Fragment {


    @BindView(R.id.list_seller_commission_tv_restuantsales_rv)
    TextView listSellerCommissionTvRestuantsalesRv;
    @BindView(R.id.list_seller_commission_tv_appcommission_rv)
    TextView listSellerCommissionTvAppcommissionRv;
    @BindView(R.id.list_seller_commission_tv_paid_rv)
    TextView listSellerCommissionTvPaidRv;
    @BindView(R.id.list_seller_commission_tv_residual_rv)
    TextView listSellerCommissionTvResidualRv;
    @BindView(R.id.user_order_name)
    TextView userOrderName;
    @BindView(R.id.user_order_order)
    TextView userOrderOrder;
    Unbinder unbinder;
    private API ApiServices;

    public RestaurantCommissionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_commission, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices= RetrofitClient.getClient().create(API.class);

        GetAPiData();
        return view;
    }

    private void GetAPiData() {

        String api_token = SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN);

        ApiServices.restaurantcommissions(api_token).enqueue(new Callback<RestaurantCommissions>() {
            @Override
            public void onResponse(Call<RestaurantCommissions> call, Response<RestaurantCommissions> response) {

                try {

                    if (response.body().getStatus()==1) {


                        listSellerCommissionTvRestuantsalesRv.setText("مبيعات المطعم :"+response.body().getData().getTotal());
                        listSellerCommissionTvAppcommissionRv.setText("عمولات التطبيق :"+response.body().getData().getCommission());
                        listSellerCommissionTvPaidRv.setText("ماتم سداده :"+response.body().getData().getPayments());
                        listSellerCommissionTvResidualRv.setText("المتبقي :"+response.body().getData().getNetCommissions());


                    }

                }catch (Exception e){

                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<RestaurantCommissions> call, Throwable t) {

            }
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
