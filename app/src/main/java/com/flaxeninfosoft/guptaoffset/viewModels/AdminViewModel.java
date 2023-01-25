package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;

import java.util.List;

public class AdminViewModel extends SuperEmployeeViewModel{

    private final MainRepository repo;

    private final MutableLiveData<List<Employee>> employeeList;


    public AdminViewModel(@NonNull Application application){
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());


        employeeList = new MutableLiveData<>();
    }


    public LiveData<List<Employee>> getAllEmployees(){

        repo.getAllEmployees(new ApiResponseListener<List<Employee>, String>() {
            @Override
            public void onSuccess(List<Employee> response) {
                employeeList.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                employeeList.postValue(null);
                getToastMessageLiveData().postValue(error);
            }
        });

        return employeeList;
    }
}
