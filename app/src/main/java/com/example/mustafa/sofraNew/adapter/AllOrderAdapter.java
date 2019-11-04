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
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.ViewHolder> {
    private final List<FoodItemsData> foodItemsAllOrderData;
    private Context context;
    private Activity activity;
    private int result = 1;

    public AllOrderAdapter(Activity activity, Context context, List<FoodItemsData> foodItemsDataList) {

        this.activity = activity;
        this.context = context;
        this.foodItemsAllOrderData = foodItemsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_all_order,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        Glide.with(context).load(foodItemsAllOrderData.get(position).getPhotoUrl()).into(holder.listAllOrderImgOrderimage);
        holder.listAllOrderTvOrdername.setText(foodItemsAllOrderData.get(position).getName());
        holder.listAllOrderTvOrderprice.setText(foodItemsAllOrderData.get(position).getPrice());
    }

    private void setAction(final ViewHolder holder, final int position) {

        holder.listAllOrderImgOrdercancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
            }
        });


        holder.listAllOrderTvOrderplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.listAllOrderTvOrderresult.setText(String.valueOf(result++));
            }
        });

        holder.listAllOrderTvOrderminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (result < 1) {

                } else {
                    holder.listAllOrderTvOrderresult.setText(String.valueOf(result--));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return foodItemsAllOrderData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_all_order_img_orderimage)
        PorterShapeImageView listAllOrderImgOrderimage;
        @BindView(R.id.list_all_order_tv_ordername)
        TextView listAllOrderTvOrdername;
        @BindView(R.id.list_all_order_tv_orderprice)
        TextView listAllOrderTvOrderprice;
        @BindView(R.id.list_all_order_img_ordercancel)
        ImageView listAllOrderImgOrdercancel;
        @BindView(R.id.list_all_order_tv_orderplus)
        TextView listAllOrderTvOrderplus;
        @BindView(R.id.list_all_order_tv_orderminus)
        TextView listAllOrderTvOrderminus;
        @BindView(R.id.list_all_order_tv_orderresult)
        TextView listAllOrderTvOrderresult;
        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
