package com.flaxeninfosoft.guptaoffset.api;

import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface AuthApiInterface {

    @FormUrlEncoded
    @POST(ApiEndpoints.LOGIN)
    Call<Employee> loginByEmailAndPassword(@Field(Constants.EMAIL) String email, @Field(Constants.PASSWORD) String password);

}
