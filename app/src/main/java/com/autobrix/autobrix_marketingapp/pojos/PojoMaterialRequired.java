package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoMaterialRequired
{
    @SerializedName("material_name")
    String material_name;
    @SerializedName("id")
    String id;

    String total_count="";
    String selected_comment="";

    public String getSelected_comment() {
        return selected_comment;
    }

    public void setSelected_comment(String selected_comment) {
        this.selected_comment = selected_comment;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public String getMaterial_name() {
        return material_name;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
