package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoEmployees {
        @SerializedName("employee_name")
        private String employee_name;
        @SerializedName("employee_id")
        private String employee_id;
       @SerializedName("phone_no")
       private String phone_no;
    @SerializedName("account_exists")
    private String account_exists;

    public String getAccount_exists() {
        return account_exists;
    }


}
