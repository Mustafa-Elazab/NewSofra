package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.RestaurantsAdapter;
import com.example.mustafa.sofraNew.data.models.general.generalResponse.GeneralResponse;
import com.example.mustafa.sofraNew.data.models.rest.restaurantList.RestaurantsList;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.RestaurantAllData;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.RestaurantData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.util.ArrayList;
import java.util.List;

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
public class HomeFragment extends Fragment {
    @BindView(R.id.Fragment_Home_Cycle_ed_search)
    EditText FragmentHomeCycleEdSearch;
    @BindView(R.id.Fragment_Home_Cycle_sp_city)
    Spinner FragmentHomeCycleSpCity;
    @BindView(R.id.Fragment_Home_Cycle)
    RecyclerView FragmentHomeCycleRcycler;
    Unbinder unbinder;
    private API ApiServices;
    private List<RestaurantData> dataArrayList = new ArrayList<>();
    private RestaurantsAdapter adapter;
    private ArrayList<String> names;
    private ArrayList<Integer> city_ids;
    private String City_name;
    private int City_id;
    private String keyword;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);
        GetRestaurantsData();
        setupRecycler();
        getCity();
        getFilter();
        return view;
    }

    private void getFilter() {

        ApiServices.getFilter(keyword, City_id).enqueue(new Callback<RestaurantsList>() {
            @Override
            public void onResponse(Call<RestaurantsList> call, Response<RestaurantsList> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        List<RestaurantData> restaurantFilterData = response.body().getData().getData();
                        dataArrayList.addAll(restaurantFilterData);
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<RestaurantsList> call, Throwable t) {

            }
        });
    }

    private void getCity() {
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

                        FragmentHomeCycleSpCity.setAdapter(adapter);
                        FragmentHomeCycleSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    City_name = names.get(position);
                                    City_id = city_ids.get(position);
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
    private void GetRestaurantsData() {
        ApiServices.onRestaurant().enqueue(new Callback<RestaurantsList>() {
            @Override
            public void onResponse(Call<RestaurantsList> call, Response<RestaurantsList> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        List<RestaurantData> data = response.body().getData().getData();
                        dataArrayList.addAll(data);
                        adapter.notifyDataSetChanged();
                    }

                } catch (Exception e) {
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantsList> call, Throwable t) {

            }
        });
    }

    private void setupRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(), FragmentHomeCycleRcycler, manager);
        adapter = new RestaurantsAdapter(getActivity(), getActivity(), dataArrayList);
        FragmentHomeCycleRcycler.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_Home_Cycle_img_search)
    public void onViewClicked() {

        keyword = FragmentHomeCycleEdSearch.getText().toString();

        if (keyword.isEmpty()) {
            Toast.makeText(getActivity(), "Keyword is Empty !!", Toast.LENGTH_SHORT).show();
        }
        if (City_id == 0 && keyword == "") {
            GetRestaurantsData();
        } else {
            getFilter();
        }
    }
}

