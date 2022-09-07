package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.utils.widget.ImageFilterView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.autobrix.autobrix_marketingapp.adapters.AdapterMarketMtrlList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMngrEmpList;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketingMaterials;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.bumptech.glide.load.resource.gif.GifOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MngrMtrlList extends AppCompatActivity {

    ProgressBar progress_bar1;
    private static MngrMtrlList instance;
    String ma,serveryear,servermonth;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1;
    String type="1";
    public ImageView close_iv;
    public EditText selected_emp;
    TextView noresults,noresults1,month_picker;
    RelativeLayout rl_emp_list;
    RecyclerView rv_emp_list,rv_mtrlist;
    AdapterMngrEmpList adapterMngrEmpList;
    ArrayList<PojoSalesList> pojoMngrEmpListArrayList;
    TextView tv_standard,tv_custom;
    ImageView search_bar_img,cancel_btn,back;
    View line1,line2;
    EditText search_mrkt_mtrl;
    private ProgressDialog progressDialog;
    ProgressBar progress_bar;
    public AdapterMarketMtrlList adapter;
    public  ArrayList<PojoMarketingMaterials> list;
    private ApiInterface apiInterface;
    public RelativeLayout rl_empname,rl_select_emp,rl_transparent1;
    public TextView tv_emp_name,all_tv;
    private DatePickerDialog.OnDateSetListener listener1;
    MonthYearPicker pd1 ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr_mtrl_list);
        instance=this;
        progress_bar1=findViewById(R.id.progress_bar1);
        back=findViewById(R.id.back);
        month_picker=findViewById(R.id.month_picker);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        progress_bar=findViewById(R.id.progress_bar);
        rl_empname=findViewById(R.id.rl_empname);
        rl_select_emp=findViewById(R.id.rl_select_emp);
        rl_transparent1=findViewById(R.id.rl_transparent1);
        tv_emp_name=findViewById(R.id.tv_emp_name);
        all_tv=findViewById(R.id.all_tv);
        rv_emp_list=findViewById(R.id.rv_emp_list);
        rv_mtrlist=findViewById(R.id.rv_mtrlist);
        rl_emp_list=findViewById(R.id.rl_emp_list);
        noresults=findViewById(R.id.noresults);
        noresults1=findViewById(R.id.noresults1);
        selected_emp=findViewById(R.id.selected_emp);
        close_iv=findViewById(R.id.close_iv);
        search_mrkt_mtrl=findViewById(R.id.search_mrkt_mtrl);
        tv_standard=findViewById(R.id.tv_standard);
        tv_custom=findViewById(R.id.tv_custom);
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());

        SPHelper.mngr_came="mtrl";
        pd1 = new MonthYearPicker();
        pd1.setListener(listener1);
        month_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                SPHelper.picker_month="mng_mmt";
                pd1.show(getSupportFragmentManager(), "MonthYearPicker");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(MngrMtrlList.this,DashBoardPage.class);
                startActivity(intent1);
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
                tv_emp_name.setText("All");
                SPHelper.selected_eid="";
                get_All_market_mtrl_list();
            }
        });
        close_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_emp.setText("");
                close_iv.setVisibility(View.GONE);
                noresults.setVisibility(View.GONE);
            }
        });

        get_All_market_mtrl_list();
        onfocuschage();
        search1();
    }

    private void search1() {
        search_mrkt_mtrl.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (search_mrkt_mtrl.getText().toString().trim().length() >= 1)
                {
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    get_All_market_mtrl_list();
                }else {
                    hideKeybaord();
                    cancel_btn.setVisibility(View.GONE);
                    get_All_market_mtrl_list();
                }
            }
        });
    }

    public void get_EmpList(){

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
        if(!Connectivity.isNetworkConnected(MngrMtrlList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrEmpList(selected_emp.getText().toString(),SPHelper.getSPData(MngrMtrlList.this,SPHelper.e_id,""),
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
                                adapterMngrEmpList = new AdapterMngrEmpList(pojoMngrEmpListArrayList, MngrMtrlList.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(MngrMtrlList.this, LinearLayoutManager.VERTICAL, false);
                                rv_emp_list.setLayoutManager(layoutManager2);
                                rv_emp_list.setAdapter(adapterMngrEmpList);
                                MngrMtrlList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterMngrEmpList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(MngrMtrlList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public  void onfocuschage(){
        selected_emp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                SPHelper.emp_selected="";
                search();
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
                    rl_emp_list.setVisibility(View.VISIBLE);
                    getMngrEmpList();

                }else{
                    hideKeybaord();
                    SPHelper.emp_selected="";
                    close_iv.setVisibility(View.GONE);
                    rl_emp_list.setVisibility(View.GONE);
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
        if(!Connectivity.isNetworkConnected(MngrMtrlList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress_bar1.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrMarketingMaterial(SPHelper.getSPData(MngrMtrlList.this,SPHelper.e_id,""),"","",
                   "",SPHelper.monthvalue_dashboard,SPHelper.yearvalue_dashboard,
                    search_mrkt_mtrl.getText().toString().trim(),"1",type,SPHelper.selected_eid);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    System.out.println("code"+response.code());
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progress_bar1.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            list=new ArrayList<>();
                            list=appResponse.getResponseModel().getManagerMarketingList();
                            if(list.isEmpty()){

                                rv_mtrlist.setVisibility(View.GONE);
                                noresults1.setVisibility(View.VISIBLE);
                                //noresults foung
                            }else{
                                rv_mtrlist.setVisibility(View.VISIBLE);
                                noresults1.setVisibility(View.GONE);
                                adapter = new AdapterMarketMtrlList(list, MngrMtrlList.this);
                                LinearLayoutManager layoutManager1 = new LinearLayoutManager(MngrMtrlList.this, LinearLayoutManager.VERTICAL, false);
                                rv_mtrlist.setLayoutManager(layoutManager1);
                                rv_mtrlist.setAdapter(adapter);
                                MngrMtrlList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else if(response.code() ==500)
                        {
                            progress_bar1.setVisibility(View.GONE);
                            rv_mtrlist.setVisibility(View.GONE);

                            Common.CallToast(MngrMtrlList.this,"  Error:"+response.code(),1);
                        }
                        else {
                            progress_bar1.setVisibility(View.GONE);
                            rv_mtrlist.setVisibility(View.GONE);
                            Common.CallToast(MngrMtrlList.this,"  Error:"+response.code(),1);
                        }
                    }
                    else{
                        progress_bar1.setVisibility(View.GONE);
                        rv_mtrlist.setVisibility(View.GONE);
                        Common.CallToast(MngrMtrlList.this,"  Error:"+response.code(),1);

                    }
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

    public static MngrMtrlList getInstance() {
        return instance;
    }


    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) MngrMtrlList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MngrMtrlList.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void oncancelSelect(View view) {
        hideKeybaord();
        search_mrkt_mtrl.setText("");
        cancel_btn.setVisibility(View.GONE);
    }
}