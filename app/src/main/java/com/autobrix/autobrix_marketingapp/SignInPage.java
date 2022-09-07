package com.autobrix.autobrix_marketingapp;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInPage extends AppCompatActivity
{
    TextView tv_login,tv_sendotp,tv_signup,tv_otp,tv_resend;
    EditText selected_mobileno,otp1,otp2,otp3,otp4;
    private ApiInterface apiInterface;
    private ProgressDialog progressDialog;
    String otps;
    Integer textlength1,textlength2,textlength3;
    LinearLayout otp_ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        SPHelper.sharedPreferenceInitialization(SignInPage.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(SignInPage.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        otp1=findViewById(R.id.otp1);
        otp2=findViewById(R.id.otp2);
        otp3=findViewById(R.id.otp3);
        otp4=findViewById(R.id.otp4);
        otp_ll=(LinearLayout)findViewById(R.id.otp_ll);
        selected_mobileno=findViewById(R.id.selected_mobileno);
        tv_otp=findViewById(R.id.tv_otp);

        tv_login=findViewById(R.id.tv_login);
        tv_sendotp=findViewById(R.id.tv_sendotp);
        tv_resend=findViewById(R.id.tv_resend);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Your PhoneNo",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    verify_otp();
                }
            }
        });
        tv_sendotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    send_otp();
                }

            }
        });
        tv_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp_ll.setVisibility(View.VISIBLE);
                tv_otp.setVisibility(View.VISIBLE);
                tv_resend.setVisibility(View.VISIBLE);
                //tv_sendotp.setVisibility(View.GONE);
                if(selected_mobileno.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter your Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else if(selected_mobileno.getText().toString().length()<10){
                    Toast.makeText(getApplicationContext(),
                            "Please Enter Valid Phone Number",
                            Toast.LENGTH_SHORT).show();
                }else{
                    otp1.setText("");
                    otp2.setText("");
                    otp3.setText("");
                    otp4.setText("");
                    otp1.requestFocus();
                    send_otp();
                }

            }
        });

        selected_mobileno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()==10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(selected_mobileno.getText().toString().length()==10){
                    tv_sendotp.setVisibility(View.VISIBLE);
                }
            }

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_mobileno.getText().toString().length()<10){
                    tv_sendotp.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_sendotp.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.greywhite));
                    tv_sendotp.setTextColor(Color.parseColor("#0619c3"));
                    tv_login.setVisibility(View.GONE);
                    otp_ll.setVisibility(View.GONE);
                    tv_otp.setVisibility(View.GONE);
                    tv_resend.setVisibility(View.GONE);

                }else{
                    hideKeybaord();
                    tv_sendotp.setVisibility(View.VISIBLE);
                    tv_sendotp.setBackground(getApplicationContext().getDrawable(R.drawable.cardview_dealership));
                    tv_sendotp.setBackgroundTintList(ContextCompat.getColorStateList(getApplicationContext(), R.color.blue));
                    tv_sendotp.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

            }
        });

        otp1.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }
        });
        otp2.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength2 = otp2.getText().length();

                if (textlength2 >= 1) {
                    otp3.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub


            }
        });
        otp3.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });
        otp4.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                Integer textlength4 = otp3.getText().length();

                if (textlength4 >= 1) {
                    tv_login.setVisibility(View.VISIBLE);
                    hideKeybaord();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub
            }
        });


       /* otp4.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp4.setText("");
                    otp3.requestFocus();
                }
                Integer textlength4 = otp4.getText().length();
                if (textlength4 >= 1) {
                    tv_login.setVisibility(View.VISIBLE);

                }
                return false;
            }
        });
        otp3.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp3.setText("");
                    otp2.requestFocus();
                }
                textlength3 = otp3.getText().length();

                if (textlength3 >= 1) {
                    otp4.requestFocus();
                }
                return false;
            }
        });
        otp2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp2.setText("");
                    otp1.requestFocus();
                }
                textlength2 = otp2.getText().length();
                if (textlength2 >= 1) {
                    otp3.requestFocus();
                }
                return false;
            }
        });
        otp1.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //You can identify which key pressed buy checking keyCode value with KeyEvent.KEYCODE_
                if(keyCode == KeyEvent.KEYCODE_DEL) {
                    //this is for backspace
                    otp1.setText("");
                    otp1.requestFocus();
                }
                textlength1 = otp1.getText().length();
                if (textlength1 >= 1) {
                    otp2.requestFocus();
                }
                return false;
            }
        });*/
    }
    public void send_otp() {
        if(!Connectivity.isNetworkConnected(SignInPage.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Plaese Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {
            tv_sendotp.setVisibility(View.GONE);
            progressDialog.show();
            Call<AppResponse> call =  apiInterface.getOtp(selected_mobileno.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200")) {
                            progressDialog.dismiss();
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.role_id, appResponse.getResponseModel().getEmployeeData().getEmployee_role_id());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_id, appResponse.getResponseModel().getEmployeeData().getEmployee_id());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_name, appResponse.getResponseModel().getEmployeeData().getEmployee_name());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_email_id, appResponse.getResponseModel().getEmployeeData().getEmail_id());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_designation, appResponse.getResponseModel().getEmployeeData().getEmployee_role());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.cityid, appResponse.getResponseModel().getEmployeeData().getCity_id());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_number, selected_mobileno.getText().toString());
                            //get aws credential
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.awssecret, appResponse.getResponseModel().getCredential().getAws_secret());
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.awskey, appResponse.getResponseModel().getCredential().getAws_key());
                            Toast.makeText(SignInPage.this, "otp sent", Toast.LENGTH_SHORT).show();
                            otp_ll.setVisibility(View.VISIBLE);
                            tv_otp.setVisibility(View.VISIBLE);
                            tv_resend.setVisibility(View.VISIBLE);
                            otp1.requestFocus();
                        } else if (response_code.equals("300")) {
                            progressDialog.dismiss();
                            SPHelper.saveSPdata(SignInPage.this, SPHelper.e_id, "");
                            Toast.makeText(SignInPage.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                            selected_mobileno.setText("");
                        }
                    } else{
                        progressDialog.dismiss();
                        Toast.makeText(SignInPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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
    public  void verify_otp(){
        {
            otps=otp1.getText().toString()+otp2.getText().toString()+otp3.getText().toString()+otp4.getText().toString();
            if(!Connectivity.isNetworkConnected(SignInPage.this))
            {
                Toast.makeText(getApplicationContext(),
                        "Please Check Your Internet",
                        Toast.LENGTH_SHORT).show();
            }else
            {
                progressDialog.show();
                Call<AppResponse> call =  apiInterface.verifyOtp(selected_mobileno.getText().toString().trim(),otps);
                call.enqueue(new Callback<AppResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                    {
                        AppResponse appResponse = response.body();
                        assert appResponse != null;
                        String response_code = appResponse.getResponseType();
                        if (response.body()!=null)
                        {
                            if (response_code.equals("200")) {
                                progressDialog.dismiss();
                                SPHelper.saveSPdata(SignInPage.this,SPHelper.is_otp_verified,"y");
                                Intent intent=new Intent(SignInPage.this,DashBoardPage.class);
                                startActivity(intent);
                                finish();
                            } else if (response_code.equals("300")) {
                                progressDialog.dismiss();
                                SPHelper.saveSPdata(SignInPage.this,SPHelper.is_otp_verified,"n");
                                Toast.makeText(SignInPage.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                                otp1.setText("");
                                otp2.setText("");
                                otp3.setText("");
                                otp4.setText("");
                                otp1.requestFocus();
                                tv_login.setVisibility(View.GONE);
                                tv_sendotp.setVisibility(View.GONE);
                            }
                        } else{
                            progressDialog.dismiss();
                            Toast.makeText(SignInPage.this, "internal server error" , Toast.LENGTH_SHORT).show();
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

    //    @Override
//    public boolean onKeyUp(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_TAB) editText1.tab();
//        if(event.isCtrlPressed())
//        {
//            if(keyCode == KeyEvent.KEYCODE_O) // ctrl+o
//            {
//                onClick(open);
//            }
//            else if(keyCode == KeyEvent.KEYCODE_N) // ctrl+n
//            {
//                addFileTab(null);                   // add untitled tab
//                editText1.setText("");
//            }
//            else if(keyCode == KeyEvent.KEYCODE_A) // ctrl+a
//            {
//                editText1.selectAll();
//            }
//            else if(keyCode == KeyEvent.KEYCODE_S) // ctrl+s
//            {
//                onClick(save);
//            }
//            else if(keyCode == KeyEvent.KEYCODE_C) // ctrl+c
//            {
//                String selected = editText1.selection();
//                ClipData clip;
//                if(current != null)
//                    clip = ClipData.newPlainText(current.getName() + " selection", selected);
//                else
//                    clip = ClipData.newPlainText("Cerulean selection", selected);
//                clipMan.setPrimaryClip(clip);
//            }
//            else if(keyCode == KeyEvent.KEYCODE_P && clipMan.hasPrimaryClip()) // ctrl+p
//            {
//                if(clipMan.getPrimaryClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN))
//                {
//                    CharSequence clip = clipMan.getPrimaryClip().getItemAt(0).coerceToText(getBaseContext());
//                    editText1.insert(clip.toString());
//                }
//            }
//            return false;
//        }
//        if(keyCode == KeyEvent.KEYCODE_SPACE || keyCode == KeyEvent.KEYCODE_ENTER)
//        {
//            onTabClicked(current, curTab);
//        }
//        return super.onKeyUp(keyCode, event);
//    }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) SignInPage.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(SignInPage.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
    }

}