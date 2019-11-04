package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.CategoryAdapter;
import com.example.mustafa.sofraNew.adapter.ItemsAdapter;
import com.example.mustafa.sofraNew.data.models.categories.Categories;
import com.example.mustafa.sofraNew.data.models.categories.Categories_Data;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItems;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantHomeFragment.Restaurant_Id;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantMenuFragment extends Fragment {
    @BindView(R.id.Fragment_resturantinfo_resturant_menu_rv)
    RecyclerView FragmentResturantinfoResturantMenuRv;
    @BindView(R.id.Fragment_resturantinfo_resturant_category_rv)
    RecyclerView FragmentResturantinfoResturantCategoryRv;
    Unbinder unbinder;
    private API ApiServices;
    private ItemsAdapter adapter;
    private CategoryAdapter category_adapter;

    public ResturantMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_resturant_menu, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        getItemsData();
        getCateoryData();
        setUpRecycler();
        return view;
    }

    private void getCateoryData() {

        ApiServices.OnCategories(Restaurant_Id).enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        Toast.makeText(getActivity(), response.body().getData().getData().toString(), Toast.LENGTH_SHORT).show();
                        Log.i("onRes",response.body().getData().getData().toString());
                        List<Categories_Data> data = response.body().getData().getData();
                        category_adapter = new CategoryAdapter(getActivity(), getActivity(), data);
                        FragmentResturantinfoResturantCategoryRv.setAdapter(category_adapter);
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

    private void getItemsData() {

        ApiServices.onItems(Restaurant_Id).enqueue(new Callback<FoodItems>() {
            @Override
            public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        List<FoodItemsData> data = response.body().getData().getData();
                        adapter = new ItemsAdapter(getActivity(), getActivity(), data);
                        FragmentResturantinfoResturantMenuRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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

    private void setUpRecycler() {

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentResturantinfoResturantMenuRv, manager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentResturantinfoResturantCategoryRv, layoutManager);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
