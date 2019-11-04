
package com.example.mustafa.sofraNew.data.models.client.clientData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ClientAllData {

    @SerializedName("api_token")
    @Expose
    private String apiToken;
    @SerializedName("user")
    @Expose
    private ClientData user;

    public String getApiToken() {
        return apiToken;
    }

    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public ClientData getUser() {
        return user;
    }

    public void setUser(ClientData user) {
        this.user = user;
    }

}
