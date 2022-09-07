package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.GpsTracker;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddReminders;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDailyUpdate extends AppCompatActivity
{
    boolean gps_enabled = false;
    boolean network_enabled = false;
    LocationManager mLocationManager;
    String lat="0",lon="0";
    GpsTracker gpsTracker;
    Button btn;
    RelativeLayout rl1,rl2;
    DatePickerDialog d_picker;
    TimePickerDialog t_picker;
    ImageView imageView;
    TextView yes,no,date_set,time_set,time_tv,time_tv1,inputLayout,date,v,selectdealer;
    EditText comments;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    public  Dialog dialog;
    String eid,serverdate,newtime="";
    Toast toast;
    Spinner select_reason;
    ArrayList<String> reasonname,reasonid;
    String reason="",reason_id="";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_daily_update);
        reasonname=new ArrayList<>();
        reasonid=new ArrayList<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddDailyUpdate.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        selectdealer= findViewById(R.id.selectdealer);
        select_reason=findViewById(R.id.select_reason);
        rl2=findViewById(R.id.rl_time);
        btn=findViewById(R.id.button_submit);
        imageView=findViewById(R.id.back);
        comments=findViewById(R.id.comments);
        time_tv1=findViewById(R.id.et_time) ;
        time_set=findViewById(R.id.time_set) ;
        time_tv=findViewById(R.id.time) ;

        //getLocation();
        selectdealer.setText(SPHelper.dealername);
        rl2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                time_tv.setVisibility(View.INVISIBLE);
                time_tv1.setVisibility(View.VISIBLE);
                time_set.setVisibility(View.VISIBLE);
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                int seconds=mcurrentTime.get(Calendar.SECOND);
                t_picker = new TimePickerDialog(AddDailyUpdate.this, new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int selectedMinute)
                    {
                        String time = hour+":"+minute;
                        newtime=hour+":"+minute+":"+seconds;
                        if(hourOfDay>=0 && hourOfDay<12){
                            time =hourOfDay+":"+selectedMinute+" "+"AM";
                            newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                        }
                        else {
                            if(hourOfDay == 12){
                                time =hourOfDay+":"+selectedMinute+" "+"PM";
                                newtime=hourOfDay+":"+selectedMinute+":"+seconds;
                            } else{
                                hourOfDay = hourOfDay -12;
                                time =hourOfDay +":"+selectedMinute+" "+"PM";
                                newtime=(hourOfDay+12)+":"+selectedMinute+":"+seconds;
                            }
                        }
                        time_set.setText(time);
                       // time_set.setText("12:47:50");
                    }
                },hour,minute,false);
                t_picker.setTitle("Select Time");
                t_picker.show();
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.dealer_id="";
                SPHelper.dealername="Select dealer";
                Intent intent=new Intent(AddDailyUpdate.this, DailyUpdateList.class);
                startActivity(intent);
                finish();
            }
        });
        dialog = new Dialog(AddDailyUpdate.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        yes=dialog.findViewById(R.id.yes) ;
        no=dialog.findViewById(R.id.no) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
              // servicecall_addReminder();
            }
        });
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled&&!network_enabled){

        }else{
            getLocation();
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
                boolean gps_enabled1 = false;
                boolean network_enabled1 = false;
                try {
                    gps_enabled1 = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
                } catch(Exception ex) {}
                try {
                    network_enabled1 = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
                } catch(Exception ex) {}
                if(SPHelper.dealer_id.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Select dealer ",
                            Toast.LENGTH_SHORT).show();
                }
                else if(reason.equals("")||reason.equals("Select Reason")){
                    Toast.makeText(getApplicationContext(),
                            "Select a reason",
                            Toast.LENGTH_SHORT).show();
                }
                else if(newtime.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Select time ",
                            Toast.LENGTH_SHORT).show();
                }
                else if(comments.getText().toString().trim().equals("")||comments.getText().toString().trim().equals(null)){
                    Toast.makeText(getApplicationContext(),
                            "Add comments ",
                            Toast.LENGTH_SHORT).show();
                }
                else if(!gps_enabled1&&!network_enabled1)
                {
                    new AlertDialog.Builder(AddDailyUpdate.this)
                            .setMessage("GPS not enabled")
                            .setPositiveButton("Open Location Settings", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    AddDailyUpdate.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                                }
                            })
                            .setNegativeButton("Cancel",null)
                            .show();
                }
                else {
                    getLocation();
                    servicecall_addReminder();
                }
            }
        });
        get_reasonlist();
        select_reason.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {

                TextView tv =  (TextView) view;
                String item = parent.getItemAtPosition(position).toString();
                if (position == 0) {
                    tv.setTextColor(Color.rgb(0, 0, 0));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                } else {
                    tv.setTextColor(Color.rgb(0, 0, 0));
                    tv.setTextSize(15);
                    tv.setTypeface(Typeface.DEFAULT);
                }
                if (select_reason.getSelectedItemPosition() > 0) {
                    reason_id=reasonid.get(position);
                    reason = reasonname.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void get_reasonlist() {
        if(!Connectivity.isNetworkConnected(AddDailyUpdate.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.get_ReasonList();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            reasonname.clear();
                            reasonid.clear();
                            reasonname.add("Select Reason");
                            reasonid.add("0");

                            for (int i = 0; i < appResponse.getResponseModel().getReasonList().size(); i++)
                            {
                                reasonname.add(appResponse.getResponseModel().getReasonList().get(i).getReason());
                                reasonid.add(appResponse.getResponseModel().getReasonList().get(i).getId());
                            }
                            AddDailyUpdate.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    status();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(AddDailyUpdate.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void status() {
        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, reasonname);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        select_reason.setAdapter(dataAdapter3);
    }
    public  void servicecall_addReminder()
    {
            if (!Connectivity.isNetworkConnected(AddDailyUpdate.this)) {
                Toast.makeText(getApplicationContext(),
                        "Internet not connected",
                        Toast.LENGTH_SHORT).show();
            } else {
                PojoAddReminders posts = new PojoAddReminders(SPHelper.dealer_id,SPHelper.getSPData(AddDailyUpdate.this,SPHelper.e_id,""),
                        reason_id,newtime,comments.getText().toString().trim(),lat,lon);

                Call<AppResponse> call = apiInterface.updatdealer(posts);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                        if (response.code() == 200)
                        {
                            AppResponse postresponse = response.body();
                            toast = Toast.makeText(AddDailyUpdate.this, "Update added", Toast.LENGTH_SHORT);
                            toast.show();
                            Intent intent = new Intent(AddDailyUpdate.this, DailyUpdateList.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Toast.makeText(getApplicationContext(),
                                    "Code: " + response.code() + "Server issue",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<AppResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    public void search_dealer(View view) {
        SPHelper.camefrom="remind";
        Intent intent = new Intent(AddDailyUpdate.this, SearchDealer.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddDailyUpdate.this, DailyUpdateList.class);
        startActivity(intent);
        SPHelper.dealer_id="";
        SPHelper.dealername="Select dealer";
    }

    @SuppressLint("SetTextI18n")
    public void getLocation(){
        gpsTracker = new GpsTracker(AddDailyUpdate.this);
        if(gpsTracker.canGetLocation()){
            double latitude1 = gpsTracker.getLatitude();
            double longitude1 = gpsTracker.getLongitude();
             lat = String.valueOf(latitude1);
             lon = String.valueOf(longitude1);
            System.out.println("latitude :"+lat);
            System.out.println("longitude :"+lon);

        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    public  void check_gps(){
        LocationManager lm = (LocationManager)getApplicationContext().getSystemService(Context.LOCATION_SERVICE);

        try {
            gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}
        try {
            network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}
    }
}