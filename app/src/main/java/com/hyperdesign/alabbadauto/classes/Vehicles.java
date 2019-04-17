package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Hyper Design Hussien on 2/3/2018.
 */

public class Vehicles {
    @SerializedName("id")
    public int vehicleId;

    @SerializedName("name_en")
    public String nameVehicleEng;

    @SerializedName("name_ar")
    public String nameVehicleArab;

    @SerializedName("image")
    public String imageUrlVehicles;

    public Vehicles() {
    }

    public Vehicles(int vehicleId, String nameVehicleEng, String nameVehicleArab, String imageUrlVehicles) {
        this.vehicleId = vehicleId;
        this.nameVehicleEng = nameVehicleEng;
        this.nameVehicleArab = nameVehicleArab;
        this.imageUrlVehicles = imageUrlVehicles;
    }

    public Vehicles(String nameVehicleEng, String nameVehicleArab, String imageUrlVehicles) {
        this.nameVehicleEng = nameVehicleEng;
        this.imageUrlVehicles = imageUrlVehicles;
        this.nameVehicleArab = nameVehicleArab;
    }

    public Vehicles(int vehicleId, String nameVehicleEng) {
        this.vehicleId= vehicleId;
        this.nameVehicleEng = nameVehicleEng;
    }

    public void setImageUrlVehicles(String imageUrlVehicles) {
        this.imageUrlVehicles = imageUrlVehicles;
    }

    public String getImageUrlVehicles() {
        return imageUrlVehicles;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public String getNameVehicleEng() {
        return nameVehicleEng;
    }

    public String getNameVehicleArab() {
        return nameVehicleArab;
    }

    public void setNameVehicleEng(String nameVehicleEng) {
        this.nameVehicleEng = nameVehicleEng;
    }

    public void setNameVehicleArab(String nameVehicleArab) {
        this.nameVehicleArab = nameVehicleArab;
    }
}
