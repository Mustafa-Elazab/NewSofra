package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.Client;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantRegister2Fragment extends Fragment {


    public List<String> Restaurnat_Info;
    @BindView(R.id.Fragment_seller_register_sp_categories)
    Spinner FragmentSellerRegisterSpCategories;
    @BindView(R.id.Fragment_seller_register2_ed_min)
    TextInputLayout FragmentSellerRegister2EdMin;
    @BindView(R.id.Fragment_seller_register2_ed_delivery)
    TextInputLayout FragmentSellerRegister2EdDelivery;
    @BindView(R.id.Fragment_seller_register2_ed_phone)
    TextInputLayout FragmentSellerRegister2EdPhone;
    @BindView(R.id.Fragment_seller_register2_ed_whatsapp)
    TextInputLayout FragmentSellerRegister2EdWhatsapp;
    @BindView(R.id.Fragment_seller_register2_img_market)
    ImageView FragmentSellerRegister2ImgMarket;
    @BindView(R.id.Fragment_seller_register2_btn_sign)
    Button FragmentSellerRegister2BtnSign;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    Unbinder unbinder;
    private API ApiServices;

    public RestaurantRegister2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_register2, container, false);


        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_seller_register2_img_market, R.id.Fragment_seller_register2_btn_sign})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_seller_register2_img_market:
                GetImage();
                break;
            case R.id.Fragment_seller_register2_btn_sign:

                CheckVariables();

                break;
        }
    }

    private void GetImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentSellerRegister2ImgMarket, ImagesFiles.get(0).getPath(), getActivity());

            }

        };

        openAlbum(4, getActivity(), ImagesFiles, action);
    }


    private void CheckVariables() {

        String Minmun = FragmentSellerRegister2EdMin.getEditText().getText().toString();
        String Delivery_charage = FragmentSellerRegister2EdDelivery.getEditText().getText().toString();
        String Phone = FragmentSellerRegister2EdPhone.getEditText().getText().toString();
        String WhatsApp = FragmentSellerRegister2EdWhatsapp.getEditText().getText().toString();

        if (TextUtils.isEmpty(Minmun)) {
            Toast.makeText(getActivity(), "الحد الادني مطلوب", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Delivery_charage)) {
            Toast.makeText(getActivity(), "ثمن التوصيل مطلوب", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(getActivity(), "  رقم الجوال مطلوب", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(WhatsApp)) {
            Toast.makeText(getActivity(), " رقم الواتس مطلوب", Toast.LENGTH_SHORT).show();
        } else {

            OnRegister(Minmun, Delivery_charage, Phone, WhatsApp);
        }


    }

    private void OnRegister(String minmun, String delivery_charage, String phone, String whatsApp) {

        Client client = new Client();
        String name = client.getName();
        String email = client.getEmail();
        String password = client.getPassword();
        String confirm_password = client.getConfirm_password();
        String phone1 = client.getPhone();
        String region_id = client.getRegion_id();
        String city_id = client.getCity_id();

        ApiServices.onRegister(convertToRequestBody(name), convertToRequestBody(email), convertToRequestBody(password),
                convertToRequestBody(confirm_password),convertToRequestBody(phone),convertToRequestBody(whatsApp),
                convertToRequestBody(region_id),convertToRequestBody(delivery_charage),
                convertToRequestBody(minmun),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo")
        );
    }
}
