package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeApiInterface {

    @GET(ApiEndpoints.GET_EMPLOYEE_BY_ID)
    Call<Employee> getEmployeeById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.GET_ALL_EMPLOYEES)
    Call<List<Employee>> getAllEmployees();

    @GET(ApiEndpoints.GET_ALL_SUPER_EMPLOYEES)
    Call<List<Employee>> getAllSuperEmployees();

    @POST(ApiEndpoints.ADD_EMPLOYEE)
    Call<Employee> addEmployee(@Body Employee employee);

    @POST(ApiEndpoints.UPDATE_EMPLOYEE_BY_ID)
    Call<Employee> updateEmployeeById(@Body Employee employee);

    @GET(ApiEndpoints.SUSPEND_EMPLOYEE_BY_ID)
    Call<Employee> suspendEmployeeById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.ACTIVATE_EMPLOYEE_BY_ID)
    Call<Employee> activateEmployeeById(@Query(Constants.EMPLOYEE_ID) Long empId);

}
