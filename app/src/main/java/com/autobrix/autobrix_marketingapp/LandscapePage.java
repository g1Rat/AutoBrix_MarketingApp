package com.autobrix.autobrix_marketingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.autobrix.autobrix_marketingapp.adapters.AdapterCategoryList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMngrEmployeeList;
import com.autobrix.autobrix_marketingapp.adapters.AdapterMonthDates;
import com.autobrix.autobrix_marketingapp.adapters.AdapterSalesList;
import com.autobrix.autobrix_marketingapp.pojos.PojoCategoryList;
import com.autobrix.autobrix_marketingapp.pojos.PojoMngrDates;
import com.autobrix.autobrix_marketingapp.pojos.PojoMngrEmp;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;

import java.util.ArrayList;

public class LandscapePage extends AppCompatActivity {
    ArrayList<PojoMngrEmp> pojoMngrEmps;
    ArrayList<PojoMngrDates> pojoMngrDates;
    ArrayList<PojoCategoryList> pojoCategoryLists;
    AdapterCategoryList adapterCategoryList;
    RecyclerView rv_emp_list,rv_date_list,rv_emp_updates;
    AdapterMngrEmployeeList adapterMngrEmployeeList;
    AdapterMonthDates adapterMonthDates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landscape_page);
        rv_emp_list= findViewById(R.id.rv_emp_list);
        rv_date_list= findViewById(R.id.rv_date_list);
        rv_emp_updates= findViewById(R.id.rv_emp_updates);
        pojoMngrDates=new ArrayList<>();
        pojoMngrEmps=new ArrayList<>();
        pojoCategoryLists=new ArrayList<>();
        pojoMngrDates.add(new PojoMngrDates("AUG 1"));
        pojoMngrDates.add(new PojoMngrDates("AUG 2"));
        pojoMngrDates.add(new PojoMngrDates("AUG 3"));
        pojoMngrDates.add(new PojoMngrDates("AUG 4"));
        pojoMngrDates.add(new PojoMngrDates("AUG 5"));
        adapterMonthDates=new AdapterMonthDates(pojoMngrDates,LandscapePage.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(LandscapePage.this, LinearLayoutManager.VERTICAL, false);
        rv_date_list.setLayoutManager(layoutManager);
        rv_date_list.setAdapter(adapterMonthDates);

        pojoMngrEmps.add(new PojoMngrEmp("MAHI"));
        pojoMngrEmps.add(new PojoMngrEmp("SHREYANK"));
        pojoMngrEmps.add(new PojoMngrEmp("SAGAR"));

        adapterMngrEmployeeList=new AdapterMngrEmployeeList(pojoMngrEmps,LandscapePage.this);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(LandscapePage.this, LinearLayoutManager.HORIZONTAL, false);
        rv_emp_list.setLayoutManager(layoutManager1);
        rv_emp_list.setAdapter(adapterMngrEmployeeList);

        pojoCategoryLists.add(new PojoCategoryList("Pricing"));
        pojoCategoryLists.add(new PojoCategoryList("OnBoard Discussion"));
        pojoCategoryLists.add(new PojoCategoryList("Pricing"));
        pojoCategoryLists.add(new PojoCategoryList("OnBoard Discussion"));
        pojoCategoryLists.add(new PojoCategoryList("Pricing"));
        pojoCategoryLists.add(new PojoCategoryList("OnBoard Discussion"));

        adapterCategoryList=new AdapterCategoryList(pojoCategoryLists,LandscapePage.this);
        GridLayoutManager layoutManager3 = new GridLayoutManager(LandscapePage.this, 3);
        rv_emp_updates.setLayoutManager(layoutManager3);
        rv_emp_updates.setAdapter(adapterCategoryList);
    }
}