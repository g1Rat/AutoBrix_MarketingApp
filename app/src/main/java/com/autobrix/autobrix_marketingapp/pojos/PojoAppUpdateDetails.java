package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoAppUpdateDetails {
    @SerializedName("can_skip")
    String can_skip;
    @SerializedName("appversion")
    String appversion;
    @SerializedName("app_url")
    String app_url;
    @SerializedName("is_current")
    String is_current;
    @SerializedName("ostype_id")
    String ostype_id;
    @SerializedName("app_name")
    String app_name;
    @SerializedName("application_id")
    String application_id;

    public String getCan_skip() {
        return can_skip;
    }

    public String getAppversion() {
        return appversion;
    }

    public String getApp_url() {
        return app_url;
    }

    public String getIs_current() {
        return is_current;
    }


}
