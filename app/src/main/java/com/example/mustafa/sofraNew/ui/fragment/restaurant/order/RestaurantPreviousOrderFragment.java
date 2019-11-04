package com.example.mustafa.sofraNew.ui.fragment.restaurant.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.ResturantNewOrderAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.ListOfOrders;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.OrdersData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantPreviousOrderFragment extends Fragment {


    @BindView(R.id.Fragment_seller_pervious_order)
    RecyclerView FragmentSellerPerviousOrder;
    Unbinder unbinder;
    private API ApiServices;
    private ResturantNewOrderAdapter adapter;
    private List<OrdersData> restaurantperviousorderDataList = new ArrayList<>();
    private String type="completed";


    public RestaurantPreviousOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_previous_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        setRecycler();
        GetMyNewOrder();
        return view;
    }

    private void setRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentSellerPerviousOrder, manager);
        adapter = new ResturantNewOrderAdapter(getActivity(), getActivity(), restaurantperviousorderDataList ,type);
        FragmentSellerPerviousOrder.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }



    private void GetMyNewOrder() {
        ApiServices.restaurantmyorder(SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN),
                "completed", 1).enqueue(new Callback<ListOfOrders>() {
            @Override
            public void onResponse(Call<ListOfOrders> call, Response<ListOfOrders> response) {

                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                try {
                    if (response.body().getStatus() == 1) {
                        restaurantperviousorderDataList.addAll(response.body().getData().getData());
                        Log.i("onOrderData", response.body().getData().getData().toString());
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ListOfOrders> call, Throwable t) {
            }
        });
    }
}
