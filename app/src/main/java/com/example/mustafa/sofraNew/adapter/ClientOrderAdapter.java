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

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.listoforders.OrdersData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientOrderAdapter extends RecyclerView.Adapter<ClientOrderAdapter.ViewHolder> {

    private final List<OrdersData> client_order_data;
    private final String type;
    private Context context;
    private Activity activity;

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


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

//        holder.orderName.setText(client_order_data.get(position).getItems().get(position).getName());
//        holder.clientOrder.setText("رقم الطلب : " + client_order_data.get(position).getItems().get(position).getPivot().getOrderId());
//        holder.orderTotal.setText("الاجمالي : " + client_order_data.get(position).getItems().get(position).getPivot().getPrice());
//        Glide.with(activity).load(client_order_data.get(position).getItems().get(position).getPhotoUrl()).into(holder.orderimage);

    }

    private void setAction(ViewHolder holder, int position) {

        if (type == "completed") {

            holder.buttonRecive.setVisibility(View.GONE);
            holder.buttonRefuse.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return client_order_data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.circlue)
        CircleImageView orderimage;
        @BindView(R.id.restaurant_name)
        TextView orderName;
        @BindView(R.id.restaurant_order)
        TextView clientOrder;
        @BindView(R.id.restaurant_total)
        TextView orderTotal;
        @BindView(R.id.button_rec)
        Button buttonRecive;
        @BindView(R.id.button_ref)
        Button buttonRefuse;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
