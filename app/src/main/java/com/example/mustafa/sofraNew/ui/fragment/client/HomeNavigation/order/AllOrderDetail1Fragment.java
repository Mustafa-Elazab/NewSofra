package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOrderDetail1Fragment extends Fragment {


    public List<FoodItemsData> foodItemDataList=new ArrayList<>();
    @BindView(R.id.Fragment_client_all_order_detail1_ed_notes)
    TextInputLayout FragmentClientAllOrderDetail1EdNotes;
    @BindView(R.id.Fragment_client_all_order_detail1_tv_txtaddress)
    TextView FragmentClientAllOrderDetail1TvTxtaddress;
    @BindView(R.id.Fragment_client_all_order_detail1_tv_price)
    TextView FragmentClientAllOrderDetail1TvPrice;
    @BindView(R.id.Fragment_client_all_order_detail1_tv_deliverycost)
    TextView FragmentClientAllOrderDetail1TvDeliverycost;
    @BindView(R.id.Fragment_client_all_order_detail1_tv_total)
    TextView FragmentClientAllOrderDetail1TvTotal;
    Unbinder unbinder;

    public AllOrderDetail1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_order_detail1, container, false);
        unbinder = ButterKnife.bind(this, view);

        for (int i = 0; i < foodItemDataList.size(); i++) {

            FragmentClientAllOrderDetail1TvPrice.setText(" المجموع : "+foodItemDataList.get(i).getPrice());
//            String price = foodItemDataList.get(i).getPrice();
//            String deliveryCost = foodItemDataList.get(i).getRestaurant().getDeliveryCost();
//            FragmentClientAllOrderDetail1TvDeliverycost.setText(" رسوم التوصيل : "+foodItemDataList.get(i).getRestaurant().getDeliveryCost());
//            FragmentClientAllOrderDetail1TvTotal.setText("المبلغ الاجمالي : "+price+deliveryCost);
        }

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_client_all_order_detail1_btn_confirm)
    public void onViewClicked() {

        AllOrderDetail2Fragment orderDetail2Fragment=new AllOrderDetail2Fragment();
        orderDetail2Fragment.foodItemList=foodItemDataList;
        HelperMethods.replace(orderDetail2Fragment,getActivity().getSupportFragmentManager(),R.id.Activity_Frame_Home
        ,null,null);
    }
}
