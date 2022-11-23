package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EmployeeApiInterface {

    @POST(ApiEndpoints.LOGIN)
    Call<Employee> loginByEmailAndPassword(@Body LoginModel loginModel);

    @POST(ApiEndpoints.ADD_EMPLOYEE)
    Call<Employee> addEmployee(@Body Employee employee);

    @GET(ApiEndpoints.GET_EMPLOYEE_BY_ID)
    Call<Employee> getEmployeeById(@Query("emp_id") Long empId);

    @GET(ApiEndpoints.GET_ALL_EMPLOYEES)
    Call<List<Employee>> getAllEmployees();

    @POST(ApiEndpoints.UPDATE_EMPLOYEE_BY_ID)
    Call<Employee> updateEmployeeById(@Query("emp_id") Long empId, @Body Employee employee);

}
