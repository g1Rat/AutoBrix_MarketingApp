package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspCompList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectedVehicleList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionReqList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerRequests extends AppCompatActivity
{
    RelativeLayout rl_back;
    ProgressBar progress_bar;
    public  int currentPage=1,TOTAL_PAGES=30;
    boolean isLoading,isLastPage;
    private static DealerRequests instance;
    Dialog dialog,dialog_inps_comp;
    TextView tv_inspect,tv_services,noresults,month_picker;
    View line2,line1;
    RecyclerView rv_request_list,rv_insp_veh_list,rv_insp_completed_list;
    ArrayList<PojoInspectionReqList> inspectionReqLists;
    ArrayList<PojoInspectionReqList> inspection_search_ReqLists;
    AdapterInspectReq adapterInspectReq;
    ArrayList<PojoInspectedVehicleList> inspectedVehicleLists;
    ArrayList<PojoInspectedVehicleList> insp_search_vehlist;
    AdapterInspectedVehicleList adapterInspectedVehicleList;
    ArrayList<PojoInspCompList> inspCompLists;
    AdapterInspCompVehList adapterInspCompVehList;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    EditText search_dealer;
    ImageView cancel_btn,search_bar_img,back;
    String isInspect_clicked="y";
    private DatePickerDialog.OnDateSetListener listener1;
    MonthYearPicker pd1 ;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1;
    String ma,serveryear,servermonth;
    FloatingActionButton add_request;
    public TextView kms,noof_days,title,title1,title2,tv_comments;
    RelativeLayout rl_noofdays,rl_kms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_requests);
        instance=this;
        inspCompLists=new ArrayList<>();
        dialog = new Dialog(DealerRequests.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_inspect_approved);
        dialog.setCancelable(true);
        dialog_inps_comp= new Dialog(DealerRequests.this);
        dialog_inps_comp.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog_inps_comp.setContentView(R.layout.popup_insp_completed_veh_list);
        dialog_inps_comp.setCancelable(true);
        rv_insp_completed_list=dialog_inps_comp.findViewById(R.id.rv_insp_completed_list);
        progress_bar=findViewById(R.id.progress_bar);
        rl_back=findViewById(R.id.rl_back);
        noof_days=dialog.findViewById(R.id.noof_days);
        kms=dialog.findViewById(R.id.kms);
        tv_comments=dialog.findViewById(R.id.tv_comments);
        title=dialog.findViewById(R.id.title);
        title1=dialog.findViewById(R.id.title1);
        title2=dialog.findViewById(R.id.title2);
        rl_noofdays=dialog.findViewById(R.id.rl_noofdays);
        rl_kms=dialog.findViewById(R.id.rl_kms);
        back=findViewById(R.id.back);
        month_picker= findViewById(R.id.month_picker);
        noresults=findViewById(R.id.noresults);
        cancel_btn=findViewById(R.id.cancel_btn);
        search_bar_img=findViewById(R.id.search_bar_img);
        search_dealer= findViewById(R.id.search_dealer);
        rv_request_list=findViewById(R.id.rv_request_list);
        rv_insp_veh_list=findViewById(R.id.rv_insp_veh_list);
        tv_inspect= findViewById(R.id.tv_inspect);
        tv_services= findViewById(R.id.tv_services);
        line2= findViewById(R.id.line2);
        line1=findViewById(R.id.line1);
        add_request=findViewById(R.id.add_request);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(DealerRequests.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        pd1 = new MonthYearPicker();
        pd1.setListener(listener1);
        month_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.picker_month="dr";
                pd1.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(DealerRequests.this,DashBoardPage.class);
                startActivity(intent);
            }
        });
        add_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.dealername="Select dealer";
                SPHelper.dealer_id="";
                SPHelper.camefrom="";
                SPHelper.inspection_type="Presale";
                Intent intent=new Intent(DealerRequests.this,AddDealerRequest.class);
                startActivity(intent);
            }
        });
        adapterInspectedVehicleList = new AdapterInspectedVehicleList( DealerRequests.this);
        adapterInspectReq = new AdapterInspectReq( DealerRequests.this);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
        rv_request_list.setLayoutManager(layoutManager1);
        rv_insp_veh_list.setLayoutManager(layoutManager2);
        rv_request_list.setAdapter(adapterInspectReq);
        rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);

        rv_request_list.addOnScrollListener(new  PaginationScrollListener(layoutManager1) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(inspectionReqLists.size()>=30){
                    get_Inspection_pag__ReqList();
                }else if(inspection_search_ReqLists.size()>=30){
                    get_Inspection_pag__ReqList();
                }
            }
            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
        rv_insp_veh_list.addOnScrollListener(new  PaginationScrollListener(layoutManager1) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                if(inspectedVehicleLists.size()>=30){
                    get_insp_pag_vehList();
                }else if(insp_search_vehlist.size()>=30){
                    get_insp_pag_vehList();
                }
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });

        search();
        get_Inspection_ReqList();
    }
    public abstract class PaginationScrollListener extends RecyclerView.OnScrollListener {

        private LinearLayoutManager layoutManager;

        public PaginationScrollListener(LinearLayoutManager layoutManager) {
            this.layoutManager = layoutManager;
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (!isLoading() && !isLastPage())
            {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadMoreItems();
                }
            }
        }

        protected abstract void loadMoreItems();

        public abstract boolean isLastPage();

        public abstract boolean isLoading();

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

    public  void open_dialog(){
        noof_days.setText(SPHelper.cool_days);
        kms.setText(SPHelper.cool_kms);
        if(SPHelper.comments.equals("")){
            tv_comments.setVisibility(View.GONE);
        }else{
            tv_comments.setVisibility(View.VISIBLE);
            tv_comments.setText(SPHelper.comments);
        }
        dialog.show();
    }

    public  void open_insp_comp(){
        get_Inspection_CompletedList();
        dialog_inps_comp.show();
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

    public void search(){
        search_dealer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(search_dealer.getText().toString().trim().length()>=2)
                {
                   // rv_request_list.getRecycledViewPool().clear();
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    if(isInspect_clicked.equals("y")){
                        get_Inspection_ReqList();
                    }else{
                        get_insp_vehList();
                    }
                }else {
                    noresults.setVisibility(View.GONE);
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
    public void get_insp_vehList()
    {
        currentPage=1;
        if(!search_dealer.getText().toString().equals("")){
            month_picker.setText("All");
            SPHelper.monthvalue_dashboard="";
            SPHelper.yearvalue_dashboard="";
        }else{
            if(SPHelper.monthvalue_reminder.equals("")){
                month_picker.setText(ma+"-"+serveryear);
                SPHelper.monthvalue_dashboard=servermonth;
                SPHelper.yearvalue_dashboard=serveryear;
            }
            else{
                SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
                SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
            }
        }

        if(!Connectivity.isNetworkConnected(DealerRequests.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_InspectedVehList("","","",search_dealer.getText().toString().trim(),String.valueOf(currentPage),"",
                    SPHelper.getSPData(DealerRequests.this,SPHelper.e_id,""),SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getInspectedVehicleList().isEmpty()){
                               noresults.setVisibility(View.VISIBLE);
                               rv_request_list.setVisibility(View.GONE);
                               rv_request_list.setVisibility(View.GONE);
                            }else {
                                rv_request_list.setVisibility(View.GONE);
                                rv_insp_veh_list.setVisibility(View.VISIBLE);
                                inspectedVehicleLists = new ArrayList<>();
                                inspectedVehicleLists = appResponse.getResponseModel().getInspectedVehicleList();
                                insp_search_vehlist = appResponse.getResponseModel().getInspectedVehicleList();
                                if(search_dealer.getText().toString().equals("")){
                                    adapterInspectedVehicleList = new AdapterInspectedVehicleList( DealerRequests.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
                                    rv_insp_veh_list.setLayoutManager(layoutManager1);
                                    rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
                                    adapterInspectedVehicleList.addAll(inspectedVehicleLists);
                                }else{
                                    adapterInspectedVehicleList = new AdapterInspectedVehicleList( DealerRequests.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
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
                            Toast.makeText(DealerRequests.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public void get_insp_pag_vehList()
    {

        if(!search_dealer.getText().toString().equals("")){
            month_picker.setText("All");
            SPHelper.monthvalue_dashboard="";
            SPHelper.yearvalue_dashboard="";
        }else{
            if(SPHelper.monthvalue_reminder.equals("")){
                month_picker.setText(ma+"-"+serveryear);
                SPHelper.monthvalue_dashboard=servermonth;
                SPHelper.yearvalue_dashboard=serveryear;
            }
            else{
                SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
                SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
            }
        }

        if(!Connectivity.isNetworkConnected(DealerRequests.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_InspectedVehList("","",
                    "",search_dealer.getText().toString().trim(),String.valueOf(currentPage),"",
                    SPHelper.getSPData(DealerRequests.this,SPHelper.e_id,""),SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            inspectedVehicleLists=new ArrayList<>();
                            inspectedVehicleLists=appResponse.getResponseModel().getInspectedVehicleList();
                            if(inspectedVehicleLists.isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                rv_request_list.setVisibility(View.GONE);
                                rv_insp_veh_list.setVisibility(View.GONE);
                                adapterInspectedVehicleList.removeLoadingFooter();
                            }
                            else
                            {
                                noresults.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.GONE);
                                rv_insp_veh_list.setVisibility(View.VISIBLE);
                                adapterInspectedVehicleList.removeLoadingFooter();
                                isLoading = false;
                                adapterInspectedVehicleList.addAll(inspectedVehicleLists);
                                if (currentPage != TOTAL_PAGES)
                                {
                                }
                                else isLastPage = true;
                            }
                        }
                        else
                        {
                            Toast.makeText(DealerRequests.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public void get_Inspection_ReqList()
    {
        currentPage=1;
        if(SPHelper.monthvalue_reminder.equals("")){
            month_picker.setText(ma+"-"+serveryear);
            SPHelper.monthvalue_dashboard=servermonth;
            SPHelper.yearvalue_dashboard=serveryear;
        }
        else{
            SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
            SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
        }
        if(!Connectivity.isNetworkConnected(DealerRequests.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_InspectionRequest("","", "",search_dealer.getText().toString(),String.valueOf(currentPage),"",
                    SPHelper.getSPData(DealerRequests.this,SPHelper.e_id,""),SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();

                            if(appResponse.getResponseModel().getInspectionRequestList().isEmpty()){
                                  noresults.setVisibility(View.VISIBLE);
                                  rv_insp_veh_list.setVisibility(View.GONE);
                                  rv_request_list.setVisibility(View.GONE);

                            }else {

                                rv_insp_veh_list.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                inspectionReqLists=new ArrayList<>();
                                inspectionReqLists= appResponse.getResponseModel().getInspectionRequestList();
                                inspection_search_ReqLists= appResponse.getResponseModel().getInspectionRequestList();
                                //adapterInspectReq.notifyItemRangeRemoved(0,adapterInspectReq.getItemCount());
                                if(search_dealer.getText().toString().equals("")){
                                    adapterInspectReq = new AdapterInspectReq( DealerRequests.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
                                    rv_request_list.setLayoutManager(layoutManager1);
                                    rv_request_list.setAdapter(adapterInspectReq);
                                    adapterInspectReq.addAll(inspectionReqLists);
                                }else{
                                    adapterInspectReq = new AdapterInspectReq( DealerRequests.this);
                                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
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
                            Toast.makeText(DealerRequests.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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

    public void get_Inspection_pag__ReqList()
    {
        if(SPHelper.monthvalue_reminder.equals("")){
            month_picker.setText(ma+"-"+serveryear);
            SPHelper.monthvalue_dashboard=servermonth;
            SPHelper.yearvalue_dashboard=serveryear;
        }
        else{
            SPHelper.monthvalue_dashboard=SPHelper.monthvalue_reminder;
            SPHelper.yearvalue_dashboard=SPHelper.yearvalue_reminder;
        }
        if(!Connectivity.isNetworkConnected(DealerRequests.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_InspectionRequest("","", "",search_dealer.getText().toString().trim(),String.valueOf(currentPage),"",
                    SPHelper.getSPData(DealerRequests.this,SPHelper.e_id,""),SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            inspectionReqLists=new ArrayList<>();
                            inspectionReqLists=appResponse.getResponseModel().getInspectionRequestList();
                            if(inspectionReqLists.isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                rv_request_list.setVisibility(View.GONE);
                                rv_insp_veh_list.setVisibility(View.GONE);
                                adapterInspectReq.removeLoadingFooter();
                            }
                            else
                            {

                                rv_insp_veh_list.setVisibility(View.GONE);
                                rv_request_list.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                adapterInspectReq.removeLoadingFooter();
                                isLoading = false;
                                adapterInspectReq.addAll(inspectionReqLists);
                                if (currentPage != TOTAL_PAGES)
                                {
                                }
                                else isLastPage = true;
                            }
                        }
                        else
                        {
                            Toast.makeText(DealerRequests.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public void get_Inspection_CompletedList()
    {
        if(!Connectivity.isNetworkConnected(DealerRequests.this))
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
                                adapterInspCompVehList = new AdapterInspCompVehList(inspCompLists, DealerRequests.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
                                rv_insp_completed_list.setLayoutManager(layoutManager1);
                                rv_insp_completed_list.setAdapter(adapterInspCompVehList);
                                DealerRequests.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterInspCompVehList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(DealerRequests.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public  void oncancelSelect(View view){
        hideKeybaord(view);
        search_dealer.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
        if(isInspect_clicked.equals("y")){
            add_insp_req_adapter();
            get_Inspection_ReqList();
        }else{
            add_insp_veh_adapter();
            get_insp_vehList();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent=new Intent(DealerRequests.this,DashBoardPage.class);
        startActivity(intent);
    }

    public static DealerRequests getInstance() {
        return instance;
    }

    public void add_insp_req_adapter(){
        adapterInspectReq = new AdapterInspectReq( DealerRequests.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
        rv_request_list.setLayoutManager(layoutManager1);
        rv_request_list.setAdapter(adapterInspectReq);
    }
    public void add_insp_veh_adapter(){
        adapterInspectedVehicleList = new AdapterInspectedVehicleList( DealerRequests.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
        rv_insp_veh_list.setLayoutManager(layoutManager1);
        rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
    }
    @Override
    protected void onResume() {
        super.onResume();
    //        adapterInspectedVehicleList = new AdapterInspectedVehicleList( DealerRequests.this);
//        adapterInspectReq = new AdapterInspectReq( DealerRequests.this);
//        LinearLayoutManager layoutManager2 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
//        LinearLayoutManager layoutManager1 = new LinearLayoutManager(DealerRequests.this, LinearLayoutManager.VERTICAL, false);
//        rv_request_list.setLayoutManager(layoutManager1);
//        rv_insp_veh_list.setLayoutManager(layoutManager2);
//        rv_request_list.setAdapter(adapterInspectReq);
//        rv_insp_veh_list.setAdapter(adapterInspectedVehicleList);
//
//        rv_request_list.addOnScrollListener(new  PaginationScrollListener(layoutManager1) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                if(inspectionReqLists.size()>=30){
//                    get_Inspection_pag__ReqList();
//                }else if(inspection_search_ReqLists.size()>=30){
//                    get_Inspection_pag__ReqList();
//                }
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
//        rv_insp_veh_list.addOnScrollListener(new  PaginationScrollListener(layoutManager1) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage += 1;
//                if(inspectedVehicleLists.size()>=30){
//                    get_insp_pag_vehList();
//                }else if(insp_search_vehlist.size()>=30){
//                    get_insp_pag_vehList();
//                }
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });
//
//        search();
    }
}