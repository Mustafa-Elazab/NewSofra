package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.clientnotifications.NotificationData;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItems;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.ListOfOrders;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.NotificationsFragment;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {
    private final List<NotificationData> notificationData;
    private Context context;
    private Activity activity;

    public NotificationAdapter(Activity activity, Context context, List<NotificationData> notificationData) {

        this.activity = activity;
        this.context = context;
        this.notificationData = notificationData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_notifications_rv,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.listNotificationsRvText.setText(notificationData.get(position).getTitle());
        holder.listNotificationsRvTime.setText(notificationData.get(position).getCreatedAt());
        getOrder(notificationData.get(position).getOrderId(),position);
    }

    private void getOrder(String orderId, int position) {

        String resturant_api_token=SharedPreferencesManger.LoadData(activity,RESTURANT_API_TOKEN);
        API ApiServices=RetrofitClient.getClient().create(API.class);
        ApiServices.restaurantmyitems(resturant_api_token,1).enqueue(new Callback<FoodItems>() {
            @Override
            public void onResponse(Call<FoodItems> call, Response<FoodItems> response) {
                Log.i("onResponse",response.body().getMsg());

                try{

                    if (response.body().getStatus()==1) {

                        List<FoodItemsData> data = response.body().getData().getData();
                        ResturantMyItemsAdapter adapter=new ResturantMyItemsAdapter(activity,activity);
                        adapter.sendDataToAdapter(data);
                        adapter.notifyDataSetChanged();
                    }

                }catch (Exception e){
                    Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<FoodItems> call, Throwable t) {

            }
        });
    }

    private void setAction(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return notificationData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_notifications_rv_notifiy)
        ImageView listNotificationsRvNotifiy;
        @BindView(R.id.list_notifications_rv_text)
        TextView listNotificationsRvText;
        @BindView(R.id.list_notifications_rv_time)
        TextView listNotificationsRvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
