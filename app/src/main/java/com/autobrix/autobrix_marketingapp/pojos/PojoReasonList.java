package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoReasonList {
    @SerializedName("reason")
    String reason;
    @SerializedName("id")
    String id;

    public String getReason() {
        return reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
