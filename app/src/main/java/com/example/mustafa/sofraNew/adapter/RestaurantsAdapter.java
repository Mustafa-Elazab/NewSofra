package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.login.Client;
import com.example.mustafa.sofraNew.data.model.restaurants.Restaurants_Data;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.resturant_info.ResturantHomeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class RestaurantsAdapter extends RecyclerView.Adapter<RestaurantsAdapter.ViewHolder> {
    private final List<Restaurants_Data> restaurantsdata;
    private Context context;
    public Activity activity;
    private String opened = "open";

    public RestaurantsAdapter(Context context, Activity activity, List<Restaurants_Data> dataArrayList) {

        this.activity = activity;
        this.context = context;
        this.restaurantsdata = dataArrayList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_detail_item,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        Glide.with(activity).load(restaurantsdata.get(position).getPhotoUrl()).into(holder.ListDetailImgResImgRv);
        holder.ListDetailTvResNameRv.setText(restaurantsdata.get(position).getName());
        holder.ListDetailTvMinmumRv.setText(restaurantsdata.get(position).getMinimumCharger());
        holder.ListDetailTvResDelvieryRv.setText(restaurantsdata.get(position).getDeliveryCost());
        holder.ListDetailRatRateRv.setRating(Float.valueOf(restaurantsdata.get(position).getRate()));
        holder.listOpen.setText(restaurantsdata.get(position).getAvailability());
        if (restaurantsdata.get(position).getAvailability().equals(opened)) {
            holder.ActivityHomeImgNotificationsOpen.setImageResource(R.drawable.ic_brightness_green);

        } else {
            holder.ActivityHomeImgNotificationsOpen.setImageResource(R.drawable.ic_brightness_marrom);


        }

    }

    private void setAction(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResturantHomeFragment resturantHomeFragment=new ResturantHomeFragment();

                resturantHomeFragment.restaurantsdata=restaurantsdata.get(position);
                ClientActivity clientActivity=(ClientActivity) activity;

                HelperMethods.replace(resturantHomeFragment,clientActivity.getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);

            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantsdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.List_Detail_Tv_ResName_Rv)
        TextView ListDetailTvResNameRv;
        @BindView(R.id.Activity_home_img_notifications_open)
        ImageView ActivityHomeImgNotificationsOpen;
        @BindView(R.id.List_Detail_Rat_Rate_Rv)
        RatingBar ListDetailRatRateRv;
        @BindView(R.id.List_Detail_Tv_Minmum_Rv)
        TextView ListDetailTvMinmumRv;
        @BindView(R.id.List_Detail_Tv_ResDelviery_Rv)
        TextView ListDetailTvResDelvieryRv;
        @BindView(R.id.List_Detail_Img_ResImg_Rv)
        CircleImageView ListDetailImgResImgRv;
        @BindView(R.id.list_open)
        TextView listOpen;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
