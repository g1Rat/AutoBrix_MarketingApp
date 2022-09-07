package com.autobrix.autobrix_marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterdailyUpdates;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.GpsTracker;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateList;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DailyUpdateList extends AppCompatActivity
{
    GpsTracker gpsTracker;
    private static DailyUpdateList instance;
    ImageView backbutton, search_bar_img, cancel_btn,calendar_icon;
    FloatingActionButton add_reminder;
    DatePickerDialog picker;
    public AdapterdailyUpdates adapterdailyUpdates;
    public ArrayList<PojoUpdateList> list;
    TextView  noresults, date,tv_dateset;
    RecyclerView rv_update_list;
    ApiInterface apiInterface;
    ProgressBar progress;
    ProgressDialog progressDialog;
    String eid, newdate = "";
    EditText search_reminder;
    CalendarView calendar_view;
    RelativeLayout rl2;
    String ma,serveryear,servermonth,serverday;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1,day;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_update_list);
        instance=this;
        progress=findViewById(R.id.progress);
        rl2=findViewById(R.id.rl2);
        calendar_view=findViewById(R.id.calendar_view);
        calendar_icon=findViewById(R.id.calendar_icon);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(DailyUpdateList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        tv_dateset=findViewById(R.id.tv_dateset);
        rv_update_list=  findViewById(R.id.rv_update_list);
        search_reminder =  findViewById(R.id.search_reminder);
        search_bar_img =  findViewById(R.id.search_bar_img);
        cancel_btn =  findViewById(R.id.cancel_btn);
        noresults = findViewById(R.id.noresults);
        backbutton =  findViewById(R.id.back);
        add_reminder =  findViewById(R.id.add);
        date =  findViewById(R.id.selecteddate);

        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");//APR
        year_date = new SimpleDateFormat("yyyy");//2022
        month_date1=new SimpleDateFormat("MM");//01
        day=new  SimpleDateFormat("dd");

        serverday=day.format(cal.getTime());
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        newdate=serveryear+"-"+servermonth+"-"+serverday;
        calendar_view.setMaxDate(System.currentTimeMillis() - 1000);
        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int dayOfMonth, int monthOfYear, int year) {
               // calendarView.setVisibility(View.GONE);
               // rl2.setVisibility(View.VISIBLE);
               // rv_update_list.setVisibility(View.VISIBLE);
                newdate=dayOfMonth+"-"+(monthOfYear + 1)+"-"+year;
                tv_dateset.setText(Common.parseDate(newdate));
                get_Daily_updateList();
            }
        });
        /*calendar_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendar_view.setVisibility(View.VISIBLE);
                rv_update_list.setVisibility(View.GONE);
                rl2.setVisibility(View.GONE);
            }
        });*/
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyUpdateList.this, DashBoardPage.class);
                startActivity(intent);
                finish();
            }
        });

        add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyUpdateList.this, AddDailyUpdate.class);
                startActivity(intent);
                finish();
            }
        });
        //getLocation();
        search();
        get_Daily_updateList();




    }

    public void search() {
        search_reminder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (search_reminder.getText().toString().trim().length() >= 2)
                {
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    get_Daily_updateList();
                }else if(search_reminder.getText().toString().length()==0) {
                    cancel_btn.setVisibility(View.GONE);
                    get_Daily_updateList();
                }
                else
                 {
                    noresults.setVisibility(View.GONE);
                }
            }
        });
    }

    public void get_Daily_updateList() {

        if (!Connectivity.isNetworkConnected(DailyUpdateList.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else
        {

            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_UpdateList(SPHelper.getSPData(DailyUpdateList.this,SPHelper.e_id,""),newdate,search_reminder.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body() != null) {
                        if (response.code() == 200) {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            list = new ArrayList();
                            list = appResponse.getResponseModel().getUpdateList();
                            if (list.isEmpty()) {
                                rv_update_list.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else{
                                rv_update_list.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                adapterdailyUpdates = new AdapterdailyUpdates(list, DailyUpdateList.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(DailyUpdateList.this, LinearLayoutManager.VERTICAL, false);
                                rv_update_list.setLayoutManager(layoutManager1);
                                rv_update_list.setAdapter(adapterdailyUpdates);
                                DailyUpdateList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterdailyUpdates.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else {
                            Toast.makeText(DailyUpdateList.this, "Error:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progress.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void oncancelSelect(View view) {
        hideKeybaord(view);
        search_reminder.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
        get_Daily_updateList();
    }

    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
    }
    public static DailyUpdateList getInstance() {
        return instance;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DailyUpdateList.this, DashBoardPage.class);
        startActivity(intent);
        finish();
    }

    @SuppressLint("SetTextI18n")
    public void getLocation(){
        gpsTracker = new GpsTracker(DailyUpdateList.this);
        if(gpsTracker.canGetLocation()){
            double latitude1 = gpsTracker.getLatitude();
            double longitude1 = gpsTracker.getLongitude();
            String lat = String.valueOf(latitude1);
            String lon = String.valueOf(longitude1);

            System.out.println("latitude :"+lat);
            System.out.println("longitude :"+lon);
        }else{
            gpsTracker.showSettingsAlert();
        }
    }
}









