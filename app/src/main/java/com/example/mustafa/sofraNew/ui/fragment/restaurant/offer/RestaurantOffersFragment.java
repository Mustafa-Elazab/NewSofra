package com.example.mustafa.sofraNew.ui.fragment.restaurant.offer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.ResturantOffersAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantmyoffers.RestaurantMyOffers;
import com.example.mustafa.sofraNew.data.model.restaurantmyoffers.Restaurant_My_Offers_Data;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantOffersFragment extends Fragment {


    @BindView(R.id.Fragment_seller_offers_rv)
    RecyclerView FragmentSellerOffersRv;
    Unbinder unbinder;
    private API ApiServices;
    private List<Restaurant_My_Offers_Data> restaurantoffersDataList = new ArrayList<>();
    private ResturantOffersAdapter adapter;


    public RestaurantOffersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_offers, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices= RetrofitClient.getClient().create(API.class);
        setupRecycler();
        GetDataOffers();
        return view;
    }

    private void GetDataOffers() {

        String api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        Toast.makeText(getActivity(), api_token, Toast.LENGTH_SHORT).show();
        ApiServices.restaurantmyoffers(api_token,1).enqueue(
                new Callback<RestaurantMyOffers>() {
                    @Override
                    public void onResponse(Call<RestaurantMyOffers> call, Response<RestaurantMyOffers> response) {
                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                        try {

                            if (response.body().getStatus()==1) {

                                restaurantoffersDataList.addAll(response.body().getData().getData());
                                adapter.notifyDataSetChanged();


                            }

                        }catch (Exception e){
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }


                    }

                    @Override
                    public void onFailure(Call<RestaurantMyOffers> call, Throwable t) {

                    }
                }

        );

    }

    private void setupRecycler() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentSellerOffersRv, manager);

        adapter = new ResturantOffersAdapter(getActivity(), getActivity(), restaurantoffersDataList);
        FragmentSellerOffersRv.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_seller_offers_btn_add)
    public void onViewClicked() {

        RestaurantAddOfferFragment sellerAddOfferFragment=new RestaurantAddOfferFragment();
        HelperMethods.replace(sellerAddOfferFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Resturant_Frame_Home,null,null);


    }
}
