package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoInspCompList {

    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("status")
    String status;

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
