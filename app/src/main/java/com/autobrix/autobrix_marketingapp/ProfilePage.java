package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;

public class ProfilePage extends AppCompatActivity
{

    TextView yes,no,phoneno;
    Button button_logout;
     TextView employeeName, email,city,designation;
     String eid,ename,email_id,enumber,edesignation,ecityid;
    SharedPreferences emp_details;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        SPHelper.sharedPreferenceInitialization(ProfilePage.this);
        employeeName=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phoneno=findViewById(R.id.phoneno) ;
        back=findViewById(R.id.back);
        city=findViewById(R.id.city);
        designation=findViewById(R.id.designation);

        ename =SPHelper.getSPData(ProfilePage.this,SPHelper.e_name,"");
        email_id =SPHelper.getSPData(ProfilePage.this,SPHelper.e_email_id,"");
        enumber=SPHelper.getSPData(ProfilePage.this,SPHelper.e_number,"");
        edesignation=SPHelper.getSPData(ProfilePage.this,SPHelper.e_designation,"");
        ecityid=SPHelper.getSPData(ProfilePage.this,SPHelper.cityid,"");
        employeeName.setText(ename);
        email.setText(email_id);
        phoneno.setText(enumber);
        designation.setText(edesignation);
        if(ecityid.equals("1")){
            city.setText("BANGALORE");
        }
        else if(ecityid.equals("4")){
            city.setText("HYDERABAD");
        }

    Dialog dialog = new Dialog(ProfilePage.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.logout_dialog);
        dialog.setCancelable(true);

        yes=dialog.findViewById(R.id.yes) ;
        yes.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.is_otp_verified,"n");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_id,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_designation_id,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_number,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_name,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_email_id,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.e_designation,"");
                SPHelper.saveSPdata(ProfilePage.this,SPHelper.cityid,"");
                Intent i = new Intent(ProfilePage.this, SignInPage.class);
                startActivity(i);
                finish();
                dialog.dismiss();
            }
        });
        no=dialog.findViewById(R.id.no) ;
        no.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        button_logout=findViewById(R.id.button_logout);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent=new Intent(ProfilePage.this,HomePage.class);
                startActivity(intent);*/
                finish();
            }
        });
    }



}