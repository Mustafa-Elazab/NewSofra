package com.example.mustafa.sofraNew.ui.fragment.restaurant.order;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.restaurantnewitem.RestaurantNewItem;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.LoadData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.disappearKeypad;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantAddFoodFragment extends Fragment {


    @BindView(R.id.fragment_resturant_addfood_img_add)
    ImageView fragmentresturantAddfoodImgAdd;
    @BindView(R.id.fragment_resturant_addfood_ed_add_foodname)
    TextInputLayout fragmentresturantAddfoodEdAddFoodname;
    @BindView(R.id.fragment_resturant_addfood_ed_add_fooddiscription)
    TextInputLayout fragmentresturantAddfoodEdAddFooddiscription;
    @BindView(R.id.fragment_resturant_addfood_ed_add_foodprice)
    TextInputLayout fragmentresturantAddfoodEdAddFoodprice;
    @BindView(R.id.fragment_resturant_addfood_ed_add_fooddiscount)
    TextInputLayout fragmentresturantAddfoodEdAddFooddiscount;
    @BindView(R.id.fragment_resturant_addfood_ed_add_foodready)
    TextInputLayout fragmentresturantAddfoodEdAddFoodready;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    @BindView(R.id.relative_write)
    RelativeLayout relativeWrite;


    Unbinder unbinder;
    private API ApiServices;
    private String ImageUri;

    public RestaurantAddFoodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_addfood, container, false);
        unbinder = ButterKnife.bind(this, view);


        ApiServices = RetrofitClient.getClient().create(API.class);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_resturant_addfood_img_add, R.id.fragment_resturant_addfood_btn_add_food, R.id.relative_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_resturant_addfood_img_add:
                getGallayImage();
                break;
            case R.id.fragment_resturant_addfood_btn_add_food:

                CheckVariable();

                break;
            case R.id.relative_write:

                disappearKeypad(getActivity(), getView());

                break;
        }
    }

    private void CheckVariable() {

        String Food_Name = fragmentresturantAddfoodEdAddFoodname.getEditText().getText().toString();
        String Food_Discrp = fragmentresturantAddfoodEdAddFooddiscription.getEditText().getText().toString();
        String Food_Price = fragmentresturantAddfoodEdAddFoodprice.getEditText().getText().toString();
        String Food_ReadyForGo = fragmentresturantAddfoodEdAddFoodready.getEditText().getText().toString();
        String Food_OnSale = fragmentresturantAddfoodEdAddFooddiscount.getEditText().getText().toString();


        if (Food_Name.isEmpty()) {
            Toast.makeText(getActivity(), "FoodName Is Requird..", Toast.LENGTH_SHORT).show();
        } else if (Food_Discrp.isEmpty()) {
            Toast.makeText(getActivity(), "DiscriptionFood Is Requird..", Toast.LENGTH_SHORT).show();
        } else if (Food_Price.isEmpty()) {
            Toast.makeText(getActivity(), "FoodPrice Is Requird..", Toast.LENGTH_SHORT).show();
        } else if (Food_ReadyForGo.isEmpty()) {
            Toast.makeText(getActivity(), "FoodForDelivery Is Requird..", Toast.LENGTH_SHORT).show();
        } else if (Food_OnSale.isEmpty()) {
            Toast.makeText(getActivity(), "FoodOnSalle Is Requird..", Toast.LENGTH_SHORT).show();
        } else {

            AddNewFood(Food_Name, Food_Discrp, Food_Price, Food_ReadyForGo, Food_OnSale);
        }

    }

    private void AddNewFood(String food_name, String food_discrp, String food_price, String food_readyForGo, String food_onSale) {

        String Api_token=LoadData(getActivity(),RESTURANT_API_TOKEN);

        ApiServices.onResturantNewItem(convertToRequestBody(food_discrp),convertToRequestBody(food_price),
                convertToRequestBody(food_readyForGo),convertToRequestBody(food_name),
                convertToRequestBody(Api_token),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo")
        ).enqueue(new Callback<RestaurantNewItem>() {
            @Override
            public void onResponse(Call<RestaurantNewItem> call, Response<RestaurantNewItem> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                try {

                    if (response.body().getStatus() == 1) {


                        Toast.makeText(getActivity(), "Order is Uploaded", Toast.LENGTH_SHORT).show();
                        RestaurantHomeFragment restaurantHomeFragment =new RestaurantHomeFragment();
                        HelperMethods.replace(restaurantHomeFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Resturant_Frame_Home,null,null);;
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantNewItem> call, Throwable t) {

            }
        });

    }

    private void getGallayImage() {

        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {

                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(fragmentresturantAddfoodImgAdd, ImagesFiles.get(0).getPath(), getActivity());

            }

        };

        openAlbum(3, getActivity(), ImagesFiles, action);
    }


}