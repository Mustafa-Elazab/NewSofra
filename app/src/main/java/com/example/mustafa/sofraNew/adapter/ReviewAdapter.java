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

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.rest.restaurantReview.RestaurantReviewData;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<RestaurantReviewData> reviews_data;
    private Context context;
    private Activity activity;

    public ReviewAdapter(Activity activity, Context context, List<RestaurantReviewData> reviewData) {
        this.activity = activity;
        this.context = context;
        this.reviews_data = reviewData;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_resturantinfo_resturant_coment_rv,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.listReviewName.setText(reviews_data.get(position).getClient().getData().getUser().getName());
        holder.listReviewText.setText(reviews_data.get(position).getComment());
        switch (reviews_data.get(position).getRestaurant().getData().getData().getRate()) {
            case 1:
                holder.listReviewFace.setImageResource(R.drawable.ic_face_very_sad);
                break;
            case 2:
                holder.listReviewFace.setImageResource(R.drawable.ic_face_sad);

                break;
            case 3:
                holder.listReviewFace.setImageResource(R.drawable.ic_face_emoji);

                break;
            case 4:
                holder.listReviewFace.setImageResource(R.drawable.ic_face_happy);

                break;
            case 5:
                holder.listReviewFace.setImageResource(R.drawable.ic_face_very_happy);

                break; }
    }


    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reviews_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_review_name)
        TextView listReviewName;
        @BindView(R.id.list_review_text)
        TextView listReviewText;
        @BindView(R.id.list_review_face)
        ImageView listReviewFace;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
