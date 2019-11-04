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
import com.chauthai.swipereveallayout.SwipeRevealLayout;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.order.RestaurantEditOrderFragment;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ResturantMyItemsAdapter extends RecyclerView.Adapter<ResturantMyItemsAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<FoodItemsData> data;


    public ResturantMyItemsAdapter(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_seller_food_info,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setSwip(holder, position);
    }

    private void setSwip(final ViewHolder holder, final int position) {

        holder.SwipeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ResturantActivity Activity = (ResturantActivity) activity;
                RestaurantEditOrderFragment restaurantEditOrderFragment = new RestaurantEditOrderFragment();
                restaurantEditOrderFragment.resturantdata = data.get(position);
                restaurantEditOrderFragment.item_id = data.get(position).getId();
                HelperMethods.replace(restaurantEditOrderFragment, Activity.getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
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

        Glide.with(context).load(data.get(position).getPhotoUrl()).into(holder.ListResturantHomeImage);
        holder.ListResturantOrderHomeMoney.setText("السعر :" + data.get(position).getPrice() + "\n" + " ريال ");
        holder.ListResturantOrderHomeContent.setText("وصف الطلب :" + data.get(position).getDescription());
        holder.ListResturantOrderHomeTotal.setText("الاجمالي :" + data.get(position).getPrice() + "\n" + " ريال ");
        holder.ListResturantOrderHomeName.setText("الاسم :" + data.get(position).getName());

    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public void sendDataToAdapter(List<FoodItemsData> data) {

        this.data = data;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;
        @BindView(R.id.Activity_Item_PsIv_Activity_Swipe_Layout)
        SwipeRevealLayout ActivityItemPsIvActivitySwipeLayout;
        @BindView(R.id.List_Resturant_home_Image)
        PorterShapeImageView ListResturantHomeImage;
        @BindView(R.id.List_Resturant_order_home_name)
        TextView ListResturantOrderHomeName;
        @BindView(R.id.List_Resturant_order_home_content)
        TextView ListResturantOrderHomeContent;
        @BindView(R.id.List_Resturant_order_home_total)
        TextView ListResturantOrderHomeTotal;
        @BindView(R.id.List_Resturant_order_home_money)
        TextView ListResturantOrderHomeMoney;
        @BindView(R.id.Swipe_Edit)
        ImageView SwipeEdit;
        @BindView(R.id.Swipe_Delete)
        ImageView SwipeDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}

