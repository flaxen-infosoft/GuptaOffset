package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AdminMainViewModel extends AndroidViewModel {

    private final MutableLiveData<String> toastMessage;

    public AdminMainViewModel(@NonNull Application application) {
        super(application);

        toastMessage = new MutableLiveData<>();
    }

    public LiveData<String> getToastMessageLiveData(){
        return toastMessage;
    }
}
