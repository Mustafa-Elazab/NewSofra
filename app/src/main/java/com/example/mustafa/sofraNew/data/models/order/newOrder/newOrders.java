
package com.example.mustafa.sofraNew.data.models.order.newOrder;

import com.example.mustafa.sofraNew.data.models.order.listOfOrders.ListOfOrdersPagination;
import com.example.mustafa.sofraNew.data.models.order.listOfOrders.OrdersData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class newOrders {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private OrdersData data;

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

    public OrdersData getData() {
        return data;
    }

    public void setData(OrdersData data) {
        this.data = data;
    }

}
