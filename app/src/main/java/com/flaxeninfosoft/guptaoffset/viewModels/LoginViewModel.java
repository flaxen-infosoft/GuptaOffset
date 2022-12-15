package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

public class LoginViewModel extends AndroidViewModel {

    private final MainRepository repo;
    private final SharedPrefs sharedPrefs;

    private final MutableLiveData<String> toastMessage;

    public LoginViewModel(@NonNull Application application) {
        super(application);

        repo = MainRepository.getInstance(application.getApplicationContext());
        sharedPrefs = SharedPrefs.getInstance(application.getApplicationContext());

        toastMessage = new MutableLiveData<>();
    }

    public LiveData<String> getToastMessage(){
        return toastMessage;
    }

    public LiveData<Boolean> loginUser(LoginModel credentials) {

        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        repo.login(credentials, new ApiResponseListener<Employee, String>() {

            @Override
            public void onSuccess(Employee emp) {
                sharedPrefs.setCredentials(credentials);
                sharedPrefs.setCurrentEmployee(emp);
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