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
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterCarModelLists;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddCar;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarModels;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectCarModel extends AppCompatActivity {

    RecyclerView rv_car_modellist;
    public AdapterCarModelLists adapterCarModelLists;
    public ArrayList<PojoAllCarModels> carModels;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    RelativeLayout rl_back;
    TextView add_car;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_car_model);
        SPHelper.sharedPreferenceInitialization(this);
        rv_car_modellist=findViewById(R.id.rv_car_modellist);
        rl_back=findViewById(R.id.rl_back);
        add_car=findViewById(R.id.add_car);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SelectCarModel.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        rl_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SelectCarModel.this, SelectCarBrand.class);
                startActivity(intent);
            }
        });
        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.carmodelid.equals("")){
                    Common.CallToast(SelectCarModel.this,"select model",1);
                }else{
                    add_car();
                }
            }
        });
        get_carmodel_list();
    }
    public  void get_carmodel_list()
    {
        {
            if (!Connectivity.isNetworkConnected(SelectCarModel.this)) {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            } else {
                progressDialog.show();
                Call<AppResponse> call = apiInterface.get_modellist(SPHelper.carbrandid);
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
                                carModels=new ArrayList<>();
                                carModels=appResponse.getResponseModel().getModelList();
                                adapterCarModelLists = new AdapterCarModelLists(carModels, getApplicationContext());
                                GridLayoutManager layoutManager = new GridLayoutManager(SelectCarModel.this,3);
                                rv_car_modellist.setLayoutManager(layoutManager);
                                rv_car_modellist.setAdapter(adapterCarModelLists);
                                SelectCarModel.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterCarModelLists.notifyDataSetChanged();
                                    }
                                });
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                            }
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SelectCarModel.this, "internal server error", Toast.LENGTH_SHORT).show();
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

    public void add_car() {
        if (!Connectivity.isNetworkConnected(SelectCarModel.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {
            PojoAddCar posts = new PojoAddCar(SPHelper.dealer_id,SPHelper.carmodelid,"","","",SPHelper.veh_no +
                    "","","","","","Y","","","","",
                    SPHelper.getSPData(SelectCarModel.this,SPHelper.e_id,"") );

            Call<AppResponse> call = apiInterface.add_car_details(posts);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.code() == 200) {
                        AppResponse response2=response.body();
                        Toast.makeText(SelectCarModel.this, "vehicle added", Toast.LENGTH_SHORT).show();
                        SPHelper.camefrom="add_car";
                        Intent intent = new Intent(SelectCarModel.this, AddDealerRequest.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(SelectCarModel.this,"Server Error"+response.code(),Toast.LENGTH_SHORT).show();
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
}