package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllOrderDetail2Fragment extends Fragment {


    public List<FoodItemsData> foodItemList=new ArrayList<>();
    @BindView(R.id.Fragment_client_all_order_detail2_img_clientimage)
    CircleImageView FragmentClientAllOrderDetail2ImgClientimage;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_clientname)
    TextView FragmentClientAllOrderDetail2TvClientname;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_clientcreate)
    TextView FragmentClientAllOrderDetail2TvClientcreate;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_clientaddress)
    TextView FragmentClientAllOrderDetail2TvClientaddress;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_order1)
    TextView FragmentClientAllOrderDetail2TvOrder1;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_order2)
    TextView FragmentClientAllOrderDetail2TvOrder2;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_order3)
    TextView FragmentClientAllOrderDetail2TvOrder3;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_price)
    TextView FragmentClientAllOrderDetail2TvPrice;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_deliverycost)
    TextView FragmentClientAllOrderDetail2TvDeliverycost;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_total)
    TextView FragmentClientAllOrderDetail2TvTotal;
    @BindView(R.id.Fragment_client_all_order_detail2_tv_paid)
    TextView FragmentClientAllOrderDetail2TvPaid;
    Unbinder unbinder;

    public AllOrderDetail2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_all_order_detail2, container, false);
        unbinder = ButterKnife.bind(this, view);
        getData();
        return view;
    }

    private void getData() {

        for (int i = 0; i < foodItemList.size(); i++) {

//            Glide.with(getActivity()).load(foodItemList.get(i).getRestaurant().getPhotoUrl()).into(FragmentClientAllOrderDetail2ImgClientimage);
//            FragmentClientAllOrderDetail2TvClientname.setText(foodItemList.get(i).getRestaurant().getName());
//            FragmentClientAllOrderDetail2TvClientcreate.setText(foodItemList.get(i).getRestaurant().getCreatedAt());

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
