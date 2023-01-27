package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AttendanceApiInterface {

    @GET(ApiEndpoints.GET_TODAYS_ATTENDANCE)
    Call<Attendance> getEmployeeTodaysAttendance(@Query(Constants.EMPLOYEE_ID) Long empId);

    @POST(ApiEndpoints.PUNCH_ATTENDANCE)
    @FormUrlEncoded
    Call<Attendance> punchAttendance(@Query("empId") Long empId,@Field("reading") String reading, @Field("snap") String encodedImage);
}
