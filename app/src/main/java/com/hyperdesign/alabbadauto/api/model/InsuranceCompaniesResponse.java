package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.InsuranceCompanies;

import java.util.ArrayList;

/**
 * Created by Hyper Design Hussien on 3/18/2018.
 */

public class InsuranceCompaniesResponse {

    @SerializedName("insurances")
    private ArrayList<InsuranceCompanies> insuranceCompaniesList;

    public void setInsuranceCompaniesList(ArrayList<InsuranceCompanies> insuranceCompaniesList) {
        this.insuranceCompaniesList = insuranceCompaniesList;
    }

    public ArrayList<InsuranceCompanies> getInsuranceCompaniesList() {
        return insuranceCompaniesList;
    }
}
