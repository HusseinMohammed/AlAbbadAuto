package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.location.Area;
import com.hyperdesign.alabbadauto.classes.location.Country;
import com.hyperdesign.alabbadauto.classes.location.Region;

/**
 * Created by Hyper Design Hussien on 2/11/2018.
 */

public class InsuranceCompanies {
    @SerializedName("id")
    private int id;

    @SerializedName("logo")
    public String imageUrlLogo;

    @SerializedName("name_en")
    public String textNameEn;

    @SerializedName("name_ar")
    public String textNameAr;

    @SerializedName("address_en")
    public String textAddressEn;

    @SerializedName("address_ar")
    public String textAddressAr;

    @SerializedName("about_en")
    private String infoEn;

    @SerializedName("about_ar")
    private String infoAr;

    public String textInfo;

    public String about;
    public String address;

    @SerializedName("country")
    public Country country;

    private String countryNameEn;
    private String countryNameAr;
    public String city;

    @SerializedName("region")
    public Region region;
    private String regionNameEn;
    private String regionNameAr;

    @SerializedName("area")
    public Area area;

    private String areaNameEn;
    private String areaNameAr;

    @SerializedName("work_times")
    public String workTimes;

    @SerializedName("lat")
    public double latitude;

    @SerializedName("long")
    public double longitude;

    public InsuranceCompanies() {

    }

    public InsuranceCompanies(int id, String imageUrlLogo, String textName, String textAddress, String textInfo) {
        this.id = id;
        this.imageUrlLogo = imageUrlLogo;
        this.textNameEn = textName;
        this.textAddressEn = textAddress;
        this.infoEn = textInfo;
    }

    public InsuranceCompanies(String imageUrlLogo, String textName, String textAddress, String textInfo, int id) {
        this.id = id;
        this.imageUrlLogo = imageUrlLogo;
        this.textNameAr = textName;
        this.textAddressAr = textAddress;
        this.infoAr = textInfo;
    }


    public InsuranceCompanies(int id, String imageUrlLogo, String textNameEn, String textAddressEn, String infoEn, String countryNameEn, String regionNameEn, String areaNameEn, String workTimes, double latitude, double longitude) {
        this.id = id;
        this.imageUrlLogo = imageUrlLogo;
        this.textNameEn = textNameEn;
        this.textAddressEn = textAddressEn;
        this.infoEn = infoEn;
        this.countryNameEn = countryNameEn;
        this.regionNameEn = regionNameEn;
        this.areaNameEn = areaNameEn;
        this.workTimes = workTimes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public InsuranceCompanies(String imageUrlLogo, String textNameAr, String textAddressAr, String infoAr, String countryNameAr, String regionNameAr, String areaNameAr, String workTimes, double latitude, double longitude, int id) {
        this.imageUrlLogo = imageUrlLogo;
        this.textNameAr = textNameAr;
        this.textAddressAr = textAddressAr;
        this.infoAr = infoAr;
        this.countryNameAr = countryNameAr;
        this.regionNameAr = regionNameAr;
        this.areaNameAr = areaNameAr;
        this.workTimes = workTimes;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public InsuranceCompanies(String imageUrlLogo, String textName, String textAddress, String textInfo, String about, String address, Country country, String city, Region region, Area area, String workTimes, double latitude, double longitude) {
        this.imageUrlLogo = imageUrlLogo;
        this.textNameEn = textName;
        this.textAddressEn = textAddress;
        this.textInfo = textInfo;
        this.about = about;
        this.address = address;
        this.country = country;
        this.city = city;
        this.region = region;
        this.area = area;
        this.workTimes = workTimes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getId() {
        return id;
    }

    public String getImageUrlLogo() {
        return imageUrlLogo;
    }

    public String getTextNameEn() {
        return textNameEn;
    }

    public String getTextAddressEn() {
        return textAddressEn    ;
    }

    public String getInfoEn() {
        return infoEn;
    }

    public String getTextNameAr() {
        return textNameAr;
    }

    public String getTextAddressAr() {
        return textAddressAr;
    }

    public String getInfoAr() {
        return infoAr;
    }

    public String getAbout() {
        return about;
    }

    public String getAddress() {
        return address;
    }

    public Country getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public Region getRegion() {
        return region;
    }

    public Area getArea() {
        return area;
    }

    public String getWorkTimes() {
        return workTimes;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
