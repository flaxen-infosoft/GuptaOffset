package com.flaxeninfosoft.guptaoffset.repositories;

import android.content.Context;

import com.flaxeninfosoft.guptaoffset.api.AdminApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EmployeeApiInterface;
import com.flaxeninfosoft.guptaoffset.utils.RetrofitClient;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

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

    public MainRepository getInstance(Context context){
        if (instance == null){
            instance = new MainRepository(context);
        }

        return instance;
    }

}
