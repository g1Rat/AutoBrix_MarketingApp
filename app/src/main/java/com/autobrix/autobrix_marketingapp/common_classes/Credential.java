package com.autobrix.autobrix_marketingapp.common_classes;

import com.google.gson.annotations.SerializedName;

public class Credential {
    @SerializedName("asure_container")
    private String asure_container;
    @SerializedName("asure_container_string")
    private String asure_container_string;

    public String getAsure_container() {
        return asure_container;
    }

    public void setAsure_container(String asure_container) {
        this.asure_container = asure_container;
    }

    public String getAsure_container_string() {
        return asure_container_string;
    }

    public void setAsure_container_string(String asure_container_string) {
        this.asure_container_string = asure_container_string;
    }
}
