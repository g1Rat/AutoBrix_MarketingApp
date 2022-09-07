package com.autobrix.autobrix_marketingapp.pojos;

public class PojoAddInspection {

    String dealerId;
    String vehicleId;
    String vehNo;
    String type;
    String loctype;
    String noOfCar;
    String date;
    String custName;
    String custNum;
    String address;
    String employeeId;
    String location;

    public PojoAddInspection(String dealerId, String vehicleId, String vehNo, String type, String loctype,
                             String noOfCar, String date, String custName, String custNum, String address,
                             String employeeId,String location) {
        this.dealerId = dealerId;
        this.vehicleId = vehicleId;
        this.vehNo = vehNo;
        this.type = type;
        this.loctype = loctype;
        this.noOfCar = noOfCar;
        this.date = date;
        this.custName = custName;
        this.custNum = custNum;
        this.address = address;
        this.employeeId = employeeId;
        this.location=location;
    }
}
