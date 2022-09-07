package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoInspectedVehicleList {
        @SerializedName("car_model")
        String car_model;
        @SerializedName("dealer_id")
        String dealer_id;
        @SerializedName("fuel_type")
        String fuel_type;
        @SerializedName("inspection_comments")
        String inspection_comments;
        @SerializedName("inspected_by_name")
        String inspected_by_name;
        @SerializedName("dealer_name")
        String dealer_name;
        @SerializedName("cooling_period_days")
        String cooling_period_days;
        @SerializedName("inspection_status")
        String inspection_status;
        @SerializedName("owner_name")
        String owner_name;
        @SerializedName("inspected_by_id")
        String inspected_by_id;
        @SerializedName("is_with_cooling_period")
        String is_with_cooling_period;
        @SerializedName("cooling_period_kms")
        String cooling_period_kms;
        @SerializedName("vehicle_no")
        String vehicle_no;
        @SerializedName("inspection_on")
        String inspection_on;
        @SerializedName("phone_no")
        String phone_no;
        @SerializedName("inspection_on_date")
        String inspection_on_date;
        @SerializedName("vehicle_id")
        String vehicle_id;
        @SerializedName("transmission_type")
        String transmission_type;
        @SerializedName("brand_logo")
        String brand_logo;

        public String getBrand_logo() {
                return brand_logo;
        }

        public String getTransmission_type() {
                return transmission_type;
        }

        public String getCar_model() {
                return car_model;
        }

        public String getDealer_id() {
                return dealer_id;
        }

        public void setDealer_id(String dealer_id) {
                this.dealer_id = dealer_id;
        }

        public String getFuel_type() {
                return fuel_type;
        }

        public String getInspection_comments() {
                return inspection_comments;
        }
        public String getDealer_name() {
                return dealer_name;
        }

        public void setDealer_name(String dealer_name) {
                this.dealer_name = dealer_name;
        }

        public String getCooling_period_days() {
                return cooling_period_days;
        }


        public String getInspection_status() {
                return inspection_status;
        }


        public String getIs_with_cooling_period() {
                return is_with_cooling_period;
        }

        public String getCooling_period_kms() {
                return cooling_period_kms;
        }

        public String getVehicle_no() {
                return vehicle_no;
        }

        public String getPhone_no() {
                return phone_no;
        }

}
