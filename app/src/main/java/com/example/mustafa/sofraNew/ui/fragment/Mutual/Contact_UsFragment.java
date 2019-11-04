package com.example.mustafa.sofraNew.ui.fragment.Mutual;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.general.contactUs.ContactUs;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.HomeFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class Contact_UsFragment extends Fragment {


    @BindView(R.id.fragment_contactus_name)
    TextInputLayout fragmentContactusName;
    @BindView(R.id.fragment_contactus_email)
    TextInputLayout fragmentContactusEmail;
    @BindView(R.id.fragment_contactus_phone)
    TextInputLayout fragmentContactusPhone;
    @BindView(R.id.fragment_contactus_message)
    TextInputLayout fragmentContactusMessage;
    @BindView(R.id.radio_complaint)
    RadioButton radioComplaint;
    @BindView(R.id.radio_suggestion)
    RadioButton radioSuggestion;
    @BindView(R.id.radio_inquiry)
    RadioButton radioInquiry;
    @BindView(R.id.relative_write)
    RelativeLayout relativeWrite;
    Unbinder unbinder;
    @BindView(R.id.Radio_group)
    android.widget.RadioGroup RadioGroup;
    private API ApiServices;
    private String Type;

    public Contact_UsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contact_us, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(API.class);

        RadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(android.widget.RadioGroup group, int checkedId) {


                    switch(checkedId){
                        case R.id.radio_complaint:
                            // do operations specific to this selection
                             Type="complaint";
                            break;
                        case R.id.radio_suggestion:
                            // do operations specific to this selection
                             Type="suggestion";
                            break;
                        case R.id.radio_inquiry:
                            // do operations specific to this selection
                            Type="inquiry";
                            break;
                    }
                }
            });


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_contactus_btn_send, R.id.relative_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_contactus_btn_send:

                onContactSend();


                break;
            case R.id.relative_write:

                HelperMethods.disappearKeypad(getActivity(), getView());
                break;
        }
    }

    private void onContactSend() {

        String name = fragmentContactusName.getEditText().getText().toString();
        String email = fragmentContactusEmail.getEditText().getText().toString();
        String phone = fragmentContactusPhone.getEditText().getText().toString();
        String content = fragmentContactusMessage.getEditText().getText().toString();

        if (name.isEmpty()) {
            Toast.makeText(getActivity(), "Name is Requird..", Toast.LENGTH_SHORT).show();
        } else if (email.isEmpty()) {
            Toast.makeText(getActivity(), "Email is Requird..", Toast.LENGTH_SHORT).show();
        } else if (phone.isEmpty()) {
            Toast.makeText(getActivity(), "Phone is Requird..", Toast.LENGTH_SHORT).show();
        } else if (content.isEmpty()) {
            Toast.makeText(getActivity(), "Content is Requird..", Toast.LENGTH_SHORT).show();
        } else {


            ApiServices.onContact(name,email,phone,Type,content).enqueue(new Callback<ContactUs>() {
                @Override
                public void onResponse(Call<ContactUs> call, Response<ContactUs> response) {

                    try {
                        if (response.body().getStatus()==1) {

                            Toast.makeText(getActivity(), "Message Has been Sent", Toast.LENGTH_SHORT).show();
                            HomeFragment homeFragment=new HomeFragment();
                            HelperMethods.replace(homeFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);
                        }

                    }catch (Exception e){
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ContactUs> call, Throwable t) {

                }
            });
        }
    }
}
