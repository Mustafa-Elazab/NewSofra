
package com.example.mustafa.sofraNew.data.models.offer.addNewOffer;
import com.example.mustafa.sofraNew.data.models.offer.offers.OffersData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddNewOffer {

    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("msg")
    @Expose
    private String msg;
    @SerializedName("data")
    @Expose
    private OffersData data;

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

    public OffersData getData() {
        return data;
    }

    public void setData(OffersData data) {
        this.data = data;
    }

}
