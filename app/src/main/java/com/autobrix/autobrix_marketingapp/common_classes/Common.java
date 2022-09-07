package com.autobrix.autobrix_marketingapp.common_classes;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.AddDealer;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Common
{
    Context context;
    GpsTracker gpsTracker;
    public  static String DealerLogo;
    private  String lat,lon;
    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }
    public final static String TAG = "Log";
    public final static int MY_PERMISSIONS_REQUEST_CAMERA = 100;
    public final static int MY_PERMISSIONS_REQUEST_LOCATION = 100;

    public static void CallToast(Context context, String message, int duration) {
        Toast.makeText(context,message,duration).show();
    }

    public final static String PhoneNumberRegix = "[0-9]{10}";

    public static String getCurrentDateDay() {
        Date current = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String parsed = dateFormat.format(current);
        return parsed;
    }
    public static String getDateFromString(String dateStr) {

        try {

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date got = format.parse(dateStr);
            format = new SimpleDateFormat("dd-MM-yyyy");
            return format.format(got);
        }
        catch (Exception e) {
            e.printStackTrace();
        }


        return "";
    }
    public static String parseDate(String time) {
        String inputPattern = "yyyy-MM-dd";
        String outputPattern = "dd-MMM-yy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    public  static String add_zero_to(String inputdigit)
    {
        String converted_digit="0";
        if(inputdigit.length()==1){
            converted_digit="0"+inputdigit;
        }
        else {
            converted_digit=inputdigit;
        }

        return converted_digit;
    }

    @SuppressLint("SetTextI18n")
    public void getLocation(View view){
        gpsTracker = new GpsTracker(context);
        if(gpsTracker.canGetLocation()){
            double latitude1 = gpsTracker.getLatitude();
            double longitude1 = gpsTracker.getLongitude();
            lat=String.valueOf(latitude1);
            lon=String.valueOf(longitude1);

        }else{
            gpsTracker.showSettingsAlert();
        }
    }
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(DealerLogo);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            Log.e("Bitmap","returned");
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("Exception",e.getMessage());
            return null;
        }
    }

    public static double  getDistanceFromLatLonInKm(double lat1,double lon1,double lat2,double lon2) {
        int R = 6371; // Radius of the earth in km
        double dLat = deg2rad(lat2-lat1);  // deg2rad below
        double dLon = deg2rad(lon2-lon1);
        double a =
                Math.sin(dLat/2) * Math.sin(dLat/2) +
                        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
                                Math.sin(dLon/2) * Math.sin(dLon/2)
                ;
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double d = R * c; // Distance in km

        return d;
    }

    public static double deg2rad(double deg) {
        return deg * (Math.PI/180);
    }
}


