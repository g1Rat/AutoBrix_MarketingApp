package com.autobrix.autobrix_marketingapp.common_classes;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class SPHelper {

    private static SharedPreferences prefs;
    public static String onstandard="";
    public static String role_id="role_id";
    public static String request_id="";
    public static String status_id="";
    public static String camefrom="";
    public static String dash_month_value="";
    public static String dash_year_value="";
    public static String picker_month="";
    public static String veh_id="";
    public static String carbrandid="";
    public static String brandlogo="";
    public static String model_name="";
    public static String carmodelid="";
    public static String veh_no="";
    public static String inspection_type="";
    public static String can_skip="";
    public static String app_url="";
    public static String insp_status="";
    public static String comments="";
    public static String cool_days="";
    public static String cool_kms="";
    public static String comb_req_id="";
    public static String more_visible="";
    public static String is_otp_verified="is_otp_verified";
    public static String emp_selected="";
    public static String mngr_came="";
    public static String sales_mv="";
    public static String sales_yv="";
    public static String displaymv_sales="";
    public static String selected_eid="";
    private  static String spName="DealerMarketing";
    public static String e_name="e_name";
    public static String e_email_id="e_email_id";
    public static String e_designation="e_designation";
    public static String cityid="cityid";
    public static String e_id="e_id";
    public static String e_designation_id="e_designation_id";
    public static String e_number="e_number";
    public static String monthvalue="";
    public static String yearvalue="";
    public static String monthvalue_dashboard="";
    public static String yearvalue_dashboard="";
    public static String monthvalue_reminder="";
    public static String yearvalue_reminder="";
    public static  String displaymonthvalue_reminder="";
    public static  String displaymonthvalue_dashboard1="";
    public static String dealername="Select dealer";
    public static String dealer_id="";
    public static String awssecret="awssecret";
    public static String awskey="awskey";

    public static void sharedPreferenceInitialization(Context ctx) {
        prefs = ctx.getSharedPreferences(spName,Context.MODE_PRIVATE);
    }

    public static void saveSPdata(Context ctx, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSPData(Context ctx, String key, String defaultValue) {
        prefs = ctx.getSharedPreferences(spName, Context.MODE_PRIVATE);
        return prefs.getString(key, defaultValue);
    }
}
