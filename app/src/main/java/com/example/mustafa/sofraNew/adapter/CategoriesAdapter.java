package com.example.mustafa.sofraNew.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger;
import com.example.mustafa.sofraNew.data.models.categories.Categories;
import com.example.mustafa.sofraNew.data.models.categories.Categories_Data;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.reset.API;
import com.example.mustafa.sofraNew.data.reset.RetrofitClient;
import com.example.mustafa.sofraNew.helper.HelperMethods;
import com.example.mustafa.sofraNew.ui.activity.ResturantActivity;
import com.example.mustafa.sofraNew.ui.fragment.restaurant.HomeNavigation.RestaurantHomeFragment;
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView;
import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.AlbumFile;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.mustafa.sofraNew.data.local.SharedPreferences.SharedPreferencesManger.RESTURANT_API_TOKEN;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertFileToMultipart;
import static com.example.mustafa.sofraNew.helper.HelperMethods.convertToRequestBody;
import static com.example.mustafa.sofraNew.helper.HelperMethods.onLoadImageFromUrl;
import static com.example.mustafa.sofraNew.helper.HelperMethods.openAlbum;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {

    private final List<Categories_Data> data;
    private Context context;
    private Activity activity;
    private AlertDialog.Builder builder;
    private CircleImageView alertCategoriesImgCategory;
    private EditText alertCategoriesEdCategory;
    private Button alertCategoriesBtnCategoryEdit;
    private ArrayList<AlbumFile> ImagesFiles = new ArrayList<>();
    private API ApiServices;
    private Integer CategoryId;
    private Categories_Data categories_data;
    private String CategoryName;

    public CategoriesAdapter(Activity activity, Context context, List<Categories_Data> data) {
        this.activity = activity;
        this.context = context;
        this.data = data;
    }
//    private List<RestaurantClientData> restaurantDataList = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_categories,
                parent, false);

        ApiServices = RetrofitClient.getClient().create(API.class);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
        setSwip(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        holder.ListCategoriesOrderHomeName.setText(data.get(position).getName());
        Glide.with(activity).load(data.get(position).getPhotoUrl()).into(holder
                .ListCategoriesHomeImage);
        CategoryId = data.get(position).getId();
        categories_data = data.get(position);
        CategoryName = data.get(position).getName();
    }

    private void setAction(ViewHolder holder, int position) {

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestaurantHomeFragment homeFragment = new RestaurantHomeFragment();
                ResturantActivity resturant = (ResturantActivity) activity;
                homeFragment.category_id = CategoryId;
                homeFragment.category_name=CategoryName;
                Log.i("onClick",CategoryId.toString());
                HelperMethods.replace(homeFragment, resturant.getSupportFragmentManager(), R.id.Activity_Resturant_Frame_Home, null, null);
            }
        });
    }

    private void setSwip(ViewHolder holder, final int position) {

        holder.SwipeEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialog(categories_data);
            }
        });

        holder.SwipeDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notifyItemRemoved(position);
            }
        });
    }

    private void ShowDialog(Categories_Data categories_data) {
        builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogLayout = inflater.inflate(R.layout.alert_dialog_categories, null);
        alertCategoriesImgCategory = dialogLayout.findViewById(R.id.alert_categories_img_category);
        alertCategoriesImgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenGallary();
            }
        });
        alertCategoriesEdCategory = dialogLayout.findViewById(R.id.alert_categories_ed_category);
        alertCategoriesBtnCategoryEdit = dialogLayout.findViewById(R.id.alert_categories_btn_category_add);
        builder.setView(dialogLayout);

        GetCategoryData();
        alertCategoriesBtnCategoryEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditCategory();
            }
        });

        builder.show();
    }

    private void GetCategoryData() {
        Glide.with(activity).load(categories_data.getPhotoUrl()).into(alertCategoriesImgCategory);
        alertCategoriesEdCategory.setText(categories_data.getName());

    }

    private void EditCategory() {
        String Category_name = alertCategoriesEdCategory.getText().toString();
        String api_token = SharedPreferencesManger.LoadData(activity, RESTURANT_API_TOKEN);
        ApiServices.OnUpdateCategory(convertToRequestBody(Category_name),
                convertToRequestBody(api_token), convertToRequestBody(String.valueOf(CategoryId)),
                convertFileToMultipart(ImagesFiles.get(0).getPath(), "photo"))
                .enqueue(new Callback<Categories>() {
                    @Override
                    public void onResponse(Call<Categories> call, Response<Categories> response) {
                        try {
                            if (response.body().getStatus() == 1) {
                                Toast.makeText(activity, "Done Editing Category !!", Toast.LENGTH_SHORT).show();
                                builder.setCancelable(true);
                                alertCategoriesEdCategory.setText("");
                                alertCategoriesImgCategory.setImageResource(R.drawable.ic_add_black);
                            }

                        } catch (Exception e) {
                            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Categories> call, Throwable t) {
                    }
                });
    }

    private void OpenGallary() {
        Action<ArrayList<AlbumFile>> action = new Action<ArrayList<AlbumFile>>() {

            @Override

            public void onAction(@NonNull ArrayList<AlbumFile> result) {
                // TODO accept the result.
                ImagesFiles.clear();
                ImagesFiles.addAll(result);
                onLoadImageFromUrl(alertCategoriesImgCategory, ImagesFiles.get(0).getPath(), activity);
            }
        };
        openAlbum(1, activity, ImagesFiles, action);
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View view;
        @BindView(R.id.Swipe_Edit)
        ImageView SwipeEdit;
        @BindView(R.id.Swipe_Delete)
        ImageView SwipeDelete;
        @BindView(R.id.List_categories_home_Image)
        PorterShapeImageView ListCategoriesHomeImage;
        @BindView(R.id.List_categories_order_home_name)
        TextView ListCategoriesOrderHomeName;
        @BindView(R.id.relative)
        RelativeLayout relative;

        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);
        }
    }
}
