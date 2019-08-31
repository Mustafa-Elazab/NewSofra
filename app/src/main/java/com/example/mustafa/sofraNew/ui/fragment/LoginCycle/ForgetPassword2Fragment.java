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
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantnewpassword.RestaurantNewPassword;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.PIN_CODE_RESTURANT;


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
    Unbinder unbinder;
    @BindView(R.id.relative)
    RelativeLayout relative;
    private API ApiServices;
    private String PinCode;

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


        PinCode = SharedPreferencesManger.LoadData(getActivity(), PIN_CODE_RESTURANT);
        FragmentForget1EdCode.getEditText().setText(PinCode);
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


        String password = FragmentForget1EdPassword.getEditText().getText().toString();
        String confirm_password = FragmentForget1EdConfirmPassword.getEditText().getText().toString();


        if (password.isEmpty()) {
            Toast.makeText(getActivity(), "Password Requird..", Toast.LENGTH_SHORT).show();
        } else if (confirm_password.isEmpty()) {
            Toast.makeText(getActivity(), "Password Requird..", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(password, confirm_password)) {
            Toast.makeText(getActivity(), "Password don't Match..", Toast.LENGTH_SHORT).show();
        } else {

            ApiServices.onNewPasswordResturant(String.valueOf(PinCode), password, confirm_password).enqueue(new Callback<RestaurantNewPassword>() {
                @Override
                public void onResponse(Call<RestaurantNewPassword> call, Response<RestaurantNewPassword> response) {

                    try {

                        if (response.body().getStatus() == 1) {


                            Toast.makeText(getActivity(), "Change Password Succesfully", Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(getActivity(), ResturantActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }

                    } catch (Exception e) {
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<RestaurantNewPassword> call, Throwable t) {

                }
            });

        }


    }


}
