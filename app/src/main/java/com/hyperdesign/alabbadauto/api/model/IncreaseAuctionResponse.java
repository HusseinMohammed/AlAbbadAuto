package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;

public class IncreaseAuctionResponse {

    @SerializedName("msg")
    private String message;

    @SerializedName("total")
    private String totalAuction;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTotalAuction() {
        return totalAuction;
    }

    public void setTotalAuction(String totalAuction) {
        this.totalAuction = totalAuction;
    }
}
