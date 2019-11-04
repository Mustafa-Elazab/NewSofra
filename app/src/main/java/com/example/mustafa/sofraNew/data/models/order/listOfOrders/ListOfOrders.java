
package com.example.mustafa.sofraNew.data.models.order.listOfOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListOfOrders {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private ListOfOrdersPagination data;

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

    public ListOfOrdersPagination getData() {
        return data;
    }

    public void setData(ListOfOrdersPagination data) {
        this.data = data;
    }

}
