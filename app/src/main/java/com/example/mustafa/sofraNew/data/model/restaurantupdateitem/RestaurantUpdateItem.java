
package com.example.mustafa.sofraNew.data.model.restaurantupdateitem;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantUpdateItem {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Restaurant_Updat_Item_Data data;

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

    public Restaurant_Updat_Item_Data getData() {
        return data;
    }

    public void setData(Restaurant_Updat_Item_Data data) {
        this.data = data;
    }

}
