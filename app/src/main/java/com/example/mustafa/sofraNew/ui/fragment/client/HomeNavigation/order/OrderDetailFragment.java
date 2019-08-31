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
import com.example.mustafa.sofraNew.data.model.items.Items_Data;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderDetailFragment extends Fragment {


    public Items_Data OrderDetailData;
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
    Unbinder unbinder;
    @BindView(R.id.Fragment_client_order_detail_tv_plus)
    TextView FragmentClientOrderDetailTvPlus;
    @BindView(R.id.Fragment_client_order_detail_tv_result)
    TextView FragmentClientOrderDetailTvResult;
    @BindView(R.id.Fragment_client_order_detail_tv_minus)
    TextView FragmentClientOrderDetailTvMinus;
    private int result=1;
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
                if (result == 1) {

                }else {
                    FragmentClientOrderDetailTvResult.setText(String.valueOf(result--));
                }
            }
        });

    }

    private void GetDetailData() {

        Glide.with(getActivity()).load(OrderDetailData.getPhotoUrl()).into(FragmentClientOrderDetailImgFoodimage);
        FragmentClientOrderDetailTvFoodname.setText(OrderDetailData.getName());
        FragmentClientOrderDetailTvFooddiscription.setText(OrderDetailData.getDescription());
        FragmentClientOrderDetailTvFoodprice.setText(OrderDetailData.getPrice());
        FragmentClientOrderDetailTvTimeforready.setText(" مدة تجهيز الطلب : " + OrderDetailData.getPreparingTime() +" دقيقة ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_client_order_detail_btn_add_cart)
    public void onViewClicked() {

        String Speical_Order = FragmentClientOrderDetailEdSpeialOrder.getEditText().getText().toString();

        if (Speical_Order.isEmpty()) {
            Toast.makeText(getActivity(), "لا تريد طلب خاص اشطاا عادي ", Toast.LENGTH_SHORT).show();

        }
    }


}
