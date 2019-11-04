package com.example.mustafa.sofraNew.data.reset;

import com.example.mustafa.sofraNew.data.models.categories.Categories;
import com.example.mustafa.sofraNew.data.models.client.clientData.Client;
import com.example.mustafa.sofraNew.data.models.clientnotifications.ClientNotifications;
import com.example.mustafa.sofraNew.data.models.foodItem.addNewFoodItem.AddNewFoodItem;
import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItems;
import com.example.mustafa.sofraNew.data.models.general.contactUs.ContactUs;
import com.example.mustafa.sofraNew.data.models.general.generalResponse.GeneralResponse;
import com.example.mustafa.sofraNew.data.models.general.publicResponse.PublicResponse;
import com.example.mustafa.sofraNew.data.models.general.resetPassword.ResetPassword;
import com.example.mustafa.sofraNew.data.models.offer.addNewOffer.AddNewOffer;
import com.example.mustafa.sofraNew.data.models.offer.offers.Offers;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.ListOfOrders;
import com.example.mustafa.sofraNew.data.models.order.newOrder.newOrders;
import com.example.mustafa.sofraNew.data.models.rest.restaurantCommissions.RestaurantCommissions;
import com.example.mustafa.sofraNew.data.models.rest.restaurantList.RestaurantsList;
import com.example.mustafa.sofraNew.data.models.rest.restaurantReview.RestaurantReviews;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.Restaurant;


