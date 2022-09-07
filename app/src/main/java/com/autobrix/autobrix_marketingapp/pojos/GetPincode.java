package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class GetPincode {

    @SerializedName("pincode")
    private String pincode;
    @SerializedName("state")
    private String state;
    @SerializedName("city")
    private String city;

    public String getPincode() {
        return pincode;
    }

    public String getState_name() {
        return state;
    }

    public String getCity_name() {
        return city;
    }
}
