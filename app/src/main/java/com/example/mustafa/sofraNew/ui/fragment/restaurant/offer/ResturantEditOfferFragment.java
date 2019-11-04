package com.example.mustafa.sofraNew.ui.fragment.restaurant.offer;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.offer.addNewOffer.AddNewOffer;
import com.example.mustafa.sofraNew.data.models.offer.offers.OffersData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantEditOfferFragment extends Fragment {


    public OffersData resturantofferdata;
    @BindView(R.id.fragment_Resturant_Edit_offer_img_Edit)
    ImageView fragmentResturantEditOfferImgEdit;
    @BindView(R.id.fragment_Resturant_Edit_offer_ed_Edit_foodname)
    TextInputLayout fragmentResturantEditOfferEdEditFoodname;
    @BindView(R.id.fragment_Resturant_Edit_offer_ed_Edit_fooddiscription)
    TextInputLayout fragmentResturantEditOfferEdEditFooddiscription;
    @BindView(R.id.fragment_Resturant_Edit_offer_tv_from_date)
    TextView fragmentResturantEditOfferTvFromDate;
    @BindView(R.id.fragment_Resturant_Edit_offer_tv_to_date)
    TextView fragmentResturantEditOfferTvToDate;
    Unbinder unbinder;
    private API ApiServices;
    private String datefrom, dateto;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();

    public ResturantEditOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resturant_edit_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getEditData();

        fragmentResturantEditOfferTvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HelperMethods.dateDialog(fragmentResturantEditOfferTvFromDate, getActivity());
                datefrom = fragmentResturantEditOfferTvFromDate.getText().toString();
                if (datefrom.isEmpty()) {
                    datefrom = resturantofferdata.getCreatedAt();
                }
            }
        });

        fragmentResturantEditOfferTvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.dateDialog(fragmentResturantEditOfferTvToDate, getActivity());
                dateto = fragmentResturantEditOfferTvToDate.getText().toString();
                if (dateto.isEmpty()) {
                    dateto = resturantofferdata.getEndingAt();
                }
            }
        });

        return view;
    }

    private void getEditData() {

        Glide.with(getActivity()).load(resturantofferdata.getPhotoUrl()).into(fragmentResturantEditOfferImgEdit);
        fragmentResturantEditOfferEdEditFoodname.setHint(resturantofferdata.getName());
        fragmentResturantEditOfferEdEditFooddiscription.setHint(resturantofferdata.getDescription());
        fragmentResturantEditOfferTvFromDate.setText(resturantofferdata.getCreatedAt());
        fragmentResturantEditOfferTvToDate.setText(resturantofferdata.getEndingAt());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void EditOffer() {
        String offer_name = fragmentResturantEditOfferEdEditFoodname.getEditText().getText().toString();
        String offer_discription = fragmentResturantEditOfferEdEditFooddiscription.getEditText().getText().toString();
        String api_token = SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN);

        if (offer_name.isEmpty()) {
            offer_name = fragmentResturantEditOfferEdEditFoodname.getEditText().getHint().toString();
        } else if (offer_discription.isEmpty()) {
            offer_discription = fragmentResturantEditOfferEdEditFooddiscription.getEditText().getHint().toString();
        } else {
            ApiServices.onRestaurantEditOffer(convertToRequestBody(offer_discription), convertToRequestBody(String.valueOf(100)),
                    convertToRequestBody(datefrom), convertToRequestBody(offer_name), convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"),
                    convertToRequestBody(dateto), convertToRequestBody(api_token),
                    convertToRequestBody(String.valueOf(99))).enqueue(new Callback<AddNewOffer>() {
                @Override
                public void onResponse(Call<AddNewOffer> call, Response<AddNewOffer> response) {

                    String message = response.message();
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                    try {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "Offer is Edited", Toast.LENGTH_SHORT).show();
                            RestaurantOffersFragment restaurantOffersFragment = new RestaurantOffersFragment();
                            HelperMethods.replace(restaurantOffersFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AddNewOffer> call, Throwable t) {
                    }
            });
        }
    }
    private void getGallayImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(fragmentResturantEditOfferImgEdit, ImagesFiles.get(0).getPath(), getActivity());

            }

        };

        openAlbum(1, getActivity(), ImagesFiles, action);
    }

    @OnClick({R.id.fragment_Resturant_Edit_offer_img_Edit, R.id.Fragment_Resturant_Edit_offer_btn_Edit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_Resturant_Edit_offer_img_Edit:
                getGallayImage();
                break;
            case R.id.Fragment_Resturant_Edit_offer_btn_Edit:
                EditOffer();
                break;
        }
    }
}