import java.util.List;

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
    Call<Restaurant> onLoginResturant(@Field("email") String email,
                                      @Field("password") String password);

    @FormUrlEncoded
    @POST("restaurant/profile")
    Call<Restaurant> onResturantProfile(@Field("api_token") String api_token);

    @Multipart
    @POST("restaurant/profile")
    Call<Restaurant> onResturantEditProfile(@Part("email") RequestBody email,
                                            @Part("name") RequestBody name,
                                            @Part("phone") RequestBody phone,
                                            @Part("address") RequestBody address,
                                            @Part("region_id") RequestBody region_id,
                                            @Part("delivery_method_id") RequestBody delivery_method_id,
                                            @Part("delivery_cost") RequestBody delivery_cost,
                                            @Part("minimum_charger") RequestBody minimum_charger,
                                            @Part("availability") RequestBody availability,
                                            @Part MultipartBody.Part photo,
                                            @Part("api_token") RequestBody api_token);


    @POST("restaurant/new-item")
    @Multipart
    Call<AddNewFoodItem> onResturantNewItem(@Part("description") RequestBody description,
                                            @Part("price") RequestBody price,
                                            @Part("preparing_time") RequestBody preparing_time,
                                            @Part("name") RequestBody name,
                                            @Part("api_token") RequestBody api_token,
                                            @Part MultipartBody.Part photo,
                                            @Part("offer_price") RequestBody offer_price,
                                            @Part("category") RequestBody category);

    @GET("restaurant/commissions")
    Call<RestaurantCommissions> restaurantcommissions(
            @Query("api_token") String api_token
    );

    @FormUrlEncoded
    @POST("client/reset-password")
    Call<ResetPassword> onResetPasswordClient(@Field("email") String email);

    @FormUrlEncoded
    @POST("restaurant/reset-password")
    Call<ResetPassword> onResetPasswordResturant(@Field("email") String email);

    @FormUrlEncoded
    @POST("restaurant/new-password")
    Call<PublicResponse> onNewPasswordResturant(@Field("code") String code,
                                                @Field("password") String password,
                                                @Field("email") String password_confirmation);

    @FormUrlEncoded
    @POST("client/new-password")
    Call<PublicResponse> onNewPasswordClient(@Field("code") String code,
                                             @Field("password") String password,
                                             @Field("email") String password_confirmation);

    @GET("restaurant/my-items")
    Call<FoodItems> restaurantmyitems(@Query("api_token") String api_token,
                                      @Query("category_id") int category_id);

    @GET("restaurant/my-orders")
    Call<ListOfOrders> restaurantmyorder(@Query("api_token") String api_token,
                                         @Query("state") String state,
                                         @Query("page") int page);

    @GET("restaurant/my-offers")
    Call<Offers> restaurantmyoffers(@Query("api_token") String api_token,
                                    @Query("page") int page);

    @FormUrlEncoded
    @POST("restaurant/reject-order")
    Call<PublicResponse> onRestaurantRejectOrder(@Field("api_token") String api_token,
                                                 @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("restaurant/accept-order")
    Call<PublicResponse> onRestaurantAcceptOrder(@Field("api_token") String api_token,
                                                 @Field("order_id") int order_id);

    @FormUrlEncoded
    @POST("restaurant/confirm-order")
    Call<PublicResponse> onRestaurantConfirmOrder(@Field("api_token") String api_token,
                                                  @Field("order_id") int order_id);

    @POST("restaurant/new-offer")
    @Multipart
    Call<AddNewOffer> onRestaurantNewOffer(@Part("description") RequestBody description,
                                           @Part("price") RequestBody price,
                                           @Part("starting_at") RequestBody starting_at,
                                           @Part("name") RequestBody name,
                                           @Part MultipartBody.Part photo,
                                           @Part("ending_at") RequestBody ending_at,
                                           @Part("api_token") RequestBody api_token);

    @POST("restaurant/update-offer")
    @Multipart
    Call<AddNewOffer> onRestaurantEditOffer(@Part("description") RequestBody description,
                                            @Part("price") RequestBody price,
                                            @Part("starting_at") RequestBody starting_at,
                                            @Part("name") RequestBody name,
                                            @Part MultipartBody.Part photo,
                                            @Part("ending_at") RequestBody ending_at,
                                            @Part("api_token") RequestBody api_token,
                                            @Part("offer_price") RequestBody offer_price);

    @POST("restaurant/update-item")
    @Multipart
    Call<FoodItems> onResturantEditItem(@Part("description") RequestBody description,
                                        @Part("price") RequestBody price,
                                        @Part("preparing_time") RequestBody preparing_time,
                                        @Part("name") RequestBody name,
                                        @Part MultipartBody.Part photo,
                                        @Part("item_id") RequestBody item_id,
                                        @Part("api_token") RequestBody api_token);

    @GET("restaurants")
    Call<RestaurantsList> onRestaurant();

    @GET("items")
    Call<FoodItems> onItems(@Query("restaurant_id") int restaurant_id);

    @GET("items")
    Call<RestaurantReviews> onReviews(@Query("api_token") String api_token,
                                      @Query("restaurant_id") int restaurant_id);

    @GET("restaurant")
    Call<Restaurant> onInfo(@Query("restaurant_id") int restaurant_id);

    @GET("offers")
    Call<Offers> onOffers();


    @Multipart
    @POST("restaurant/sign-up")
    Call<Restaurant> onRegister(@Part("name") RequestBody name,
                                @Part("email") RequestBody email,
                                @Part("password") RequestBody password,
                                @Part("password_confirmation") RequestBody password_confirmation,
                                @Part("phone") RequestBody phone,
                                @Part("whatsapp") RequestBody whatsapp,
                                @Part("region_id") RequestBody region_id,
                                @Part("delivery_cost") RequestBody delivery_cost,
                                @Part("delivery_time") RequestBody delivery_time,
                                @Part("minimum_charger") RequestBody minimum_charger,
                                @Part MultipartBody.Part photo);

    @GET("cities-not-paginated")
    Call<GeneralResponse> getCities();

    @GET("regions-not-paginated")
    Call<GeneralResponse> getRegions(@Query("city_id") int city_id);

    @FormUrlEncoded
    @POST("contact")
    Call<ContactUs> onContact(@Field("name") String name,
                              @Field("email") String email,
                              @Field("phone") String phone,
                              @Field("type") String type,
                              @Field("content") String content);

    @GET("restaurants")
    Call<RestaurantsList> getFilter(@Query("keywork") String keywork,
                                    @Query("region_id") int region_id);

    @Multipart
    @POST("client/sign-up")
    Call<Client> onClientRegister(@Part("name") RequestBody name,
                                  @Part("email") RequestBody email,
                                  @Part("password") RequestBody password,
                                  @Part("password_confirmation") RequestBody password_confirmation,
                                  @Part("phone") RequestBody phone,
                                  @Part("address") RequestBody address,
                                  @Part("region_id") RequestBody region_id,
                                  @Part MultipartBody.Part photo);

    @GET("client/my-orders")
    Call<ListOfOrders> getClientOrder(@Query("api_token") String api_token,
                                      @Query("state") String state,
                                      @Query("page") int page);

    @FormUrlEncoded
    @POST("client/login")
    Call<Client> onClientLogin(@Field("email") String email,
                               @Field("password") String password);

    @FormUrlEncoded
    @POST("client/profile")
    Call<Client> onClientProfile(@Field("api_token") String api_token);

    @Multipart
    @POST("client/profile")
    Call<Client> onClientEditProfile(@Part("api_token") RequestBody api_token,
                                     @Part("name") RequestBody name,
                                     @Part("email") RequestBody email,
                                     @Part("phone") RequestBody phone,
                                     @Part("address") RequestBody address,
                                     @Part("region_id") RequestBody region_id,
                                     @Part MultipartBody.Part photo);


    @GET("categories")
    Call<Categories> OnCategories(@Query("restaurant_id") int restaurant_id);

    @FormUrlEncoded
    @POST("client/decline-order")
    Call<PublicResponse> onClientConfirmOrder(@Field("order_id") int order_id,
                                              @Field("api_token") String api_token);

    @FormUrlEncoded
    @POST("test-notification")
    Call<PublicResponse> onNotification(@Field("title") int title,
                                        @Field("token") String token,
                                        @Field("body") String body);

    @POST("client/new-order")
    @FormUrlEncoded
    Call<newOrders> newOrder(@Field("api_token") String api_token,
                             @Field("restaurant_id") int restaurant_id,
                             @Field("note") String note,
                             @Field("address") String address,
                             @Field("payment_method_id") int payment_method_id,
                             @Field("phone") String phone,
                             @Field("name") String name,
                             @Field("items[]") List<Integer> items,
                             @Field("quantities[]") List<Integer> quantities,
                             @Field("notes[]") List<String> notes);


    @POST("client/restaurant/review")
    @FormUrlEncoded
    Call<RestaurantReviews> AddReview(@Field("rate") float rate,
                                      @Field("comment") String comment,
                                      @Field("restaurant_id") int restaurant_id,
                                      @Field("api_token") String api_token);

    @POST("client/register-token")
    @FormUrlEncoded
    Call<PublicResponse> RegisterToken(@Field("token") String token
            , @Field("api_token") String api_token, @Field("type") String type);

    @POST("client/remove-token")
    @FormUrlEncoded
    Call<PublicResponse> RemoveToken(@Field("token") String token
            , @Field("api_token") String api_token);


    @GET("client/notifications")
    Call<ClientNotifications> getClientNotifications(@Query("api_token") String api_token, @Query("page") int page);

    @GET("restaurant/notifications")
    Call<ClientNotifications> getResturantNotifications(@Query("api_token") String api_token, @Query("page") int page);

    @Multipart
    @POST("restaurant/new-category")
    Call<Categories> OnAddNewCategory(@Part("name") RequestBody name,
                                      @Part("api_token") RequestBody api_token,
                                      @Part MultipartBody.Part photo);

    @Multipart
    @POST("restaurant/update-category")
    Call<Categories> OnUpdateCategory(@Part("name") RequestBody name,
                                      @Part("api_token") RequestBody api_token,
                                      @Part("category_id") RequestBody category_id,
                                      @Part MultipartBody.Part photo);

    @GET("restaurant/my-categories")
    Call<Categories> getResturantCategory(@Query("api_token") String api_token);

    @GET("restaurant/delete-category")
    Call<PublicResponse> OnDeleteCategories(@Query("api_token") String api_token,
                                            @Query("category_id") int category_id);

    @POST("client/change-password")
    @FormUrlEncoded
    Call<PublicResponse> onClientChangePassword(@Field("old_password") String old_password,
                                                @Field("password") String password,
                                                @Field("password_confirmation") String password_confirmation,
                                                @Field("api_token") String api_token);

    @POST("restaurant/change-password")
    @FormUrlEncoded
    Call<PublicResponse> onRestaurantChangePassword(@Field("old_password") String old_password,
                                                @Field("password") String password,
                                                @Field("password_confirmation") String password_confirmation,
                                                @Field("api_token") String api_token);
}


