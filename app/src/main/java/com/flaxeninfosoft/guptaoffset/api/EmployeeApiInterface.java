package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.android.gms.common.api.Api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
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

    @GET(ApiEndpoints.GET_EMPLOYEES_OF_SUPER_EMPLOYEE)
    Call<List<Employee>> getEmployeesOfSuperEmployee(@Query(Constants.EMPLOYEE_ID) Long empId);

    @POST(ApiEndpoints.ADD_EMPLOYEE)
    Call<Employee> addEmployee(@Body Employee employee);

    @POST(ApiEndpoints.ADD_SUPER_EMPLOYEE)
    Call<Employee> addSuperEmployee(@Body Employee employee);

    @POST(ApiEndpoints.UPDATE_EMPLOYEE_BY_ID)
    Call<Employee> updateEmployeeById(@Body Employee employee);

    @GET(ApiEndpoints.SUSPEND_EMPLOYEE_BY_ID)
    Call<Employee> suspendEmployeeById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.ACTIVATE_EMPLOYEE_BY_ID)
    Call<Employee> activateEmployeeById(@Query(Constants.EMPLOYEE_ID) Long empId);

    @GET(ApiEndpoints.UPDATE_EMPLOYEE_BATTERY_STATUS)
    Call<Object> updateEmployeeBatteryStatus(@Query(Constants.EMPLOYEE_ID) Long currentEmployeeId,@Query(Constants.BATTERY_STATUS) String status);
}
