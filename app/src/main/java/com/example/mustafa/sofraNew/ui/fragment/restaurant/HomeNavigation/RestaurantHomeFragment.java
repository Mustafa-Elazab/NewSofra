package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.ResturantMyItemsAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantmyitems.RestaurantMyItems;
import com.example.mustafa.sofraNew.data.model.restaurantmyitems.Resturant_My_Items_Data;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.order.RestaurantAddFoodFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantHomeFragment extends Fragment {

    @BindView(R.id.Fragment_home_resturantname)
    TextView FragmentHomeResturantname;
    @BindView(R.id.Fragment_home_recycler)
    RecyclerView FragmentHomeRecycler;
    @BindView(R.id.Fragment_home_addorder)
    FloatingActionButton FragmentHomeAddorder;
    private API ApiServices;
    private List<Resturant_My_Items_Data> data;

    private ResturantMyItemsAdapter adapter;
    Unbinder unbinder;
    private String api_token = "";


    public RestaurantHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_home, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(API.class);
        FragmentHomeResturantname.setText(SharedPreferencesManger.LoadData(getActivity(),RESTURANT_NAME));

        setRecycler();
        return view;
    }


    private void setRecycler() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        FragmentHomeRecycler.setLayoutManager(manager);
        adapter = new ResturantMyItemsAdapter(getContext(), getActivity());
        FragmentHomeRecycler.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_home_addorder)
    public void onViewClicked() {

        RestaurantAddFoodFragment restaurantAddFoodFragment = new RestaurantAddFoodFragment();
        HelperMethods.replace(restaurantAddFoodFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

    }


    @Override
    public void onStart() {
        super.onStart();
        getApiServicesData();
    }

    private void getApiServicesData() {

        api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        ApiServices.restaurantmyitems(api_token, 1)
                .enqueue(new Callback<RestaurantMyItems>() {
                    @Override
                    public void onResponse(Call<RestaurantMyItems> call, Response<RestaurantMyItems> response) {

                        Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                        try {

                            if (response.body().getStatus() == 1) {

                                Log.i("onRes: ", response.body().getData().getData().toString());


                                List<Resturant_My_Items_Data> data = response.body().getData().getData();
                                adapter.sendDataToAdapter(data);

                                adapter.notifyDataSetChanged();
                            }

                        } catch (Exception e) {

                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<RestaurantMyItems> call, Throwable t) {

                    }
                });
    }


}
