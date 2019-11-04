package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.room.RoomDao;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mustafa.sofraNew.data.local.room.RoomManger.getInstance;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {

    public FoodItemsData OrderDetailData;
    @BindView(R.id.Fragment_client_order_detail_img_foodimage)
    PorterShapeImageView FragmentClientOrderDetailImgFoodimage;
    @BindView(R.id.Fragment_client_order_detail_tv_foodname)
    TextView FragmentClientOrderDetailTvFoodname;
    @BindView(R.id.Fragment_client_order_detail_tv_fooddiscription)
    TextView FragmentClientOrderDetailTvFooddiscription;
    @BindView(R.id.Fragment_client_order_detail_tv_foodprice)
    TextView FragmentClientOrderDetailTvFoodprice;
    @BindView(R.id.Fragment_client_order_detail_tv_timeforready)
    TextView FragmentClientOrderDetailTvTimeforready;
    @BindView(R.id.Fragment_client_order_detail_ed_speial_order)
    TextInputLayout FragmentClientOrderDetailEdSpeialOrder;
    @BindView(R.id.Fragment_client_order_detail_tv_plus)
    TextView FragmentClientOrderDetailTvPlus;
    @BindView(R.id.Fragment_client_order_detail_tv_result)
    TextView FragmentClientOrderDetailTvResult;
    @BindView(R.id.Fragment_client_order_detail_tv_minus)
    TextView FragmentClientOrderDetailTvMinus;
    Unbinder unbinder;
    private int result = 1;
    private RoomDao roomDao;
    private AllOrderFragment allOrderFragment;
//    private RoomDao roomDao;
//    private List<FoodItemsData> itemFoodDataList=new ArrayList<>();

    public OrderDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_order_detail, container, false);
        unbinder = ButterKnife.bind(this, view);
        GetDetailData();
        checkcounter();
        return view;
    }

    private void checkcounter() {

        FragmentClientOrderDetailTvPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentClientOrderDetailTvResult.setText(String.valueOf(result++));
            }
        });

        FragmentClientOrderDetailTvMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result < 1) {

                } else {
                    FragmentClientOrderDetailTvResult.setText(String.valueOf(result--));
                }
            }
        });
    }

    private void GetDetailData() {

        Glide.with(getActivity()).load(OrderDetailData.getPhotoUrl()).into(FragmentClientOrderDetailImgFoodimage);
        FragmentClientOrderDetailTvFoodname.setText(OrderDetailData.getName());
        FragmentClientOrderDetailTvFooddiscription.setText(OrderDetailData.getDescription());
        FragmentClientOrderDetailTvFoodprice.setText(" السعر : " + OrderDetailData.getPrice() + " ريال ");
        FragmentClientOrderDetailTvTimeforready.setText(" مدة تجهيز الطلب : " + OrderDetailData.getPreparingTime() + " دقيقة ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_client_order_detail_btn_add_cart)
    public void onViewClicked() {
//        final FoodItemsData itemFoodData = new FoodItemsData(OrderDetailData.getId(),OrderDetailData.getCreatedAt(),OrderDetailData.getUpdatedAt(),
//                OrderDetailData.getName(),OrderDetailData.getDescription(),OrderDetailData.getPrice(),OrderDetailData.getOfferPrice(),
//                OrderDetailData.getPreparingTime(),OrderDetailData.getPhoto(),OrderDetailData.getRestaurantId(),OrderDetailData.getPhotoUrl(),
//                OrderDetailData.getHasOffer(),OrderDetailData.getCategory(),OrderDetailData.getNote(),OrderDetailData.getCount());
//        roomDao = getInstance(getActivity()).roomDao();
//        Executors.newSingleThreadExecutor().execute(new Runnable() {
//            @Override
//            public void run() {
//                roomDao.insert(itemFoodData);
//                allOrderFragment = new AllOrderFragment();
//                allOrderFragment.foodItemsDataList = roomDao.getAllItem();
//                getActivity().runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HelperMethods.replace(allOrderFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
//                    }
//                });
//            }
//        });
//    }
    }
}