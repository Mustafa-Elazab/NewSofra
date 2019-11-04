package com.example.mustafa.sofraNew.data.models.foodItem.foodItems;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.example.mustafa.sofraNew.data.models.client.clientData.ClientData;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.Pivot;
import com.example.mustafa.sofraNew.data.models.rest.restaurantsData.RestaurantData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "FoodItemsData")
public class FoodItemsData {

    @PrimaryKey(autoGenerate = true)
    private int itemId;

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("offer_price")
    @Expose
    private String offerPrice;
    @SerializedName("preparing_time")
    @Expose
    private String preparingTime;
    @SerializedName("photo")
    @Expose
    private String photo;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("photo_url")
    @Expose
    private String photoUrl;
    @SerializedName("has_offer")
    @Expose
    private String hasOffer;
    @SerializedName("category")
    @Expose
    private String category;
//    @SerializedName("restaurant")
//    @Expose
//    private RestaurantData restaurant;
//    @SerializedName("client")
//    @Expose
//    private ClientData client;
//

    private String note;
    private String count;

    public FoodItemsData() {

    }

    public FoodItemsData(Integer id, String createdAt, String updatedAt, String name, String description, String price, String offerPrice, String preparingTime, String photo, String restaurantId,
                         String photoUrl, String hasOffer, String category, String note, String count,RestaurantData restaurant, ClientData client) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.name = name;
        this.description = description;
        this.price = price;
        this.offerPrice = offerPrice;
        this.preparingTime = preparingTime;
        this.photo = photo;
        this.restaurantId = restaurantId;
        this.photoUrl = photoUrl;
        this.hasOffer = hasOffer;
        this.category = category;
        this.note = note;
        this.count = count;
//        this.restaurant = restaurant;
//        this.client = client;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(String offerPrice) {
        this.offerPrice = offerPrice;
    }

    public String getPreparingTime() {
        return preparingTime;
    }

    public void setPreparingTime(String preparingTime) {
        this.preparingTime = preparingTime;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getHasOffer() {
        return hasOffer;
    }

    public void setHasOffer(String hasOffer) {
        this.hasOffer = hasOffer;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

//    public RestaurantData getRestaurant() {
//        return restaurant;
//    }
//
//    public void setRestaurant(RestaurantData restaurant) {
//        this.restaurant = restaurant;
//    }
//
//    public ClientData getClient() {
//        return client;
//    }
//
//    public void setClient(ClientData client) {
//        this.client = client;
//    }
}
