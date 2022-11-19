package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LocationApiInterface {

    @GET(ApiEndpoints.GET_ALL_EMPLOYEES_LOCATION)
    Call<List<Location>> getAllEmployeesLocation();

    @GET(ApiEndpoints.GET_EMPLOYEE_LOCATION_BY_ID)
    Call<Location> getEmployeeLocationById(@Query("emp_id") Long empId);

    @POST(ApiEndpoints.UPDATE_EMPLOYEE_LOCATION_BY_ID)
    Call<Location> updateEmployeeLocation(@Query("emp_id") Long empId, @Body Location location);

}
