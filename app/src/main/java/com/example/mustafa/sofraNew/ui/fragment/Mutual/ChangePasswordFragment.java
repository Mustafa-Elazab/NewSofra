package com.example.mustafa.sofraNew.ui.fragment.Mutual;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.ui.activity.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChangePasswordFragment extends Fragment {


    public String Type;
    @BindView(R.id.Fragment_change_password_ed_oldpassword)
    TextInputLayout FragmentChangePasswordEdOldpassword;
    @BindView(R.id.Fragment_change_password_ed_newpassword)
    TextInputLayout FragmentChangePasswordEdNewpassword;
    @BindView(R.id.Fragment_change_password_ed_newpassword_confirm)
    TextInputLayout FragmentChangePasswordEdNewpasswordConfirm;
    Unbinder unbinder;
    private API ApiServices;
    private String oldPassword,newPassword,confirmnewPassword;

    public ChangePasswordFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices=RetrofitClient.getClient().create(API.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_change_password_btn_edit)
    public void onViewClicked() {

        CheckVariable();
    }

    private void CheckVariable() {

         oldPassword=FragmentChangePasswordEdOldpassword.getEditText().getText().toString();
         newPassword=FragmentChangePasswordEdNewpassword.getEditText().getText().toString();
         confirmnewPassword=FragmentChangePasswordEdNewpasswordConfirm.getEditText().getText().toString();

        if (oldPassword.isEmpty()){
            Toast.makeText(getActivity(), "OldPassword Field is Requird.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (newPassword.isEmpty()){
            Toast.makeText(getActivity(), "NewPassword Field is Requird.", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (confirmnewPassword.isEmpty()){
            Toast.makeText(getActivity(), "NewPassword Field is Requird.", Toast.LENGTH_SHORT).show();
            return;
        }
        else {

            if (Type.equals("Client")){
                ClientChangePassword();
            }
            else {
                ResturantChangePassword();
            }
        }

    }

    private void ClientChangePassword() {
        String api_token=SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN);
        ApiServices.onClientChangePassword(oldPassword,newPassword,confirmnewPassword,api_token).enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                try{
                    if (response.body().getStatus()==1) {

                        Toast.makeText(getActivity(), "Password Change Sucessfully !!", Toast.LENGTH_SHORT).show();
                        FragmentChangePasswordEdNewpassword.getEditText().setText("");
                        FragmentChangePasswordEdNewpasswordConfirm.getEditText().setText("");
                        FragmentChangePasswordEdOldpassword.getEditText().setText("");
                        Intent intent=new Intent(getActivity(),LoginActivity.class);

                        getActivity().startActivity(intent);
                        getActivity().finish();
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {

            }
        });

    }

    private void ResturantChangePassword() {
        String api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        ApiServices.onRestaurantChangePassword(oldPassword,newPassword,confirmnewPassword,api_token).enqueue(new Callback<PublicResponse>() {
            @Override
            public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                try{
                    if (response.body().getStatus()==1) {

                        Toast.makeText(getActivity(), "Password Change Sucessfully !!", Toast.LENGTH_SHORT).show();
                        FragmentChangePasswordEdNewpassword.getEditText().setText("");
                        FragmentChangePasswordEdNewpasswordConfirm.getEditText().setText("");
                        FragmentChangePasswordEdOldpassword.getEditText().setText("");
                        Intent intent=new Intent(getActivity(),LoginActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PublicResponse> call, Throwable t) {

            }
        });

    }

}
