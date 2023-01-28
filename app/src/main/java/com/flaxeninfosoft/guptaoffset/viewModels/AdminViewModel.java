package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

public class AdminViewModel extends SuperEmployeeViewModel{

    private final MainRepository repo;


    private final MutableLiveData<List<Employee>> employeeList;


    public AdminViewModel(@NonNull Application application){
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());


        employeeList = new MutableLiveData<>();
    }

    public LiveData<Boolean> addEmployee(Employee employee) {
        employee.setAssignedTo(getCurrentEmployeeId());
        employee.setDesignation(Constants.DESIGNATION_SUPER_EMPLOYEE);

        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        repo.addEmployee(employee, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(true);
            }

            @Override
            public void onFailure(String error) {
               getToastMessageLiveData().postValue(error);
                flag.postValue(false);
            }
        });

        return flag;
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

    public LiveData<Boolean> addSuperEmployee(Employee employee) {
        employee.setAssignedTo(getCurrentEmployeeId());
        employee.setDesignation(Constants.DESIGNATION_SUPER_EMPLOYEE);

        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        repo.addEmployee(employee, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                getToastMessageLiveData().postValue(error);
                flag.postValue(false);
            }
        });

        return flag;
    }
}
