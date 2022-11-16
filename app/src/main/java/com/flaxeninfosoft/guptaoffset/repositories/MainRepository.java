package com.flaxeninfosoft.guptaoffset.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.flaxeninfosoft.guptaoffset.api.AdminApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EmployeeApiInterface;
import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Dto;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.RetrofitClient;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainRepository {

    private static MainRepository instance;

    private final SharedPrefs sharedPrefs;
    private final AdminApiInterface adminApiInterface;
    private final EmployeeApiInterface employeeApiInterface;

    private final int STATUS_OK = 200;
    private final int STATUS_NOT_FOUND = 404;

    private MainRepository(Context context){
        Retrofit apiClient = RetrofitClient.getClient();

        sharedPrefs = SharedPrefs.getInstance(context);
        adminApiInterface = apiClient.create(AdminApiInterface.class);
        employeeApiInterface = apiClient.create(EmployeeApiInterface.class);
    }

    public static MainRepository getInstance(Context context){
        if (instance == null){
            instance = new MainRepository(context);
        }

        return instance;
    }

    public void login(LoginModel credentials, ApiResponseListener<Employee, String> listener) {

        Call<Dto<Employee>> loginCall = employeeApiInterface.loginByEmailAndPassword(credentials);

        loginCall.enqueue(new Callback<Dto<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<Dto<Employee>> call, @NonNull Response<Dto<Employee>> response) {

                if (response.isSuccessful()){
                    Dto<Employee> resBody = response.body();

                   if (resBody != null){
                       if (resBody.getStatusCode() == STATUS_OK){
                           listener.onSuccess(resBody.getData());
                       }else{
                           listener.onFailure(resBody.getMessage());
                       }
                   }else {
                       Log.e(Constants.LOG_TAG, response.message());
                       listener.onFailure("Invalid response from server");
                   }
                }else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure("Something went wrong");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dto<Employee>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Something went wrong");
            }
        });
    }

}
