package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAllCarModels {


    @SerializedName("car_model")
    String car_model;
    @SerializedName("model_id")
    String model_id;
    String isSelected="n";

    public String getCar_model() {
        return car_model;
    }
    public String getModel_id() {
        return model_id;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }
}
