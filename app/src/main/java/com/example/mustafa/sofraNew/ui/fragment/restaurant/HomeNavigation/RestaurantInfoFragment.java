package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;

import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.example.mustafa.sofraNew.helper.HelperMethods.disappearKeypad;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantInfoFragment extends Fragment {


    @BindView(R.id.Fragment_seller_edit_img_profile)
    CircleImageView FragmentSellerEditImgProfile;
    @BindView(R.id.Fragment_seller_edit_ed_restaurantname)
    TextInputLayout FragmentSellerEditEdRestaurantname;
    @BindView(R.id.Fragment_seller_edit_email)
    TextInputLayout FragmentSellerEditEmail;
    @BindView(R.id.Fragment_seller_edit_phone)
    TextInputLayout FragmentSellerEditPhone;
    @BindView(R.id.Fragment_seller_edit_sp_city)
    Spinner FragmentSellerEditSpCity;
    @BindView(R.id.Fragment_seller_edit_sp_block)
    Spinner FragmentSellerEditSpBlock;
    @BindView(R.id.Fragment_seller_edit_ed_password)
    TextInputLayout FragmentSellerEditEdPassword;
    @BindView(R.id.Fragment_seller_edit_ed_password_confirm)
    TextInputLayout FragmentSellerEditEdPasswordConfirm;
    @BindView(R.id.Fragment_seller_edit_sp_categories)
    Spinner FragmentSellerEditSpCategories;
    @BindView(R.id.Fragment_seller_edit_ed_min)
    TextInputLayout FragmentSellerEditEdMin;
    @BindView(R.id.Fragment_seller_edit_ed_delivery)
    TextInputLayout FragmentSellerEditEdDelivery;
    @BindView(R.id.switch_status)
    Switch switchStatus;
    @BindView(R.id.Fragment_seller_edit_ed_phone)
    TextInputLayout FragmentSellerEditEdPhone;
    @BindView(R.id.Fragment_seller_edit_ed_whatsapp)
    TextInputLayout FragmentSellerEditEdWhatsapp;
    @BindView(R.id.Fragment_seller_edit_img_market)
    ImageView FragmentSellerEditImgMarket;
    Unbinder unbinder;
    @BindView(R.id.relative)
    RelativeLayout relative;
    @BindView(R.id.relative2)
    RelativeLayout relative2;
    @BindView(R.id.relative3)
    RelativeLayout relative3;
    @BindView(R.id.Fragment_seller_edit_tv_delivery_detail)
    TextView FragmentSellerEditTvDeliveryDetail;
    @BindView(R.id.Fragment_seller_edit_tv_market)
    TextView FragmentSellerEditTvMarket;
    @BindView(R.id.Fragment_seller_edit_btn_sign)
    Button FragmentSellerEditBtnSign;
    @BindView(R.id.bhghf)
    RelativeLayout bhghf;
    private int PICK_PHOTO_FOR_AVATAR=1;
    private API ApiServices;

    public RestaurantInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_info, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices= RetrofitClient.getClient().create(API.class);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_seller_edit_img_profile, R.id.Fragment_seller_edit_btn_sign ,R.id.bhghf})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.Fragment_seller_edit_img_profile:
                
                ImageIntent();
                
                break;
            case R.id.Fragment_seller_edit_btn_sign:
                break;
            case R.id.bhghf:
                disappearKeypad(getActivity() , getView());
                break;
        }
    }

    private void ImageIntent() {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        photoPickerIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(photoPickerIntent,"Select Picture"), PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            try {
                final Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                FragmentSellerEditImgProfile.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        GetDataOnStart();
    }

    private void GetDataOnStart() {


    }
}
