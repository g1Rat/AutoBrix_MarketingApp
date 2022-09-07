package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddInspection;
import com.autobrix.autobrix_marketingapp.pojos.PojoVehInspectEligible;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddDealerRequest extends AppCompatActivity
{
    ProgressBar progress_bar;
    public String  inspection_type="Presale",locationType="",preserverdate,postserverdate ;
    RelativeLayout rl_car_count,rl_presale,rl_postsale,rl_dealership,rl_customer;
    RelativeLayout rl_post_sale,rl_customer_postsale,rl_veh_no;
    TextView tv_presale,tv_postsale,tv_dealership,tv_customer,add_car,tv_post_selecteddate;
    DatePickerDialog picker,date_picker;
    RelativeLayout rl4;
    ImageView back;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    EditText entered_carcount,selected_vehno,selected_name,selected_no,selected_customer_address,entered_location;
    RelativeLayout rl_inspection_on;
    String serverdate="",newdate="",monthvalue = "",yearvalue = "";
    TextView tv_inspection_on,submit_details,selectdealer;
    RadioButton check1,check2;
    String type="";
    Boolean ch1=false,ch2=false;
    ImageView selected,selected1,unselected,unselected1,iv_close;
    String MobilePattern = "^[6-9][0-9]{9}$";
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer_request);
        SPHelper.sharedPreferenceInitialization(AddDealerRequest.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddDealerRequest.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        progress_bar=findViewById(R.id.progress_bar);
        entered_location=findViewById(R.id.entered_location);
        iv_close=findViewById(R.id.iv_close);
        add_car=findViewById(R.id.add_car);
        selected_vehno=findViewById(R.id.selected_vehno);
        tv_post_selecteddate=findViewById(R.id.tv_post_selecteddate);
        selected_name=findViewById(R.id.selected_name);
        submit_details=findViewById(R.id.submit_details);
        selected_customer_address=findViewById(R.id.selected_customer_address);
        selected_no=findViewById(R.id.selected_no);
        selected= findViewById(R.id.selected);
        unselected= findViewById(R.id.unselected);
        selected1=findViewById(R.id.selected1);
        unselected1= findViewById(R.id.unselected1);
        rl_inspection_on=findViewById(R.id.rl_inspection_on);
        entered_carcount=findViewById(R.id.entered_carcount);
        back=findViewById(R.id.back);
        rl_car_count=findViewById(R.id.rl_car_count);
        rl4=findViewById(R.id.rl4);
        tv_inspection_on=findViewById(R.id.tv_inspection_on);
        tv_customer=findViewById(R.id.tv_customer);
        tv_dealership=findViewById(R.id.tv_dealership);
        rl_veh_no=findViewById(R.id.rl_veh_no);
        rl_dealership=findViewById(R.id.rl_dealership);
        rl_customer=findViewById(R.id.rl_customer);
        rl_postsale=findViewById(R.id.rl_postsale);
        rl_presale=findViewById(R.id.rl_presale);
        rl_customer_postsale=findViewById(R.id.rl_customer_postsale);
        rl_post_sale=findViewById(R.id.rl_post_sale);
        tv_postsale=findViewById(R.id.tv_postsale);
        tv_presale=findViewById(R.id.tv_presale);
        selectdealer=findViewById(R.id.selectdealer);
        selectdealer.setText(SPHelper.dealername);
        rl4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="insp";
                Intent intent=new Intent(AddDealerRequest.this,SearchDealer.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.camefrom="";
                SPHelper.dealer_id="";
                finish();
                finish();
            }
        });
        rl_inspection_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                monthvalue = "";
                yearvalue = "";
                newdate = "";
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                picker = new DatePickerDialog(AddDealerRequest.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                tv_inspection_on.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                preserverdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                            }
                        }, year, month, day);
                picker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                picker.show();
            }
        });
        add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPHelper.veh_no=selected_vehno.getText().toString();
                Intent intent=new Intent(AddDealerRequest.this,SelectCarBrand.class);
                startActivity(intent);
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected_vehno.setText("");
                hideKeybaord();
            }
        });

        if(SPHelper.inspection_type.equals("Presale")){
            inspection_type="Presale";
            rl_veh_no.setVisibility(View.GONE);
            rl_car_count.setVisibility(View.VISIBLE);
            rl_post_sale.setVisibility(View.GONE);
            add_car.setVisibility(View.GONE);
            submit_details.setVisibility(View.VISIBLE);
            rl_presale.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_presale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            tv_presale.setTextColor(Color.parseColor("#FFFFFF"));
            rl_postsale.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_postsale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
            tv_postsale.setTextColor(Color.parseColor("#FF000000"));
        }
        else if(SPHelper.inspection_type.equals("Postsale")){
            inspection_type="Postsale";
            rl_veh_no.setVisibility(View.VISIBLE);
            rl_car_count.setVisibility(View.GONE);
            submit_details.setVisibility(View.GONE);
            selected_vehno.setText(SPHelper.veh_no);
            search_vehicle_status();
            rl_postsale.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_postsale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
            tv_postsale.setTextColor(Color.parseColor("#FFFFFF"));
            rl_presale.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_presale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
            tv_presale.setTextColor(Color.parseColor("#FF000000"));
            rl_customer_postsale.setVisibility(View.GONE);
            rl_dealership.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_dealership.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
            tv_dealership.setTextColor(Color.parseColor("#FF000000"));
            rl_customer.setBackground(getDrawable(R.drawable.cardview_dealership));
            rl_customer.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
            tv_customer.setTextColor(Color.parseColor("#FF000000"));
        }
        if(SPHelper.camefrom.equals("add_car")){
            check_veh_inspect_eligibility();
        }
    }

    public  void onreqserviceselect(View view)
    {
        if(SPHelper.dealer_id.equals(""))
        {
            Toast.makeText(getApplicationContext(),
                    "Select dealer ",
                    Toast.LENGTH_SHORT).show();
        }else if(inspection_type.equals("Presale"))
        {
            if(entered_carcount.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(),
                        "Enter Car count ",
                        Toast.LENGTH_SHORT).show();
            }
            else if(entered_carcount.getText().toString().startsWith("0")){
                Toast.makeText(getApplicationContext(),
                        "Enter a number other than 0",
                        Toast.LENGTH_SHORT).show();
            }else if(tv_inspection_on.getText().equals("")){
                Toast.makeText(getApplicationContext(),
                        "select Inspection date ",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                serverdate=preserverdate;
                locationType="Dealership";
                servicecall_AddDealerRequest();
            }
        }
        else
        {
            if(locationType.equals("")){
                Common.CallToast(AddDealerRequest.this,"select location",1);
            }
            else if(locationType.equals("Dealership"))
            {
                if(selected_vehno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Vehicle Number",
                            Toast.LENGTH_SHORT).show();
                }
                else if(tv_inspection_on.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Inspection date",
                            Toast.LENGTH_SHORT).show();
                }else {
                    serverdate=preserverdate;
                    System.out.println("serverdate"+serverdate);
                    servicecall_AddDealerRequest();
                }
            }
            else
            {
                if(tv_inspection_on.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Inspection date",
                            Toast.LENGTH_SHORT).show();
                }
                else if (selected_name.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Customer name",
                            Toast.LENGTH_SHORT).show();
                } else if (selected_no.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Customer PhoneNo",
                            Toast.LENGTH_SHORT).show();
                } else if (selected_no.getText().toString().length() < 10) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }  else if (!selected_no.getText().toString().matches(MobilePattern)) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                } else if (entered_location.getText().toString().equals("")) {
                     Toast.makeText(getApplicationContext(),
                        "Please Enter Customer location",
                        Toast.LENGTH_SHORT).show();
                }
                else if (selected_customer_address.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Customer Address",
                            Toast.LENGTH_SHORT).show();
                } else if (selected_vehno.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Vehicle Number",
                            Toast.LENGTH_SHORT).show();
                }  else {
                    serverdate=preserverdate;
                    servicecall_AddDealerRequest();
                }
            }
        }
    }

    public void servicecall_AddDealerRequest()
    {

        if (!Connectivity.isNetworkConnected(AddDealerRequest.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.show();
            PojoAddInspection posts = new PojoAddInspection(SPHelper.dealer_id,SPHelper.veh_id,selected_vehno.getText().toString(),inspection_type,locationType,
                    entered_carcount.getText().toString(),serverdate,selected_name.getText().toString(),selected_no.getText().toString(),
                    selected_customer_address.getText().toString(),SPHelper.getSPData(AddDealerRequest.this,SPHelper.e_id,""),
                    entered_location.getText().toString());

            Call<AppResponse> call = apiInterface.add_Inspection(posts);
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.code() == 200) {
                        progressDialog.dismiss();
                        AppResponse response2=response.body();
                        SPHelper.monthvalue_reminder="";
                        Toast.makeText(AddDealerRequest.this, "inspection request added", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddDealerRequest.this, DealerRequests.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        progressDialog.dismiss();
                        Toast.makeText(AddDealerRequest.this,"Server Error"+response.code(),Toast.LENGTH_SHORT).show();
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


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void onpresaleselect(View view) {
        SPHelper.inspection_type="Presale";
        inspection_type="Presale";
        tv_inspection_on.setText("");
        entered_carcount.setText("");
        rl_veh_no.setVisibility(View.GONE);
        rl_car_count.setVisibility(View.VISIBLE);
        rl_post_sale.setVisibility(View.GONE);
        add_car.setVisibility(View.GONE);
        submit_details.setVisibility(View.VISIBLE);
        rl_presale.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_presale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        tv_presale.setTextColor(Color.parseColor("#FFFFFF"));
        rl_postsale.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_postsale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_postsale.setTextColor(Color.parseColor("#FF000000"));
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void onpostsaleselect(View view) {
        SPHelper.inspection_type="Postsale";
        inspection_type="Postsale";
        tv_inspection_on.setText("");
        selected_vehno.setText("");
        search_vehicle_status();
        rl_veh_no.setVisibility(View.VISIBLE);
        rl_car_count.setVisibility(View.GONE);
        submit_details.setVisibility(View.GONE);
        rl_postsale.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_postsale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        tv_postsale.setTextColor(Color.parseColor("#FFFFFF"));
        rl_presale.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_presale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_presale.setTextColor(Color.parseColor("#FF000000"));
        rl_customer_postsale.setVisibility(View.GONE);
        rl_dealership.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_dealership.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_dealership.setTextColor(Color.parseColor("#FF000000"));
        rl_customer.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_customer.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_customer.setTextColor(Color.parseColor("#FF000000"));

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void oncustomerselect(View view) {
        locationType="Customer";
        rl_customer_postsale.setVisibility(View.VISIBLE);
        rl_customer.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_customer.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        tv_customer.setTextColor(Color.parseColor("#FFFFFF"));
        rl_dealership.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_dealership.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_dealership.setTextColor(Color.parseColor("#FF000000"));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("UseCompatLoadingForDrawables")
    public void ondealershipselect(View view) {
        locationType="Dealership";
        rl_customer_postsale.setVisibility(View.GONE);
        rl_dealership.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_dealership.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
        tv_dealership.setTextColor(Color.parseColor("#FFFFFF"));
        rl_customer.setBackground(getDrawable(R.drawable.cardview_dealership));
        rl_customer.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
        tv_customer.setTextColor(Color.parseColor("#FF000000"));
    }
    public void search_vehicle_status(){
        selected_vehno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void afterTextChanged(Editable editable) {
                SPHelper.veh_no=selected_vehno.getText().toString();
                if(selected_vehno.getText().toString().trim().length()>=6)
                {
                    if(SPHelper.dealer_id.equals("")){
                        Common.CallToast(AddDealerRequest.this,"select dealer",1);
                    }else {
                        check_veh_inspect_eligibility();
                    }
                }
                if(selected_vehno.getText().toString().trim().length()<6)
                {
                    rl_post_sale.setVisibility(View.GONE);
                    add_car.setVisibility(View.GONE);
                    submit_details.setVisibility(View.GONE);
                    selected_vehno.setError(null);
                }
                if(selected_vehno.getText().toString().length()==0){
                    add_car.setVisibility(View.GONE);
                    submit_details.setVisibility(View.GONE);
                    iv_close.setVisibility(View.GONE);
                     selected_vehno.setError(null);
                }
                if(selected_vehno.getText().toString().length()>=2){
                    iv_close.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    public  void check_veh_inspect_eligibility(){
        {
            if(!Connectivity.isNetworkConnected(AddDealerRequest.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Plaese Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                progress_bar.setVisibility(View.VISIBLE);
                PojoVehInspectEligible post1=
                        new PojoVehInspectEligible(SPHelper.dealer_id,SPHelper.veh_no);
                Call<AppResponse> call =  apiInterface.check_veh_inspect_eligible(post1);
                call.enqueue(new Callback<AppResponse>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @SuppressLint("UseCompatLoadingForDrawables")
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                progress_bar.setVisibility(View.GONE);
                                String errormsg=appResponse.getResponseModel().getInspectionEligibility().getError_msg();
                                if(errormsg.equals(""))
                                {
                                    add_car.setVisibility(View.GONE);
                                    rl_post_sale.setVisibility(View.VISIBLE);
                                    submit_details.setVisibility(View.VISIBLE);
                                    rl_postsale.setBackground(getDrawable(R.drawable.cardview_dealership));
                                    rl_postsale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                                    tv_postsale.setTextColor(Color.parseColor("#FFFFFF"));
                                    rl_presale.setBackground(getDrawable(R.drawable.cardview_dealership));
                                    rl_presale.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.very_very_light_grey));
                                    tv_presale.setTextColor(Color.parseColor("#FF000000"));
                                    rl_customer_postsale.setVisibility(View.GONE);
                                }else{
                                    selected_vehno.setError(errormsg);
                                    if(appResponse.getResponseModel().getInspectionEligibility().getIs_vehicle_exists().equalsIgnoreCase("y")){
                                        add_car.setVisibility(View.GONE);
                                    }else{
                                        add_car.setVisibility(View.VISIBLE);
                                    }
                                    submit_details.setVisibility(View.GONE);
                                    rl_post_sale.setVisibility(View.GONE);

                                }
                            } else if (response_code.equals("300")) {
                                progress_bar.setVisibility(View.GONE);
                                Toast.makeText(AddDealerRequest.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else{
                            progress_bar.setVisibility(View.GONE);
                            Toast.makeText(AddDealerRequest.this, "internal server error" , Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                        Toast.makeText(getApplicationContext(),
                                t.getMessage(),
                                Toast.LENGTH_SHORT).show();
                        progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        }
    }

    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) AddDealerRequest.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(AddDealerRequest.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SPHelper.camefrom="";
        SPHelper.dealer_id="";
        finish();
    }

}