package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAllCarBrands {
    @SerializedName("brand_icon")
    String brand_icon;
    @SerializedName("id")
    String id;
    @SerializedName("count")
    String count;
    @SerializedName("status_id")
    String status_id;
    @SerializedName("car_brand")
    String car_brand;
    String is_Selected="n";


    public String getBrand_icon() {
        return brand_icon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCar_brand() {
        return car_brand;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
