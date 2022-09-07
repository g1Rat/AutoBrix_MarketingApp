package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashBoardPage extends AppCompatActivity
{
    LinearProgressIndicator lineear_progress;
    public  String dc,mc,dr,du,sc,text="";
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    RelativeLayout rl_menu,rl_manager_list,rl_emp_list;
    TextView pick_month,emp_name,no_of_mtrls,no_of_dealer_req,no_of_updates,tv_no_of_dealers,new_dealers
            ,no_of_sales,mngr_target_count,mng_achieved_count,count_daily_updates,tv_no_dealers,
            new_totdealers,req_count,tv_no_marketmtrl,insp_count;
    ImageView profile;
    private DatePickerDialog.OnDateSetListener listener;
    SimpleDateFormat month_date,year_date,month_date1;
    public String ma,serveryear,eid,edesignationid,monthvalue,yearvalue,servermonth,cityid;
    Calendar cal;
    private boolean doubleBackToExitPressedOnce=false;
    MonthYearPicker pd ;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_page);
        SPHelper.sharedPreferenceInitialization(DashBoardPage.this);
        lineear_progress=findViewById(R.id.lineear_progress);
        no_of_mtrls=findViewById(R.id.no_of_mtrls);
        no_of_dealer_req=findViewById(R.id.no_of_dealer_req);
        no_of_updates=findViewById(R.id.no_of_updates);
        tv_no_of_dealers=findViewById(R.id.tv_no_of_dealers);
        new_dealers=findViewById(R.id.new_dealers);
        no_of_sales=findViewById(R.id.no_of_sales);
        mngr_target_count=findViewById(R.id.mngr_target_count);
        mng_achieved_count=findViewById(R.id.mng_achieved_count);
        count_daily_updates=findViewById(R.id.count_daily_updates);
        tv_no_dealers=findViewById(R.id.tv_no_dealers);
        new_totdealers=findViewById(R.id.new_totdealers);
        req_count=findViewById(R.id.req_count);
        insp_count=findViewById(R.id.insp_count);
        tv_no_marketmtrl=findViewById(R.id.tv_no_marketmtrl);
        rl_manager_list=findViewById(R.id.rl_manager_list);
        rl_emp_list=findViewById(R.id.rl_emp_list);
        SPHelper.dash_year_value="";SPHelper.dash_month_value="";
        pick_month=findViewById(R.id.pick_month);
        rl_menu=findViewById(R.id.rl_menu);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(DashBoardPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        emp_name=findViewById(R.id.emp_name);
        profile=findViewById(R.id.profile) ;
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        emp_name.setText("Hi"+" "+SPHelper.getSPData(DashBoardPage.this,SPHelper.e_name,""));
//        double in_kms=Common.getDistanceFromLatLonInKm(12.967011,77.750660,12.859718,77.786791);
//        System.out.println("distance b/w"+in_kms);
//        System.out.println("inkms"+(int)in_kms);
        if(SPHelper.getSPData(DashBoardPage.this,SPHelper.role_id,"").equals("190")){
            SPHelper.picker_month="dbm";
            rl_emp_list.setVisibility(View.GONE);
            rl_manager_list.setVisibility(View.VISIBLE);
            servicecall_mngr_dashboardcount();
        }
        else{
            SPHelper.picker_month="db";
            rl_emp_list.setVisibility(View.VISIBLE);
            rl_manager_list.setVisibility(View.GONE);
            servicecall_getdashboardcount();
        }

        profile.setOnClickListener(view -> {
            Intent intent=new Intent(DashBoardPage.this,ProfilePage.class);
            startActivity(intent);
        });
        rl_menu.setOnClickListener(view -> {
            if(SPHelper.getSPData(DashBoardPage.this,SPHelper.role_id,"").equals("190")) {
                SPHelper.picker_month = "dbm";
            }
            else{
                SPHelper.picker_month = "db";
            }
            text="";
            pd=new MonthYearPicker();
            pd.setListener(listener);
            pd.show(getSupportFragmentManager(), "MonthPicker");
        });
    }

    public  void servicecall_getdashboardcount(){
        if (SPHelper.dash_month_value.equalsIgnoreCase("")) {
            SPHelper.monthvalue = servermonth;
            SPHelper.yearvalue = serveryear;
            pick_month.setText(ma+"-"+serveryear);
        } else{
            SPHelper.monthvalue=SPHelper.dash_month_value;
            SPHelper.yearvalue=SPHelper.dash_year_value;
        }

            if(!Connectivity.isNetworkConnected(DashBoardPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Internet not connected",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.getDashboardCount("","","",
                        SPHelper.getSPData(DashBoardPage.this, SPHelper.e_id, "")
                         ,SPHelper.monthvalue,SPHelper.yearvalue);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                        if (response.body()!=null)
                        {
                            if (response.code() == 200)
                            {
                                progressDialog.dismiss();
                                AppResponse appResponse = response.body();
                                String total_added_by=appResponse.getResponseModel().getDashboardDetails().getTotal_dealers_added_by_executive();
                                du=appResponse.getResponseModel().getDashboardDetails().getDaily_updates();
                                dc=appResponse.getResponseModel().getDashboardDetails().getDealer_count();
                                dr=appResponse.getResponseModel().getDashboardDetails().getDealer_requests();
                                mc=appResponse.getResponseModel().getDashboardDetails().getMaterial_count();
                                String td=appResponse.getResponseModel().getDashboardDetails().getTotal_dealer_count();
                                //dashboard count
                                new_dealers.setText(Common.add_zero_to(dc));
                                tv_no_of_dealers.setText(Common.add_zero_to(total_added_by));
                                no_of_dealer_req.setText(Common.add_zero_to(dr));
                                no_of_updates.setText(Common.add_zero_to(du));
                                no_of_mtrls.setText(Common.add_zero_to(mc));
                            }
                            else
                            {
                                progressDialog.dismiss();
                                Toast.makeText(DashBoardPage.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<AppResponse> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                    }
                });
            }
    }

    public  void servicecall_mngr_dashboardcount(){
        if (SPHelper.dash_month_value.equalsIgnoreCase("")) {
            SPHelper.monthvalue = servermonth;
            SPHelper.yearvalue = serveryear;
            pick_month.setText(ma+"-"+serveryear);
        } else{
            SPHelper.monthvalue=SPHelper.dash_month_value;
            SPHelper.yearvalue=SPHelper.dash_year_value;
        }

        if(!Connectivity.isNetworkConnected(DashBoardPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.getMngrDashboardCount(SPHelper.getSPData(DashBoardPage.this, SPHelper.e_id, ""),
                    "","",SPHelper.monthvalue,SPHelper.yearvalue,"");
            call.enqueue(new Callback<AppResponse>() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            String ts=appResponse.getResponseModel().getManagerDashboard().getTotal_sales();
                            String target_count=appResponse.getResponseModel().getManagerDashboard().getTarget_count();
                            String td=appResponse.getResponseModel().getManagerDashboard().getTotal_dealer_count();
                            String du=appResponse.getResponseModel().getManagerDashboard().getDaily_updates_count();
                            String insp_req_c=appResponse.getResponseModel().getManagerDashboard().getInspection_request_count();
                            String insp_veh_c=appResponse.getResponseModel().getManagerDashboard().getInspected_vehicle_list_count();
                            String mmt_c=appResponse.getResponseModel().getManagerDashboard().getMarketing_material_count();
                            String dc=appResponse.getResponseModel().getManagerDashboard().getDealer_count();

                            no_of_sales.setText(Common.add_zero_to(ts));
                            mngr_target_count.setText(Common.add_zero_to("("+target_count)+")");
                            tv_no_dealers.setText(Common.add_zero_to(td));
                            mng_achieved_count.setText(Common.add_zero_to(ts)+"/"+Common.add_zero_to(target_count));
                            count_daily_updates.setText(Common.add_zero_to(du));
                            insp_count.setText(Common.add_zero_to(insp_veh_c));
                            req_count.setText(Common.add_zero_to(insp_req_c));
                            tv_no_marketmtrl.setText(Common.add_zero_to(mmt_c));
                            new_totdealers.setText(Common.add_zero_to(dc));
                            double ach_count=Integer.parseInt(ts);
                            double target_c=Integer.parseInt(target_count);
                            int fl=(int)((ach_count/target_c)*100);
                            lineear_progress.setProgress(fl);

                            if(ach_count>target_c){
                                lineear_progress.setIndicatorColor(Color.parseColor("#00c42e"));
                            }else{
                                lineear_progress.setIndicatorColor(Color.parseColor("#0619c3"));
                            }
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(DashBoardPage.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public  void onDealerSelect(View view){
        SPHelper.camefrom="";
        Intent intent=new Intent(this,DealerListPage.class);
        startActivity(intent);
    }

    public  void todaystaskselect(View view){
        Intent intent=new Intent(this, DailyUpdateList.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed()
    {
        if (doubleBackToExitPressedOnce)
        {
            ActivityCompat.finishAffinity(DashBoardPage.this);
            finish();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    public void on_mmtrl_select(View view) {
        SPHelper.monthvalue="";
        SPHelper.displaymonthvalue_dashboard1="";
        Intent intent=new Intent(this,MarketMtrlList.class);
        startActivity(intent);
    }

    public void ondealer_requests(View view) {
        SPHelper.monthvalue_reminder="";
        SPHelper.displaymonthvalue_reminder="";
        Intent intent=new Intent(this,DealerRequests.class);
        startActivity(intent);
    }

    public void onSalesSelect(View view) {
        SPHelper.sales_mv="";
        Intent intent=new Intent(this, MngrSalesPage.class);
        startActivity(intent);
    }

    public void onDealerList(View view) {
        Intent intent=new Intent(this,DealerListPage.class);
        startActivity(intent);
    }

    public void onTargetSelect(View view) {
        SPHelper.sales_mv="";
        Intent intent=new Intent(this, MngrTargetList.class);
        startActivity(intent);
    }

    public void on_mngr_daily(View view) {
        SPHelper.selected_eid="";
        Intent intent=new Intent(this,MngrDailyUpdates.class);
        startActivity(intent);
    }

    public void on_mngr_InspSelect(View view) {
        SPHelper.monthvalue_reminder="";
        SPHelper.selected_eid="";
        Intent intent=new Intent(this,MngrInspList.class);
        startActivity(intent);
    }

    public void onMngrMtrlSelect(View view) {
        SPHelper.monthvalue="";
        SPHelper.selected_eid="";
        Intent intent=new Intent(this,MngrMtrlList.class);
        startActivity(intent);
    }

}
