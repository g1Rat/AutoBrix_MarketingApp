package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoMaterialStatus {
    @SerializedName("status_id")
    String status_id;
    @SerializedName("status_name")
    String status_name;

    String is_Selected="n";

    public String getIs_Selected() {
        return is_Selected;
    }

    public void setIs_Selected(String is_Selected) {
        this.is_Selected = is_Selected;
    }

    public String getStatus_id() {
        return status_id;
    }

    public String getStatus_name() {
        return status_name;
    }
}
