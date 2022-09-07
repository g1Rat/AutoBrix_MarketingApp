package com.autobrix.autobrix_marketingapp.pojos;

import com.autobrix.autobrix_marketingapp.MarketMtrlList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoMarketingMaterials
{

    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("material_status")
    String material_status;
    @SerializedName("updated_on")
    String updated_on;
    @SerializedName("created_on")
    String created_on;
    @SerializedName("request_id")
    String request_id;
    @SerializedName("requested_on")
    String requested_on;
    @SerializedName("material_type")
    String material_type;
    @SerializedName("comments")
    String comments;

    @SerializedName("MaterialList")
    ArrayList<PojoMarketMtrlList> MaterialList;
    @SerializedName("ManagerMaterialDetails")
    private  ArrayList<PojoMarketMtrlList> ManagerMaterialDetails;

    public ArrayList<PojoMarketMtrlList> getManagerMaterialDetails() {
        return ManagerMaterialDetails;
    }

    public ArrayList<PojoMarketMtrlList> getMaterialList() {
        return MaterialList;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getRequested_on() {
        return requested_on;
    }


    public String getRequest_id() {
        return request_id;
    }


    public String getDealer_name() {
        return dealer_name;
    }

    public void setDealer_name(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getMaterial_status() {
        return material_status;
    }

    public String getUpdated_on() {
        return updated_on;
    }

}
