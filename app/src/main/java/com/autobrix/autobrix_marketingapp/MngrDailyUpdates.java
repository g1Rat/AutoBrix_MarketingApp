package com.autobrix.autobrix_marketingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterMngrEmpList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterdailyUpdates;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MngrDailyUpdates extends AppCompatActivity {
    private static MngrDailyUpdates instance;
    ProgressBar progress_bar1;
    View popupView;
    ImageView backbutton, search_bar_img, cancel_btn,calendar_icon;
    FloatingActionButton add_reminder;
    DatePickerDialog picker;
    public AdapterdailyUpdates adapterdailyUpdates;
    public ArrayList<PojoUpdateList> list;
    ProgressBar  progress_bar;
    TextView noresults, date,tv_dateset;
    RecyclerView rv_update_list,rv_emp_list;
    ApiInterface apiInterface;
    String eid, newdate = "";
    EditText search_reminder;
    CalendarView calendar_view;
    RelativeLayout rl2;
    String ma,serveryear,servermonth,serverday;
    Calendar cal;
    EditText selected_emp;
    SimpleDateFormat month_date,year_date,month_date1,day;
     PopupWindow popupWindow;
    public TextView tv_inspect,tv_services,noresults1,month_picker,tv_emp_name,all_tv;
    View line2,line1;
    String isInspect_clicked="y";
    public  int currentPage=1,TOTAL_PAGES=30;
    public ImageView close_iv;
    public RelativeLayout rl_empname,rl_select_emp,rl_transparent1;
    AdapterMngrEmpList adapterMngrEmpList;
    ArrayList<PojoSalesList> pojoMngrEmpListArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr_daily_updates);
        instance=this;
        progress_bar1=findViewById(R.id.progress_bar1);
        rl2=findViewById(R.id.rl2);
        all_tv=findViewById(R.id.all_tv);
        progress_bar=findViewById(R.id.progress_bar);
        noresults=findViewById(R.id.noresults);
        selected_emp=findViewById(R.id.selected_emp);
        tv_emp_name=findViewById(R.id.tv_emp_name);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_select_emp=findViewById(R.id.rl_select_emp);
        tv_services=findViewById(R.id.tv_services);
        tv_inspect=findViewById(R.id.tv_inspect);
        noresults1=findViewById(R.id.noresults1);
        noresults=findViewById(R.id.noresults);
        close_iv=findViewById(R.id.close_iv);
        line1=findViewById(R.id.line1);
        line2=findViewById(R.id.line2);
        rl_empname=findViewById(R.id.rl_empname);
        rv_emp_list=findViewById(R.id.rv_emp_list);
        calendar_icon=findViewById(R.id.calendar_icon);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        tv_dateset=findViewById(R.id.tv_dateset);
        rv_update_list=findViewById(R.id.rv_update_list);
        search_reminder =findViewById(R.id.search_reminder);
        search_bar_img =  findViewById(R.id.search_bar_img);
        cancel_btn =  findViewById(R.id.cancel_btn);
        noresults =  findViewById(R.id.noresults);
        backbutton = findViewById(R.id.back);
        add_reminder = findViewById(R.id.add);
        date =  findViewById(R.id.selecteddate);
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
         popupView = inflater.inflate(R.layout.pop_up_calendar, null);
        calendar_view=popupView.findViewById(R.id.calendar_view);
        calendar_view.setMaxDate(System.currentTimeMillis() - 1000);
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
         popupWindow = new PopupWindow(popupView, width, height, focusable);
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");//APR
        year_date = new SimpleDateFormat("yyyy");//2022
        month_date1=new SimpleDateFormat("MM");//01
        day=new  SimpleDateFormat("dd");
        SPHelper.mngr_came="du";
        serverday=day.format(cal.getTime());
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        newdate=serveryear+"-"+servermonth+"-"+serverday;
        tv_dateset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick(view);
            }
        });
        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int dayOfMonth, int monthOfYear, int year) {
                popupWindow.dismiss();

                newdate=dayOfMonth+"-"+(monthOfYear + 1)+"-"+year;
                tv_dateset.setText(Common.parseDate(newdate));
                get_Daily_updateList();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MngrDailyUpdates.this, DashBoardPage.class);
                startActivity(intent);
                finish();
            }
        });

        add_reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MngrDailyUpdates.this, AddDailyUpdate.class);
                startActivity(intent);
                finish();
            }
        });

        rl_empname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_emp.setVisibility(View.VISIBLE);
                getMngrEmpList();
            }
        });
        rl_transparent1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_emp.setVisibility(View.GONE);
            }
        });
        all_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_emp.setVisibility(View.GONE);
                SPHelper.selected_eid="";
                tv_emp_name.setText("All");
                get_Daily_updateList();
            }
        });
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_emp.setText("");
                noresults.setVisibility(View.GONE);
                SPHelper.selected_eid="";
                get_Daily_updateList();
            }
        });
        search1();
        onfocuschage();
        search();
        get_Daily_updateList();
    }

    public  void onfocuschage(){
        selected_emp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SPHelper.emp_selected="";
                search();
            }
        });
    }
    public void search1(){
        selected_emp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(selected_emp.getText().toString().trim().length()>=1)
                {
                    close_iv.setVisibility(View.VISIBLE);
                    getMngrEmpList();

                }else{
                    SPHelper.emp_selected="";
                    hideKeybaord();
                    close_iv.setVisibility(View.GONE);
                    getMngrEmpList();
                }
                if(SPHelper.emp_selected.equals("y")){
                    rv_emp_list.setVisibility(View.GONE);
                }else {
                    rv_emp_list.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(SPHelper.emp_selected.equals("y")){
                    rv_emp_list.setVisibility(View.GONE);
                }else {
                    rv_emp_list.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    public void getMngrEmpList()
    {
//        if (SPHelper.sales_mv.equalsIgnoreCase("")) {
//            SPHelper.monthvalue = servermonth;
//            SPHelper.yearvalue = serveryear;
//            month_picker.setText(ma+"-"+serveryear);
//        } else{
//            SPHelper.monthvalue=SPHelper.sales_mv;
//            SPHelper.yearvalue=SPHelper.sales_yv;
//        }
        if(!Connectivity.isNetworkConnected(MngrDailyUpdates.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrEmpList(selected_emp.getText().toString(),SPHelper.getSPData(MngrDailyUpdates.this,SPHelper.e_id,""),
                    "", "","","", "");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();

                            pojoMngrEmpListArrayList=new ArrayList<>();
                            pojoMngrEmpListArrayList=appResponse.getResponseModel().getManagerEmployeeList();
                            if(pojoMngrEmpListArrayList.isEmpty()){
                                rv_emp_list.setVisibility(View.GONE);
                                noresults1.setVisibility(View.VISIBLE);
                            }else
                            {
                                noresults1.setVisibility(View.GONE);
                                rv_emp_list.setVisibility(View.VISIBLE);

                                adapterMngrEmpList = new AdapterMngrEmpList(pojoMngrEmpListArrayList, MngrDailyUpdates.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(MngrDailyUpdates.this, LinearLayoutManager.VERTICAL, false);
                                rv_emp_list.setLayoutManager(layoutManager2);
                                rv_emp_list.setAdapter(adapterMngrEmpList);
                                MngrDailyUpdates.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterMngrEmpList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(MngrDailyUpdates.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progress_bar.setVisibility(View.GONE);
                }
                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progress_bar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
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
                if (search_reminder.getText().toString().trim().length() >= 1)
                {
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    get_Daily_updateList();
                }else  {
                    cancel_btn.setVisibility(View.GONE);
                    get_Daily_updateList();
                }
            }
        });
    }

    public void get_Daily_updateList() {

        if (!Connectivity.isNetworkConnected(MngrDailyUpdates.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else
        {
            progress_bar1.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrDailyUpdateList(
                    SPHelper.getSPData(MngrDailyUpdates.this,SPHelper.e_id,""),newdate,newdate,"",
                    "","",SPHelper.selected_eid,"1",search_reminder.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    if (response.body() != null)
                    {
                        if (response.code() == 200) {
                            progress_bar1.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            list = new ArrayList();
                            list = appResponse.getResponseModel().getManagerDailyUpdate();
                            if (list.isEmpty()) {
                                rv_update_list.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else{
                                rv_update_list.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                adapterdailyUpdates = new AdapterdailyUpdates(list, MngrDailyUpdates.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrDailyUpdates.this, LinearLayoutManager.VERTICAL, false);
                                rv_update_list.setLayoutManager(layoutManager1);
                                rv_update_list.setAdapter(adapterdailyUpdates);
                                MngrDailyUpdates.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterdailyUpdates.notifyDataSetChanged();
                                    }
                                });
                            }

                        } else {
                            progress_bar1.setVisibility(View.GONE);
                            Toast.makeText(MngrDailyUpdates.this, "Error:" + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    progress_bar1.setVisibility(View.GONE);
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progress_bar1.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void oncancelSelect(View view) {
        hideKeybaord();
        search_reminder.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
        get_Daily_updateList();
    }

    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) MngrDailyUpdates.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MngrDailyUpdates.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void onButtonShowPopupWindowClick(View view) {

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    public static MngrDailyUpdates getInstance() {
        return instance;
    }
}