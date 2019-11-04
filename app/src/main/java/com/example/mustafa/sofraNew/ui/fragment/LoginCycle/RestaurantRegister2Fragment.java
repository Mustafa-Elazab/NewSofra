package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.RestaurantData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_EMAIL;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_NAME;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_PASSWORD;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_PHOTO_URL;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_REGION_ID;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantRegister2Fragment extends Fragment {

    public HashMap<String, String> resturantFields;
    @BindView(R.id.Fragment_seller_register2_ed_phone)
    TextInputLayout FragmentSellerRegister2EdPhone;
    @BindView(R.id.Fragment_seller_register2_ed_whatsapp)
    TextInputLayout FragmentSellerRegister2EdWhatsapp;
    @BindView(R.id.Fragment_seller_register2_btn_sign)
    Button FragmentSellerRegister2BtnSign;
    @BindView(R.id.Fragment_Resturant_register2_img_restaurantimage)
    CircleImageView FragmentResturantRegisterImgRestaurantimage;
    Unbinder unbinder;
    private API ApiServices;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();

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

    @OnClick({R.id.Fragment_seller_register2_btn_sign,R.id.Fragment_Resturant_register2_img_restaurantimage})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_seller_register2_btn_sign:
                CheckVariables();
                break;
            case R.id.Fragment_Resturant_register2_img_restaurantimage:
                OpenImageGallary();
                break;
        }
    }




    private void CheckVariables() {
        String Phone = FragmentSellerRegister2EdPhone.getEditText().getText().toString();
        String WhatsApp = FragmentSellerRegister2EdWhatsapp.getEditText().getText().toString();

          if (TextUtils.isEmpty(Phone)) {
            Toast.makeText(getActivity(), "  رقم الجوال مطلوب", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(WhatsApp)) {
            Toast.makeText(getActivity(), " رقم الواتس مطلوب", Toast.LENGTH_SHORT).show();
              return;
        } else {
            OnRegister(Phone, WhatsApp);
        }


    }

    private void OnRegister( String phone, String whatsApp) {

        String Resturant_name = resturantFields.get("RESTURANT_NAME");
        String Resturant_email=resturantFields.get("RESTURANT_EMAIL");
        String Resturant_Region_id=resturantFields.get("RESTURANT_REGION_ID");
        String Resturant_password=resturantFields.get("RESTURANT_PASSWORD");
        String Resturant_delivery_charage=resturantFields.get("RESTURANT_DELIVERY_CHARAGE");
        String Resturant_minmun=resturantFields.get("RESTURANT_MINMUN");
        String Resturant_delivery_time=resturantFields.get("RESTURANT_DELIVERY_TIME");

        ApiServices.onRegister(convertToRequestBody(Resturant_name),convertToRequestBody(Resturant_email),
                convertToRequestBody(Resturant_password),convertToRequestBody(Resturant_password),
                convertToRequestBody(phone),convertToRequestBody(whatsApp),convertToRequestBody(Resturant_Region_id),
                convertToRequestBody(Resturant_delivery_charage),convertToRequestBody(Resturant_delivery_time),
                convertToRequestBody(Resturant_minmun),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo")).
                enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
               Log.i("onResponse",response.body().getMsg());
                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                try {
                    if (response.body().getStatus()==1) {
                        Intent Resturant=new Intent(getActivity(),ResturantActivity.class);
                        getActivity().startActivity(Resturant);
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

            }
        });
    }

    private void OpenImageGallary() {
        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentResturantRegisterImgRestaurantimage, ImagesFiles.get(0).getPath(), getActivity());
            }

        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }

}
