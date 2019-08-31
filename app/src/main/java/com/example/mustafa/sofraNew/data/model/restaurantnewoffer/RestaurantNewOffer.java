
package com.example.mustafa.sofraNew.data.model.restaurantnewoffer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantNewOffer {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Resturant_Newoffer_Data data;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Resturant_Newoffer_Data getData() {
        return data;
    }

    public void setData(Resturant_Newoffer_Data data) {
        this.data = data;
    }

}
