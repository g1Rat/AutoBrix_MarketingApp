package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterAddMtrl;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddMaterial;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddMtrlList;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialRequired;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateStatus;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMarketMtrl extends AppCompatActivity
{
    RelativeLayout select_dealer;
    ImageView back;
    RecyclerView rv_add_mtrlist;
    public AdapterAddMtrl adapter;
    public ArrayList<PojoMaterialRequired> list;
    public  ArrayList<PojoAddMtrlList> addMtrlLists;
    TextView tv_standard,tv_custom;
    View line1,line2;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    String material_type="1";
    TextView button_submit,tv_dealername;
    PojoAddMtrlList obj;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_market_mtrl);
        SPHelper.onstandard="y";
        line1=(View)findViewById(R.id.line1);
        line2=(View)findViewById(R.id.line2);
        tv_dealername=findViewById(R.id.tv_dealername);
        tv_standard=findViewById(R.id.tv_standard);
        button_submit=findViewById(R.id.button_submit);
        tv_custom=findViewById(R.id.tv_custom);
        select_dealer=findViewById(R.id.rl_dealer);
        back=findViewById(R.id.back);
        rv_add_mtrlist=findViewById(R.id.rv_add_mtrlist);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddMarketMtrl.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddMarketMtrl.this,MarketMtrlList.class);
                startActivity(intent);
            }
        });
        button_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addMtrlLists=new ArrayList<PojoAddMtrlList>();
                for (int i = 0; i <list.size(); i++)
                {
                    if(list.get(i).getTotal_count().equals("")){

                    }else{
                         obj=new PojoAddMtrlList();
                        obj.setComment(list.get(i).getSelected_comment());
                        obj.setMaterial_id(list.get(i).getId());
                        obj.setQuantity(list.get(i).getTotal_count());
                        addMtrlLists.add(obj);
                    }

                }
                System.out.println("addMtrlLists"+addMtrlLists);
                if(SPHelper.dealer_id.equals("")){
                    Common.CallToast(AddMarketMtrl.this,"Select dealer name",2);
                }else if(addMtrlLists.isEmpty()){
                    Common.CallToast(AddMarketMtrl.this,"Enter required material count",2);
                }
                else if(material_type.equals("1"))
                {
                    Boolean valid = true;

                    for (int i = 0; i <addMtrlLists.size(); i++)
                    {
                        if(addMtrlLists.get(i).getQuantity().startsWith("0"))
                        {
                            valid=false;
                            Toast.makeText(AddMarketMtrl.this,"Enter quantity other than 0",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if(valid){
                        add_marketing_mtrl();
                    }
                }else if(material_type.equals("2"))
                {
                    Boolean isValid=true;
                    for (int i = 0; i <addMtrlLists.size(); i++)
                    {
                        if(addMtrlLists.get(i).getQuantity().startsWith("0"))
                        {
                            isValid=false;
                            Toast.makeText(AddMarketMtrl.this,"Enter valid count",Toast.LENGTH_SHORT).show();
                            break;
                        }else if(addMtrlLists.get(i).getComment().equals("")){
                            isValid=false;
                            Toast.makeText(AddMarketMtrl.this,"Enter Comments",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }

                    if(isValid){
                        add_marketing_mtrl();
                    }
                }
            }
        });
        tv_dealername.setText(SPHelper.dealername);
        get_market_mtrl_list();
    }

    public void search_dealer(View view) {
        SPHelper.camefrom="add";
        Intent intent=new Intent(AddMarketMtrl.this,SearchDealer.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void onstandard_select(View view){
        material_type="1";
        SPHelper.onstandard="y";
        get_market_mtrl_list();
        tv_standard.setTextColor(Color.parseColor("#0619c3"));
        tv_custom.setTextColor(Color.parseColor("#D3D3D3"));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void oncustom_select(View view){

        SPHelper.onstandard="n";
        material_type="2";
        get_market_mtrl_list();
        tv_custom.setTextColor(Color.parseColor("#0619c3"));
        tv_standard.setTextColor(Color.parseColor("#D3D3D3"));
        line2.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        line1.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.lightgrey));
    }

    public void get_market_mtrl_list()
    {
        if(!Connectivity.isNetworkConnected(AddMarketMtrl.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.getMaterialList();
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null)
                    {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            list=new ArrayList();
                            list=appResponse.getResponseModel().getMaterialList();
                            adapter = new AdapterAddMtrl(list, AddMarketMtrl.this);
                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(AddMarketMtrl.this, LinearLayoutManager.VERTICAL, false);
                            rv_add_mtrlist.setLayoutManager(layoutManager1);
                            rv_add_mtrlist.setAdapter(adapter);
                            AddMarketMtrl.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.notifyDataSetChanged();
                                }
                            });
                        }
                        else
                        {
                            progressDialog.dismiss();
                            Toast.makeText(AddMarketMtrl.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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

    public  void get_mtrllist(){
        addMtrlLists=new ArrayList<PojoAddMtrlList>();
        for (int i = 0; i <list.size(); i++)
        {
            PojoAddMtrlList obj=new PojoAddMtrlList();
            obj.setComment(adapter.comments.getText().toString());
            obj.setMaterial_id(list.get(i).getId());
            obj.setQuantity(adapter.count_mtrl.getText().toString());
            addMtrlLists.add(obj);
            System.out.println("addMtrlLists"+addMtrlLists);
        }
    }
    public void add_marketing_mtrl()
    {

        if (!Connectivity.isNetworkConnected(AddMarketMtrl.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {
            PojoAddMaterial posts = new PojoAddMaterial(SPHelper.dealer_id,material_type,SPHelper.getSPData(AddMarketMtrl.this,SPHelper.e_id,"")
            ,"",addMtrlLists);

            Call<AppResponse> call = apiInterface.add_market_mtrls(posts);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.code() == 200) {
                        AppResponse response2=response.body();
                        Toast.makeText(AddMarketMtrl.this, "materials  addded", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(AddMarketMtrl.this,MarketMtrlList.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(AddMarketMtrl.this,"Server Error"+response.code(),Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        Intent intent=new Intent(AddMarketMtrl.this,MarketMtrlList.class);
        startActivity(intent);
        finish();
    }
}