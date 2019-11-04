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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.models.offer.offers.OffersData;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ClientActivity;
import com.example.mustafa.sofraNew.ui.fragment.client.HomeNavigation.offer.ClientOfferDetailFragment;

import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientOffersAdapter extends RecyclerView.Adapter<ClientOffersAdapter.ViewHolder> {
    private final List<OffersData> offer_client_data;
    private Context context;
    private Activity activity;

    public ClientOffersAdapter(Activity activity, Context context, List<OffersData> offersData) {

        this.activity = activity;
        this.context = context;
        this.offer_client_data = offersData;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_client_offer,
                parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        Glide.with(context).load(offer_client_data.get(position).getPhotoUrl()).into(holder.listResturantOfferImgRv);
        holder.listClientOfferTvText.setText(offer_client_data.get(position).getDescription());

    }

    private void setAction(ViewHolder holder, final int position) {

        holder.listClientOfferBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ClientActivity clientActivity=(ClientActivity)activity;
                ClientOfferDetailFragment fragment=new ClientOfferDetailFragment();
                fragment.offer_data=offer_client_data.get(position);
                HelperMethods.replace(fragment,clientActivity.getSupportFragmentManager(),R.id.Activity_Frame_Home,null,null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offer_client_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.list_Client_offer_tv_text)
        TextView listClientOfferTvText;
        @BindView(R.id.list_Client_offer_btn_send)
        Button listClientOfferBtnSend;
        @BindView(R.id.list_Resturant_offer_img_rv)
        CircleImageView listResturantOfferImgRv;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
