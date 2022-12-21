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

    @GET(ApiEndpoints.GET_LEAVE_BY_ID)
    Call<Leave> getLeaveById(@Query("leave_id") Long leaveId);

    @GET(ApiEndpoints.APPROVE_LEAVE_BY_ID)
    Call<Leave> approveLeaveById(@Query("leaveId") Long leaveId);

    @GET(ApiEndpoints.REJECT_LEAVE_BY_ID)
    Call<Leave> rejectLeaveById(@Query("leaveId") Long leaveId);

    @GET(ApiEndpoints.GET_ALL_LEAVES)
    Call<List<Leave>> getAllLeaves();

    @GET(ApiEndpoints.GET_ALL_PENDING_LEAVES)
    Call<List<Leave>> getAllPendingLeaves();

    @GET(ApiEndpoints.GET_ALL_APPROVED_LEAVES)
    Call<List<Leave>> getAllApprovedLeaves();

    @GET(ApiEndpoints.GET_ALL_REJECTED_LEAVES)
    Call<List<Leave>> getAllRejectedLeaves();

    @GET(ApiEndpoints.GET_EMPLOYEE_ALL_LEAVES)
    Call<List<Leave>> getEmployeeLeave(@Query("empId") Long empId);

    @POST(ApiEndpoints.ADD_LEAVE)
    Call<Leave> addLeave(@Body Leave leave);


}
