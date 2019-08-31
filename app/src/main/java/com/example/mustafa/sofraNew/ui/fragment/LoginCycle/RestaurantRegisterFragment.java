package com.example.mustafa.sofraNew.ui.fragment.LoginCycle;


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
import com.example.mustafa.sofraNew.data.model.Client;
import com.example.mustafa.sofraNew.data.model.cities.Cities;
import com.example.mustafa.sofraNew.data.model.regions.Regions;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantRegisterFragment extends Fragment {


    @BindView(R.id.Fragment_Resturant_register_img_profile)
    CircleImageView FragmentResturantRegisterImgProfile;
    @BindView(R.id.Fragment_Resturant_register_ed_restaurantname)
    TextInputLayout FragmentResturantRegisterEdRestaurantname;
    @BindView(R.id.Fragment_Resturant_register_email)
    TextInputLayout FragmentResturantRegisterEmail;
    @BindView(R.id.Fragment_Resturant_register_phone)
    TextInputLayout FragmentResturantRegisterPhone;
    @BindView(R.id.Fragment_Resturant_register_sp_city)
    Spinner FragmentResturantRegisterSpCity;
    @BindView(R.id.Fragment_Resturant_register_sp_block)
    Spinner FragmentResturantRegisterSpBlock;
    @BindView(R.id.Fragment_Resturant_register_ed_password)
    TextInputLayout FragmentResturantRegisterEdPassword;
    @BindView(R.id.Fragment_Resturant_register_ed_password_confirm)
    TextInputLayout FragmentResturantRegisterEdPasswordConfirm;
    private String Resturant_confirm, Resturant_email, Resturant_phone, Resturant_password, Resturant_name;
    private int PICK_PHOTO_FOR_AVATAR = 1;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    Unbinder unbinder;
    private API ApiServices;
    private String City_name, Region_name;
    private Integer city_id, Region_id;
    private ArrayList<String> names;
    private ArrayList<Integer> city_ids;
    private List<String> Restaurnat_Info;

    public RestaurantRegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_register, container, false);


        unbinder = ButterKnife.bind(this, view);

        ApiServices = RetrofitClient.getClient().create(API.class);
        getCities();
        return view;
    }

    private void getCities() {

        ApiServices.getCities().enqueue(new Callback<Cities>() {
            @Override
            public void onResponse(Call<Cities> call, Response<Cities> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        names = new ArrayList<>();
                        city_ids = new ArrayList<>();
                        names.add(getString(R.string.city));
                        city_ids.add(0);

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {

                            names.add(response.body().getData().getData().get(i).getName());
                            city_ids.add(response.body().getData().getData().get(i).getId());

                        }
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                                android.R.layout.simple_spinner_item, names);

                        FragmentResturantRegisterSpCity.setAdapter(adapter);


                        FragmentResturantRegisterSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
            public void onFailure(Call<Cities> call, Throwable t) {

            }
        });
    }

    private void getRegoins(Integer city_id) {

        ApiServices.getRegions(city_id).enqueue(new Callback<Regions>() {
            @Override
            public void onResponse(Call<Regions> call, Response<Regions> response) {

                try {

                    if (response.body().getStatus() == 1) {

                        final List<String> regions_names = new ArrayList<>();
                        final List<Integer> regions_ids = new ArrayList<>();

                        regions_names.add(getString(R.string.region));
                        regions_ids.add(0);

                        for (int i = 0; i < response.body().getData().getData().size(); i++) {

                            regions_names.add(response.body().getData().getData().get(i).getName());
                            regions_ids.add(response.body().getData().getData().get(i).getId());

                        }

                        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity(), R.layout.support_simple_spinner_dropdown_item, regions_names);
                        FragmentResturantRegisterSpBlock.setAdapter(arrayAdapter);


                        FragmentResturantRegisterSpBlock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    Region_name = regions_names.get(position);
                                    Region_id = regions_ids.get(position);

                                }
                            }
                        });
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Regions> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private void CheckVariables() {

        Resturant_name = FragmentResturantRegisterEdRestaurantname.getEditText().getText().toString();
        Resturant_email = FragmentResturantRegisterEmail.getEditText().getText().toString();
        Resturant_phone = FragmentResturantRegisterPhone.getEditText().getText().toString();
        Resturant_password = FragmentResturantRegisterEdPassword.getEditText().getText().toString();
        Resturant_confirm = FragmentResturantRegisterEdPasswordConfirm.getEditText().getText().toString();

        if (TextUtils.isEmpty(Resturant_name)) {
            Toast.makeText(getActivity(), "ResturantActivity name is Requird.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Resturant_email)) {
            Toast.makeText(getActivity(), "Email is Requird.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Resturant_phone)) {
            Toast.makeText(getActivity(), "Phone is Requird.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Resturant_password)) {
            Toast.makeText(getActivity(), "Password is Requird.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(Resturant_confirm)) {
            Toast.makeText(getActivity(), "Password is Requird.", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.equals(Resturant_password, Resturant_confirm)) {
            Toast.makeText(getActivity(), "Password Dosen't Match", Toast.LENGTH_SHORT).show();
        } else {

            OnRegister();
        }

    }

    private void OnRegister() {

        Client client=new Client(Resturant_name,Resturant_email,Resturant_phone,Resturant_password,Resturant_confirm,String.valueOf(city_id),String.valueOf(Region_id));
    }


    @OnClick({R.id.Fragment_Resturant_register_img_profile, R.id.Fragment_Resturant_register_btn_continue})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_Resturant_register_img_profile:
                GetImage();

                break;
            case R.id.Fragment_Resturant_register_btn_continue:

                CheckVariables();

                RestaurantRegister2Fragment ResturantRegister2Fragment = new RestaurantRegister2Fragment();
                ResturantRegister2Fragment.Restaurnat_Info=Restaurnat_Info;
                HelperMethods.replace(ResturantRegister2Fragment, getActivity().getSupportFragmentManager()
                        , R.id.login_cycle, null, null);
                break;
        }
    }

    private void GetImage() {


        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(FragmentResturantRegisterImgProfile, ImagesFiles.get(0).getPath(), getActivity());

            }

        };

        openAlbum(1, getActivity(), ImagesFiles, action);
    }
}
