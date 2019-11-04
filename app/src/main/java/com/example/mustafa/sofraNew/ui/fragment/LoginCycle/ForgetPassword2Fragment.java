package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.LoadData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.PIN_CODE_RESTURANT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.PIN_CODE_USER;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTAURANT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_TYPE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassword2Fragment extends Fragment {


    @BindView(R.id.Fragment_forget1_ed_phone)
    TextInputLayout FragmentForget1EdCode;
    @BindView(R.id.Fragment_forget1_ed_password)
    TextInputLayout FragmentForget1EdPassword;
    @BindView(R.id.Fragment_forget1_ed_confirm_password)
    TextInputLayout FragmentForget1EdConfirmPassword;
    @BindView(R.id.relative)
    RelativeLayout relative;
    Unbinder unbinder;
    private API ApiServices;
    private String PinCode;
    private String UserType;
    private String pin_code,password, confirm_password;

    public ForgetPassword2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password2, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        UserType = LoadData(getActivity(), USER_TYPE);
//        PinCode = SharedPreferencesManger.LoadData(getActivity(), PIN_CODE_RESTURANT);
//        FragmentForget1EdCode.getEditText().setText(PinCode);
//        PinCode = SharedPreferencesManger.LoadData(getActivity(), PIN_CODE_USER);
//        FragmentForget1EdCode.getEditText().setText(PinCode);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_forget1_btn_next, R.id.relative})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_forget1_btn_next:
                NewPassword();
                break;
            case R.id.relative:
                HelperMethods.disappearKeypad(getActivity(), getView());
                break;
        }
    }

    private void NewPassword() {
        pin_code = FragmentForget1EdCode.getEditText().getText().toString();
        password = FragmentForget1EdPassword.getEditText().getText().toString();
        confirm_password = FragmentForget1EdConfirmPassword.getEditText().getText().toString();

        if (password.isEmpty()) {
            Toast.makeText(getActivity(), "Password Requird..", Toast.LENGTH_SHORT).show();
        } else if (confirm_password.isEmpty()) {
            Toast.makeText(getActivity(), "Password Requird..", Toast.LENGTH_SHORT).show();
        } else if (pin_code.isEmpty()) {
            Toast.makeText(getActivity(), "Password Requird..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(password, confirm_password)) {
            Toast.makeText(getActivity(), "Password don't Match..", Toast.LENGTH_SHORT).show();
        } else {
            if (UserType.equals(RESTAURANT)) {
                RestaurantNewPassword();
            } else {
                ClientNewPassword();
            }
        }
    }

    private void ClientNewPassword() {
        ApiServices.onNewPasswordClient(pin_code, password, confirm_password).enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Change Password Succesfully", Toast.LENGTH_SHORT).show();
                        Intent clientActivity = new Intent(getActivity(),ClientActivity.class);
                        startActivity(clientActivity);
                        getActivity().finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {

            }
        });

    }

    private void RestaurantNewPassword() {
        ApiServices.onNewPasswordResturant(String.valueOf(PinCode), password, confirm_password).enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Change Password Succesfully", Toast.LENGTH_SHORT).show();
                        Intent resturantActivity = new Intent(getActivity(), ResturantActivity.class);
                        startActivity(resturantActivity);
                        getActivity().finish();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {

            }
        });

    }


}
