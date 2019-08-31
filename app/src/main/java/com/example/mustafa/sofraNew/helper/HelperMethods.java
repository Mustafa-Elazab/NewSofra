package com.example.mustafa.sofraNew.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;

import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import com.yanzhenjie.album.Action;
import com.yanzhenjie.album.Album;
import com.yanzhenjie.album.AlbumConfig;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.api.widget.Widget;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class HelperMethods {
    private static ProgressDialog checkDialog;

    public static void replace(Fragment fragment, FragmentManager supportFragmentManager, int id, TextView tool_bar_title,
                               String title) {


        FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
        beginTransaction.replace(id, fragment);
        beginTransaction.addToBackStack(null);

        if (tool_bar_title != null) {
            tool_bar_title.setText(title);
        }
        beginTransaction.commit();
    }

    public static void onLoadImageFromUrl(ImageView imageView, String URl, Context context) {

        Glide.with(context)

                .load(URl)

                .into(imageView);

    }

    public static void setInitRecyclerViewAsLinearLayoutManager(Context context, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager) {

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(linearLayoutManager);

    }

    public static void setInitRecyclerViewAsGridLayoutManager(Activity activity, RecyclerView recyclerView, GridLayoutManager gridLayoutManager, int numberOfColumns) {

        gridLayoutManager = new GridLayoutManager(activity, numberOfColumns);

        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(gridLayoutManager);

    }

    public static void disappearKeypad(Activity activity, View v) {

        try {

            if (v != null) {

                InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

            }

        } catch (Exception e) {
        }

    }

    public static void showProgressDialog(Activity activity, String title) {

        try {

            if (checkDialog == null) {

                checkDialog = new ProgressDialog(activity);

                checkDialog.setMessage(title);

                checkDialog.setIndeterminate(false);

                checkDialog.setCancelable(false);
            }

            checkDialog.show();
        } catch (Exception e) {
        }

    }

    public static void dismissProgressDialog() {

        try {

            if (checkDialog != null && checkDialog.isShowing()) {

                checkDialog.dismiss();

            }

        } catch (Exception e) {
        }

    }

    public static void dateDialog(final TextView textView, Context context) {
        Calendar c;
        int day, month, year;
        c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        DatePickerDialog datepickerdialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker datePicker, int mDay, int mMonth, int mYear) {
                        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.US);

                        DecimalFormat mFormat = new DecimalFormat("00", symbols);

                        final String data = mDay + "-" + String.format(new Locale("en"), mFormat.format(Double.valueOf((mMonth + 1)))) + "-"
                                + mYear;
                        textView.setText(data);
                    }

                }, day, month, year);

        datepickerdialog.show();

    }


    public static MultipartBody.Part convertFileToMultipart(String pathImageFile, String Key) {

        if (pathImageFile != null) {

            File file = new File(pathImageFile);
            RequestBody reqFileselect = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part Imagebody = MultipartBody.Part.createFormData(Key, file.getName(), reqFileselect);
            return Imagebody;

        } else {

            return null;

        }

    }

    public static RequestBody convertToRequestBody(String part) {
        try {
            if (!part.equals("")) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), part);
                return requestBody;
            } else {

                return null;

            }
        } catch (Exception e) {
            return null;
        }
    }

    public static void openAlbum(int Counter, Context context, final ArrayList<AlbumFile> ImagesFiles, Action<ArrayList<AlbumFile>> action) {

        Album album = new Album();
        Album.initialize(AlbumConfig.newBuilder(context)
                .setAlbumLoader(new MediaLoader())
                .setLocale(Locale.ENGLISH).build());

        album.image(context)// Image and video mix options.
                .multipleChoice()// Multi-Mode, Single-Mode: singleChoice().
                .columnCount(3) // The number of columns in the page list.
                .selectCount(Counter)  // Choose up to a few images.
                .camera(true) // Whether the camera appears in the Item.
                .checkedList(ImagesFiles) // To reverse the list.
                .widget(
                        Widget.newLightBuilder(context)
                                .title("")
                                .statusBarColor(Color.WHITE) // StatusBar color.
                                .toolBarColor(Color.WHITE) // Toolbar color.
                                .navigationBarColor(Color.WHITE) // Virtual NavigationBar color of Android5.0+.
                                .mediaItemCheckSelector(Color.BLUE, Color.GREEN) // Image or video selection box.
                                .bucketItemCheckSelector(Color.RED, Color.YELLOW) // Select the folder selection box.
                                .build()
                )

                .onResult(action)
                .onCancel(new Action<String>() {
                    @Override
                    public void onAction(@NonNull String result) {

// The user canceled the operation.
                    }
                })
                .start();


    }

    private static class MediaLoader implements AlbumLoader {
        @Override
        public void load(ImageView imageView, AlbumFile albumFile) {
            load(imageView, albumFile.getPath());
        }

        @Override
        public void load(ImageView imageView, String url) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .into(imageView);
        }
    }
}