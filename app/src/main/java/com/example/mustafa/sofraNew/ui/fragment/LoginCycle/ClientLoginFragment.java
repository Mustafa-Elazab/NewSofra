package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.client.clientData.Client;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;
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
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTAURANT;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_TYPE;
import static com.example.mustafa.sofraNew.helper.HelperMethods.disappearKeypad;


/**
 * A simple {@link Fragment} subclass.
 */
public class ClientLoginFragment extends Fragment {

    public String Type, UserType;
    @BindView(R.id.Fragment_login_ed_email)
    TextInputLayout FragmentLoginEdEmail;
    @BindView(R.id.Fragment_login_ed_password)
    TextInputLayout FragmentLoginEdPassword;
    @BindView(R.id.Fragment_login_tv_forget_password)
    TextView FragmentLoginTvForgetPassword;
    @BindView(R.id.relative_write)
    RelativeLayout relativeWrite;
    private API ApiServices;
    Unbinder unbinder;

    public ClientLoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_login, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_login_tv_forget_password, R.id.Fragment_login_btn_enter, R.id.Fragment_login_tv_create_account, R.id.relative_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_login_tv_forget_password:
                ForgetPassword1Fragment forgetPassword1Fragment = new ForgetPassword1Fragment();
                HelperMethods.replace(forgetPassword1Fragment, getActivity().getSupportFragmentManager(), R.id.login_cycle, null, null);
                break;
            case R.id.Fragment_login_btn_enter:
                GetSignIn();
                break;
            case R.id.relative_write:
                disappearKeypad(getActivity(), getView());
                break;
            case R.id.Fragment_login_tv_create_account:
                CreateClientAccount();
                break;
        }
    }

    private void CreateClientAccount() {
        ClientRegisterFragment clientRegisterFragment = new ClientRegisterFragment();
        HelperMethods.replace(clientRegisterFragment, getActivity().getSupportFragmentManager(), R.id.login_cycle, null, null);
    }


    private void GetSignIn() {

        String email = FragmentLoginEdEmail.getEditText().getText().toString();
        String password = FragmentLoginEdPassword.getEditText().getText().toString();

        if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email is Requird..", Toast.LENGTH_SHORT).show();
        } else if (password.isEmpty()) {
            Toast.makeText(getActivity(), "Password is Requird..", Toast.LENGTH_SHORT).show();
        } else {
            OnClientLogin(email, password);
        }
    }

    private void OnClientLogin(final String email, final String password) {

        ApiServices.onClientLogin(email, password).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        SharedPreferencesManger.SaveData(getActivity(), "USER_API_TOKEN", response.body().getData().getApiToken());
                        Toast.makeText(getActivity(), response.body().getData().getApiToken(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(getActivity(), "Done Sign As ClientData", Toast.LENGTH_SHORT).show();
                        SharedPreferencesManger.SaveData(getActivity(), "USER_NAME", response.body().getData().getUser().getName());
                        SharedPreferencesManger.SaveData(getActivity(), "USER_PASSWORD", password);
                        Intent intent = new Intent(getActivity(), ClientActivity.class);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

            }
        });
    }
}
