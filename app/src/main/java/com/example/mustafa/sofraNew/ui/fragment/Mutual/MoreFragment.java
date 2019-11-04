package com.example.mustafa.sofraNew.ui.fragment.Mutual;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.Splash.SplashActivity;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.offer.ClientOfferFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.offer.RestaurantOffersFragment;
import com.google.firebase.iid.FirebaseInstanceId;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.LoadData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_TYPE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends Fragment {


    public String Type;
    @BindView(R.id.Fragment_Seller_more_tv_offers)
    TextView FragmentSellerMoreTvOffers;
    @BindView(R.id.Fragment_Seller_more_tv_contactus)
    TextView FragmentSellerMoreTvContactus;
    @BindView(R.id.Fragment_Seller_more_tv_aboutapp)
    TextView FragmentSellerMoreTvAboutapp;
    @BindView(R.id.Fragment_Seller_more_tv_comment)
    TextView FragmentSellerMoreTvComment;
    @BindView(R.id.Fragment_Seller_more_tv_exit)
    TextView FragmentSellerMoreTvExit;
    @BindView(R.id.view)
    View view;
    Unbinder unbinder;
    @BindView(R.id.text)
    TextView text;
    @BindView(R.id.Fragment_Seller_more_tv_change_password)
    TextView FragmentSellerMoreTvChangePassword;
    private String UserType;
    private String Resturant_api_token,Client_api_token;
    private API ApiServices;


    public MoreFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_more, container, false);
        unbinder = ButterKnife.bind(this, view);
        Resturant_api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        Client_api_token=SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN);
        ApiServices=RetrofitClient.getClient().create(API.class);
        UserType = LoadData(getActivity(), USER_TYPE);
        if (Type == "RestaurantsList") {
            RestaurantThings();
        } else if (Type == "Client") {
            ClientThings();
        }


        return view;
    }

    private void ClientThings() {

        FragmentSellerMoreTvOffers.setText("العروض ");
        FragmentSellerMoreTvOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClientOfferFragment ClientOffersFragment = new ClientOfferFragment();
                HelperMethods.replace(ClientOffersFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);

            }
        });

        FragmentSellerMoreTvContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact_UsFragment fragment = new Contact_UsFragment();
                HelperMethods.replace(fragment, getActivity().getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
            }
        });


        FragmentSellerMoreTvAboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About_AppFragment about_appFragment = new About_AppFragment();
                HelperMethods.replace(about_appFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
            }
        });

        view.setVisibility(View.GONE);
        FragmentSellerMoreTvComment.setVisibility(View.GONE);


        FragmentSellerMoreTvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                changePasswordFragment.Type="Client";
                HelperMethods.replace(changePasswordFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);
            }
        });

        FragmentSellerMoreTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exit( Client_api_token);

            }

        });
    }

    private void RestaurantThings() {


        FragmentSellerMoreTvOffers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantOffersFragment restaurantOffersFragment = new RestaurantOffersFragment();
                HelperMethods.replace(restaurantOffersFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);

            }
        });

        FragmentSellerMoreTvContactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Contact_UsFragment fragment = new Contact_UsFragment();
                HelperMethods.replace(fragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
            }
        });


        FragmentSellerMoreTvAboutapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                About_AppFragment about_appFragment = new About_AppFragment();
                HelperMethods.replace(about_appFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
            }
        });

        FragmentSellerMoreTvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        FragmentSellerMoreTvChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePasswordFragment changePasswordFragment=new ChangePasswordFragment();
                changePasswordFragment.Type="Restaurant";
                HelperMethods.replace(changePasswordFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Resturant_Frame_Home,null,null);
            }
        });

        FragmentSellerMoreTvExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Exit( Resturant_api_token);
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    public void Exit(final String Api_token) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setMessage(getResources().getString(R.string.do_you_want_to_exit));
        builder.setCancelable(false);
        builder.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferencesManger.clean(getActivity());

                String token = FirebaseInstanceId.getInstance().getToken();

                ApiServices.RemoveToken(token, Api_token).enqueue(
                        new Callback<PublicResponse>() {
                            @Override
                            public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                                try {

                                } catch (Exception e) {
                                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<PublicResponse> call, Throwable t) {

                            }
                        }
                );


            Intent splash = new Intent(getActivity(), SplashActivity.class);
                getActivity().startActivity(splash);

            }
        }).setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

}


