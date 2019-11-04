package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


import android.os.Bundle;


import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.resetPassword.ResetPassword;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.LoadData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTAURANT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_TYPE;


/**
 * A simple {@link Fragment} subclass.
 */
public class ForgetPassword1Fragment extends Fragment {


    @BindView(R.id.Fragment_forget1_ed_phone)
    TextInputLayout FragmentForget1EdPhone;
    Unbinder unbinder;
    private API ApiServices;
    private String UserType;
    private String email;

    public ForgetPassword1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forget_password1, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        UserType = LoadData(getActivity(), USER_TYPE);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_forget1_btn_next)
    public void onViewClicked() {
        CheckEmail();
    }

    private void CheckEmail() {
        email = FragmentForget1EdPhone.getEditText().getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email Requird..", Toast.LENGTH_SHORT).show();
        } else {

            if (UserType.equals(RESTAURANT)) {
                RestaurantResetPassword();
            } else {

                ClientResetPassword();
            }

        }
    }

    private void ClientResetPassword() {
        ApiServices.onResetPasswordClient(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 1) {
                        ForgetPassword2Fragment forgetPassword2Fragment = new ForgetPassword2Fragment();
                        Integer data = response.body().getData().getCode();
                        SharedPreferencesManger.SaveData(getActivity(), "PIN_CODE_USER", String.valueOf(data));
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                        HelperMethods.replace(forgetPassword2Fragment, getActivity().getSupportFragmentManager(), R.id.login_cycle, null, null);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }

    private void RestaurantResetPassword() {
        ApiServices.onResetPasswordResturant(email).enqueue(new Callback<ResetPassword>() {
            @Override
            public void onResponse(Call<ResetPassword> call, Response<ResetPassword> response) {
                try {
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus() == 1) {
                        String s = response.body().getData().toString();
                        ForgetPassword2Fragment forgetPassword2Fragment = new ForgetPassword2Fragment();
                        Integer data = response.body().getData().getCode();
                        SharedPreferencesManger.SaveData(getActivity(), "PIN_CODE_RESTURANT", String.valueOf(data));
                        Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
                        HelperMethods.replace(forgetPassword2Fragment, getActivity().getSupportFragmentManager(), R.id.login_cycle, null, null);
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<ResetPassword> call, Throwable t) {
                Log.i(TAG, "onFailure: ");
            }
        });
    }
}
