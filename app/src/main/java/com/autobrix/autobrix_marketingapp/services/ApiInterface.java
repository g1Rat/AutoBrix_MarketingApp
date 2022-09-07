package com.autobrix.autobrix_marketingapp.services;

import com.autobrix.autobrix_marketingapp.pojos.PojoAddCar;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddInspection;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddMaterial;
import com.autobrix.autobrix_marketingapp.pojos.PojoUpdateStatus;
import com.autobrix.autobrix_marketingapp.pojos.PojoVehInspectEligible;
import com.autobrix.autobrix_marketingapp.responses.AppResponse;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddDealers;
import com.autobrix.autobrix_marketingapp.pojos.PojoAddReminders;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiInterface
{
    @GET("/marketing/send/otp")
    Call<AppResponse> getOtp(@Query("phoneNo") String phoneNo);

    @GET("/marketing/verify/otp")
    Call<AppResponse> verifyOtp(@Query("phoneNo") String phoneNo,@Query("otp") String otp);

    @GET("/getAppUpdateDetails")
    Call<AppResponse> getapp_update_deatails(@Query("appId") String appId,@Query("osType") String osType);

    @GET("/userhomeservices/getEmployeedealerList")
    Call<AppResponse> get_emp_DealerList(@Query("fromDate") String fromDate, @Query("toDate") String toDate,
                                         @Query("cityId") String cityId,@Query("search") String search,
                                         @Query("pageNo") String pageNo, @Query("text") String text,
                                          @Query("employeeId") String employeeId);

    @GET("/userhomeservices/getdealerList")
    Call<AppResponse> getDealerList(@Query("fromDate") String fromDate,
                                               @Query("toDate") String toDate, @Query("cityId") String cityId,
                                               @Query("search") String search, @Query("pageNo") String pageNo,
                                               @Query("text") String text,@Query("month") String month,
                                              @Query("year") String year);

    @GET("/userhomeservices/checkDealer")
    Call<AppResponse> check_dealer (@Query("phoneNum")String phoneNum);

     @POST("/userhomeservices/addDealer")
      Call<PojoAddDealers> addDealerList(@Body PojoAddDealers post);
    @GET("/userhomeservices/getpincode")
    Call<AppResponse> getPinCode (@Query("pincode")String pincode);


    @GET("/MarketingMaterial/getstatuslist")
    Call<AppResponse> getStatusList (@Query("roleId")String roleId);

    @POST("/MarketingMaterial/updateStatus")
    Call<AppResponse> update_Status(@Body PojoUpdateStatus post);

    @GET("/MarketingMaterial/listMarketingMaterial")
    Call<AppResponse> getAllMarketingMtrLlist(@Query("fromDate") String fromDate,
                                    @Query("toDate") String toDate, @Query("cityId") String cityId,
                                    @Query("search") String search, @Query("pageNo") String pageNo,
                                    @Query("text") String text,@Query("employeeId") String employeeId,
                                    @Query("month") String month,@Query("year") String year,@Query("type") String type);

    @GET("/MarketingMaterial/getmaterialList")
    Call<AppResponse> getMaterialList ();

    @POST("/MarketingMaterial/addMarketingMaterial")
    Call<AppResponse> add_market_mtrls(@Body PojoAddMaterial pojoAddMaterial);

    @POST("/DailyUpdate/addUpdate")
    Call<PojoAddReminders> add_Update(@Body PojoAddReminders post_addReminder);


    @GET("/DailyUpdate/getUpdateList")
    Call<AppResponse> get_UpdateList(@Query("employeeId") String employeeId,
                                      @Query("date") String date,
                                      @Query("search") String search);


    @GET("/DealerRequest/getInspectedVehicleList")
    Call<AppResponse> get_InspectedVehList(@Query("fromDate") String fromDate,
                                         @Query("toDate") String toDate, @Query("cityId") String cityId,
                                         @Query("search") String search, @Query("pageNo") String pageNo,
                                         @Query("text") String text,@Query("employeeId") String employeeId,
                                         @Query("month") String month,@Query("year") String year);

    @GET("/DealerRequest/getInspectionRequest")
    Call<AppResponse> get_InspectionRequest(@Query("fromDate") String fromDate,
                                         @Query("toDate") String toDate, @Query("cityId") String cityId,
                                         @Query("search") String search, @Query("pageNo") String pageNo,
                                         @Query("text") String text,@Query("employeeId") String employeeId,
                                         @Query("month") String month,@Query("year") String year);

    @GET("/DealerRequest/getCompletedInspectedVehicleList")
    Call<AppResponse> get_CompletedVehList(@Query("combReqId") String combReqId);

    @GET("/DailyUpdate/getreasonList")
    Call<AppResponse> get_ReasonList();

    @POST("/DailyUpdate/addUpdate")
    Call<AppResponse> updatdealer(@Body PojoAddReminders pojoAddReminders);

    @GET("userhomeservices/DashboardDetails")
    Call<AppResponse> getDashboardCount(@Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                        @Query("text") String text, @Query("employeeId") String employeeId,
                                        @Query("month") String month, @Query("year") String year);


    @POST("/DealerRequest/addInspectionRequest")
    Call<AppResponse> add_Inspection(@Body PojoAddInspection post_Dealer);

    @POST("/DealerRequest/checkeInspectionRequestgibility")
    Call<AppResponse> check_veh_inspect_eligible(@Body PojoVehInspectEligible pojoVehInspectEligible);

    @GET("/DealerRequest/getBrandList")
    Call<AppResponse> get_brandlist();

    @GET("/DealerRequest/getModelList")
    Call<AppResponse> get_modellist(@Query("brandId") String brandId);

    @POST("/DealerRequest/addCar")
    Call<AppResponse> add_car_details(@Body PojoAddCar pojoAddCar);


    @GET("/MarketingManager/getDashboard")
    Call<AppResponse> getMngrDashboardCount(@Query("employeeId") String employeeId,
                                     @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                      @Query("month") String month, @Query("year") String year,@Query("text") String text);

    @GET("/MarketingManager/getDealerList")
    Call<AppResponse> getMngrDealerList(@Query("search") String search,@Query("employeeId") String employeeId);

    @GET("/MarketingManager/getSalesList")
    Call<AppResponse> getMngrSalesList(@Query("search") String search,@Query("employeeId") String employeeId,
                                     @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                     @Query("text") String text, @Query("month") String month, @Query("year") String year);
    @GET("/MarketingManager/getEmployeeList")
    Call<AppResponse> getMngrEmpList(@Query("search") String search,@Query("employeeId") String employeeId,
                                        @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                        @Query("text") String text, @Query("month") String month, @Query("year") String year);
    @GET("/MarketingManager/getdealerPackList")
    Call<AppResponse> getdealerPackList(@Query("search") String search,@Query("employeeId") String employeeId,
                                     @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                     @Query("text") String text, @Query("month") String month, @Query("year") String year);



    @GET("/MarketingManager/getMarketingMaterial")
    Call<AppResponse> getMngrMarketingMaterial(@Query("employeeId") String employeeId, @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                        @Query("text") String text, @Query("month") String month, @Query("year") String year,
                                        @Query("search") String search,@Query("pageNo") String pageNo,@Query("typeId") String typeId
                                        ,@Query("markExeId") String markExeId);


    @GET("/MarketingManager/getInspectionRequest")
    Call<AppResponse> get_MngrInsp_Request(@Query("employeeId") String employeeId, @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                               @Query("text") String text, @Query("month") String month, @Query("year") String year,
                                               @Query("search") String search,@Query("markExeId") String markExeId,
                                               @Query("pageNo") String pageNo
                                               );

    @GET("/MarketingManager/getInspectedVehicle")
    Call<AppResponse> get_MngrInsp_VehList(@Query("employeeId") String employeeId, @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                               @Query("text") String text, @Query("month") String month, @Query("year") String year,
                                              @Query("markExeId") String markExeId, @Query("pageNo") String pageNo,
                                           @Query("search") String search);


    @GET("/MarketingManager/getDailyUpdateList")
    Call<AppResponse> getMngrDailyUpdateList(@Query("employeeId") String employeeId, @Query("fromDate") String fromDate,@Query("toDate") String toDate,
                                               @Query("text") String text, @Query("month") String month, @Query("year") String year,
                                               @Query("markExeId") String markExeId, @Query("pageNo") String pageNo,@Query("search") String search);
}
