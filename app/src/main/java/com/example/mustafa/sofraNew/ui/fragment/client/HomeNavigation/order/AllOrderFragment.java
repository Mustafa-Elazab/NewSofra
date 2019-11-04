package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.AllOrderAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOrderFragment extends Fragment {


    public List<FoodItemsData> foodItemsDataList = new ArrayList<>();
    @BindView(R.id.Fragment_client_all_order_recycler)
    RecyclerView FragmentClientAllOrderRecycler;
    @BindView(R.id.Fragment_client_all_order_tv_text)
    TextView FragmentClientAllOrderTvText;
    @BindView(R.id.Fragment_client_all_order_tv_price)
    TextView FragmentClientAllOrderTvPrice;
    @BindView(R.id.Fragment_client_all_order_btn_more)
    Button FragmentClientAllOrderBtnMore;
    @BindView(R.id.Fragment_seller_offers_btn_add)
    Button FragmentSellerOffersBtnAdd;
    Unbinder unbinder;
    private String price;

    public AllOrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        getAllOrderData();
        setUpRecycler();
        return view;
    }

    private void setUpRecycler() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentClientAllOrderRecycler, manager);

        AllOrderAdapter adapter = new AllOrderAdapter(getActivity(), getActivity(), foodItemsDataList);
        FragmentClientAllOrderRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    private void getAllOrderData() {
        for (int i = 0; i < foodItemsDataList.size(); i++) {
            price = foodItemsDataList.get(i).getPrice();
        }
        price += price;
        FragmentClientAllOrderTvPrice.setText(price);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_client_all_order_btn_more, R.id.Fragment_seller_offers_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_client_all_order_btn_more:
                break;
            case R.id.Fragment_seller_offers_btn_add:

                if (SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN) != null){
                    AllOrderDetail1Fragment detail1Fragment=new AllOrderDetail1Fragment();
                    detail1Fragment.foodItemDataList=foodItemsDataList;
                    HelperMethods.replace(detail1Fragment,getActivity().getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);
                }
                else {

                    Intent intent=new Intent(getActivity(),LoginActivity.class);
                    getActivity().startActivity(intent);
                }


                break;
        }
    }
}
