package com.hyperdesign.alabbadauto.classes.vehicleImages;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 3/27/2018.
 */

public class Images {

    @SerializedName("id")
    private int id;

    @SerializedName("image")
    private  String imageUrl;

    public Images(){

    }

    public Images(int id, String imageUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
