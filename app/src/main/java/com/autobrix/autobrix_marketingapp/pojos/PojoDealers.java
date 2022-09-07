package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoDealers {

    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("dealer_id")
    String dealer_id;
    @SerializedName("full_address")
    String full_address;
    @SerializedName("employee_id")
    String employee_id;
    @SerializedName("dealer_logo")
    String dealer_logo;
    @SerializedName("phone_no")
    String phone_no;
    @SerializedName("employee_name")
    String employee_name;
    @SerializedName("employee_phone_no")
    String employee_phone_no;

    String isSelected="n";

    public String getEmployee_name() {
        return employee_name;
    }

    public String getEmployee_phone_no() {
        return employee_phone_no;
    }

    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getDealer_id() {
        return dealer_id;
    }

    public void setDealer_id(String dealer_id) {
        this.dealer_id = dealer_id;
    }

    public String getFull_address() {
        return full_address;
    }

    public String getDealer_logo() {
        return dealer_logo;
    }

    public String getPhone_no() {
        return phone_no;
    }

}
