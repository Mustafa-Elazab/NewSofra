package com.example.mustafa.sofraNew.ui.fragment.Mutual;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.adapter.NotificationAdapter;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.clientnotifications.ClientNotifications;
import com.example.mustafa.sofraNew.data.models.clientnotifications.NotificationData;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.OnEndless;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.USER_API_TOKEN;


/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationsFragment extends Fragment {


    public String Type;
    @BindView(R.id.Fragment_notification_recycler)
    RecyclerView FragmentNotificationRecycler;
    Unbinder unbinder;
    private API ApiServices;
    private String client_api_token = "";
    private String resturant_api_token="";
    private List<NotificationData> notificationData=new ArrayList<>();
    private OnEndless onEndless;
    private Integer max;
    private boolean checkFilterPost = true;
    private NotificationAdapter adapter;

    public NotificationsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        unbinder = ButterKnife.bind(this, view);
        ApiServices = RetrofitClient.getClient().create(API.class);

        if (Type == "RestaurantsList") {
            RestaurantNotifications(1);
            SetupRecyclerView();
        } else if (Type == "Client") {
            ClientNotifications(1);
            SetupClientRecyclerView();
        }


        return view;
    }

    private void ClientNotifications(int page) {

        Toast.makeText(getActivity(), "Client2", Toast.LENGTH_SHORT).show();
        client_api_token = SharedPreferencesManger.LoadData(getActivity(), USER_API_TOKEN);
        ApiServices.getClientNotifications(client_api_token, page).enqueue(new Callback<ClientNotifications>() {
            @Override
            public void onResponse(Call<ClientNotifications> call, Response<ClientNotifications> response) {
                try {
                    if (response.body().getStatus() == 1) {

                        List<NotificationData> data = response.body().getData().getData();
                        notificationData.addAll(data);
                        max=response.body().getData().getLastPage();
                        adapter.notifyDataSetChanged();

                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<ClientNotifications> call, Throwable t) {

            }
        });
    }

    private void RestaurantNotifications(int page) {
        Toast.makeText(getActivity(), "Resturant1", Toast.LENGTH_SHORT).show();
        resturant_api_token = SharedPreferencesManger.LoadData(getActivity(), RESTURANT_API_TOKEN);
        ApiServices.getResturantNotifications(resturant_api_token, page).enqueue(new Callback<ClientNotifications>() {
            @Override
            public void onResponse(Call<ClientNotifications> call, Response<ClientNotifications> response) {
                try {
                    if (response.body().getStatus() == 1) {
                        List<NotificationData> data = response.body().getData().getData();
                        notificationData.addAll(data);
                        max=response.body().getData().getLastPage();
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.getMessage();
                }

            }

            @Override
            public void onFailure(Call<ClientNotifications> call, Throwable t) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private void SetupRecyclerView() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        FragmentNotificationRecycler.setLayoutManager(manager);

        onEndless = new OnEndless(manager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {
                        RestaurantNotifications(current_page);
                    }
                }
            }
        };
        FragmentNotificationRecycler.addOnScrollListener(onEndless);
        adapter=new NotificationAdapter(getActivity(),getActivity(),notificationData);
        FragmentNotificationRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



    private void SetupClientRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        FragmentNotificationRecycler.setLayoutManager(linearLayoutManager);

        onEndless = new OnEndless(linearLayoutManager, 1) {
            @Override
            public void onLoadMore(int current_page) {

                if (current_page <= max || max != 0 || current_page == 1) {
                    if (checkFilterPost) {

                        ClientNotifications(current_page);
                    }
                }
            }
        };
        FragmentNotificationRecycler.addOnScrollListener(onEndless);
        adapter=new NotificationAdapter(getActivity(),getActivity(),notificationData);
        FragmentNotificationRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

}
