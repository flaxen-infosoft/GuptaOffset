package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttendanceApiInterface {

    @GET(ApiEndpoints.GET_EMPLOYEE_ATTENDANCE)
    Call<List<Attendance>> getEmployeeAttendance(@Query("emp_id") Long empId);

    @GET(ApiEndpoints.GET_ATTENDANCE_BY_ID)
    Call<Attendance> getAttendanceById(@Query("attendance_id") Long attendanceId);

    @POST(ApiEndpoints.ADD_EMPLOYEE_ATTENDANCE)
    Call<Attendance> addEmployeeAttendance(@Query("emp_id") Long empId, @Body Attendance attendance);

}

