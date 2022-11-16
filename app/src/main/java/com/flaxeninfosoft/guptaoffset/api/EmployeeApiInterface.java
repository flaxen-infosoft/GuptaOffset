package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Dto;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.viewModels.LoginViewModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;

public interface EmployeeApiInterface {

    @GET(ApiEndpoints.LOGIN)
    Call<Dto<Employee>> loginByEmailAndPassword(@Body LoginModel loginModel);
}
