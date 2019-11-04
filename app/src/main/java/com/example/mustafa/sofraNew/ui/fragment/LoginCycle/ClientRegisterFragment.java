package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.client.clientData.Client;
import com.example.mustafa.sofraNew.data.models.general.generalResponse.GeneralResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClientRegisterFragment extends Fragment {


    @BindView(R.id.Fragment_client_register_img_profile)
    CircleImageView FragmentclientRegisterImgProfile;
    @BindView(R.id.Fragment_client_register_email)
    TextInputLayout FragmentclientRegisterEmail;
    @BindView(R.id.Fragment_client_register_phone)
    TextInputLayout FragmentclientRegisterPhone;
    @BindView(R.id.Fragment_client_register_sp_city)
    Spinner FragmentclientRegisterSpCity;
    @BindView(R.id.Fragment_client_register_sp_block)
    Spinner FragmentclientRegisterSpBlock;
    @BindView(R.id.Fragment_client_register_ed_password)
    TextInputLayout FragmentclientRegisterEdPassword;
    @BindView(R.id.Fragment_client_register_ed_password_confirm)
    TextInputLayout FragmentclientRegisterEdPasswordConfirm;
    @BindView(R.id.Fragment_client_register_ed_name)
    TextInputLayout FragmentclientRegisterEdName;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    Unbinder unbinder;
    private API ApiServices;
    private String City_name, Region_name;
    private Integer city_id, Region_id;
    private ArrayList<String> names,regions_names;
    private ArrayList<Integer> city_ids,regions_ids;
    private String client_name, client_email, client_phone, client_password, client_confirm;


    public ClientRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_register, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getCities();
        return view;
    }

    private void getCities() {

        ApiServices.getCities().enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        names = new ArrayList<>();
                        city_ids = new ArrayList<>();
                        names.add(getString(R.string.city));
                        city_ids.add(0);

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            names.add(response.body().getData().get(i).getName());
                            city_ids.add(response.body().getData().get(i).getId());

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, names);

                        FragmentclientRegisterSpCity.setAdapter(adapter);


                        FragmentclientRegisterSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    City_name = names.get(position);
                                    getRegoins(city_ids.get(position));
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

    private void getRegoins(Integer city_id) {

        ApiServices.getRegions(city_id).enqueue(new Callback<GeneralResponse>() {
            @Override
            public void onResponse(Call<GeneralResponse> call, Response<GeneralResponse> response) {

                try {

                    if (response.body().getStatus() == 1) {

                         regions_names = new ArrayList<>();
                         regions_ids = new ArrayList<>();

                        regions_names.add(getString(R.string.region));
                        regions_ids.add(0);

                        for (int i = 0; i < response.body().getData().size(); i++) {

                            regions_names.add(response.body().getData().get(i).getName());
                            regions_ids.add(response.body().getData().get(i).getId());
                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_spinner_item,regions_names);
                        FragmentclientRegisterSpBlock.setAdapter(arrayAdapter);


                        FragmentclientRegisterSpBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @OnClick({R.id.Fragment_client_register_img_profile, R.id.Fragment_client_register_btn_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_client_register_img_profile:
                GetImage();
                break;
            case R.id.Fragment_client_register_btn_continue:
                CheckVariables();
                break;
        }
    }

    private void CheckVariables() {

        client_name = FragmentclientRegisterEdName.getEditText().getText().toString();
        client_email = FragmentclientRegisterEmail.getEditText().getText().toString();
        client_phone = FragmentclientRegisterPhone.getEditText().getText().toString();
        client_password = FragmentclientRegisterEdPassword.getEditText().getText().toString();
        client_confirm = FragmentclientRegisterEdPasswordConfirm.getEditText().getText().toString();

        if (TextUtils.isEmpty(client_name)) {
            Toast.makeText(getActivity(), "name is Requird.", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(client_email)) {
            Toast.makeText(getActivity(), "Email is Requird.", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(client_phone)) {
            Toast.makeText(getActivity(), "Phone is Requird.", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(client_password)) {
            Toast.makeText(getActivity(), "Password is Requird.", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(client_confirm)) {
            Toast.makeText(getActivity(), "Password is Requird.", Toast.LENGTH_SHORT).show();
            return;
        } else if (!TextUtils.equals(client_password, client_confirm)) {
            Toast.makeText(getActivity(), "Password Dosen't Match", Toast.LENGTH_SHORT).show();
            return;
        } else {

            OnRegister();
        }
    }

    private void OnRegister() {


            city_id = city_ids.get(FragmentclientRegisterSpCity.getSelectedItemPosition());
            if (city_id == 0) {

                return;
            }

            Region_id = regions_ids.get(FragmentclientRegisterSpBlock.getSelectedItemPosition());
            if (Region_id == 0) {

                return;
            }

        ApiServices.onClientRegister(convertToRequestBody(client_name), convertToRequestBody(client_email)
                , convertToRequestBody(client_password), convertToRequestBody(client_confirm), convertToRequestBody(client_phone)
                , convertToRequestBody(City_name), convertToRequestBody(String.valueOf(Region_id)),
                convertFileToMultipart(ImagesFiles.get(0).getPath(), "profile_image")).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Account Created Sucessfully !", Toast.LENGTH_SHORT).show();
                        SharedPreferencesManger.SaveData(getActivity(), "USER_API_TOKEN", response.body().getData().getApiToken());
                        SharedPreferencesManger.SaveData(getActivity(), "USER_PASSWORD",client_password);
                        Intent intent=new Intent(getActivity(),ClientActivity.class);
                        getActivity().startActivity(intent);
                        getActivity().finish();

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

    private void GetImage() {
        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {
            @Override
            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentclientRegisterImgProfile, ImagesFiles.get(0).getPath(), getActivity());
            }
        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }
}
