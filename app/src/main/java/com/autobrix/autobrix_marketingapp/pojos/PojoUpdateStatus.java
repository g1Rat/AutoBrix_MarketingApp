package com.autobrix.autobrix_marketingapp.pojos;

public class PojoUpdateStatus {

    String requestId;
    String statusId;
    String employee_id;
    String comment;

    public PojoUpdateStatus(String requestId, String statusId, String employee_id, String comment) {
        this.requestId = requestId;
        this.statusId = statusId;
        this.employee_id = employee_id;
        this.comment = comment;
    }
}
