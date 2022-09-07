package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;

public class NotificationPage extends AppCompatActivity {
    RelativeLayout tv_AU_Update1;
    TextView tv_AU_skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_page);
        SPHelper.sharedPreferenceInitialization(NotificationPage.this);
        tv_AU_skip=findViewById(R.id.tv_AU_skip);
        tv_AU_Update1= findViewById(R.id.tv_AU_Update1);

        if(SPHelper.can_skip.equalsIgnoreCase("y")){
            tv_AU_skip.setVisibility(View.VISIBLE);
        }else {
            tv_AU_skip.setVisibility(View.GONE);
        }
        tv_AU_Update1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent httpIntent = new Intent(Intent.ACTION_VIEW);
                httpIntent.setData(Uri.parse(SPHelper.app_url));
                startActivity(httpIntent);
            }
        });
        tv_AU_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SPHelper.getSPData(NotificationPage.this, SPHelper.is_otp_verified, "").equals("y"))
                {
                    Intent intent=new Intent(NotificationPage.this,DashBoardPage.class);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent1=new Intent(NotificationPage.this, SignInPage.class);
                    startActivity(intent1);
                    finish();
                }
            }
        });
    }
}