package com.autobrix.autobrix_marketingapp.pojos;

import com.autobrix.autobrix_marketingapp.responses.AppResponse;

public class PojoAddDealers extends AppResponse
{
    private String dealer_name;
    private String phone_num;
    private String employee_id;
    private String location;
    private String no_soldcars;
    private String address_line1;
    private String address_line2;
    private String landmark;
    private String pincode;
    private String city;
    private String state;
    private String latitude;
    private String longitude;
    private String owner_name;
    private String email;
  //  private String dealer_manager_name;
    private String dealer_manager_no;
    private String video_url;
    private String facebook_link;
    private String insta_link;
    private String google_link;
    private String dealer_logo;
    private String sell_luxury_car;

    public PojoAddDealers( String dealer_name, String phone_num,
                          String employee_id, String location, String no_soldcars, String address_line1,
                          String address_line2, String landmark, String pincode, String city, String state,
                          String latitude, String longitude, String owner_name, String email,
                           String dealer_manager_no, String video_url, String facebook_link, String insta_link,
                           String google_link, String dealer_logo, String sell_luxury_car)
    {

        this.dealer_name = dealer_name;
        this.phone_num = phone_num;
        this.employee_id = employee_id;
        this.location = location;
        this.no_soldcars = no_soldcars;
        this.address_line1 = address_line1;
        this.address_line2 = address_line2;
        this.landmark = landmark;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.owner_name = owner_name;
        this.email = email;
       // this.dealer_manager_name=dealer_manager_name;
        this.dealer_manager_no = dealer_manager_no;
        this.video_url = video_url;
        this.facebook_link = facebook_link;
        this.insta_link = insta_link;
        this.google_link = google_link;
        this.dealer_logo = dealer_logo;
        this.sell_luxury_car = sell_luxury_car;
    }
}
