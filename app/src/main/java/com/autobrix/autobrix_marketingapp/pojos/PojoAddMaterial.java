package com.autobrix.autobrix_marketingapp.pojos;

import java.util.ArrayList;

public class PojoAddMaterial {
    String dealerId;
    String type;
    String employeeId;
    String comment;
    ArrayList<PojoAddMtrlList> materialArr;

    public PojoAddMaterial(String dealerId, String type, String employeeId, String comment,
                           ArrayList<PojoAddMtrlList> materialArr) {
        this.dealerId = dealerId;
        this.type = type;
        this.employeeId = employeeId;
        this.comment = comment;
        this.materialArr = materialArr;
    }
}
