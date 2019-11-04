package com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.Home;


import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.CategoriesAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.categories.Categories;
import com.example.mustafa.sofraNew.data.models.categories.Categories_Data;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;
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

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantCategoriesFragment extends Fragment {


    @BindView(R.id.Fragment_categories_resturantname)
    TextView FragmentCategoriesResturantname;
    @BindView(R.id.Fragment_categories_recycler)
    RecyclerView FragmentCategoriesRecycler;
    @BindView(R.id.Fragment_categories_addorder)
    FloatingActionButton FragmentCategoriesAddorder;
    Unbinder unbinder;
    private API ApiServices;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private CircleImageView alertCategoriesImgCategory;
    private Button alertCategoriesBtnCategoryAdd;
    private EditText alertCategoriesEdCategory;
    private AlertDialog.Builder builder;


    public RestaurantCategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant_categories, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getData();
        setupRecycler();

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RestaurantHomeFragment HomeFragment = new RestaurantHomeFragment();
                HelperMethods.replace(HomeFragment, getActivity().getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
            }
        });


        return view;
    }


    private void getData() {
        String api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        ApiServices.getResturantCategory(api_token).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        List<Categories_Data> data = response.body().getData().getData();
                        CategoriesAdapter adapter = new CategoriesAdapter(getActivity(), getActivity(), data);
                        FragmentCategoriesRecycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {

            }
        });
    }


    private void setupRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentCategoriesRecycler, manager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick(R.id.Fragment_categories_addorder)
    public void onViewClicked() {
        ShowDialog();
    }

    private void ShowDialog() {
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_categories, null);

        alertCategoriesImgCategory = dialogLayout.findViewById(R.id.alert_categories_img_category);
        alertCategoriesImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenImageGallary();
            }
        });
        alertCategoriesEdCategory = dialogLayout.findViewById(R.id.alert_categories_ed_category);
        alertCategoriesBtnCategoryAdd = dialogLayout.findViewById(R.id.alert_categories_btn_category_add);
        builder.setView(dialogLayout);
     alertCategoriesBtnCategoryAdd.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             AddNewCategory();

         }
     });
        builder.show();
    }

    private void AddNewCategory() {
        String Category_name=alertCategoriesEdCategory.getText().toString();
        String api_token=SharedPreferencesManger.LoadData(getActivity(),RESTURANT_API_TOKEN);
        ApiServices.OnAddNewCategory(convertToRequestBody(Category_name),
                convertToRequestBody(api_token),convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"))
                .enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        Log.i( "onR",response.body().getMsg());
                        Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(getActivity(), "Done Adding new Category !!", Toast.LENGTH_SHORT).show();
                                builder.setCancelable(false);
                                alertCategoriesEdCategory.setText("");
                                alertCategoriesImgCategory.setImageResource(R.drawable.ic_add_black);
                            }

                        }catch (Exception e){
                            Toast.makeText(getActivity(), e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                    }
                    });
    }

    private void OpenImageGallary() {
        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(alertCategoriesImgCategory, ImagesFiles.get(0).getPath(), getActivity());
            }
        };
        openAlbum(1, getActivity(), ImagesFiles, action);
    }


}



