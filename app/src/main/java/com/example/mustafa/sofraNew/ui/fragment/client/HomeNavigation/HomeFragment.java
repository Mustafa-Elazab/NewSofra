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
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.cities.Cities;
import com.example.mustafa.sofraNew.data.model.restaurants.Restaurants_Data;
import com.example.mustafa.sofraNew.data.model.restaurants.Restaurants;
import com.example.mustafa.sofraNew.data.model.restaurantsfilter.Restaurant_Filter_Data;
import com.example.mustafa.sofraNew.data.model.restaurantsfilter.RestaurantsFilter;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantInfoFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.USER_API_TOKEN;

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
    private List<Restaurants_Data> dataArrayList=new ArrayList<>();
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

        ApiServices=RetrofitClient.getClient().create(API.class);
        Toast.makeText(getActivity(), SharedPreferencesManger.LoadData(getActivity(),USER_API_TOKEN), Toast.LENGTH_SHORT).show();

        GetRestaurantsData();
        setupRecycler();
        getCity();
        getFilter();
        return view;
    }

    private void getFilter() {

        ApiServices.getFilter(keyword,City_id).enqueue(new Callback<RestaurantsFilter>() {
            @Override
            public void onResponse(Call<RestaurantsFilter> call, Response<RestaurantsFilter> response) {

                try {

                    if (response.body().getStatus()==1) {

                        List<Restaurant_Filter_Data> data = response.body().getData().getData();
                        Log.i( "onResponse: ",data.toString());
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantsFilter> call, Throwable t) {

            }
        });
    }

    private void getCity() {
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

                        FragmentHomeCycleSpCity.setAdapter(adapter);


                        FragmentHomeCycleSpCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                if (position == 0) {

                                } else {
                                    City_name = names.get(position);
                                    City_id=city_ids.get(position);


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



    private void GetRestaurantsData() {

        ApiServices.onRestaurant().enqueue(new Callback<Restaurants>() {
            @Override
            public void onResponse(Call<Restaurants> call, Response<Restaurants> response) {

                try {

                    if (response.body().getStatus()==1) {

                        List<Restaurants_Data> data = response.body().getData().getData();
                        dataArrayList.addAll(data);
                        adapter.notifyDataSetChanged();

                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Restaurants> call, Throwable t) {

            }
        });
    }

    private void setupRecycler() {

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(),FragmentHomeCycleRcycler,manager);
        adapter=new RestaurantsAdapter(getActivity(),getActivity(),dataArrayList);
        FragmentHomeCycleRcycler.setAdapter(adapter);


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.Fragment_Home_Cycle_img_search)
    public void onViewClicked() {

        keyword=FragmentHomeCycleEdSearch.getText().toString();

        if (keyword.isEmpty()){
            Toast.makeText(getActivity(), "Keyword is Empty !!", Toast.LENGTH_SHORT).show();
        }
        if (City_id == 0 && keyword == "") {
            GetRestaurantsData();
        } else {
            getFilter();
        }
    }

    }

