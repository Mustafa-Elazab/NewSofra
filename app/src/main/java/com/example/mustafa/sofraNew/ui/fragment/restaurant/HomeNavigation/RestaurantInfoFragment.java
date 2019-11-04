package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation;


import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.generalResponse.GeneralResponse;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.MultiSelectionSpinner;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_PASSWORD;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.disappearKeypad;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {
    private String Availability = "";
    @BindView(R.id.Fragment_seller_edit_ed_restaurantname)
    TextInputLayout FragmentRestaurantEditEdRestaurantName;
    @BindView(R.id.Fragment_seller_edit_email)
    TextInputLayout FragmentRestaurantEditEdEmail;
    @BindView(R.id.Fragment_seller_edit_sp_city)
    Spinner FragmentRestaurantEditSpCity;
    @BindView(R.id.Fragment_seller_edit_sp_block)
    Spinner FragmentRestaurantEditSpBlock;
    @BindView(R.id.Fragment_seller_edit_ed_min)
    TextInputLayout FragmentRestaurantEditEdMin;
    @BindView(R.id.Fragment_seller_edit_ed_delivery)
    TextInputLayout FragmentRestaurantEditEdDelivery;
    @BindView(R.id.switch_status)
    Switch switchStatus;
    @BindView(R.id.Fragment_seller_edit_ed_phone)
    TextInputLayout FragmentRestaurantEditEdPhone;
    @BindView(R.id.Fragment_seller_edit_ed_whatsapp)
    TextInputLayout FragmentRestaurantEditEdWhatsapp;
    @BindView(R.id.Fragment_seller_edit_img_market)
    CircleImageView FragmentRestaurantEditImgMarket;
    @BindView(R.id.Fragment_seller_edit_tv_delivery_detail)
    TextView FragmentRestaurantEditTvDeliveryTvDetail;
    @BindView(R.id.Fragment_seller_edit_btn_sign)
    Button FragmentRestaurantEditBtnSign;
    @BindView(R.id.bhghf)
    RelativeLayout bhghf;
    Unbinder unbinder;
    private API ApiServices;
    private String restaurant_api_token = "";
    private String City_name, Region_name;
    private Integer Region_id, City_id;
    private ArrayList<String> names, regions_names;
    private ArrayList<Integer> city_ids, regions_ids;
    private String restaurant_name, restaurant_email, restaurant_phone, restaurant_password, restaurant_confirm;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        restaurant_api_token = SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN);
        getRestaurantData();
        switchStatus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabled
                    Availability = "open";

                } else {
                    // The toggle is disabled
                    Availability = "close";
                }
            }
        });

        return view;
    }




    private void getRestaurantData() {

        ApiServices.onResturantProfile(SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN)).enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        FragmentRestaurantEditEdRestaurantName.setHint(response.body().getData().getData().getName());
                        FragmentRestaurantEditEdEmail.setHint(response.body().getData().getData().getEmail());
                        FragmentRestaurantEditEdPhone.setHint(response.body().getData().getData().getPhone());
                        FragmentRestaurantEditEdDelivery.setHint(response.body().getData().getData().getDeliveryCost());
                        FragmentRestaurantEditEdMin.setHint(response.body().getData().getData().getMinimumCharger());
                        FragmentRestaurantEditEdWhatsapp.setHint(response.body().getData().getData().getWhatsapp());
                        getCity(response.body().getData().getData().getRegion().getCity().getId(),
                                response.body().getData().getData().getRegion().getId());
                        Glide.with(getActivity()).load(response.body().getData().getData().getPhotoUrl()).into(FragmentRestaurantEditImgMarket);
                        if (response.body().getData().getData().getAvailability().equals("open")) {
                            switchStatus.setChecked(true);
                        } else {
                            switchStatus.setChecked(false);
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {

                Log.i("kjh", "onFailure: ");
            }
        });
    }

    private void getCity(final Integer id, final Integer integer) {

        ApiServices.getCities().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                try {

                    if (response.body().getStatus() == 1) {
                        names = new ArrayList<>();
                        city_ids = new ArrayList<>();
                        names.add(getString(R.string.city));
                        city_ids.add(0);
                        int pos = 0;
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            names.add(response.body().getData().get(i).getName());
                            city_ids.add(response.body().getData().get(i).getId());
                            if (response.body().getData().get(i).getId().equals(id)) {
                                pos = 1 + i;
                            }

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, names);

                        FragmentRestaurantEditSpCity.setAdapter(adapter);
                        FragmentRestaurantEditSpCity.setSelection(pos);

                        FragmentRestaurantEditSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    City_name = names.get(position);
                                    getRegoins(city_ids.get(position), integer);
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {
                            }
                        });
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }

    private void getRegoins(int city_id, final Integer integer) {

        ApiServices.getRegions(city_id).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        regions_names = new ArrayList<>();
                        regions_ids = new ArrayList<>();

                        regions_names.add(getString(R.string.region));
                        regions_ids.add(0);
                        int pos = 0;


                        for (int i = 0; i < response.body().getData().size(); i++) {

                            regions_names.add(response.body().getData().get(i).getName());
                            regions_ids.add(response.body().getData().get(i).getId());
                            if (response.body().getData().get(i).getId().equals(integer)) {
                                pos = 1 + i;
                            }
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, regions_names);
                        FragmentRestaurantEditSpBlock.setAdapter(arrayAdapter);
                        FragmentRestaurantEditSpBlock.setSelection(pos);

                        FragmentRestaurantEditSpBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    Region_name = regions_names.get(position);
                                    Region_id = regions_ids.get(position);

                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GeneralResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_seller_edit_btn_sign, R.id.bhghf, R.id.Fragment_seller_edit_img_market})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_seller_edit_btn_sign:
                Edit();
                break;
            case R.id.bhghf:
                disappearKeypad(getActivity(), getView());
                break;
            case R.id.Fragment_seller_edit_img_market:
                MarketImage();
                break;
        }
    }

    private void Edit() {

//        ApiServices.onResturantEditProfile(convertToRequestBody(restaurant_email),
//                convertToRequestBody(restaurant_name),convertToRequestBody(restaurant_phone),convertToRequestBody(),
//                convertToRequestBody(String.valueOf(Region_id)),,convertToRequestBody());
    }

    private void MarketImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentRestaurantEditImgMarket, ImagesFiles.get(0).getPath(), getActivity());

            }

        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }

}



