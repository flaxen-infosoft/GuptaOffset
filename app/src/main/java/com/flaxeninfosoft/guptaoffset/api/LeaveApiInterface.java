package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.LeaveRequest;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LeaveApiInterface {

    @GET(ApiEndpoints.GET_ALL_LEAVE_REQUESTS)
    Call<List<LeaveRequest>> getAllLeaveRequests();

    @GET(ApiEndpoints.GET_EMPLOYEE_LEAVE_REQUESTS)
    Call<List<LeaveRequest>> getEmployeeLeaveRequests(@Query("emp_id") Long empId);

    @GET(ApiEndpoints.GET_LEAVE_REQUEST_BY_ID)
    Call<LeaveRequest> getLeaveRequestById(@Query("leave_id") Long leaveId);

    @POST(ApiEndpoints.UPDATE_LEAVE_STATUS_BY_ID)
    Call<LeaveRequest> updateLeaveRequestById(@Query("leave_id") Long leaveId, @Body LeaveRequest leave);
}
