package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.categories.Categories_Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private final List<Categories_Data> category_data;
    private Context context;
    private Activity activity;

    public CategoryAdapter(Activity activity, Context context, List<Categories_Data> data) {

        this.activity = activity;
        this.context = context;
        this.category_data = data;
    }
//    private List<RestaurantClientData> restaurantDataList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_category,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.listCategoryTvCategoryName.setText(category_data.get(position).getName());
        Glide.with(activity).load(category_data.get(position).getPhotoUrl()).into(holder.listCategoryImgCategoryImage);
    }

    private void setAction(ViewHolder holder, int position) {


    }

    @Override
    public int getItemCount() {
        return category_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_category_img_categoryImage)
        CircleImageView listCategoryImgCategoryImage;
        @BindView(R.id.list_category_tv_categoryName)
        TextView listCategoryTvCategoryName;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
