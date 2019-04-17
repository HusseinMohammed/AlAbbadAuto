package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Showrooms;
import com.hyperdesign.alabbadauto.classes.VehiclesData;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 3/25/2018.
 */

public class ShowDataResponse {
    @SerializedName("dealer")
    private Showrooms showroomData;

    @SerializedName("items")
    private ArrayList<VehiclesData> vehiclesDataList;

    public void setShowroomData(Showrooms showroomData) {
        this.showroomData = showroomData;
    }

    public Showrooms getShowroomData() {
        return showroomData;
    }

    public ArrayList<VehiclesData> getVehiclesDataList() {
        return vehiclesDataList;
    }

    public void setVehiclesDataList(ArrayList<VehiclesData> vehiclesDataList) {
        this.vehiclesDataList = vehiclesDataList;
    }

}
