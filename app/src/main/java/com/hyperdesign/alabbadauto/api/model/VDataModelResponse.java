package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.VehiclesData;

import java.util.ArrayList;

public class VDataModelResponse {
    @SerializedName("items")
    private ArrayList<VehiclesData> vehiclesDataModel;

    public ArrayList<VehiclesData> getVehiclesDataModel() {
        return vehiclesDataModel;
    }

    public void setVehiclesDataModel(ArrayList<VehiclesData> vehiclesDataModel) {
        this.vehiclesDataModel = vehiclesDataModel;
    }
}
