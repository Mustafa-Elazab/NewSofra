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
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItems;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
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

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantHomeFragment extends Fragment {

    public Integer category_id;
    public String category_name;
    @BindView(R.id.Fragment_home_resturantname)
    TextView FragmentHomeResturantname;
    @BindView(R.id.Fragment_home_recycler)
    RecyclerView FragmentHomeRecycler;
    @BindView(R.id.Fragment_home_addorder)
    FloatingActionButton FragmentHomeAddorder;
    private API ApiServices;
    private List<FoodItemsData> data;
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
        FragmentHomeResturantname.setText(category_name);
        getApiServicesData();
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
        restaurantAddFoodFragment.categoryid=category_id;
        HelperMethods.replace(restaurantAddFoodFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
    }

    private void getApiServicesData() {

        api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        ApiServices.restaurantmyitems(api_token,category_id)
                .enqueue(new Callback<FoodItems>() {
                    @Override
                    public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                List<FoodItemsData> foodItemsData = response.body().getData().getData();
                                data.addAll(foodItemsData);
                                adapter.sendDataToAdapter(RestaurantHomeFragment.this.data);
                                adapter.notifyDataSetChanged();

                                for (int i = 0; i <foodItemsData.size(); i++) {
                                    Log.i("onResponse",response.body().getData().getData().get(i).getName());
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<FoodItems> call, Throwable t) {
                        Log.i("onFailure",t.getMessage());
                    }
                });
    }
}
