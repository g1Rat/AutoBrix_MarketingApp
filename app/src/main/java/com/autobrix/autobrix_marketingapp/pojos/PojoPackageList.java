package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoPackageList {
    @SerializedName("main_package_name")
    String main_package_name;
    @SerializedName("packages_count")
    String packages_count;
    @SerializedName("quantity")
    String quantity;

    public String getMain_package_name() {
        return main_package_name;
    }

    public String getPackages_count() {
        return packages_count;
    }

}
