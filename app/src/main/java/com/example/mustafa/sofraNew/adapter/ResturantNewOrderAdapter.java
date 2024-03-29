package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.OrdersData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;

public class ResturantNewOrderAdapter extends RecyclerView.Adapter<ResturantNewOrderAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private List<OrdersData> restaurantDataList = new ArrayList<>();
    private String Type;
    private API ApiServices;

    public ResturantNewOrderAdapter(Context context, Activity activity, List<OrdersData> restaurantDataList, String type) {

        this.context = context;
        this.activity = activity;
        this.restaurantDataList = restaurantDataList;
        this.Type = type;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_seller_new_order,
                parent, false);


        ApiServices=RetrofitClient.getClient().create(API.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        Glide.with(context).load(restaurantDataList.get(position).getRestaurant().getPhotoUrl()).into(holder.ResturantOrderImage);
        holder.ResturantOrderName.setText("العميل :" + restaurantDataList.get(position).getClient().getName());
        holder.ResturantOrderOrder.setText("رقم الطلب :" + restaurantDataList.get(position).getId());
        holder.ResturantOrderPrice.setText("الاجمالي :" + restaurantDataList.get(position).getTotal());
        holder.ResturantOrderLocation.setText("العنوان :" + restaurantDataList.get(position).getAddress());


        if (Type == "pending") {


        } else if (Type == "current") {

            holder.ResturantOrderRefuse.setVisibility(View.GONE);
            holder.ResturantOrderCall.setText(restaurantDataList.get(position).getClient().getPhone());
            holder.ResturantOrderAccept.setText("تأكيد الاستلام");


        } else if (Type == "completed") {

            holder.ResturantOrderRefuse.setVisibility(View.GONE);
            holder.ResturantOrderCall.setVisibility(View.GONE);

        } else {
            Toast.makeText(context, "Null", Toast.LENGTH_SHORT).show();
        }

    }

    private void setAction(final ViewHolder holder, final int position) {

        holder.ResturantOrderRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String api_token=SharedPreferencesManger.LoadData(activity,RESTURANT_API_TOKEN);
                ApiServices.onRestaurantRejectOrder(api_token,restaurantDataList.get(position).getId()).enqueue(new Callback<PublicResponse>() {
                    @Override
                    public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(activity, "تم رفض طلبك", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(position);
                            }
                        }catch (Exception e){
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PublicResponse> call, Throwable t) {
                    }
                });
            }
        });

        holder.ResturantOrderAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String api_token=SharedPreferencesManger.LoadData(activity,RESTURANT_API_TOKEN);
                ApiServices.onRestaurantAcceptOrder(api_token,restaurantDataList.get(position).getId()).enqueue(new Callback<PublicResponse>() {
                    @Override
                    public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.body().getStatus()==1) {
                                Toast.makeText(activity, "تم قبول طلبك", Toast.LENGTH_SHORT).show();

                            }
                        }catch (Exception e){
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PublicResponse> call, Throwable t) {
                    }
                });
            }
        });
        holder.ResturantOrderCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String api_token=SharedPreferencesManger.LoadData(activity,RESTURANT_API_TOKEN);
                ApiServices.onRestaurantConfirmOrder(api_token,restaurantDataList.get(position).getId()).enqueue(new Callback<PublicResponse>() {
                    @Override
                    public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                        Toast.makeText(activity, response.message(), Toast.LENGTH_SHORT).show();
                        try {
                            if (response.body().getStatus()==1) {

                                ResturantActivity resturantActivity=(ResturantActivity)activity;
                                String phone = restaurantDataList.get(position).getClient().getPhone();
                                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
                                resturantActivity.startActivity(intent);
                            }
                        }catch (Exception e){
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<PublicResponse> call, Throwable t) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return restaurantDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Resturant_order_Image)
        PorterShapeImageView ResturantOrderImage;
        @BindView(R.id.Resturant_order_name)
        TextView ResturantOrderName;
        @BindView(R.id.Resturant_order_order)
        TextView ResturantOrderOrder;
        @BindView(R.id.Resturant_order_price)
        TextView ResturantOrderPrice;
        @BindView(R.id.Resturant_order_location)
        TextView ResturantOrderLocation;
        @BindView(R.id.Linear)
        LinearLayout Linear;
        @BindView(R.id.Resturant_order_call)
        Button ResturantOrderCall;
        @BindView(R.id.Resturant_order_accept)
        Button ResturantOrderAccept;
        @BindView(R.id.Resturant_order_refuse)
        Button ResturantOrderRefuse;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
