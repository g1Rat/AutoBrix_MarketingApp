package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoMarketMtrlList {

    @SerializedName("comments")
    String comments;
    @SerializedName("material_type")
    String material_type;
    @SerializedName("material_name")
    String material_name;
    @SerializedName("quantity")
    String quantity;
    @SerializedName("material_id")
    String material_id;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getMaterial_name() {
        return material_name;
    }


    public String getQuantity() {
        return quantity;
    }

}
