package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Lr;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;

import java.io.IOException;
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

    public LiveData<Employee> getEmployeeById(long empId) {
        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.getEmployeeById(empId, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return flag;
    }

    public LiveData<List<Employee>> getCurrentSuperEmployeeEmployees() {

        fetchCurrentEmployeesOfSuperEmployee();

        return currentSuperEmployeeEmployees;
    }

    //    Yeah Boiii!!!

    public void fetchCurrentEmployeesOfSuperEmployee(){

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
    }
    public LiveData<Boolean> addEmployee(Employee employee) {
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

    public LiveData<Employee> updateEmployee(Employee employee) {

        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.updateEmployee(employee, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(new Employee());
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return flag;
    }

    public LiveData<Employee> suspendEmployee(Employee employee) {
        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.suspendEmployeeById(employee.getId(), new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(new Employee());
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return flag;
    }

    public LiveData<List<EmployeeHistory>> getEmployeeHistoryById(Long empId) {
        MutableLiveData<List<EmployeeHistory>> flag = new MutableLiveData<>();

        //empty string is passed instead of date which gives all history data
        repo.getEmployeeHomeHistory(empId, getCurrentEmployeeId(),"", new ApiResponseListener<List<EmployeeHistory>, String>() {
            @Override
            public void onSuccess(List<EmployeeHistory> response) {
                flag.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
                flag.postValue(null);
            }
        });

        return flag;
    }

    public LiveData<List<PaymentRequest>> getAllPendingPaymentRequestsToEmployee() {
        MutableLiveData<List<PaymentRequest>> flag = new MutableLiveData<>();

        repo.getPendingRequestsToEmployee(getCurrentEmployeeId(), new ApiResponseListener<List<PaymentRequest>, String>() {
            @Override
            public void onSuccess(List<PaymentRequest> response) {
                flag.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                getToastMessageLiveData().postValue(error);
            }
        });

        return flag;
    }

    public LiveData<PaymentRequest> updatePaymentRequest(PaymentRequest payment) {

        MutableLiveData<PaymentRequest> flag = new MutableLiveData<>();

        repo.updatePaymentRequest(payment, new ApiResponseListener<PaymentRequest, String>() {

            @Override
            public void onSuccess(PaymentRequest response) {



                flag.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                getToastMessageLiveData().postValue(error);
            }
        });

        return flag;
    }

    public LiveData<Boolean> sendLr(Lr lr, Uri image) throws IOException {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();
        lr.setImage(FileEncoder.encodeImage(getApplication().getContentResolver(), image));
        repo.sendLr(lr, new ApiResponseListener<Lr, String>() {
            @Override
            public void onSuccess(Lr response) {
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
