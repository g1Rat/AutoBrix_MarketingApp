package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterInspCompVehList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterInspectReq;
import com.autobrix.autobrix_marketingapp.adapters.AdapterInspectedVehicleList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMngrEmpList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspCompList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectedVehicleList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionReqList;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MngrInspList extends AppCompatActivity {
    ProgressBar progress_bar;
    Dialog dialog,dialog_inps_comp;
    public EditText selected_emp;
    private static MngrInspList instance;
    private DatePickerDialog.OnDateSetListener listener1;
    ImageView search_bar_img,cancel_btn;
    MonthYearPicker pd1 ;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1;
    String ma,serveryear,servermonth;
    boolean isLoading,isLastPage;
    public TextView tv_inspect,tv_services,noresults1,month_picker,tv_emp_name,noresults,all_tv;
    View line2,line1;
    public String isInspect_clicked="y";
    public  int currentPage=1,TOTAL_PAGES=30;
    public ImageView close_iv;
    EditText search_dealer;
    ProgressBar progress_bar1;
    RecyclerView rv_request_list,rv_insp_veh_list,rv_insp_completed_list,rv_emp_list;
    ArrayList<PojoInspectionReqList> inspectionReqLists;
    ArrayList<PojoInspectionReqList> inspection_search_ReqLists;
    AdapterInspectReq adapterInspectReq;
    ArrayList<PojoInspectedVehicleList> inspectedVehicleLists;
    ArrayList<PojoInspectedVehicleList> insp_search_vehlist;
    AdapterInspectedVehicleList adapterInspectedVehicleList;
    ArrayList<PojoInspCompList> inspCompLists;
    AdapterInspCompVehList adapterInspCompVehList;
    private ApiInterface apiInterface;
    public RelativeLayout rl_empname,rl_select_emp,rl_transparent1,rl_back;
    AdapterMngrEmpList adapterMngrEmpList;
    ArrayList<PojoSalesList> pojoMngrEmpListArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr_insp_list);
        instance=this;
        rl_back=findViewById(R.id.rl_back);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        progress_bar=findViewById(R.id.progress_bar);
        all_tv=findViewById(R.id.all_tv);
        noresults=findViewById(R.id.noresults);
        selected_emp=findViewById(R.id.selected_emp);
        tv_emp_name=findViewById(R.id.tv_emp_name);
        search_dealer=findViewById(R.id.search_dealer);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        rl_select_emp=findViewById(R.id.rl_select_emp);
        tv_services=findViewById(R.id.tv_services);
        tv_inspect=findViewById(R.id.tv_inspect);
        noresults1=findViewById(R.id.noresults1);
        close_iv=findViewById(R.id.close_iv);
        line1=findViewById(R.id.line1);
        line2=findViewById(R.id.line2);
        rl_empname=findViewById(R.id.rl_empname);
        month_picker=findViewById(R.id.month_picker);
        progress_bar1=findViewById(R.id.progress_bar1);
        rv_emp_list=findViewById(R.id.rv_emp_list);
        rv_request_list=findViewById(R.id.rv_request_list);
        rv_insp_veh_list=findViewById(R.id.rv_insp_veh_list);
        dialog_inps_comp= new Dialog(MngrInspList.this);
        dialog_inps_comp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_inps_comp.setContentView(R.layout.popup_insp_completed_veh_list);
        dialog_inps_comp.setCancelable(true);
        rv_insp_completed_list=dialog_inps_comp.findViewById(R.id.rv_insp_completed_list);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        SPHelper.mngr_came="insp";
        rl_empname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_select_emp.setVisibility(View.VISIBLE);
                getMngrEmpList();
            }
        });
        pd1 = new MonthYearPicker();
        pd1.setListener(listener1);
        month_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.picker_month="mngrinsp";
                pd1.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(MngrInspList.this,DashBoardPage.class);
                startActivity(intent);
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
                tv_emp_name.setText("All");
                SPHelper.selected_eid="";
                get_Inspection_ReqList();
            }
        });
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_emp.setText("");
                noresults.setVisibility(View.GONE);
            }
        });
        search();
        search1();
        get_Inspection_ReqList();
        onfocuschage();
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
    private void search1() {
        search_dealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(search_dealer.getText().toString().trim().length()>=1)
                {
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    if(isInspect_clicked.equals("y")){
                        get_Inspection_ReqList();
                    }else{
                        get_insp_vehList();
                    }
                }else {
                    hideKeybaord();
                    noresults.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.GONE);
                    if(isInspect_clicked.equals("y")){
                        add_insp_req_adapter();
                        get_Inspection_ReqList();
                    }else{
                        add_insp_veh_adapter();
                        get_insp_vehList();
                    }
                }
            }
        });
    }
    public void search(){
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
                    hideKeybaord();
                    SPHelper.emp_selected="";
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
        if(!Connectivity.isNetworkConnected(MngrInspList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrEmpList(selected_emp.getText().toString(),SPHelper.getSPData(MngrInspList.this,SPHelper.e_id,""),
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
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                noresults.setVisibility(View.GONE);
                                rv_emp_list.setVisibility(View.VISIBLE);
                                adapterMngrEmpList = new AdapterMngrEmpList(pojoMngrEmpListArrayList, MngrInspList.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                rv_emp_list.setLayoutManager(layoutManager2);
                                rv_emp_list.setAdapter(adapterMngrEmpList);
                                MngrInspList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterMngrEmpList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(MngrInspList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void oninspection_req_select(View view) {
        isInspect_clicked="y";
        add_insp_req_adapter();
        get_Inspection_ReqList();
        tv_inspect.setTextColor(Color.parseColor("#0619c3"));
        tv_services.setTextColor(Color.parseColor("#D3D3D3"));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void on_inspected_veh_select(View view) {
        isInspect_clicked="n";
        add_insp_veh_adapter();
        get_insp_vehList();
        tv_services.setTextColor(Color.parseColor("#0619c3"));
        tv_inspect.setTextColor(Color.parseColor("#D3D3D3"));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }
    public void add_insp_req_adapter(){
        adapterInspectReq = new AdapterInspectReq( MngrInspList.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
        rv_request_list.setLayoutManager(layoutManager1);
        rv_request_list.setAdapter(adapterInspectReq);
    }
    public void add_insp_veh_adapter(){
        adapterInspectedVehicleList = new AdapterInspectedVehicleList( MngrInspList.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
        rv_insp_veh_list.setLayoutManager(layoutManager1);
        rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
    }
    public void get_insp_vehList()
    {
        currentPage=1;
        if(!search_dealer.getText().toString().equals("")){
            //month_picker.setText("All");
            SPHelper.monthvalue_dashboard="";
            SPHelper.yearvalue_dashboard="";
        }else{
            if(SPHelper.monthvalue_reminder.equals("")){
               // month_picker.setText(ma+"-"+serveryear);
                SPHelper.monthvalue_dashboard=servermonth;
                SPHelper.yearvalue_dashboard=serveryear;
            }
            else{
                SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
                SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
            }
        }

        if(!Connectivity.isNetworkConnected(MngrInspList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar1.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_MngrInsp_VehList(SPHelper.getSPData(MngrInspList.this,SPHelper.e_id,""),"","",
                    "",SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard,SPHelper.selected_eid,"1",search_dealer.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar1.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getManagerInspectedVehList().isEmpty()){
                                noresults1.setVisibility(View.VISIBLE);
                                rv_request_list.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.GONE);
                            }else {
                                rv_request_list.setVisibility(View.GONE);
                                rv_insp_veh_list.setVisibility(View.VISIBLE);
                                inspectedVehicleLists = new ArrayList<>();
                                inspectedVehicleLists = appResponse.getResponseModel().getManagerInspectedVehList();
                                insp_search_vehlist = appResponse.getResponseModel().getManagerInspectedVehList();
                                if(search_dealer.getText().toString().equals("")){
                                    adapterInspectedVehicleList = new AdapterInspectedVehicleList( MngrInspList.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                    rv_insp_veh_list.setLayoutManager(layoutManager1);
                                    rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
                                    adapterInspectedVehicleList.addAll(inspectedVehicleLists);
                                }else{
                                    adapterInspectedVehicleList = new AdapterInspectedVehicleList( MngrInspList.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                    rv_insp_veh_list.setLayoutManager(layoutManager1);
                                    rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
                                    adapterInspectedVehicleList.addAll(insp_search_vehlist);
                                }
                                if (currentPage <= TOTAL_PAGES) {

                                    try {
                                        adapterInspectedVehicleList.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else
                                    isLastPage = true;
                                adapterInspectedVehicleList.removeLoadingFooter();

                            }
                        }
                        else
                        {
                            Toast.makeText(MngrInspList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public void get_Inspection_ReqList()
    {
        currentPage=1;
        if(SPHelper.monthvalue_reminder.equals("")){
            SPHelper.monthvalue_dashboard=servermonth;
            SPHelper.yearvalue_dashboard=serveryear;
            month_picker.setText(ma+"-"+serveryear);
        }
        else{
            SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
            SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
        }
        if(!Connectivity.isNetworkConnected(MngrInspList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar1.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_MngrInsp_Request(SPHelper.getSPData(MngrInspList.this,SPHelper.e_id,""),"","","",
                    SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard,search_dealer.getText().toString(),
                    SPHelper.selected_eid,"1");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar1.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();

                            if(appResponse.getResponseModel().getManagerInspectionReqList().isEmpty()){
                                noresults1.setVisibility(View.VISIBLE);
                                rv_insp_veh_list.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.GONE);

                            }else {

                                rv_insp_veh_list.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.VISIBLE);
                                noresults1.setVisibility(View.GONE);
                                inspectionReqLists=new ArrayList<>();
                                inspectionReqLists= appResponse.getResponseModel().getManagerInspectionReqList();
                                inspection_search_ReqLists= appResponse.getResponseModel().getManagerInspectionReqList();
                                //adapterInspectReq.notifyItemRangeRemoved(0,adapterInspectReq.getItemCount());
                                if(search_dealer.getText().toString().equals("")){
                                    adapterInspectReq = new AdapterInspectReq( MngrInspList.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                    rv_request_list.setLayoutManager(layoutManager1);
                                    rv_request_list.setAdapter(adapterInspectReq);
                                    adapterInspectReq.addAll(inspectionReqLists);
                                }else{
                                    adapterInspectReq = new AdapterInspectReq( MngrInspList.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                    rv_request_list.setLayoutManager(layoutManager1);
                                    rv_request_list.setAdapter(adapterInspectReq);
                                    adapterInspectReq.addAll(inspection_search_ReqLists);
                                }


                                if (currentPage <= TOTAL_PAGES)
                                {
                                    try {
                                        adapterInspectReq.addLoadingFooter();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else
                                    isLastPage = true;
                                adapterInspectReq.removeLoadingFooter();

                            }

                        }
                        else
                        {
                            Toast.makeText(MngrInspList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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

    public  void open_insp_comp(){
        get_Inspection_CompletedList();
        dialog_inps_comp.show();
    }
    public void get_Inspection_CompletedList()
    {
        if(!Connectivity.isNetworkConnected(MngrInspList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_CompletedVehList(SPHelper.comb_req_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            inspCompLists=new ArrayList<>();
                            inspCompLists= appResponse.getResponseModel().getCompletedInspectedVehicleList();
                            if(inspCompLists.isEmpty()){


                            }else {
                                adapterInspCompVehList = new AdapterInspCompVehList(inspCompLists, MngrInspList.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrInspList.this, LinearLayoutManager.VERTICAL, false);
                                rv_insp_completed_list.setLayoutManager(layoutManager1);
                                rv_insp_completed_list.setAdapter(adapterInspCompVehList);
                                MngrInspList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterInspCompVehList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(MngrInspList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) MngrInspList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MngrInspList.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public static MngrInspList getInstance() {
        return instance;
    }

    public void oncancelSelect(View view) {
        cancel_btn.setVisibility(View.GONE);
        search_dealer.setText("");
        hideKeybaord();
    }
}