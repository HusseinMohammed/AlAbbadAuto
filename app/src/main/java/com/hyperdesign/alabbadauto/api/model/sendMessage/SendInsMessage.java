package com.hyperdesign.alabbadauto.api.model.sendMessage;

import com.google.gson.annotations.SerializedName;

public class SendInsMessage {

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
