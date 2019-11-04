package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.offer;


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
import com.example.mustafa.sofraNew.adapter.ClientOffersAdapter;
import com.example.mustafa.sofraNew.data.models.offer.offers.Offers;
import com.example.mustafa.sofraNew.data.models.offer.offers.OffersData;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientOfferFragment extends Fragment {


    @BindView(R.id.Fragment_clientoffer_rv_recycler)
    RecyclerView FragmentClientofferRvRecycler;
    Unbinder unbinder;
    private API ApiServices;
    private List<OffersData> offersData = new ArrayList<>();
    private ClientOffersAdapter clientOffersAdapter;


    public ClientOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getClientOfferData();
        setUpRecycler();
        return view;
    }

    private void getClientOfferData() {


        ApiServices.onOffers().enqueue(new Callback<Offers>() {
            @Override
            public void onResponse(Call<Offers> call, Response<Offers> response) {
                try {
                    Log.i( "onOffers",response.body().getData().getData().toString());
                    if (response.body().getStatus() == 1) {
                        List<OffersData> data = response.body().getData().getData();
                        offersData.addAll(data);
                        clientOffersAdapter = new ClientOffersAdapter(getActivity(), getActivity(), offersData);
                        FragmentClientofferRvRecycler.setAdapter(clientOffersAdapter);
                        clientOffersAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Offers> call, Throwable t) {

            }
        });
    }

    private void setUpRecycler() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentClientofferRvRecycler, layoutManager);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
