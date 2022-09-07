package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterDealerList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMarketMtrlList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMaterialStatus;
import com.autobrix.autobrix_marketingapp.adapters.AdapterViewPage;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddDealers;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketingMaterials;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialStatus;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateStatus;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MarketMtrlList extends AppCompatActivity
{
    Dialog dialog;
    ProgressBar progress_bar;
    ImageView back,search_bar_img,cancel_btn;
    EditText search_mrkt_mtrl,comments;
    FloatingActionButton add;
    public TextView month_picker,update_status;
    private DatePickerDialog.OnDateSetListener listener1;
    MonthYearPicker pd1 ;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public AdapterMarketMtrlList adapter;
    public  ArrayList<PojoMarketingMaterials> list;
    public AdapterMaterialStatus adapterMaterialStatus;
    public  ArrayList<PojoMaterialStatus> mtrlstatus_list;
    public RecyclerView rv_mtrlist,rv_statuslist;
    TextView tv_standard,tv_custom,noresults;
    View line1,line2;
    private static MarketMtrlList instance;
    String ma,serveryear,servermonth;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1;
    String type="1";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market_mtrl_list);
        SPHelper.onstandard="y";
        instance=this;
        SPHelper.dealername="Select dealer";
        SPHelper.dealer_id="";
        progress_bar=findViewById(R.id.progress_bar);
        noresults=findViewById(R.id.noresults);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        search_mrkt_mtrl=findViewById(R.id.search_mrkt_mtrl);
        tv_standard=findViewById(R.id.tv_standard);
        tv_custom=findViewById(R.id.tv_custom);
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(MarketMtrlList.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        month_picker=findViewById(R.id.month_picker);
        rv_mtrlist=findViewById(R.id.rv_mtrlist);
        add=(FloatingActionButton)findViewById(R.id.add);

        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());

        add.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.monthvalue="";
                Intent intent=new Intent(MarketMtrlList.this,AddMarketMtrl.class);
                startActivity(intent);
            }
        });
        back=findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent1=new Intent(MarketMtrlList.this,DashBoardPage.class);
                startActivity(intent1);
            }
        });

        pd1 = new MonthYearPicker();
        pd1.setListener(listener1);
        month_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.picker_month="mmt";
                pd1.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
        search();
        get_All_market_mtrl_list();
    }
    public void search(){
        search_mrkt_mtrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(search_mrkt_mtrl.getText().toString().trim().length()>=2)
                {
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    get_All_market_mtrl_list();
                }else{
                    search_bar_img.setVisibility(View.VISIBLE);
                    cancel_btn.setVisibility(View.GONE);
                    get_All_market_mtrl_list();
                }
            }
        });
    }
    public  void open_dialog(){
        //ppop up mtrl status
        dialog = new Dialog(MarketMtrlList.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.popup_market_mtrl_status);
        dialog.setCancelable(true);
        rv_statuslist=dialog.findViewById(R.id.rv_statuslist) ;
        comments=dialog.findViewById(R.id.comments) ;
        update_status=dialog.findViewById(R.id.update_status);
        update_status.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {

               // dialog.dismiss();
                if(SPHelper.status_id.equals("")){
                    Common.CallToast(MarketMtrlList.this,"select status",2);
                }else{
                    update_Status();
                }

            }
        });
        get_status();
    }

    public  void open(){
        open_dialog();
        dialog.show();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onstandard_select(View view) {
        type="1";
        get_All_market_mtrl_list();
        SPHelper.onstandard="y";
        tv_standard.setTextColor(Color.parseColor("#0619c3"));
        tv_custom.setTextColor(Color.parseColor("#D3D3D3"));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void oncustom_select(View view)
    {
        type="2";
        get_All_market_mtrl_list();
        SPHelper.onstandard="n";
        tv_custom.setTextColor(Color.parseColor("#0619c3"));
        tv_standard.setTextColor(Color.parseColor("#D3D3D3"));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }

    public void get_All_market_mtrl_list()
    {
        if(SPHelper.monthvalue.equals("")){
            SPHelper.monthvalue_dashboard=servermonth;
            SPHelper.yearvalue_dashboard=serveryear;
            month_picker.setText(ma+"-"+serveryear);
        }
        else{
            SPHelper.monthvalue_dashboard=SPHelper.monthvalue;
            SPHelper.yearvalue_dashboard=SPHelper.yearvalue;
        }
        if(!Connectivity.isNetworkConnected(MarketMtrlList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getAllMarketingMtrLlist("","","",
                    search_mrkt_mtrl.getText().toString().trim(),"1","",
                    SPHelper.getSPData(MarketMtrlList.this,SPHelper.e_id,""),
                    SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard,type);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    System.out.println("code"+response.code());
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progress_bar.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            list=new ArrayList<>();
                            list=appResponse.getResponseModel().getAllMarketingMaterial();
                            if(list.isEmpty()){

                                rv_mtrlist.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                                //noresults foung
                            }else{
                                rv_mtrlist.setVisibility(View.VISIBLE);
                                noresults.setVisibility(View.GONE);
                                adapter = new AdapterMarketMtrlList(list, MarketMtrlList.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(MarketMtrlList.this, LinearLayoutManager.VERTICAL, false);
                                rv_mtrlist.setLayoutManager(layoutManager1);
                                rv_mtrlist.setAdapter(adapter);
                                MarketMtrlList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else if(response.code() ==500)
                        {
                            rv_mtrlist.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            Common.CallToast(MarketMtrlList.this,"  Error:"+response.code(),1);
                        }
                        else {
                            rv_mtrlist.setVisibility(View.GONE);
                            progress_bar.setVisibility(View.GONE);
                            Common.CallToast(MarketMtrlList.this,"  Error:"+response.code(),1);
                        }
                    }
                    else{
                        rv_mtrlist.setVisibility(View.GONE);
                        Common.CallToast(MarketMtrlList.this,"  Error:"+response.code(),1);
                        progress_bar.setVisibility(View.GONE);
                    }
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

    public void get_status()
    {
        if(!Connectivity.isNetworkConnected(MarketMtrlList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.getStatusList( SPHelper.getSPData(MarketMtrlList.this,SPHelper.role_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            mtrlstatus_list=new ArrayList<>();
                            mtrlstatus_list=appResponse.getResponseModel().getStatusList();
                            adapterMaterialStatus = new AdapterMaterialStatus(mtrlstatus_list, MarketMtrlList.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(MarketMtrlList.this, LinearLayoutManager.VERTICAL, false);
                            rv_statuslist.setLayoutManager(layoutManager1);
                            rv_statuslist.setAdapter(adapterMaterialStatus);
                            MarketMtrlList.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterMaterialStatus.notifyDataSetChanged();
                                }
                            });
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(MarketMtrlList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
                        }
                    }
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

    public void update_Status() {
        if (!Connectivity.isNetworkConnected(MarketMtrlList.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {
            PojoUpdateStatus posts = new PojoUpdateStatus(SPHelper.request_id,SPHelper.status_id,
                    SPHelper.getSPData(MarketMtrlList.this,SPHelper.e_id,"")
                    ,comments.getText().toString().trim());

            Call<AppResponse> call = apiInterface.update_Status(posts);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.code() == 200) {
                        AppResponse response2=response.body();
                        Toast.makeText(MarketMtrlList.this, "status updated", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        SPHelper.status_id="";
                        get_All_market_mtrl_list();
                    }
                    else {
                        Toast.makeText(MarketMtrlList.this,"Server Error"+response.code(),Toast.LENGTH_SHORT).show();
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
    public static MarketMtrlList getInstance() {
        return instance;
    }

    public  void oncancelSelect(View view){
        hideKeybaord();
        search_mrkt_mtrl.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
        get_All_market_mtrl_list();
    }
    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) MarketMtrlList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MarketMtrlList.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(MarketMtrlList.this,DashBoardPage.class);
        startActivity(intent);
        finish();
    }
}