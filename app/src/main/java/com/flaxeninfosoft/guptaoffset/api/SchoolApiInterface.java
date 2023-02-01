package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SchoolApiInterface {

    @POST(ApiEndpoints.ADD_SCHOOL)
    Call<School> addSchool(@Query(Constants.EMPLOYEE_ID) Long empId, @Body School school);

    @GET(ApiEndpoints.GET_SCHOOL_BY_ID)
    Call<School> getSchoolById(@Query(Constants.SCHOOL_ID) Long schoolId);
}
