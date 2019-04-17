package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.VehiclesData;

import java.util.ArrayList;

/**
 * Created by dell on 17/03/2018.
 */

public class VehiclesDataResponse {
    @SerializedName("item")
    private ArrayList<VehiclesData> vehiclesDataResponseList;

    public ArrayList<VehiclesData> getVehiclesDataResponseList() {
        return vehiclesDataResponseList;
    }

    public void setVehiclesDataResponseList(ArrayList<VehiclesData> vehiclesDataResponseList) {
        this.vehiclesDataResponseList = vehiclesDataResponseList;
    }
}
