package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.ClientOrderAdapter;
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

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreviousOrderFragment extends Fragment {


    @BindView(R.id.Fragment_client_pervious_order_recycler)
    RecyclerView FragmentClientPerviousOrderRecycler;
    private String Type="completed";
    private String api_token="";
    private List<OrdersData> client_order_data=new ArrayList<>();
    Unbinder unbinder;
    private API ApiServices;

    public PreviousOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_previous_user_order, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices=RetrofitClient.getClient().create(API.class);
        api_token=SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN);
        getCurrentOrder();
        setupRecycler();
        return view;
    }

    private void setupRecycler() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(),FragmentClientPerviousOrderRecycler,manager);

    }

    private void getCurrentOrder() {


        ApiServices.getClientOrder(SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN), Type, 1).enqueue(new Callback<ListOfOrders>() {
            @Override
            public void onResponse(Call<ListOfOrders> call, Response<ListOfOrders> response) {

                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                try {

                    if (response.body().getStatus() == 1) {
                        List<OrdersData> clientOrderData = response.body().getData().getData();
                        Toast.makeText(getActivity(), clientOrderData.toString(), Toast.LENGTH_SHORT).show();
                        client_order_data.addAll(clientOrderData);
                        ClientOrderAdapter adapter = new ClientOrderAdapter(getActivity(), getActivity(), client_order_data, Type);
                        FragmentClientPerviousOrderRecycler.setAdapter(adapter);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
