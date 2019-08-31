package com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info;


import android.app.AlertDialog;
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
import com.example.mustafa.sofraNew.adapter.ReviewAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.model.restaurantreviews.RestaurantReviews;
import com.example.mustafa.sofraNew.data.model.restaurantreviews.Review_Data;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.LoadData;
import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferencesManger.USER_API_TOKEN;
import static com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantHomeFragment.Restaurant_Id;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResturantComment_RatingFragment extends Fragment {


    @BindView(R.id.Fragment_resturantinfo_resturant_comment_rv)
    RecyclerView FragmentResturantinfoResturantCommentRv;
    Unbinder unbinder;
    private BreakIterator textView;
    private API ApiServices;
    private List<Review_Data> reviewData=new ArrayList<>();
    private ReviewAdapter adapter;

    public ResturantComment_RatingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_resturant_comment, container, false);

        unbinder = ButterKnife.bind(this, view);
        ApiServices=RetrofitClient.getClient().create(API.class);
        getReviewsData();
        setUpRecycler();
        return view;
    }

    private void getReviewsData() {

        ApiServices.onReviews(LoadData(getActivity(),USER_API_TOKEN),Restaurant_Id).enqueue(new Callback<RestaurantReviews>() {
            @Override
            public void onResponse(Call<RestaurantReviews> call, Response<RestaurantReviews> response) {

                Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                try {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(getActivity(), response.body().getMsg(), Toast.LENGTH_SHORT).show();
                    if (response.body().getStatus()==1) {

                        List<Review_Data> data = response.body().getData().getData();
                        Log.i( "Respose: ",response.body().getData().getData().toString());
                        reviewData.addAll(data);
                        adapter.notifyDataSetChanged();

                    }

                }catch (Exception e){
                    Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RestaurantReviews> call, Throwable t) {

            }
        });

    }

    private void setUpRecycler() {

        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        HelperMethods.setInitRecyclerViewAsLinearLayoutManager(getActivity(),FragmentResturantinfoResturantCommentRv,manager);
        adapter=new ReviewAdapter(getActivity(),getActivity(),reviewData);
        FragmentResturantinfoResturantCommentRv.setAdapter(adapter);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.Fragment_resturantinfo_resturant_comment_btn})
    public void onViewClicked() {
        ShowDialog();


    }

    public void ShowDialog() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_with_imageview, null);

        builder.setView(dialogLayout);
        builder.show();


    }


}
