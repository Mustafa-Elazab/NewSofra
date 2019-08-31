
package com.example.mustafa.sofraNew.data.model.offers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Offers {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private Offers_Data data;

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

    public Offers_Data getData() {
        return data;
    }

    public void setData(Offers_Data data) {
        this.data = data;
    }

}
