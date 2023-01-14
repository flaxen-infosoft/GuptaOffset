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

public class SuperEmployeeViewModel extends EmployeeViewModel {

    private final MainRepository repo;

    private final MutableLiveData<List<Employee>> currentSuperEmployeeEmployees;
    private final MutableLiveData<String> toastMessage;

    public SuperEmployeeViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());

        toastMessage = new MutableLiveData<>();
        currentSuperEmployeeEmployees = new MutableLiveData<>();
    }

    public LiveData<List<Employee>> getCurrentSuperEmployeeEmployees() {
        repo.getEmployeesOfSuperEmployee(getCurrentEmployeeId(), new ApiResponseListener<List<Employee>, String>() {
            @Override
            public void onSuccess(List<Employee> response) {
                currentSuperEmployeeEmployees.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
                currentSuperEmployeeEmployees.postValue(null);
            }
        });

        return currentSuperEmployeeEmployees;
    }

    public LiveData<Boolean> addEmployee(Employee employee) {
        employee.setAssignedTo(getCurrentEmployeeId());
        employee.setDesignation(Constants.DESIGNATION_EMPLOYEE);

        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        repo.addEmployee(employee, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
                flag.postValue(false);
            }
        });

        return flag;
    }

}
