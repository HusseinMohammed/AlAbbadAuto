package com.hyperdesign.alabbadauto.api.model;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.InsuranceCompanies;

/**
 * Created by Hyper Design Hussien on 3/21/2018.
 */

public class InsuranceCompanyResponse {

    @SerializedName("insurance")
    private InsuranceCompanies insuranceCompanies;

    public InsuranceCompanies getInsuranceCompany() {
        return insuranceCompanies;
    }

    public void setInsuranceCompany(InsuranceCompanies insuranceCompanies) {
        this.insuranceCompanies = insuranceCompanies;
    }

}
