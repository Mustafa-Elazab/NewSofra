package com.example.mustafa.sofraNew.ui.fragment.restaurant.offer;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.offer.addNewOffer.AddNewOffer;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

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
    @BindView(R.id.fragment_Resturant_add_offer_ed_add_foodprice)
    TextInputLayout fragmentResturantAddOfferEdAddFoodprice;
    @BindView(R.id.Fragment_Resturant_add_offer_btn_add)
    Button FragmentResturantAddOfferBtnAdd;
    private int mYear, mMonth, mDay, mHour, mMinute;
    Unbinder unbinder;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private API ApiServices;
    private String dateto, datefrom;
    private Calendar mCalendar;

    public RestaurantAddOfferFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_resturant_add_offer, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getTime();
       // getClander();
        return view;
    }

    private void getClander() {

        mCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener dateFrom = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getFrom();
            }

        };

        fragmentResturantAddOfferTvFromDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateFrom, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        final DatePickerDialog.OnDateSetListener dateTo = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                mCalendar.set(Calendar.YEAR, year);
                mCalendar.set(Calendar.MONTH, monthOfYear);
                mCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                getTo();
            }

        };


        fragmentResturantAddOfferTvToDate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), dateTo, mCalendar
                        .get(Calendar.YEAR), mCalendar.get(Calendar.MONTH),
                        mCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }

    private void getTo() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fragmentResturantAddOfferTvToDate.setText(sdf.format(mCalendar.getTime()));
    }


    private void getFrom() {
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        fragmentResturantAddOfferTvFromDate.setText(sdf.format(mCalendar.getTime()));
    }



    private void getTime() {
        fragmentResturantAddOfferTvFromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.dateDialog(fragmentResturantAddOfferTvFromDate, getActivity());
                datefrom = fragmentResturantAddOfferTvFromDate.getText().toString();
                Log.i("TimeFrom",datefrom);
                Toast.makeText(getActivity(),datefrom, Toast.LENGTH_SHORT).show();
            }
        });

        fragmentResturantAddOfferTvToDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HelperMethods.dateDialog(fragmentResturantAddOfferTvToDate,getActivity());
                dateto = fragmentResturantAddOfferTvToDate.getText().toString();
                Log.i("TimeTo",dateto);
                Toast.makeText(getActivity(),dateto, Toast.LENGTH_SHORT).show();
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

        String offer_name = fragmentResturantAddOfferEdAddFoodname.getEditText().getText().toString();
        String offer_discription = fragmentResturantAddOfferEdAddFooddiscription.getEditText().getText().toString();
        String offer_price = fragmentResturantAddOfferEdAddFoodprice.getEditText().getText().toString();
        String api_token = SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN);

        if (offer_name.isEmpty()) {
            Toast.makeText(getActivity(), "Offer Name Is Requird..", Toast.LENGTH_SHORT).show();
        } else if (offer_discription.isEmpty()) {
            Toast.makeText(getActivity(), "Offer Discription is Requird..", Toast.LENGTH_SHORT).show();
        } else if (offer_price.isEmpty()) {
            Toast.makeText(getActivity(), "Offer Price is Requird..", Toast.LENGTH_SHORT).show();
        } else {

            ApiServices.onRestaurantNewOffer(convertToRequestBody(offer_discription), convertToRequestBody(offer_price),
                    convertToRequestBody(datefrom), convertToRequestBody(offer_name), convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"),
                    convertToRequestBody(dateto)
                    , convertToRequestBody(api_token)).enqueue(new Callback<AddNewOffer>() {
                @Override
                public void onResponse(Call<AddNewOffer> call, Response<AddNewOffer> response) {
                    try {
                        if (response.body().getStatus() == 1) {
                            Toast.makeText(getActivity(), "Offer is Uploaded", Toast.LENGTH_SHORT).show();
                            RestaurantOffersFragment RestaurantOffersFragment = new RestaurantOffersFragment();
                            HelperMethods.replace(RestaurantOffersFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
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
                onLoadImageFromUrl(fragmentResturantAddOfferImgAdd, ImagesFiles.get(0).getPath(), getActivity());
            }
        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }
}
