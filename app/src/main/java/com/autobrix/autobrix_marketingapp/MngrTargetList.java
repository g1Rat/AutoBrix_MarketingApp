package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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

import com.autobrix.autobrix_marketingapp.adapters.AdapterEmpTargetList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MngrTargetList extends AppCompatActivity
{
    private ApiInterface apiInterface;
    public ArrayList<PojoSalesList> targetLists;
    public AdapterEmpTargetList adapterEmpTargetList;
    RecyclerView rv_emplist;
    RelativeLayout rl_back;
    MonthYearPicker pd ;
    TextView month_picker,noresults;
    EditText search_emp;
    ImageView search_bar_img,cancel_btn;
    private DatePickerDialog.OnDateSetListener listener;
    SimpleDateFormat month_date,year_date,month_date1;
    public String ma,serveryear,eid,edesignationid,monthvalue,yearvalue,servermonth,cityid;
    Calendar cal;
    ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mngr_target_list);
        progress=findViewById(R.id.progress);
        noresults=findViewById(R.id.noresults);
        rv_emplist= findViewById(R.id.rv_emplist);
        rl_back=findViewById(R.id.rl_back);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        month_picker=findViewById(R.id.month_picker);
        search_emp=findViewById(R.id.search_emp);
        targetLists=new ArrayList<>();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MngrTargetList.this,DashBoardPage.class);
                startActivity(intent);
            }
        });
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        month_picker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.picker_month="target";
                pd=new MonthYearPicker();
                pd.setListener(listener);
                pd.show(getSupportFragmentManager(), "MonthPicker");
            }
        });
        getMngrEmpList();
        search();
    }

    public void search(){
        search_emp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if(search_emp.getText().toString().trim().length()>=1)
                {
                    rv_emplist.setVisibility(View.VISIBLE);
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    getMngrEmpList();
                }else {
                    hideKeybaord();
                    cancel_btn.setVisibility(View.GONE);
                    rv_emplist.setVisibility(View.GONE);
                    noresults.setVisibility(View.GONE);
                    getMngrEmpList();
                }
            }
        });
    }

    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) MngrTargetList.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(MngrTargetList.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
    public void getMngrEmpList()
    {
        if (SPHelper.sales_mv.equals("")) {
            SPHelper.monthvalue = servermonth;
            SPHelper.yearvalue = serveryear;
            month_picker.setText(ma+"-"+serveryear);
        } else{
            SPHelper.monthvalue=SPHelper.sales_mv;
            SPHelper.yearvalue=SPHelper.sales_yv;
        }
        if(!Connectivity.isNetworkConnected(MngrTargetList.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrEmpList(search_emp.getText().toString(),SPHelper.getSPData(MngrTargetList.this,SPHelper.e_id,""),
                    "", "","",SPHelper.monthvalue, SPHelper.yearvalue);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();

                            targetLists=new ArrayList<>();
                            targetLists=appResponse.getResponseModel().getManagerEmployeeList();
                            if(targetLists.isEmpty()){
                                rv_emplist.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                noresults.setVisibility(View.GONE);
                                rv_emplist.setVisibility(View.VISIBLE);
                                adapterEmpTargetList=new AdapterEmpTargetList(targetLists, MngrTargetList.this);
                                GridLayoutManager layoutManager = new GridLayoutManager(MngrTargetList.this, 2);
                                rv_emplist.setLayoutManager(layoutManager);
                                rv_emplist.setAdapter(adapterEmpTargetList);
                                MngrTargetList.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterEmpTargetList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(MngrTargetList.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
        cancel_btn.setVisibility(View.GONE);
        search_emp.setText("");
        hideKeybaord();
    }
}