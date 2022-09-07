package com.autobrix.autobrix_marketingapp.pojos;

public class PojoDealerWiseList {

    String d_name;
    String time;

    public PojoDealerWiseList(String d_name, String time) {
        this.d_name = d_name;
        this.time = time;
    }

    public String getD_name() {
        return d_name;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
