package com.autobrix.autobrix_marketingapp;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.Pair;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.autobrix.autobrix_marketingapp.adapters.AdapterDealerList;
import com.autobrix.autobrix_marketingapp.common_classes.BitmapUtility;
import com.autobrix.autobrix_marketingapp.common_classes.Common;
import com.autobrix.autobrix_marketingapp.common_classes.Connectivity;
import com.autobrix.autobrix_marketingapp.common_classes.RequestPermissionHandler;
import com.autobrix.autobrix_marketingapp.common_classes.SPHelper;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddDealers;
import com.autobrix.autobrix_marketingapp.services.ApiClient;
import com.autobrix.autobrix_marketingapp.services.ApiInterface;
import com.bumptech.glide.Glide;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class AddDealer extends AppCompatActivity
{
    String filename;
    private Uri imageUri;
    public  String dealerlogourl ="";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private RequestPermissionHandler mRequestPermissionHandler;
    private static AddDealer instance;
    EditText d_name, d_phone, d_location, no_of_soldcars, line1, pincode, city, state,
            owner_name, email, manager_name, manager_no;
     public  TextView title;
     public  Intent intent,intent3;
    ImageView cancel_btn;
    String sellcars,d_city, d_state, d_pincode,senddata="me";
    String IseditAccessable="";
    public String SellLuxuryCars;
    CheckBox checkbox;
    Button button;
    ImageView backbutton,dealer_logo;
    Dialog dialog;
    TextView yes,no,check_dealer;
    ApiInterface apiInterface;
    ProgressDialog progressDialog;
    EditText selected_no;
    NestedScrollView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);
        SPHelper.sharedPreferenceInitialization(AddDealer.this);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        progressDialog = new ProgressDialog(AddDealer.this);
        progressDialog.setMessage("Please wait...");
        progressDialog.setCancelable(false);
        AWSMobileClient.getInstance().initialize(AddDealer.this).execute();
        credentials = new BasicAWSCredentials(SPHelper.getSPData(AddDealer.this,SPHelper.awskey,""), SPHelper.getSPData(AddDealer.this,SPHelper.awssecret,""));
        s3Client = new AmazonS3Client(credentials);
        mRequestPermissionHandler = new RequestPermissionHandler();
        sv= findViewById(R.id.sv);
        check_dealer=findViewById(R.id.check_dealer);
        selected_no=findViewById(R.id.selected_no);
        backbutton = findViewById(R.id.iv3);
        button =  findViewById(R.id.button_submit);
        title=findViewById(R.id.title);
        cancel_btn=findViewById(R.id.cancel_btn);
        d_name = findViewById(R.id.d_name);
        d_phone = findViewById(R.id.d_phone);
        no_of_soldcars = findViewById(R.id.no_of_cars);
        d_location=findViewById(R.id.d_location);
        line1 = findViewById(R.id.line1);
        pincode =  findViewById(R.id.pincode);
        city =  findViewById(R.id.city);
        state = findViewById(R.id.state);
        owner_name =  findViewById(R.id.owner_name);
        email = findViewById(R.id.email);
        manager_name = findViewById(R.id.manager_name);
        manager_no = findViewById(R.id.manager_no);
        checkbox =  findViewById(R.id.checkbox);
        dealer_logo = findViewById(R.id.dealer_logo);
        instance=this;
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (checkbox.isChecked())
                {
                    sellcars = "Y";
                } else {
                    sellcars = "N";
                }
            }
        });

        if(SellLuxuryCars==null){

            sellcars="N";
        }
        else if(SellLuxuryCars.equalsIgnoreCase("y")){
            checkbox.setChecked(true);
            sellcars="Y";
        }
        else {
            checkbox.setChecked(false);
                    sellcars="N";
        }

       // pincode.addTextChangedListener(textWatcher);
        dialog = new Dialog(AddDealer.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog);
        dialog.setCancelable(true);
        yes = dialog.findViewById(R.id.yes);
        no = dialog.findViewById(R.id.no);
        if(IseditAccessable==null){

        }
         else  if(IseditAccessable.equalsIgnoreCase("y")){
            d_phone.setFocusable(false);
            d_phone.setEnabled(false);}
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(IseditAccessable==null||IseditAccessable=="")
                {
                    dialog.cancel();
                    servicecall_addDealer();
                }
                  else  if(IseditAccessable.equalsIgnoreCase("y"))
                    {
                        dialog.cancel();
                        //updateDealer();
                    }
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.dismiss();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(d_name.getText().toString().equals("")){
                    Toast.makeText(AddDealer.this,"Enter dealership name",Toast.LENGTH_SHORT).show();
                }
                else if(d_name.getText().toString().trim().length()<2){
                    d_name.setError("field should contain atleast 2 elements");
                }
                else if(line1.getText().toString().equals("")){
                    Toast.makeText(AddDealer.this,"Enter address",Toast.LENGTH_SHORT).show();
                }
                else if(d_location.getText().toString().equals("")){
                    Toast.makeText(AddDealer.this,"Enter location",Toast.LENGTH_SHORT).show();
                }
                else if(pincode.getText().toString().equals("")){
                    Toast.makeText(AddDealer.this,"Enter pincode",Toast.LENGTH_SHORT).show();
                }
                else if((pincode.getText().toString().length()<6)){
                    Toast.makeText(AddDealer.this," Invalid Pincode",Toast.LENGTH_SHORT).show();
                }
                else if(owner_name.getText().toString().equals("")){
                    Toast.makeText(AddDealer.this,"Enter Owner name",Toast.LENGTH_SHORT).show();
                }
                else if(owner_name.getText().toString().trim().length()<2){
                    owner_name.setError("field should contain atleast 2 elements");
                }
               else
               {
                   dialog.show();
                }
            }
        });

        check_dealer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d_name.setText("");
                no_of_soldcars.setText("");
                line1.setText("");
                d_location.setText("");
                pincode.setText("");
                city.setText("");
                state.setText("");
                owner_name.setText("");
                email.setText("");
                manager_no.setText("");
                dealerlogourl="";
                sellcars="N";
                checkbox.setChecked(false);
                Glide.with(AddDealer.this).load(R.drawable.camer_icon).placeholder(R.drawable.icon_noimage).into(dealer_logo);
                String MobilePattern = "^[6-9][0-9]{9}$";
                if(selected_no.getText().toString().equals("")){
                    Common.CallToast(AddDealer.this,"Enter PhoneNo",2);
                }else if(selected_no.getText().toString().length()<10){
                    Common.CallToast(AddDealer.this,"Enter Valid PhoneNo",2);
                }else if(!selected_no.getText().toString().matches(MobilePattern)){
                    Common.CallToast(AddDealer.this,"Enter Valid PhoneNo",2);
                }
                else{
                    check_dealer();
                }
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent3 = new Intent(AddDealer.this, DealerListPage.class);
                intent3.putExtra("confirmed",senddata);
                startActivity(intent3);
                finish();
            }
        });
        dealer_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opendialog(view);
            }
        });

        pincode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        pincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(pincode.getText().toString().trim().length() >0||pincode.getText().toString().trim().length() <6){
                    cancel_btn.setVisibility(View.VISIBLE);
                }
                if (pincode.getText().toString().trim().length() == 6) {
                    hideKeybaord();
                    cancel_btn.setVisibility(View.GONE);
                    city.setFocusable(false);
                    state.setFocusable(false);
                    servicecall_getpincode();
                }
            }
        });
        search_phoneno();
    }

    public  void search_phoneno(){
        selected_no.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(selected_no.getText().toString().length()<10){
                    sv.setVisibility(View.GONE);
                }else{
                    hideKeybaord();
                }
            }
        });
    }
    //servicecall to add dealerdata
    public void servicecall_addDealer() {
        if (!Connectivity.isNetworkConnected(AddDealer.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {
            PojoAddDealers posts = new PojoAddDealers(d_name.getText().toString().trim(), d_phone.getText().toString().trim(),
                    SPHelper.getSPData(AddDealer.this,SPHelper.e_id,""), d_location.getText().toString().trim(),
                    no_of_soldcars.getText().toString().trim(), line1.getText().toString().trim(), "",
                    "", pincode.getText().toString().trim(), city.getText().toString().trim(), state.getText().toString().trim(),
                    "", "", owner_name.getText().toString().trim(), email.getText().toString().trim(),
                    manager_no.getText().toString().trim(), "", "",
                    "", "", dealerlogourl ,sellcars);

            Call<PojoAddDealers> call = apiInterface.addDealerList(posts);
            call.enqueue(new Callback<PojoAddDealers>() {
                @Override
                public void onResponse(Call<PojoAddDealers> call, Response<PojoAddDealers> response) {
                    if (response.code() == 200) {
                        AppResponse response2=response.body();
                        SPHelper.dealer_id=response2.getResponseModel().getDealerid();
                         Toast.makeText(AddDealer.this, "dealer added", Toast.LENGTH_SHORT).show();
                         intent = new Intent(AddDealer.this, DealerListPage.class);
                         startActivity(intent);
                         finish();
                    }
                    else {
                        Toast.makeText(AddDealer.this,"Server Error"+response.code(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<PojoAddDealers> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    //service method to get pincode
    public void servicecall_getpincode()
    {
        cancel_btn.setVisibility(View.GONE);
        if (!Connectivity.isNetworkConnected(AddDealer.this)) {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        } else {

           progressDialog.show();
         // pincode.removeTextChangedListener(textWatcher);
            Call<AppResponse> call = apiInterface.getPinCode(pincode.getText().toString().trim());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (!response.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),
                                "Code: " + response.code() + "File Not found",
                                Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 200) {
                        progressDialog.dismiss();
                       // pincode.clearFocus();
                       // pincode.removeTextChangedListener(textWatcher);
                        AppResponse appResponse = response.body();
                        d_city = appResponse.getResponseModel().getGetpincodedata().getCity_name();
                        d_pincode = appResponse.getResponseModel().getGetpincodedata().getPincode();
                        d_state = appResponse.getResponseModel().getGetpincodedata().getState_name();
                        city.setText(d_city);
                        //pincode.setText(d_pincode);
                        state.setText(d_state);

                    }else {
                       // pincode.removeTextChangedListener(textWatcher);
                        Toast.makeText(getApplicationContext(),
                                "Code: " + response.code() + "Server issue",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<AppResponse> call, Throwable t) {
                    progressDialog.dismiss();
                   //pincode.removeTextChangedListener(textWatcher);
                    Toast.makeText(getApplicationContext(),
                            t.getMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
    public void opendialog(View view)
    {
        final Dialog dialog4 = new Dialog(AddDealer.this);
        dialog4.setCancelable(true);
        View view4 = AddDealer.this.getLayoutInflater().inflate(R.layout.dialog_camera_options, null);
        dialog4.setContentView(view4);
        RelativeLayout rl_camera4 = view4.findViewById(R.id.rl_camera);
        RelativeLayout rl_gallery4 = view4.findViewById(R.id.rl_gallery);

        rl_camera4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    getCameraPermissions(1);
                }
                dialog4.dismiss();
            }
        });
        rl_gallery4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    getCameraPermissions(10);
                }
                dialog4.dismiss();
            }
        });
        dialog4.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getCameraPermissions( int fromWhere)
    {
        mRequestPermissionHandler.requestPermission(AddDealer.this, new String[]
                {
                        android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, fromWhere, new RequestPermissionHandler.RequestPermissionListener() {
            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                if (fromWhere == 1) {
                    CallCamera();
                } else if (fromWhere == 10) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivityForResult(pickPhoto, 200);
                }
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void CallCamera() {
        mRequestPermissionHandler.requestPermission(AddDealer.this, new String[]{
                android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
        }, 123, new RequestPermissionHandler.RequestPermissionListener() {

            @Override
            public void onSuccess() {
                System.out.println("Succeed");
                openCamera();
            }
            @Override
            public void onFailed() {
                System.out.println("denied");
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    void openCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
            String fineName = dateFormat.format(new Date());
            filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + fineName;
            imageUri = FileProvider.getUriForFile(AddDealer.this,
                    BuildConfig.APPLICATION_ID + ".provider",
                    new File(filename));
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(takePictureIntent, 100);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //rcfront
        if (requestCode == 100 && resultCode == RESULT_OK )
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs(filename, new Pair<Integer, Integer>(800, 800), "/");
                    imageUri = FileProvider.getUriForFile(AddDealer.this,
                            BuildConfig.APPLICATION_ID + ".provider", new File(OriginalFileName));
                    filename = OriginalFileName;
                    dealer_logo.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddDealer.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDealer.this);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }

                                    private void validate() {
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "Dealerlogo/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    dealerlogourl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }
            });
        }
        else if (requestCode == 200 && data!=null)
        {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Uri imageUri = data.getData();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("-yyyy_MM_dd_HH_mm_ss_SSSSSS'.jpg'");
                    String fineName = dateFormat.format(new Date());
                    filename = BitmapUtility.PictUtil.getSavePath().getPath() + "/" + SPHelper.getSPData(AddDealer.this, "rc", "") + fineName;
                    Uri OriginalFileName = null;
                    try {
                        OriginalFileName = BitmapUtility.PictUtil.saveImageasThumbs3(AddDealer.this, imageUri, filename, new Pair<Integer, Integer>(800, 800), "/");
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    imageUri = OriginalFileName;
                    dealer_logo.setImageURI(imageUri);
                    if (!Connectivity.isNetworkConnected(AddDealer.this)) {
                        progressDialog.dismiss();
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(AddDealer.this);
                        builder1.setMessage("Please retry to Submit your Details");
                        builder1.setCancelable(true);
                        builder1.setPositiveButton(
                                "RETRY",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        validate();
                                    }
                                });
                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                        return;
                    }
                    File imageFile = new File(filename);

                    Uri uri = Uri.fromFile(imageFile);

                    try {
                        progressDialog.show();
                        final TransferUtility transferUtility =
                                TransferUtility.builder()
                                        .context(getApplicationContext())
                                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                                        .s3Client(s3Client)
                                        .build();
                        final String key = "RCFront/" + imageUri.getLastPathSegment();
                        final TransferObserver uploadObserver =
                                transferUtility.upload(key, new File(filename));
                        uploadObserver.setTransferListener(new TransferListener() {
                            @Override
                            public void onStateChanged(int id, TransferState state) {
                                if (TransferState.COMPLETED == state) {
                                    Toast.makeText(getApplicationContext(), "Upload Completed!", Toast.LENGTH_SHORT).show();
                                    String finalurl = s3Client.getResourceUrl("ab-prod-container", key);
                                    System.out.print(finalurl);
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                    dealerlogourl = finalurl;

                                } else if (TransferState.FAILED == state) {

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            progressDialog.dismiss();
                                            progressDialog.cancel();
                                        }
                                    });
                                }
                            }

                            @Override
                            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                                float percentDonef = ((float) bytesCurrent / (float) bytesTotal) * 100;
                                int percentDone = (int) percentDonef;

                            }

                            @Override
                            public void onError(int id, Exception ex) {
                                ex.printStackTrace();
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressDialog.dismiss();
                                        progressDialog.cancel();
                                    }
                                });
                            }

                        });
                    } catch (Exception je) {

                        je.printStackTrace();
                    }
                    // uploadImageAmazon();
                    /*camera_hide_view.setVisibility(View.INVISIBLE);
                    isimagepresent=true;*/

                }

            });
        }
    }
    private void validate()
    {
    }
    private void hideKeybaord() {
        InputMethodManager inputManager = (InputMethodManager) AddDealer.this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(AddDealer.this.getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);

    }
    public  void oncancelSelect(View view){
        hideKeybaord();
        pincode.setText("");
        cancel_btn.setVisibility(View.GONE);
        city.setText("");state.setText("");
    }


    public static AddDealer getInstance() {
        return instance;
    }

    public void check_dealer()
    {
        if(!Connectivity.isNetworkConnected(AddDealer.this))
        {
            Toast.makeText(getApplicationContext(),
                    "Internet not connected",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            progressDialog.show();
            Call<AppResponse> call = apiInterface.check_dealer(selected_no.getText().toString());
            call.enqueue(new Callback<AppResponse>() {
                @Override
                public void onResponse(Call<AppResponse> call, Response<AppResponse> response) {
                    if (response.body()!=null) {
                        if (response.code() == 200)
                        {
                            progressDialog.dismiss();
                            AppResponse appResponse = response.body();
                            String is_account_exists=appResponse.getResponseModel().getDealerAccount().getAccount_exists();
                            if(is_account_exists.equalsIgnoreCase("y")){
                                Common.CallToast(AddDealer.this,"Dealer exists with number",2);
                            }else{
                                sv.setVisibility(View.VISIBLE);
                                d_phone.setText(selected_no.getText().toString());
                            }
                        }
                        else
                        {
                            Toast.makeText(AddDealer.this,"Error:"+response.code(),Toast.LENGTH_SHORT).show();
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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddDealer.this, DealerListPage.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}