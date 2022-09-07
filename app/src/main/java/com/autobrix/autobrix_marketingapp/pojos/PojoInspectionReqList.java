package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoInspectionReqList {

    @SerializedName("inspection_request_id")
    String inspection_request_id;
    @SerializedName("requested_on")
    String requested_on;
    @SerializedName("inspection_status")
    String inspection_status;
    @SerializedName("inspection_on")
    String inspection_on;
    @SerializedName("vehicle_id")
    String vehicle_id;
    @SerializedName("vehicle_no")
    String vehicle_no;
    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("no_of_cars")
    String no_of_cars;
    @SerializedName("city_id")
    String city_id;
    @SerializedName("dealer_id")
    String dealer_id;
    @SerializedName("city")
    String city;
    @SerializedName("requested_by")
    String requested_by;
    @SerializedName("inspection_type")
    String inspection_type;
    @SerializedName("inspected_vehicle_count")
    String inspected_vehicle_count;
    @SerializedName("inspection_combined_id")
    String inspection_combined_id;

    public String getInspection_combined_id() {
        return inspection_combined_id;
    }

    public String getInspected_vehicle_count() {
        return inspected_vehicle_count;
    }

    public String getInspection_type() {
        return inspection_type;
    }


    public String getRequested_by() {
        return requested_by;
    }


    public String getRequested_on() {
        return requested_on;
    }


    public String getInspection_status() {
        return inspection_status;
    }


    public String getInspection_on_date() {
        return inspection_on;
    }

    public String getVehicle_no() {
        return vehicle_no;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getNo_of_cars() {
        return no_of_cars;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

}
