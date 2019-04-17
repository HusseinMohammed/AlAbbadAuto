package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.Model;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 2/25/2018.
 */

public class ModelsResponse {
    @SerializedName("models")
    private ArrayList<Model> modelsList;

    public void setModelsList(ArrayList<Model> modelsList) {
        this.modelsList = modelsList;
    }

    public ArrayList<Model> getModelsList() {
        return modelsList;
    }
}
