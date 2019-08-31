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

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantnewoffer.RestaurantNewOffer;
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

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantAddOfferFragment extends Fragment {


    @BindView(R.id.fragment_Resturant_add_offer_tv_add)
    TextView fragmentResturantAddOfferTvAdd;
    @BindView(R.id.fragment_Resturant_add_offer_img_add)
    ImageView fragmentResturantAddOfferImgAdd;
    @BindView(R.id.fragment_Resturant_add_offer_ed_add_foodname)
    TextInputLayout fragmentResturantAddOfferEdAddFoodname;
    @BindView(R.id.fragment_Resturant_add_offer_ed_add_fooddiscription)
    TextInputLayout fragmentResturantAddOfferEdAddFooddiscription;
    @BindView(R.id.fragment_Resturant_add_offer_tv_from_date)
    TextView fragmentResturantAddOfferTvFromDate;
    @BindView(R.id.fragment_Resturant_add_offer_tv_to_date)
    TextView fragmentResturantAddOfferTvToDate;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Unbinder unbinder;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private API ApiServices;
    private String dateto,datefrom;

    public RestaurantAddOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resturant_add_offer, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices=RetrofitClient.getClient().create(API.class);
        getTime();

        return view;
    }

    private void getTime() {
        fragmentResturantAddOfferTvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HelperMethods.dateDialog(fragmentResturantAddOfferTvFromDate, getActivity());
                datefrom = fragmentResturantAddOfferTvToDate.getText().toString();
                Toast.makeText(getActivity(), datefrom, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentResturantAddOfferTvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.dateDialog(fragmentResturantAddOfferTvToDate, getActivity());
                dateto=fragmentResturantAddOfferTvToDate.getText().toString();
                Toast.makeText(getActivity(), dateto, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.fragment_Resturant_add_offer_img_add, R.id.Fragment_Resturant_add_offer_btn_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_Resturant_add_offer_img_add:
                getGallayImage();
                break;
            case R.id.Fragment_Resturant_add_offer_btn_add:
                AddOffer();
                break;
        }
    }

    private void AddOffer() {

        String offer_name=fragmentResturantAddOfferEdAddFoodname.getEditText().getText().toString();
        String offer_discription=fragmentResturantAddOfferEdAddFooddiscription.getEditText().getText().toString();
        String api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);

        if(offer_name.isEmpty()){
            Toast.makeText(getActivity(), "Offer Name Is Requird..", Toast.LENGTH_SHORT).show();
        }
        else if(offer_discription.isEmpty()){
            Toast.makeText(getActivity(), "Offer Discription is Requird..", Toast.LENGTH_SHORT).show();
        }
        else {

            ApiServices.onRestaurantNewOffer(convertToRequestBody(offer_discription),convertToRequestBody(String.valueOf(100)),
                    convertToRequestBody(datefrom),convertToRequestBody(offer_name),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"),
                    convertToRequestBody(dateto),convertToRequestBody(api_token),
                    convertToRequestBody(String.valueOf(99))).enqueue(new Callback<RestaurantNewOffer>() {
                @Override
                public void onResponse(Call<RestaurantNewOffer> call, Response<RestaurantNewOffer> response) {

                    try {


                        if (response.body().getStatus() == 1) {


                            Toast.makeText(getActivity(), "Offer is Uploaded", Toast.LENGTH_SHORT).show();
                            RestaurantOffersFragment RestaurantOffersFragment = new RestaurantOffersFragment();
                            HelperMethods.replace(RestaurantOffersFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
                            ;
                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestaurantNewOffer> call, Throwable t) {

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
                onLoadImageFromUrl(fragmentResturantAddOfferImgAdd, ImagesFiles.get(0).getPath(), getActivity());

            }

        };

        openAlbum(3, getActivity(), ImagesFiles, action);
    }

}
