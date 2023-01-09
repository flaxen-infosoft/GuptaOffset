package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EodApiInterface {

    @GET(ApiEndpoints.GET_EOD_BY_ID)
    Call<Eod> getEodById(@Query(Constants.EOD_ID) Long eodId);

    @POST(ApiEndpoints.ADD_EOD)
    Call<Eod> addEod(@Query(Constants.EMPLOYEE_ID) Long empId, @Body Eod eod);

    @GET(ApiEndpoints.GET_EMPLOYEE_ALL_EOD_BY_ID)
    Call<List<Eod>> getEmployeeAllEodsById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_ALL_EOD)
    Call<List<Eod>> getAllEods();

    @GET(ApiEndpoints.GET_EMPLOYEE_TODAY_EOD)
    Call<Eod> getEmployeeTodaysEod(@Query(Constants.EMPLOYEE_ID) Long empId);

}

