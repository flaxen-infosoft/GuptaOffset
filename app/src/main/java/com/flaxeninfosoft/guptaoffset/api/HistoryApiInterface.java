package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HistoryApiInterface {

    @GET(ApiEndpoints.GET_EMPLOYEE_HISTORY)
    Call<List<EmployeeHistory>> getEmployeeHistory(@Query(Constants.EMPLOYEE_ID) Long empId, @Query(Constants.TOKEN) Long currentEmpId, @Query("date") String date);
}
