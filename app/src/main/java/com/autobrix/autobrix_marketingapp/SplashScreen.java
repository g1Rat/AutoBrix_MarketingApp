package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
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

public class SplashScreen extends AppCompatActivity {
    String app_version;
    private ApiInterface apiInterface;
    private static final int SPLASH_DISPLAY_TIME = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SPHelper.sharedPreferenceInitialization(SplashScreen.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        if(SPHelper.getSPData(SplashScreen.this, SPHelper.is_otp_verified, "").equals("y"))
        {
            Intent intent=new Intent(SplashScreen.this,DashBoardPage.class);
            startActivity(intent);
            finish();
        }else {
            Intent intent1=new Intent(SplashScreen.this, SignInPage.class);
            startActivity(intent1);
            finish();
        }
       // update_app();
    }
     public void update_app(){
        if(!Connectivity.isNetworkConnected(SplashScreen.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Please Check Your Internet",
                    Toast.LENGTH_SHORT).show();
        }else
        {

            Call<AppResponse> call =  apiInterface.getapp_update_deatails("6","1");
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(@NotNull Call<AppResponse> call, @NotNull Response<AppResponse> response)
                {
                    AppResponse appResponse = response.body();
                    assert appResponse != null;
                    String response_code = appResponse.getResponseType();
                    if (response.body()!=null)
                    {
                        if (response_code.equals("200"))
                        {
                            SPHelper.app_url=appResponse.getResponseModel().getAppUpdateDetails().getApp_url();
                            SPHelper.can_skip=appResponse.getResponseModel().getAppUpdateDetails().getCan_skip();
                            app_version=appResponse.getResponseModel().getAppUpdateDetails().getAppversion();
                            appResponse.getResponseModel().getAppUpdateDetails().getIs_current();
                            System.out.println("appversion"+getString(R.string.app_version_name));
                            System.out.println("app_version"+app_version);

                            show_update();
                        }
                        else if (response_code.equals("300")) {
                            Toast.makeText(SplashScreen.this, appResponse.getResponseModel().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else{

                        Toast.makeText(SplashScreen.this, "internal server error" , Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<AppResponse> call, @NotNull Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();

                }
            });
        }
    }

    public void show_update()
    {
        new Handler().postDelayed(new Runnable() {
            public void run()
            {
                if(!app_version.equals(getString(R.string.app_version_name))){
                    Intent intent=new Intent(SplashScreen.this, NotificationPage.class);
                    startActivity(intent);
                }else{
                    if(SPHelper.getSPData(SplashScreen.this, SPHelper.is_otp_verified, "").equals("y"))
                    {
                        Intent intent=new Intent(SplashScreen.this,DashBoardPage.class);
                        startActivity(intent);
                        finish();
                    }else {
                        Intent intent1=new Intent(SplashScreen.this, SignInPage.class);
                        startActivity(intent1);
                        finish();
                    }
                }
            }
        }, SPLASH_DISPLAY_TIME);
    }
}