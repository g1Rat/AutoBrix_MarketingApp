package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PojoSalesList {

    @SerializedName("year")
    String year;
    @SerializedName("employee_id")
    String employee_id;
    @SerializedName("target_count")
    String target_count;
    @SerializedName("created_on")
    String created_on;
    @SerializedName("month")
    String month;
    @SerializedName("employee_phone_no")
    String employee_phone_no;
    @SerializedName("employee_name")
    String employee_name;
    String is_selected="n";
    @SerializedName("target")
    PojoTargetCount target;
    @SerializedName("TargetAchieved")
    PojoTargetCount TargetAchieved;

    @SerializedName("DealerList")
    ArrayList<PojoEmpDealerList> DealerList;

    private boolean expanded;
    String isSelected="n";

    public PojoTargetCount getTargetAchieved() {
        return TargetAchieved;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
    public String getYear() {
        return year;
    }

    public String getCreated_on() {
        return created_on;
    }

    public String getMonth() {
        return month;
    }

    public ArrayList<PojoEmpDealerList> getDealerList() {
        return DealerList;
    }

    public PojoTargetCount getTarget() {
        return target;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public String getTarget_count() {
        return target_count;
    }


    public String getEmployee_phone_no() {
        return employee_phone_no;
    }


    public String getEmployee_name() {
        return employee_name;
    }


    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }
}
