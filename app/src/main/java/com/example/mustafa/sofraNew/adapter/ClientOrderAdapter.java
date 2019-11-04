package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.OrdersData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;

public class ClientOrderAdapter extends RecyclerView.Adapter<ClientOrderAdapter.ViewHolder> {

    private final List<OrdersData> client_order_data;
    private final String type;
    private Context context;
    private Activity activity;
    private API ApiServices;
    private Integer order_id;

    public ClientOrderAdapter(Activity activity, Context context, List<OrdersData> client_order_data, String type) {
        this.activity = activity;
        this.context = context;
        this.client_order_data = client_order_data;
        this.type = type;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_current_order_rv,
                parent, false);

        ApiServices = RetrofitClient.getClient().create(API.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

//       Glide.with(context).load(client_order_data.get(position).getItems().get(position).getPhotoUrl()).into(holder.listClientOrderImage);
//       holder.listClientOrderName.setText(client_order_data.get(position).getItems().get(position).getName());
//       holder.listClientOrderOrder.setText("رقم الطلب :" + client_order_data.get(position).getItems().get(position)().getOrderId();
//       holder.listClientOrderPrice.setText("الاجمالي :" + client_order_data.get(position).getItems().get(position).getPrice());
//       holder.listClientOrderLocation.setText("العنوان :" + client_order_data.get(position).getAddress());

        if (type.equals("current")) {
            holder.listClientOrderRefuse.setVisibility(View.GONE);
        }
        if (type.equals("new")) {
            holder.listClientOrderAccept.setVisibility(View.GONE);
        }
        if (type.equals("completed")) {
            holder.listClientOrderAccept.setVisibility(View.GONE);
            holder.listClientOrderRefuse.setVisibility(View.GONE);
        }
    }

    private void setAction(ViewHolder holder, final int position) {


        holder.listClientOrderAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiServices.onClientConfirmOrder(order_id, SharedPreferencesManger.LoadData(activity, USER_API_TOKEN)).enqueue(new Callback<PublicResponse>() {
                    @Override
                    public void onResponse(Call<PublicResponse> call, Response<PublicResponse> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(context, "Order Confirmed", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PublicResponse> call, Throwable t) {
                        t.getMessage();
                    }
                });
            }
        });
        holder.listClientOrderRefuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return client_order_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_client_order_Image)
        PorterShapeImageView listClientOrderImage;
        @BindView(R.id.list_client_order_name)
        TextView listClientOrderName;
        @BindView(R.id.list_client_order_order)
        TextView listClientOrderOrder;
        @BindView(R.id.list_client_order_price)
        TextView listClientOrderPrice;
        @BindView(R.id.list_client_order_location)
        TextView listClientOrderLocation;
        @BindView(R.id.list_client_order_accept)
        Button listClientOrderAccept;
        @BindView(R.id.list_client_order_refuse)
        Button listClientOrderRefuse;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
