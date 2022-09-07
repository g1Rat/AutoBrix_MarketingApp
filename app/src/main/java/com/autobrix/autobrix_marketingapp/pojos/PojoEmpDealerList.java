package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoEmpDealerList {
    @SerializedName("total_sales")
    String total_sales;
    @SerializedName("dealer_id")
    String dealer_id;
    @SerializedName("executive_id")
    String executive_id;
    @SerializedName("dealer_phone_no")
    String dealer_phone_no;
    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("target_count")
    String target_count;


    @SerializedName("packList")
    ArrayList<PojoPackageList> packList;

    public PojoEmpDealerList(String dealer_phone_no, String dealer_name) {
        this.dealer_phone_no = dealer_phone_no;
        this.dealer_name = dealer_name;
    }

    public String getTotal_sales() {
        return total_sales;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public String getExecutive_id() {
        return executive_id;
    }

    public String getDealer_phone_no() {
        return dealer_phone_no;
    }

    public ArrayList<PojoPackageList> getPackList() {
        return packList;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getTarget_count() {
        return target_count;
    }

}
