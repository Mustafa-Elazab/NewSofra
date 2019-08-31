package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.ItemsAdapter;
import com.example.mustafa.sofraNew.data.model.items.Items;
import com.example.mustafa.sofraNew.data.model.items.Items_Data;
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
    Unbinder unbinder;
    private API ApiServices;
    private List<Items_Data> itemsData=new ArrayList<>();
    private ItemsAdapter adapter;

    public ResturantMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_resturant_menu, container, false);
        unbinder = ButterKnife.bind(this, view);

        ApiServices=RetrofitClient.getClient().create(API.class);
        getItemsData();
        setUpRecycler();
        return view;
    }

    private void getItemsData() {

        ApiServices.onItems(Restaurant_Id).enqueue(new Callback<Items>() {
            @Override
            public void onResponse(Call<Items> call, Response<Items> response) {

                try {

                    if (response.body().getStatus()==1) {

                        List<Items_Data> data = response.body().getData().getData();
                        itemsData.addAll(data);
                        adapter=new ItemsAdapter(getActivity(),getActivity(),itemsData);
                        FragmentResturantinfoResturantMenuRv.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Items> call, Throwable t) {

            }
        });
    }

    private void setUpRecycler() {

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(),FragmentResturantinfoResturantMenuRv,manager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
