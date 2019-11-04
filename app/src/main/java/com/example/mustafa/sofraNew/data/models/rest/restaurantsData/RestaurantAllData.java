
package com.example.mustafa.sofraNew.data.models.rest.restaurantsData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantAllData {
    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private RestaurantData restaurant;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public RestaurantData getData() {
        return restaurant;
    }

    public void setrestaurantData(RestaurantData restaurantData) {
        this.restaurant = restaurantData;
    }

}
