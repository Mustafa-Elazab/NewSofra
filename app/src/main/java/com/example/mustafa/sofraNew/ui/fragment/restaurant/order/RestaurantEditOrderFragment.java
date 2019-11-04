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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItems;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.data.reset.API;
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

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantEditOrderFragment extends Fragment {


    public FoodItemsData resturantdata;
    public Integer item_id;
    @BindView(R.id.fragment_resturant_editfood_tv_add)
    TextView fragmentResturantEditfoodTvAdd;
    @BindView(R.id.fragment_resturant_editfood_img_add)
    ImageView fragmentResturantEditfoodImgAdd;
    @BindView(R.id.fragment_resturant_editfood_ed_add_foodname)
    TextInputLayout fragmentResturantEditfoodEdfoodname;
    @BindView(R.id.fragment_resturant_editfood_ed_add_fooddiscription)
    TextInputLayout fragmentResturantEditfoodEdfooddiscription;
    @BindView(R.id.fragment_resturant_editfood_ed_add_foodprice)
    TextInputLayout fragmentResturantEditfoodEdfoodprice;
    @BindView(R.id.fragment_resturant_editfood_ed_add_fooddiscount)
    TextInputLayout fragmentResturantEditfoodEdfooddiscount;
    @BindView(R.id.fragment_resturant_editfood_ed_add_foodready)
    TextInputLayout fragmentResturantEditfoodEdfoodready;
    @BindView(R.id.relative_write)
    RelativeLayout relativeWrite;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private API ApiServices;
    Unbinder unbinder;


    public RestaurantEditOrderFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit_order, container, false);
        unbinder = ButterKnife.bind(this, view);

        GetOrderData();
        return view;
    }

    private void GetOrderData() {


     //   item_id = resturantdata.getId();
      //  Toast.makeText(getActivity(), item_id, Toast.LENGTH_SHORT).show();
        fragmentResturantEditfoodEdfoodname.setHint(resturantdata.getName());
        fragmentResturantEditfoodEdfoodprice.setHint(resturantdata.getPrice());
        fragmentResturantEditfoodEdfooddiscription.setHint(resturantdata.getDescription());
        fragmentResturantEditfoodEdfoodready.setHint(resturantdata.getPreparingTime());
//        fragmentResturantEditfoodEdfooddiscount.setHint(resturantdata.getOfferPrice());
        Glide.with(getActivity()).load(resturantdata.getPhotoUrl()).into(fragmentResturantEditfoodImgAdd);



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fragment_resturant_editfood_img_add, R.id.fragment_resturant_editfood_btn_add_food, R.id.relative_write})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fragment_resturant_editfood_img_add:
                getGallayImage();
                break;
            case R.id.fragment_resturant_editfood_btn_add_food:
                EditOrderData();
                break;
            case R.id.relative_write:
                HelperMethods.disappearKeypad(getActivity(), getView());
                break;
        }
    }

    private void EditOrderData() {

        String Food_Name = fragmentResturantEditfoodEdfoodname.getEditText().getText().toString();
        String Food_Discrp = fragmentResturantEditfoodEdfooddiscription.getEditText().getText().toString();
        String Food_Price = fragmentResturantEditfoodEdfoodprice.getEditText().getText().toString();
        String Food_ReadyForGo = fragmentResturantEditfoodEdfoodready.getEditText().getText().toString();
        String Food_OnSale = fragmentResturantEditfoodEdfooddiscount.getEditText().getText().toString();


        if (Food_Name.isEmpty()) {
            Food_Name = fragmentResturantEditfoodEdfoodname.getHint().toString();
        } else if (Food_Discrp.isEmpty()) {

            Food_Discrp = fragmentResturantEditfoodEdfooddiscription.getHint().toString();

        } else if (Food_Price.isEmpty()) {
            Food_Price = fragmentResturantEditfoodEdfoodprice.getHint().toString();

        } else if (Food_ReadyForGo.isEmpty()) {
            Food_ReadyForGo = fragmentResturantEditfoodEdfoodready.getHint().toString();

        } else if (Food_OnSale.isEmpty()) {
            Food_OnSale = fragmentResturantEditfoodEdfooddiscount.getHint().toString();

        } else {

            EditOrder(Food_Name, Food_Discrp, Food_Price, Food_ReadyForGo, Food_OnSale);
        }
    }
    private void EditOrder(String food_name, String food_discrp, String food_price, String food_readyForGo, String food_onSale) {
        ApiServices.onResturantEditItem(convertToRequestBody(food_discrp),convertToRequestBody(food_price),
                convertToRequestBody(food_readyForGo),convertToRequestBody(food_name),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"),
                convertToRequestBody(String.valueOf(item_id)),convertToRequestBody(SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN))
        ).enqueue(new Callback<FoodItems>() {
            @Override
            public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();

                try {

                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), "Order is Edited", Toast.LENGTH_SHORT).show();
                        RestaurantHomeFragment restaurantHomeFragment =new RestaurantHomeFragment();
                        HelperMethods.replace(restaurantHomeFragment,getActivity().getSupportFragmentManager(),R.id.Activity_Resturant_Frame_Home,null,null);;
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FoodItems> call, Throwable t) {
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
                onLoadImageFromUrl(fragmentResturantEditfoodImgAdd, ImagesFiles.get(0).getPath(), getActivity());

            }

        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }

}
