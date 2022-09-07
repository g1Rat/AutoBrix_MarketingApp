package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
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
import com.autobrix.autobrix_marketingapp.adapters.AdapterSeachDealerList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealers;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDealer extends AppCompatActivity
{
    Button next;
    TextView noresults;
    ImageView imageView,search_bar_img,cancel_btn;
    RecyclerView recyclerView;
    EditText search_dealer;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    public ArrayList<PojoDealers> dealerlist;
   public AdapterSeachDealerList adapterDealerList;
   ProgressBar progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_dealer);
        dealerlist = new ArrayList<PojoDealers>();
        next=findViewById(R.id.next) ;
        progress=(ProgressBar)findViewById(R.id.progress);
        noresults=findViewById(R.id.noresults);
        search_dealer=findViewById(R.id.search_dealer);
        cancel_btn= findViewById(R.id.cancel_btn);
        search_bar_img= findViewById(R.id.search_bar_img);
        imageView= findViewById(R.id.iv1);
        recyclerView=findViewById(R.id.rv1) ;

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SearchDealer.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               finish();
            }
        });
        search();
        servicecall_emp_dealer();
    }

    public void servicecall_dealer()
    {
       // SPHelper.mydealers="n";
        if(!Connectivity.isNetworkConnected(SearchDealer.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.getDealerList("","","",search_dealer.getText().toString().trim(),"1","","","");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getDealerList().isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                            }
                            dealerlist=new ArrayList();//list clear
                            dealerlist = appResponse.getResponseModel().getDealerList();
                            adapterDealerList = new AdapterSeachDealerList(dealerlist, SearchDealer.this);
                            LinearLayoutManager layoutManager2 = new LinearLayoutManager(SearchDealer.this, LinearLayoutManager.VERTICAL, false);
                            recyclerView.setLayoutManager(layoutManager2);
                            recyclerView.setAdapter(adapterDealerList);
                            SearchDealer.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapterDealerList.notifyDataSetChanged();
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SearchDealer.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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

    public void servicecall_emp_dealer()
    {
       // SPHelper.mydealers="y";
        if(!Connectivity.isNetworkConnected(SearchDealer.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            //progressDialog.show();
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.get_emp_DealerList("","",
                    "", search_dealer.getText().toString().trim(),"1","",
                    SPHelper.getSPData(SearchDealer.this,SPHelper.e_id,""));
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response)
                {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();
                            if(appResponse.getResponseModel().getEmployeeDealerList().isEmpty()){
                                noresults.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
                            }else{
                                noresults.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                dealerlist=new ArrayList();//list clear
                                dealerlist = appResponse.getResponseModel().getEmployeeDealerList();
                                adapterDealerList = new AdapterSeachDealerList(dealerlist, SearchDealer.this);
                                LinearLayoutManager layoutManager2 = new LinearLayoutManager(SearchDealer.this, LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(layoutManager2);
                                recyclerView.setAdapter(adapterDealerList);
                                SearchDealer.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterDealerList.notifyDataSetChanged();
                                    }
                                });
                            }

                        }
                        else
                        {
                            Toast.makeText(SearchDealer.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                   // servicecall_dealer();
                    servicecall_emp_dealer();
                }
            }
        });
    }
    public  void oncancelSelect(View view){
        hideKeybaord(view);
        search_dealer.setText("");
        search_bar_img.setVisibility(View.VISIBLE);
        cancel_btn.setVisibility(View.GONE);
        servicecall_emp_dealer();

    }
    private void hideKeybaord(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
    }
}