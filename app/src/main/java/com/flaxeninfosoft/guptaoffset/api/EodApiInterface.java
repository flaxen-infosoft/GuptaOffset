package com.flaxeninfosoft.guptaoffset.api;

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
    Call<EOD> getEodById(@Query(Constants.EOD_ID) Long eodId);

    @POST(ApiEndpoints.ADD_EOD)
    Call<EOD> addEod(@Body EOD eod);

    @GET(ApiEndpoints.GET_EMPLOYEE_ALL_EOD_BY_ID)
    Call<List<EOD>> getEmployeeAllEodsById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_ALL_EOD)
    Call<List<EOD>> getAllEods();

    @GET(ApiEndpoints.GET_EMPLOYEE_TODAY_EOD)
    Call<EOD> getEmployeeTodaysEod(@Query(Constants.EMPLOYEE_ID) Long empId);

}

