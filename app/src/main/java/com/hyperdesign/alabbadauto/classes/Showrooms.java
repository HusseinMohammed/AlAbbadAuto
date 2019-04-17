package com.hyperdesign.alabbadauto.classes;

import com.google.gson.annotations.SerializedName;
import com.hyperdesign.alabbadauto.classes.location.Area;
import com.hyperdesign.alabbadauto.classes.location.Country;
import com.hyperdesign.alabbadauto.classes.location.Region;

/**
 * Created by Hyper Design Hussein on 2/14/2018.
 */

public class Showrooms {

    @SerializedName("id")
    public int id;

    @SerializedName("name_en")
    public String nameEng;

    @SerializedName("name_ar")
    public String nameArab;

    @SerializedName("logo")
    public String logo;

    @SerializedName("phone")
    public String phone;

    @SerializedName("address_en")
    public String addressEn;

    @SerializedName("address_ar")
    public String addressAr;

    @SerializedName("about_en")
    public String aboutEn;

    @SerializedName("about_ar")
    public String aboutAr;

    public String about;

    public String address;
    public String city;

    @SerializedName("work_times")
    public String workTimes;

    @SerializedName("country")
    public Country country;

    @SerializedName("region")
    public Region region;

    @SerializedName("area")
    public Area area;

    public double latitude;
    public double longitude;

    public int showroomCars;

    public int logoDrawable;

    public Showrooms(){

    }

    public Showrooms(int id, String nameEng, String nameArab, int showroomCars, String logo) {
        this.id = id;
        this.nameEng = nameEng;
        this.nameArab = nameArab;
        this.showroomCars = showroomCars;
        this.logo = logo;
    }

    public Showrooms(String logo, String nameEng, int showroomCars) {
        this.logo = logo;
        this.nameEng = nameEng;
        this.showroomCars = showroomCars;
    }

    public Showrooms(String nameEng, String logo, String phone, String address) {
        this.nameEng = nameEng;
        this.logo = logo;
        this.phone = phone;
        this.address = address;
    }


    public Showrooms(int id, String nameEng, String logo,
                     String phone, String addressEn, String aboutEn,
                     String workTimes, Country country, Region region,
                     Area area, double latitude, double longitude) {
        this.id = id;
        this.nameEng = nameEng;
        this.logo = logo;
        this.phone = phone;
        this.addressEn = addressEn;
        this.aboutEn = aboutEn;
        this.workTimes = workTimes;
        this.country = country;
        this.region = region;
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Showrooms(String nameArab, String logo, String phone,
                     String addressAr, String aboutAr, String workTimes,
                     Country country, Region region, Area area,
                     double latitude, double longitude, int id) {
        this.nameArab = nameArab;
        this.logo = logo;
        this.phone = phone;
        this.addressAr = addressAr;
        this.aboutAr = aboutAr;
        this.workTimes = workTimes;
        this.country = country;
        this.region = region;
        this.area = area;
        this.latitude = latitude;
        this.longitude = longitude;
        this.id = id;
    }

    public Showrooms(String nameEng, String logo, String phone, String about, String address, Country country, Region region, Area area, String workTimes, double latitude, double longitude) {
        this.nameEng = nameEng;
        this.logo = logo;
        this.phone = phone;
        this.about = about;
        this.address = address;
        this.country = country;
        this.region = region;
        this.area = area;
        this.workTimes = workTimes;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLogo() {
        return logo;
    }

    public int getShowroomCars() {
        return showroomCars;
    }

    public int getId() {
        return id;
    }

    public String getNameEng() {
        return nameEng;
    }

    public String getNameArab() {
        return nameArab;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public String getAddressAr() {
        return addressAr;
    }

    public String getAboutEn() {
        return aboutEn;
    }

    public String getAboutAr() {
        return aboutAr;
    }

    public String getAbout() {
        return about;
    }

    public String getAddress() {
        return address;
    }

    public String getWorkTimes() {
        return workTimes;
    }

    public Country getCountry() {
        return country;
    }

    public Region getRegion() {
        return region;
    }

    public Area getArea() {
        return area;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
