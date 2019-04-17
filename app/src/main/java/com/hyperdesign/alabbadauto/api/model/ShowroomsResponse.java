package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Showrooms;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 3/18/2018.
 */

public class ShowroomsResponse {
    @SerializedName("dealers")
    private ArrayList<Showrooms> showroomsList;

    public void setShowroomsList(ArrayList<Showrooms> showroomsList) {
        this.showroomsList = showroomsList;
    }

    public ArrayList<Showrooms> getShowroomsList() {
        return showroomsList;
    }
}
