package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;

public class TenderMessageResponse {
    @SerializedName("msg")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
