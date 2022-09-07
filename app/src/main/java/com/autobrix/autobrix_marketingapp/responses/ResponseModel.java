package com.autobrix.autobrix_marketingapp.responses;

import com.autobrix.autobrix_marketingapp.pojos.Credential;
import com.autobrix.autobrix_marketingapp.pojos.EmployeeData;
import com.autobrix.autobrix_marketingapp.pojos.GetDashBoard;
import com.autobrix.autobrix_marketingapp.pojos.GetPincode;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarBrands;
import com.autobrix.autobrix_marketingapp.pojos.PojoAllCarModels;
import com.autobrix.autobrix_marketingapp.pojos.PojoAppUpdateDetails;
import com.autobrix.autobrix_marketingapp.pojos.PojoDealers;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmpDealerList;
import com.autobrix.autobrix_marketingapp.pojos.PojoEmployees;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspCompList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectedVehicleList;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionEligibleStatus;
import com.autobrix.autobrix_marketingapp.pojos.PojoInspectionReqList;
import com.autobrix.autobrix_marketingapp.pojos.PojoMarketingMaterials;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialRequired;
import com.autobrix.autobrix_marketingapp.pojos.PojoMaterialStatus;
import com.autobrix.autobrix_marketingapp.pojos.PojoReasonList;
import com.autobrix.autobrix_marketingapp.pojos.PojoSalesList;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateList;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseModel
{

    @SerializedName("message")
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @SerializedName("dealerid")
    private String dealerid;

    public String getDealerid() {
        return dealerid;
    }
    @SerializedName("phoneNo")
    private String phoneNo;
    @SerializedName("otp")
    private String otp;

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    //get Employee Details

    @SerializedName("employeeData")
    private EmployeeData employeeData;

    public EmployeeData getEmployeeData() {
        return employeeData;
    }

    //credential dataa
    @SerializedName("credential")
    private Credential credential;

    public Credential getCredential() {
        return credential;
    }

    // get Dealer Lists
    @SerializedName("DealerList")
    private ArrayList<PojoDealers> DealerList;
    @SerializedName("ManagerDealerList")
    private ArrayList<PojoDealers> ManagerDealerList;

    public ArrayList<PojoDealers> getManagerDealerList() {
        return ManagerDealerList;
    }

    @SerializedName("EmployeeDealerList")
    private ArrayList<PojoDealers> EmployeeDealerList;

    @SerializedName("search")
    private String search;

    public ArrayList<PojoDealers> getEmployeeDealerList() {
        return EmployeeDealerList;
    }

    public ArrayList<PojoDealers> getDealerList() {
        return DealerList;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    //getpincode
    @SerializedName("getpincodedata")
    private GetPincode getpincodedata;

    public GetPincode getGetpincodedata() {
        return getpincodedata;
    }

    //get All Market Mtrl List
    @SerializedName("AllMarketingList")
    private  ArrayList<PojoMarketingMaterials> AllMarketingList;

    @SerializedName("ManagerMarketingList")
    private  ArrayList<PojoMarketingMaterials> ManagerMarketingList;

    public ArrayList<PojoMarketingMaterials> getManagerMarketingList() {
        return ManagerMarketingList;
    }

    public ArrayList<PojoMarketingMaterials> getAllMarketingMaterial() {
        return AllMarketingList;
    }


    //getmaterallist
    @SerializedName("MaterialList")
    private  ArrayList<PojoMaterialRequired> MaterialList;


    public ArrayList<PojoMaterialRequired> getMaterialList() {
        return MaterialList;
    }

    //get status list

    @SerializedName("statusList")
    private  ArrayList<PojoMaterialStatus> statusList;
    public ArrayList<PojoMaterialStatus> getStatusList() {
        return statusList;
    }

    //get inspection req list
    @SerializedName("InspectionRequestList")
    private  ArrayList<PojoInspectionReqList> InspectionRequestList;
    @SerializedName("ManagerInspectionReqList")
    private  ArrayList<PojoInspectionReqList> ManagerInspectionReqList;

    public ArrayList<PojoInspectionReqList> getInspectionRequestList() {
        return InspectionRequestList;
    }

    public ArrayList<PojoInspectionReqList> getManagerInspectionReqList() {
        return ManagerInspectionReqList;
    }
//insp_completed_list

    @SerializedName("CompletedInspectedVehicleList")
    private  ArrayList<PojoInspCompList> CompletedInspectedVehicleList;

    public ArrayList<PojoInspCompList> getCompletedInspectedVehicleList() {
        return CompletedInspectedVehicleList;
    }


    //get update list
    @SerializedName("UpdateList")
    private  ArrayList<PojoUpdateList> UpdateList;
    @SerializedName("ManagerDailyUpdate")
    private  ArrayList<PojoUpdateList> ManagerDailyUpdate;

    public ArrayList<PojoUpdateList> getManagerDailyUpdate() {
        return ManagerDailyUpdate;
    }

    public ArrayList<PojoUpdateList> getUpdateList() {
        return UpdateList;
    }

    //getReasonList
    @SerializedName("ReasonList")
    private  ArrayList<PojoReasonList> ReasonList;

    public ArrayList<PojoReasonList> getReasonList() {
        return ReasonList;
    }

    //check dealer
    @SerializedName("dealerAccount")
    PojoEmployees dealerAccount;

    public PojoEmployees getDealerAccount() {
        return dealerAccount;
    }

    //getDashboardCount

    @SerializedName("DashboardDetails")
    private GetDashBoard DashboardDetails;
    @SerializedName("ManagerDashboard")
    private GetDashBoard ManagerDashboard;

    public GetDashBoard getManagerDashboard() {
        return ManagerDashboard;
    }

    public GetDashBoard getDashboardDetails() {
        return DashboardDetails;
    }

    //getinspectioneligility status
    @SerializedName("InspectionEligibility")
    PojoInspectionEligibleStatus InspectionEligibility;

    public PojoInspectionEligibleStatus getInspectionEligibility() {
        return InspectionEligibility;
    }

    //getbrandlist
    @SerializedName("brandList")
    ArrayList<PojoAllCarBrands> brandList;
    //getmodellist
    @SerializedName("modelList")
    ArrayList<PojoAllCarModels> modelList;

    public ArrayList<PojoAllCarBrands> getBrandList() {
        return brandList;
    }

    public ArrayList<PojoAllCarModels> getModelList() {
        return modelList;
    }

    //get app update details
    @SerializedName("appUpdateDetails")
    PojoAppUpdateDetails appUpdateDetails;

    public PojoAppUpdateDetails getAppUpdateDetails() {
        return appUpdateDetails;
    }

    @SerializedName("InspectedVehicleList")
    ArrayList<PojoInspectedVehicleList> InspectedVehicleList;
    @SerializedName("ManagerInspectedVehList")
    ArrayList<PojoInspectedVehicleList> ManagerInspectedVehList;

    public ArrayList<PojoInspectedVehicleList> getManagerInspectedVehList() {
        return ManagerInspectedVehList;
    }

    public ArrayList<PojoInspectedVehicleList> getInspectedVehicleList() {
        return InspectedVehicleList;
    }

    @SerializedName("ManagerTargetList")
    ArrayList<PojoSalesList> ManagerTargetList;

    public ArrayList<PojoSalesList> getManagerTargetList() {
        return ManagerTargetList;
    }

    @SerializedName("ManagerEmployeeList")
    ArrayList<PojoSalesList> ManagerEmployeeList;

    public ArrayList<PojoSalesList> getManagerEmployeeList() {
        return ManagerEmployeeList;
    }
    @SerializedName("SalesDealerList")
    ArrayList<PojoEmpDealerList> SalesDealerList;

    public ArrayList<PojoEmpDealerList> getSalesDealerList() {
        return SalesDealerList;
    }
}
