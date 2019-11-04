package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.client.clientData.Client;
import com.example.mustafa.sofraNew.data.models.general.generalResponse.GeneralResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
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

import static android.support.constraint.Constraints.TAG;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_PASSWORD;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class YourInfoFragment extends Fragment {
    @BindView(R.id.Fragment_client_edit_img_profile)
    CircleImageView FragmentClientEditImgProfile;
    @BindView(R.id.Fragment_client_edit_ed_name)
    TextInputLayout FragmentClientEditEdName;
    @BindView(R.id.Fragment_client_edit_email)
    TextInputLayout FragmentClientEditEdEmail;
    @BindView(R.id.Fragment_client_edit_phone)
    TextInputLayout FragmentClientEditEdPhone;
    @BindView(R.id.Fragment_client_edit_sp_city)
    Spinner FragmentClientEditSpCity;
    @BindView(R.id.Fragment_client_edit_sp_block)
    Spinner FragmentClientEditSpBlock;
    @BindView(R.id.Fragment_client_edit_btn_continue)
    Button FragmentClientEditBtnContinue;
    @BindView(R.id.relative_write)
    RelativeLayout relativeWrite;
    Unbinder unbinder;
    private API ApiServices;
    private String City_name, Region_name;
    private Integer Region_id, City_id;
    private ArrayList<String> names, regions_names;
    private ArrayList<Integer> city_ids, regions_ids;
    private String client_name, client_email, client_phone, client_password, client_confirm;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private String api_token = "";

    public YourInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        api_token = SharedPreferencesManger.LoadData(getActivity(), USER_API_TOKEN);
        getClientData();
        return view;
    }

    private void getClientData() {
        ApiServices.onClientProfile(SharedPreferencesManger.LoadData(getActivity(), USER_API_TOKEN)).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                try {
                    if (response.body().getStatus() == 1) {
                        FragmentClientEditEdName.setHint(response.body().getData().getUser().getName());
                        FragmentClientEditEdEmail.setHint(response.body().getData().getUser().getEmail());
                        FragmentClientEditEdPhone.setHint(response.body().getData().getUser().getPhone());
                        Glide.with(getActivity()).load(response.body().getData().getUser().getPhotoUrl()).into(FragmentClientEditImgProfile);
//                        SharedPreferencesManger.SaveData(getActivity(), "USER_ID", response.body().getData().getUser().getId());
                        getCity(response.body().getData().getUser().getRegion().getCity().getId(),
                                response.body().getData().getUser().getRegion().getId());
                    }
                } catch (Exception e) {
                    Log.i(TAG, "onResponse: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {

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
                        FragmentClientEditSpCity.setAdapter(adapter);
                        FragmentClientEditSpCity.setSelection(pos);
                        FragmentClientEditSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                        FragmentClientEditSpBlock.setAdapter(arrayAdapter);
                        FragmentClientEditSpBlock.setSelection(pos);
                        FragmentClientEditSpBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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

    @OnClick({R.id.Fragment_client_edit_img_profile, R.id.Fragment_client_edit_btn_continue, R.id.relative_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_client_edit_img_profile:
                OpenGallary();
                break;
            case R.id.Fragment_client_edit_btn_continue:
                UpdateProfile();
                break;
            case R.id.relative_write:
                HelperMethods.disappearKeypad(getActivity(), getView());
                break;
        }
    }

    private void UpdateProfile() {

        client_name = FragmentClientEditEdName.getEditText().getText().toString();
        client_email = FragmentClientEditEdEmail.getEditText().getText().toString();
        client_phone = FragmentClientEditEdPhone.getEditText().getText().toString();
        if (client_name.isEmpty()) {
            return;
        } else if (client_email.isEmpty()) {
        } else if (client_phone.isEmpty()) {
            client_phone = (String) FragmentClientEditEdPhone.getEditText().getHint();
        } else if (!TextUtils.equals(client_password, client_confirm)) {
            Toast.makeText(getActivity(), "Password didn't Match", Toast.LENGTH_SHORT).show();
        } else {
            EditClientProfile();
        }
    }

    private void EditClientProfile() {
        City_id = city_ids.get(FragmentClientEditSpCity.getSelectedItemPosition());
        if (City_id == 0) {
            return;
        }
        Region_id = regions_ids.get(FragmentClientEditSpBlock.getSelectedItemPosition());
        if (Region_id == 0) {
            return;
        }
        ApiServices.onClientEditProfile(convertToRequestBody(api_token), convertToRequestBody(client_name),
                convertToRequestBody(client_email)
                , convertToRequestBody(client_phone)
                , convertToRequestBody(City_name), convertToRequestBody(String.valueOf(Region_id)),
                convertFileToMultipart(ImagesFiles.get(0).getPath(), "profile_image")).enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {

                Log.i(TAG, "onResponse:" + response.message());
                try {
                    if (response.body().getStatus() == 1) {
                        SharedPreferencesManger.SaveData(getActivity(), "USER_PASSWORD", client_password);
                        SharedPreferencesManger.SaveData(getActivity(), "USER_EMAIL", client_email);
                        HomeFragment homeFragment = new HomeFragment();
                        HelperMethods.replace(homeFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Frame_Home, null, null);
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

    private void OpenGallary() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentClientEditImgProfile, ImagesFiles.get(0).getPath(), getActivity());
            }
        };

        openAlbum(1, getActivity(), ImagesFiles, action);
    }
}

