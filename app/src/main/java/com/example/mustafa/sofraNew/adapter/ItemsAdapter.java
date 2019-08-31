package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.Client;
import com.example.mustafa.sofraNew.data.model.items.Items_Data;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.order.OrderDetailFragment;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder> {
    private final List<Items_Data> itemsData;
    private Context context;
    private Activity activity;

    public ItemsAdapter(Activity activity, Context context, List<Items_Data> itemsData) {

        this.activity = activity;
        this.context = context;
        this.itemsData = itemsData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_resturantinfo_resturant_menu_rv,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.listUserMenuTvOrderName.setText(itemsData.get(position).getName());
        holder.listUserMenuTvOrderPrice.setText(itemsData.get(position).getPrice());
        holder.listUserMenuTvOrderDescription.setText(itemsData.get(position).getDescription());
        Glide.with(activity).load(itemsData.get(position).getPhotoUrl()).into(holder.ListResturantHomeImage);
    }

    private void setAction(ViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClientActivity clientActivity=(ClientActivity)activity;

                OrderDetailFragment orderDetailFragment=new OrderDetailFragment();
                orderDetailFragment.OrderDetailData=itemsData.get(position);
                HelperMethods.replace(orderDetailFragment,clientActivity.getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.List_Resturant_home_Image)
        PorterShapeImageView ListResturantHomeImage;
        @BindView(R.id.list_user_menu_tv_order_name)
        TextView listUserMenuTvOrderName;
        @BindView(R.id.list_user_menu_tv_order_description)
        TextView listUserMenuTvOrderDescription;
        @BindView(R.id.list_user_menu_tv_order_price)
        TextView listUserMenuTvOrderPrice;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
