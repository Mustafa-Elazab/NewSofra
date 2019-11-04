package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.offer;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.offer.offers.OffersData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientOfferDetailFragment extends Fragment {


    public OffersData offer_data;
    Unbinder unbinder;
    @BindView(R.id.Fragment_offer_detail_tv_text)
    TextView FragmentOfferDetailTvText;
    @BindView(R.id.Fragment_offer_detail_tv_description)
    TextView FragmentOfferDetailTvDescription;
    @BindView(R.id.Fragment_offer_detail_tv_dateTo)
    TextView FragmentOfferDetailTvDateTo;
    @BindView(R.id.Fragment_offer_detail_tv_dateFrom)
    TextView FragmentOfferDetailTvDateFrom;
    @BindView(R.id.Fragment_offer_detail_Btn_getIn)
    Button FragmentOfferDetailBtnGetIn;
    private API ApiServices;


    public ClientOfferDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_offer_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getClientOfferData();
        return view;
    }

    private void getClientOfferData() {
           FragmentOfferDetailTvText.setText(offer_data.getName());
           FragmentOfferDetailTvDescription.setText(offer_data.getDescription());
           FragmentOfferDetailTvDateTo.setText(offer_data.getCreatedAt());
           FragmentOfferDetailTvDateFrom.setText(offer_data.getEndingAt());

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_offer_detail_Btn_getIn)
    public void onViewClicked() {

    }
}
