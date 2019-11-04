
package com.example.mustafa.sofraNew.data.models.foodItem.addNewFoodItem;

import com.example.mustafa.sofraNew.data.models.foodItem.foodItems.FoodItemsData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewFoodItem {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private FoodItemsData data;

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

    public FoodItemsData getData() {
        return data;
    }

    public void setData(FoodItemsData data) {
        this.data = data;
    }

}
