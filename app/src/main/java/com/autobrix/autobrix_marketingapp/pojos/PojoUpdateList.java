package com.autobrix.autobrix_marketingapp.pojos;

import com.google.gson.annotations.SerializedName;

public class PojoUpdateList
{
    @SerializedName("dealer_name")
    String dealer_name;
    @SerializedName("comments")
    String comments;
    @SerializedName("reminder_time")
    String reminder_time;
    @SerializedName("reason")
    String reason;
    @SerializedName("reason_id")
    String reason_id;

    @SerializedName("latitude")
    String latitude;
    @SerializedName("longitude")
    String longitude;

    public String getLatitude() {
        return latitude;
    }


    public String getLongitude() {
        return longitude;
    }

    public String getDealername() {
        return dealer_name;
    }

    public void setDealername(String dealer_name) {
        this.dealer_name = dealer_name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReminder_time() {
        return reminder_time;
    }


    public String getReason() {
        return reason;
    }

}
