package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.offers.Offers_Data;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class ClientOffersAdapter extends RecyclerView.Adapter<ClientOffersAdapter.ViewHolder> {
    private Context context;
    private Activity activity;

    public ClientOffersAdapter(Activity activity, Context context) {

        this.activity=activity;
        this.context=context;


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


    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
