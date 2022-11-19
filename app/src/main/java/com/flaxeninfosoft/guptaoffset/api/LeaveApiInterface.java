package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LeaveApiInterface {

    @GET(ApiEndpoints.GET_ALL_LEAVE_REQUESTS)
    Call<List<Leave>> getAllLeaveRequests();

    @GET(ApiEndpoints.GET_EMPLOYEE_LEAVE_REQUESTS)
    Call<List<Leave>> getEmployeeLeaveRequests(@Query("emp_id") Long empId);

    @GET(ApiEndpoints.GET_LEAVE_REQUEST_BY_ID)
    Call<Leave> getLeaveRequestById(@Query("leave_id") Long leaveId);

    @POST(ApiEndpoints.UPDATE_LEAVE_STATUS_BY_ID)
    Call<Leave> updateLeaveRequestById(@Query("leave_id") Long leaveId, @Body Leave leave);
}
