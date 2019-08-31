package com.example.mustafa.sofraNew.data.reset;


import com.example.mustafa.sofraNew.R;
import com.example.mustafa.sofraNew.data.model.cities.Cities;

import com.example.mustafa.sofraNew.data.model.clientlogin.ClientLogin;
import com.example.mustafa.sofraNew.data.model.clientsignup.ClientSignUp;
import com.example.mustafa.sofraNew.data.model.contact.Contact;
import com.example.mustafa.sofraNew.data.model.items.Items;
import com.example.mustafa.sofraNew.data.model.listoforders.ListOfOrders;
import com.example.mustafa.sofraNew.data.model.login.Login;
import com.example.mustafa.sofraNew.data.model.offers.Offers;
import com.example.mustafa.sofraNew.data.model.regions.Regions;
import com.example.mustafa.sofraNew.data.model.restaurantcommissions.RestaurantCommissions;
import com.example.mustafa.sofraNew.data.model.restaurantinfo.RestaurantInfo;
import com.example.mustafa.sofraNew.data.model.restaurantlogin.RestaurantLogin;
import com.example.mustafa.sofraNew.data.model.restaurantmyitems.RestaurantMyItems;
import com.example.mustafa.sofraNew.data.model.restaurantmyoffers.RestaurantMyOffers;
import com.example.mustafa.sofraNew.data.model.restaurantmyorders.RestaurantMyOrders;
import com.example.mustafa.sofraNew.data.model.restaurantnewitem.RestaurantNewItem;
import com.example.mustafa.sofraNew.data.model.restaurantnewoffer.RestaurantNewOffer;
import com.example.mustafa.sofraNew.data.model.restaurantnewpassword.RestaurantNewPassword;
import com.example.mustafa.sofraNew.data.model.restaurantprofile.RestaurantProfile;
import com.example.mustafa.sofraNew.data.model.restaurantregister.RestaurantRegister;
import com.example.mustafa.sofraNew.data.model.restaurantrejectorder.RestaurantRejectOrder;
import com.example.mustafa.sofraNew.data.model.restaurantresetpassword.RestaurantResetPassword;
import com.example.mustafa.sofraNew.data.model.restaurantreviews.RestaurantReviews;
import com.example.mustafa.sofraNew.data.model.restaurants.Restaurants;
import com.example.mustafa.sofraNew.data.model.restaurantsfilter.RestaurantsFilter;
import com.example.mustafa.sofraNew.data.model.restaurantupdateitem.RestaurantUpdateItem;
import com.example.mustafa.sofraNew.data.model.restaurantupdateoffer.RestaurantUpdateOffer;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface API {


    @FormUrlEncoded
    @POST("restaurant/login")
    Call<RestaurantLogin> onLoginResturant(@Field("email") String email,
                                           @Field("password") String password);


    @FormUrlEncoded
    @POST("restaurant/profile")
    Call<RestaurantProfile> onProfileResturant(

            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody password_confirmation,
            @Part("name") RequestBody name,
            @Part("phone") RequestBody phone,
            @Part("address") RequestBody address,
            @Part("region_id") RequestBody region_id,
            @Part("delivery_method_id") RequestBody delivery_method_id,
            @Part("delivery_cost") RequestBody delivery_cost,
            @Part("minimum_charger") RequestBody minimum_charger,
            @Part("availability") RequestBody availability,
            @Part MultipartBody.Part photo,
            @Part("api_token") RequestBody api_token

    );


    @POST("restaurant/new-item")
    @Multipart
    Call<RestaurantNewItem> onResturantNewItem(@Part("description") RequestBody description,
                                               @Part("price") RequestBody price,
                                               @Part("preparing_time") RequestBody preparing_time,
                                               @Part("name") RequestBody name,
                                               @Part("api_token") RequestBody api_token,
                                               @Part MultipartBody.Part photo
    );

    @GET("restaurant/commissions")
    Call<RestaurantCommissions> restaurantcommissions(
            @Query("api_token") String api_token
    );

    @FormUrlEncoded
    @POST("restaurant/reset-password")
    Call<RestaurantResetPassword> onResetPasswordResturant(

            @Field("email") String email);


    @FormUrlEncoded
    @POST("restaurant/new-password")
    Call<RestaurantNewPassword> onNewPasswordResturant(
            @Field("code") String code,
            @Field("password") String password,
            @Field("email") String password_confirmation
    );

    @GET("restaurant/my-items")
    Call<RestaurantMyItems> restaurantmyitems(
            @Query("api_token") String api_token,
            @Query("page") int page
    );

    @GET("restaurant/my-orders")
    Call<RestaurantMyOrders> restaurantmyorder(
            @Query("api_token") String api_token,
            @Query("state") String state,
            @Query("page") int page
    );

    @GET("restaurant/my-offers")
    Call<RestaurantMyOffers> restaurantmyoffers(
            @Query("api_token") String api_token,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST("restaurant/reject-order")
    Call<RestaurantRejectOrder> onRestaurantRejectOrder(@Field("api_token") String api_token,
                                                        @Field("order_id") int order_id);

    @POST("restaurant/new-offer")
    @Multipart
    Call<RestaurantNewOffer> onRestaurantNewOffer(@Part("description") RequestBody description,
                                                  @Part("price") RequestBody price,
                                                  @Part("starting_at") RequestBody starting_at,
                                                  @Part("name") RequestBody name,
                                                  @Part MultipartBody.Part photo,
                                                  @Part("ending_at") RequestBody ending_at,
                                                  @Part("api_token") RequestBody api_token,
                                                  @Part("offer_price") RequestBody offer_price
    );

    @POST("restaurant/update-offer")
    @Multipart
    Call<RestaurantUpdateOffer> onRestaurantEditOffer(@Part("description") RequestBody description,
                                                      @Part("price") RequestBody price,
                                                      @Part("starting_at") RequestBody starting_at,
                                                      @Part("name") RequestBody name,
                                                      @Part MultipartBody.Part photo,
                                                      @Part("ending_at") RequestBody ending_at,
                                                      @Part("api_token") RequestBody api_token,
                                                      @Part("offer_price") RequestBody offer_price
    );

    @POST("restaurant/update-item")
    @Multipart
    Call<RestaurantUpdateItem> onResturantEditItem(@Part("description") RequestBody description,
                                                   @Part("price") RequestBody price,
                                                   @Part("preparing_time") RequestBody preparing_time,
                                                   @Part("name") RequestBody name,
                                                   @Part MultipartBody.Part photo,
                                                   @Part("item_id") RequestBody item_id,
                                                   @Part("api_token") RequestBody api_token
    );

    @GET("restaurants")
    Call<Restaurants> onRestaurant(

    );

    @GET("items")
    Call<Items> onItems(
            @Query("restaurant_id") int restaurant_id
    );

    @GET("items")
    Call<RestaurantReviews> onReviews(
            @Query("api_token") String api_token,
            @Query("restaurant_id") int restaurant_id

    );

    @GET("restaurant")
    Call<RestaurantInfo> onInfo(
            @Query("restaurant_id") int restaurant_id
    );

    @GET("offers")
    Call<Offers> onOffers(
            @Query("restaurant_id") int restaurant_id
    );


    @Multipart
    @POST("restaurant/register")
    Call<RestaurantRegister> onRegister(

            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody password_confirmation,
            @Part("phone") RequestBody phone,
            @Part("whatsapp") RequestBody whatsapp,
            @Part("region_id") RequestBody region_id,
            @Part("delivery_cost") RequestBody delivery_method_id,
            @Part("minimum_charger") RequestBody minimum_charger,
            @Part MultipartBody.Part photo

    );

    @GET("cities")
    Call<Cities> getCities(
    );

    @GET("regions")
    Call<Regions> getRegions(
            @Query("city_id") int city_id
    );

    @FormUrlEncoded
    @POST("contact")
    Call<Contact> onContact(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("type") String type,
            @Field("content") String content
    );

    @GET("restaurants")
    Call<RestaurantsFilter> getFilter(
            @Query("keywork") String keywork,
            @Query("region_id") int region_id
    );

    @Multipart
    @POST("client/sign-up")
    Call<ClientSignUp> onClientRegister(

            @Part("name") RequestBody name,
            @Part("email") RequestBody email,
            @Part("password") RequestBody password,
            @Part("password_confirmation") RequestBody password_confirmation,
            @Part("phone") RequestBody phone,
            @Part("address") RequestBody address,
            @Part("region_id") RequestBody region_id,
            @Part MultipartBody.Part photo

    );
    @GET("client/my-orders")
    Call<ListOfOrders> getClientOrder(
            @Query("api_token") String api_token,
            @Query("state") String state,
            @Query("page") int page
    );

    @FormUrlEncoded
    @POST("client/login")
    Call<ClientLogin> onClientLogin(
            @Field("email") String email,
            @Field("password") String password
    );
}


