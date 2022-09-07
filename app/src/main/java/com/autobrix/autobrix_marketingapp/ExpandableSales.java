package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.adapters.AdapterExpandable;
import com.autobrix.autobrix_marketingapp.adapters.AdapterSalesList;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
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

public class ExpandableSales extends AppCompatActivity {
    private ApiInterface apiInterface;
    ArrayList<PojoSalesList> pojoSales;
    public AdapterExpandable adapterSalesList;
    ArrayList<PojoEmpDealerList> pojoEmpDealerLists;
    //RecyclerView rv_emplist;
    ProgressBar progress;
    RelativeLayout rl_back;
    EditText search_emp;
    ImageView search_bar_img,cancel_btn;
    TextView noresults,month_picker;
    private static MarketMtrlList instance;
    MonthYearPicker pd ;
    private DatePickerDialog.OnDateSetListener listener;
    SimpleDateFormat month_date,year_date,month_date1;
    public String ma,serveryear,eid,edesignationid,monthvalue,yearvalue,servermonth,cityid;
    Calendar cal;
    ExpandableListView expandableListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expandable_listt_view);
        expandableListView=findViewById(R.id.expandableListView);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        rl_back=findViewById(R.id.rl_back);
        noresults=findViewById(R.id.noresults);
        search_bar_img=findViewById(R.id.search_bar_img);
        cancel_btn=findViewById(R.id.cancel_btn);
        search_emp=findViewById(R.id.search_emp);
        progress=findViewById(R.id.progress);
        month_picker=findViewById(R.id.month_picker);
        cal= Calendar.getInstance();
        month_date = new SimpleDateFormat("MMM");
        year_date = new SimpleDateFormat("yyyy");
        month_date1=new SimpleDateFormat("MM");
        ma=month_date.format(cal.getTime());
        serveryear=year_date.format(cal.getTime());
        servermonth=month_date1.format(cal.getTime());
        rl_back.setOnClickListener(view -> {
            Intent intent =new Intent(ExpandableSales.this,DashBoardPage.class);
            startActivity(intent);
        });
        month_picker.setOnClickListener(view -> {
            SPHelper.picker_month="sales";
            pd=new MonthYearPicker();
            pd.setListener(listener);
            pd.show(getSupportFragmentManager(), "MonthPicker");
        });
        getMngrEmpList();
        search();

        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        pojoSales.get(groupPosition) + " List Expanded.",
                        Toast.LENGTH_SHORT).show();
                adapterSalesList.rl_list.setVisibility(View.VISIBLE);
            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        pojoSales.get(groupPosition) + " List Collapsed.",
                        Toast.LENGTH_SHORT).show();
                adapterSalesList.rl_list.setVisibility(View.GONE);

            }
        });

//        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//                Toast.makeText(
//                        getApplicationContext(),
//                        pojoSales.get(groupPosition)
//                                + " -> "
//                                + pojoEmpDealerLists.get(
//                                childPosition), Toast.LENGTH_SHORT
//                ).show();
//                return false;
//            }
//        });
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
                    expandableListView.setVisibility(View.VISIBLE);
                    search_bar_img.setVisibility(View.GONE);
                    cancel_btn.setVisibility(View.VISIBLE);
                    getMngrEmpList();
                }else {
                    expandableListView.setVisibility(View.GONE);
                    noresults.setVisibility(View.GONE);
                    hideKeybaord();
                    getMngrEmpList();
                }
            }
        });
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
        if(!Connectivity.isNetworkConnected(ExpandableSales.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progress.setVisibility(View.VISIBLE);
            Call<AppResponse> call = apiInterface.getMngrSalesList(search_emp.getText().toString(),SPHelper.getSPData(ExpandableSales.this,SPHelper.e_id,""),
                    "", "","",SPHelper.monthvalue, SPHelper.yearvalue);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progress.setVisibility(View.GONE);
                            AppResponse appResponse = response.body();

                            pojoSales=new ArrayList<>();
                            pojoSales=appResponse.getResponseModel().getManagerTargetList();
                            pojoEmpDealerLists=new ArrayList<>();
                               for(int i=0;i<pojoSales.size();i++){
                                   pojoEmpDealerLists=pojoSales.get(i).getDealerList();
                               }

                            if(pojoSales.isEmpty()){
                                expandableListView.setVisibility(View.GONE);
                                noresults.setVisibility(View.VISIBLE);
                            }else
                            {
                                noresults.setVisibility(View.GONE);
                                expandableListView.setVisibility(View.VISIBLE);
                                adapterSalesList = new AdapterExpandable(ExpandableSales.this, pojoSales, pojoEmpDealerLists);
                                expandableListView.setAdapter(adapterSalesList);

                                ExpandableSales.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        adapterSalesList.notifyDataSetChanged();
                                    }
                                });
                            }
                        }
                        else
                        {
                            Toast.makeText(ExpandableSales.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
        search_emp.setText("");
        hideKeybaord();
    }

    public void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) ExpandableSales.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(ExpandableSales.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }
}