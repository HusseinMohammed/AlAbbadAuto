package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;

public class SubmitBuyCardResponse {

    @SerializedName("done")
    private String done;
    @SerializedName("msg")
    private String message;

    public String getDone() {
        return done;
    }

    public void setDone(String done) {
        this.done = done;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
