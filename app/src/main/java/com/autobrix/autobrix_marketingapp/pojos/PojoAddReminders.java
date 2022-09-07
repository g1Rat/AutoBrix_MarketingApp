package com.autobrix.autobrix_marketingapp.pojos;

public class PojoAddReminders {


    String dealerId;
    String employee_id;
    String reasonId;
    String time;
    String comment;
    String latitude;
    String longitude;


    public PojoAddReminders(String dealerId, String employee_id, String reasonId, String time,
                            String comment, String latitude, String longitude) {
        this.dealerId = dealerId;
        this.employee_id = employee_id;
        this.reasonId = reasonId;
        this.time = time;
        this.comment = comment;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
