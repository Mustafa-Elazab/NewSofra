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
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.restaurantreviews.Review_Data;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.About_AppFragment;
import com.example.mustafa.sofraNew.ui.fragment.Mutual.Contact_UsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private final List<Review_Data> reviews_data;
    private Context context;
    private Activity activity;

    public ReviewAdapter(Activity activity, Context context, List<Review_Data> reviewData) {
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


      /*  holder.listReviewName.setText(reviews_data.get(position).getClient().getName());
        holder.listReviewText.setText(reviews_data.get(position).getComment());
        switch (reviews_data.get(position).getRestaurant().getRate()) {
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

                break;
        }

*/
    }


    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return reviews_data !=null?reviews_data.size():0;
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
