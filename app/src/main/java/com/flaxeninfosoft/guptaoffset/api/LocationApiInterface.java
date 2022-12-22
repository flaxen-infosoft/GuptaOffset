package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationApiInterface {

    @GET(ApiEndpoints.GET_EMPLOYEE_CURRENT_LOCATION)
    Call<Location> getEmployeeCurrentLocationById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_EMPLOYEE_TODAYS_LOCATION_HISTORY)
    Call<List<Location>> getEmployeeTodaysLocationHistory(@Query(Constants.EMPLOYEE_ID) Long empId);

    @POST(ApiEndpoints.ADD_EMPLOYEE_LOCATION)
    Call<Location> addEmployeeLocation(@Body Location location);

}
