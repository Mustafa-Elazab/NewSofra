package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.restaurantmyoffers.Restaurant_My_Offers_Data;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.offer.ResturantEditOfferFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ResturantOffersAdapter extends RecyclerView.Adapter<ResturantOffersAdapter.ViewHolder> {



    private Context context;
    private Activity activity;
    private List<Restaurant_My_Offers_Data> restaurantOffersDataList = new ArrayList<>();

    public ResturantOffersAdapter(Context context, Activity activity, List<Restaurant_My_Offers_Data> restaurantoffersDataList) {

        this.context = context;
        this.activity = activity;
        this.restaurantOffersDataList = restaurantoffersDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_seller_offer,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setSwip(holder,position);
    }

    private void setSwip(ViewHolder holder, final int position) {

        holder.SwipeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResturantActivity Activity = (ResturantActivity) activity;
                ResturantEditOfferFragment resturantEditOfferFragment=new ResturantEditOfferFragment();
                resturantEditOfferFragment.resturantofferdata = restaurantOffersDataList.get(position);
                HelperMethods.replace(resturantEditOfferFragment, Activity.getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
            }
        });

        holder.SwipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
            }
        });
    }

    private void setData(ViewHolder holder, int position) {

        holder.listResturantOfferTvRv.setText(restaurantOffersDataList.get(position).getDescription());
        Glide.with(context).load(restaurantOffersDataList.get(position).getRestaurant().getPhotoUrl()).into(holder.listResturantOfferImgRv);
    }

    private void setAction(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return restaurantOffersDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Swipe_Edit)
        ImageView SwipeEdit;
        @BindView(R.id.Swipe_Delete)
        ImageView SwipeDelete;
        @BindView(R.id.list_Resturant_offer_tv_rv)
        TextView listResturantOfferTvRv;
        @BindView(R.id.list_Resturant_offer_img_rv)
        CircleImageView listResturantOfferImgRv;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}


