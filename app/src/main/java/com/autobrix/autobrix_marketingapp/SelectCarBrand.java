package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterCarBrandList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarBrands;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarBrand extends AppCompatActivity {
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    ImageView go_back_home;
    RecyclerView rv_car_makelist;
    RelativeLayout rl_back;
    public AdapterCarBrandList adapterCarBrandList;
    public ArrayList<PojoAllCarBrands> allCarBrands;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_brand);
        SPHelper.sharedPreferenceInitialization(this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SelectCarBrand.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rv_car_makelist=findViewById(R.id.rv_car_makelist);
        rl_back=findViewById(R.id.rl_back);

        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });
        get_carbrands_list();
    }
    public  void get_carbrands_list() {
        {
            if (!Connectivity.isNetworkConnected(SelectCarBrand.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_brandlist();
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response) {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body() != null) {
                            if (response_code.equals("200"))
                            {
                                progressDialog.dismiss();
                                allCarBrands=new ArrayList<>();
                                allCarBrands=appResponse.getResponseModel().getBrandList();
                                adapterCarBrandList = new AdapterCarBrandList(allCarBrands, getApplicationContext());
                                GridLayoutManager layoutManager = new GridLayoutManager(SelectCarBrand.this,3);
                                rv_car_makelist.setLayoutManager(layoutManager);
                                rv_car_makelist.setAdapter(adapterCarBrandList);
                                SelectCarBrand.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterCarBrandList.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SelectCarBrand.this, "internal server error", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }
}