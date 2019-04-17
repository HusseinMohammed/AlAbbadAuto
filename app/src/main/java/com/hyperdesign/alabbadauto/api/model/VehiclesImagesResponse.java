package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Images;
import com.hyperdesign.alabbadauto.classes.Vehicles;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hyper Design Hussien on 2/24/2018.
 */

public class VehiclesImagesResponse {
    @SerializedName("images")
    private ArrayList<Images> imagesList;

    @SerializedName("category")
    private ArrayList<Vehicles> vehiclesList;

    public ArrayList<Images> getImagesList() {
        return imagesList;
    }

    public void setImagesList(ArrayList<Images> imagesList) {
        this.imagesList = imagesList;
    }

    public ArrayList<Vehicles> getVehiclesList() {
        return vehiclesList;
    }

    public void setVehiclesList(ArrayList<Vehicles> vehiclesList) {
        this.vehiclesList = vehiclesList;
    }
}
