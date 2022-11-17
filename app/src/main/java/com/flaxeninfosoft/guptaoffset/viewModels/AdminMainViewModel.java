package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;

import java.util.List;

public class AdminMainViewModel extends AndroidViewModel {

    private final MainRepository repo;

    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<List<Employee>> allEmployeeListLiveData;

    public AdminMainViewModel(@NonNull Application application) {
        super(application);

        repo = MainRepository.getInstance(application.getApplicationContext());

        toastMessage = new MutableLiveData<>();
        allEmployeeListLiveData = new MutableLiveData<>();
    }

    public void fetchAllEmployees() {
        repo.fetchAllEmployees(new ApiResponseListener<List<Employee>, String>( ){

            @Override
            public void onSuccess(List<Employee> response) {
                allEmployeeListLiveData.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
            }
        });
    }

    public LiveData<String> getToastMessageLiveData(){
        return toastMessage;
    }

    public LiveData<List<Employee>> getAllEmployeeListLiveData(){
        if (allEmployeeListLiveData == null || allEmployeeListLiveData.getValue().isEmpty()){
            fetchAllEmployees();
        }
        return allEmployeeListLiveData;
    }

    public LiveData<String> toastMessage(){
        return toastMessage;
    }
}
