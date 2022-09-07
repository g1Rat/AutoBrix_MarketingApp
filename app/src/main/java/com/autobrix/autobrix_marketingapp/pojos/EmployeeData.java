package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class EmployeeData {

    @SerializedName("employee_name")
    private String employee_name;
    @SerializedName("employee_id")
    private String employee_id;
    @SerializedName("email_id")
    private String email_id;
    @SerializedName("employee_role")
    private String employee_role;
    @SerializedName("city_id")
    private String city_id;
    @SerializedName("employee_role_id")
    private String employee_role_id;
    @SerializedName("employee_work_location")
    private String employee_work_location;
    @SerializedName("error_msg")
    private String error_msg;

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmail_id() {
        return email_id;
    }

    public void setEmail_id(String email_id) {
        this.email_id = email_id;
    }

    public String getEmployee_role() {
        return employee_role;
    }

    public void setEmployee_role(String employee_role) {
        this.employee_role = employee_role;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getEmployee_role_id() {
        return employee_role_id;
    }

    public void setEmployee_role_id(String employee_role_id) {
        this.employee_role_id = employee_role_id;
    }

    public String getEmployee_work_location() {
        return employee_work_location;
    }

    public void setEmployee_work_location(String employee_work_location) {
        this.employee_work_location = employee_work_location;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
