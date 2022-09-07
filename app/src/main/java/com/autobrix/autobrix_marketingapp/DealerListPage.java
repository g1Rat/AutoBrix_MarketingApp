package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterDealerList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealers;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DealerListPage extends AppCompatActivity
{
    public String d_id="";
    Intent intent1;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    EditText search_dealer;
    public ArrayList<PojoDealers> dealerlist;
    RecyclerView recyclerView;
    public AdapterDealerList adapter;
    ImageView back,search_bar_img,cancel_btn;
    FloatingActionButton add_dealer;
    Button next;
    public TextView noresults;
    public String eid;
    Calendar cal;
    SimpleDateFormat month_date,year_date,month_date1;
    String ma,serveryear,servermonth;
    public String monthvalue,displaymonth;
    public String yearvalue;
    ProgressBar progress;
    private DatePickerDialog.OnDateSetListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_list_page);
         dealerlist = new ArrayList<PojoDealers>();
        progress=(ProgressBar)findViewById(R.id.progress);
        noresults=findViewById(R.id.noresults);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        search_dealer= findViewById(R.id.search_dealer) ;
        back= findViewById(R.id.iv1);
        recyclerView=findViewById(R.id.rv1) ;
        add_dealer=(FloatingActionButton)findViewById(R.id.iv2);
        next=findViewById(R.id.next) ;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(DealerListPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        if(SPHelper.getSPData(DealerListPage.this,SPHelper.role_id,"").equals("190")){
            add_dealer.setVisibility(View.GONE);
            servicecall_mngr_dealer();
        }
        else{
            servicecall_emp_dealer();
            add_dealer.setVisibility(View.VISIBLE);
        }
        add_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2=new Intent(DealerListPage.this,AddDealer.class);
                startActivity(intent2);
                finish();
            }
        });
         back.setOnClickListener(new View.OnClickListener()
         {@Override
             public void onClick(View view) {
             finish();
             }
         });
         search();
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
                if(search_dealer.getText().toString().trim().length()>=1)
                {
                    recyclerView.setVisibility(View.VISIBLE);
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    if(SPHelper.getSPData(DealerListPage.this,SPHelper.role_id,"").equals("190")){
                        servicecall_mngr_dealer();
                    }
                    else{
                        servicecall_emp_dealer();
                    }
                }else {
                    hideKeybaord();
                    recyclerView.setVisibility(View.GONE);
                    noresults.setVisibility(View.GONE);
                    if(SPHelper.getSPData(DealerListPage.this,SPHelper.role_id,"").equals("190")){
                        servicecall_mngr_dealer();
                    }
                    else{
                        servicecall_emp_dealer();
                    }
                }
            }
        });
    }


    public void servicecall_emp_dealer()
    {
        //SPHelper.mydealers="y";
        if(search_dealer.getText().toString().equals("")){
             d_id=SPHelper.getSPData(DealerListPage.this,SPHelper.e_id,"");
        }else{
             d_id="";
        }
        if(!Connectivity.isNetworkConnected(DealerListPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_emp_DealerList("","",
                    "", search_dealer.getText().toString().trim(),"1","", d_id);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getEmployeeDealerList().isEmpty()){
                                recyclerView.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                dealerlist=new ArrayList();//list clear
                                dealerlist = appResponse.getResponseModel().getEmployeeDealerList();
                                adapter = new AdapterDealerList(dealerlist, DealerListPage.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(DealerListPage.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager2);
                                recyclerView.setAdapter(adapter);
                                DealerListPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(DealerListPage.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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

    public void servicecall_mngr_dealer()
    {
        //SPHelper.mydealers="n";
        d_id="x";
        if(!Connectivity.isNetworkConnected(DealerListPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrDealerList(search_dealer.getText().toString(),
                    SPHelper.getSPData(DealerListPage.this,SPHelper.e_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getManagerDealerList().isEmpty()){
                                recyclerView.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                recyclerView.setVisibility(View.VISIBLE);
                                dealerlist=new ArrayList();//list clear
                                dealerlist = appResponse.getResponseModel().getManagerDealerList();
                                adapter = new AdapterDealerList(dealerlist, DealerListPage.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(DealerListPage.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager2);
                                recyclerView.setAdapter(adapter);
                                DealerListPage.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(DealerListPage.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    public  void oncancelSelect(View view){
        hideKeybaord();
        search_dealer.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);

    }
    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) DealerListPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(DealerListPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finish();
    }
}

