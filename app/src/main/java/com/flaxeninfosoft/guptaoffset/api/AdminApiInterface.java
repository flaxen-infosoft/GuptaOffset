package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface AdminApiInterface {

    @GET(ApiEndpoints.ADMIN_GET_ALL_EMPLOYEES)
    Call<List<Employee>> getAllEmployees();
}
