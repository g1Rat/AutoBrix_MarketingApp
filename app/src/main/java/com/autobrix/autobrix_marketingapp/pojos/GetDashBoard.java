package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

import retrofit2.http.Query;

public class GetDashBoard {
    @SerializedName("dealer_count")
    String dealer_count;
    @SerializedName("daily_updates")
    String daily_updates;
    @SerializedName("material_count")
    String material_count;
    @SerializedName("dealer_requests")
    String dealer_requests;
    @SerializedName("total_dealer_count")
    String total_dealer_count;
    @SerializedName("total_dealers_added_by_executive")
    String total_dealers_added_by_executive;

    @SerializedName("total_sales")
    String total_sales;
    @SerializedName("target_count")
    String target_count;
    @SerializedName("marketing_material_count")
    String marketing_material_count;
    @SerializedName("daily_updates_count")
    String daily_updates_count;
    @SerializedName("inspection_request_count")
    String inspection_request_count;
    @SerializedName("inspected_vehicle_list_count")
    String inspected_vehicle_list_count;


    public String getMarketing_material_count() {
        return marketing_material_count;
    }

    public String getDaily_updates_count() {
        return daily_updates_count;
    }

    public String getInspection_request_count() {
        return inspection_request_count;
    }

    public String getInspected_vehicle_list_count() {
        return inspected_vehicle_list_count;
    }

    public String getTotal_sales() {
        return total_sales;
    }

    public String getTarget_count() {
        return target_count;
    }

    public String getTotal_dealers_added_by_executive() {
        return total_dealers_added_by_executive;
    }


    public String getTotal_dealer_count() {
        return total_dealer_count;
    }

    public String getDealer_count() {
        return dealer_count;
    }

    public String getDaily_updates() {
        return daily_updates;
    }

    public String getMaterial_count() {
        return material_count;
    }

    public String getDealer_requests() {
        return dealer_requests;
    }

}
