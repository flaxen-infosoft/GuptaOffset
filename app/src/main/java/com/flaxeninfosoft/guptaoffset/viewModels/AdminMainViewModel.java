package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.Constants;

import java.util.List;

public class AdminMainViewModel extends BaseViewModel {

    private final MainRepository repo;

    private final MutableLiveData<List<Client>> allClients;
    private final MutableLiveData<List<Employee>> allSuperEmployees;
    private final MutableLiveData<List<Employee>> allEmployees;

    private final MutableLiveData<List<Leave>> allLeaves;
    private final MutableLiveData<List<Leave>> pendingLeaves;
    private final MutableLiveData<List<Leave>> approvedLeaves;
    private final MutableLiveData<List<Leave>> rejectedLeaves;

    private final MutableLiveData<List<Expense>> allExpenses;

    private final MutableLiveData<List<Order>> allOrders;

    private final MutableLiveData<List<Eod>> allEods;

    private final MutableLiveData<String> toastMessage;

    public AdminMainViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());

        allClients = new MutableLiveData<>();
        allSuperEmployees = new MutableLiveData<>();
        allEmployees = new MutableLiveData<>();

        allLeaves = new MutableLiveData<>();
        pendingLeaves = new MutableLiveData<>();
        approvedLeaves = new MutableLiveData<>();
        rejectedLeaves = new MutableLiveData<>();

        allExpenses = new MutableLiveData<>();

        allOrders = new MutableLiveData<>();

        allEods = new MutableLiveData<>();

        toastMessage = new MutableLiveData<>();
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Employee> getEmployeeById(Long empId) {
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

    public LiveData<List<Employee>> getAllSuperEmployees() {

        repo.getAllSuperEmployees(new ApiResponseListener<List<Employee>, String>() {
            @Override
            public void onSuccess(List<Employee> response) {
                allSuperEmployees.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allSuperEmployees.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allSuperEmployees;
    }

    public LiveData<List<Employee>> getAllEmployees() {

        repo.getAllEmployees(new ApiResponseListener<List<Employee>, String>() {
            @Override
            public void onSuccess(List<Employee> response) {
                allEmployees.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allEmployees.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allEmployees;
    }

    public LiveData<Employee> updateEmployee(Employee employee) {
        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.updateEmployee(employee, new ApiResponseListener<Employee, String>() {
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

    public LiveData<Employee> suspendEmployee(Long empId) {
        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.suspendEmployeeById(empId, new ApiResponseListener<Employee, String>() {
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

    public LiveData<Employee> activateEmployee(Long empId) {
        MutableLiveData<Employee> flag = new MutableLiveData<>();

        repo.activateEmployeeById(empId, new ApiResponseListener<Employee, String>() {
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

    public LiveData<Boolean> addSuperEmployee(Employee employee) {

        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        employee.setDesignation(Constants.DESIGNATION_SUPER_EMPLOYEE);
        employee.setAssignedTo(0L);
        employee.setStatus(Constants.EMPLOYEE_STATUS_ACTIVE);

        repo.addEmployee(employee, new ApiResponseListener<Employee, String>() {
            @Override
            public void onSuccess(Employee response) {
                flag.postValue(true);
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(false);
                toastMessage.postValue(error);
            }
        });

        return flag;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Leave> approveLeaveById(Long leaveId) {
        MutableLiveData<Leave> flag = new MutableLiveData<>();

        repo.approveLeaveById(leaveId, new ApiResponseListener<Leave, String>() {
            @Override
            public void onSuccess(Leave response) {
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

    public LiveData<Leave> rejectLeaveById(Long leaveId) {
        MutableLiveData<Leave> flag = new MutableLiveData<>();

        repo.rejectLeaveById(leaveId, new ApiResponseListener<Leave, String>() {
            @Override
            public void onSuccess(Leave response) {
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

    public LiveData<List<Leave>> getAllLeaves() {

        repo.getAllLeaves(new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
                allLeaves.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allLeaves.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allLeaves;
    }

    public LiveData<List<Leave>> getAllPendingLeaves() {

        repo.getAllPendingLeaves(new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
                pendingLeaves.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                pendingLeaves.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return pendingLeaves;
    }

    public LiveData<List<Leave>> getAllApprovedLeaves() {

        repo.getAllApprovedLeaves(new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
                approvedLeaves.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                approvedLeaves.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return approvedLeaves;
    }

    public LiveData<List<Leave>> getAllRejectedLeaves() {

        repo.getAllRejectedLeaves(new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
                rejectedLeaves.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                rejectedLeaves.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return rejectedLeaves;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Expense>> getAllExpenses() {

        repo.getAllExpenses(new ApiResponseListener<List<Expense>, String>() {
            @Override
            public void onSuccess(List<Expense> response) {
                allExpenses.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allExpenses.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allExpenses;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Client>> getAllClients() {

        repo.getAllClients(new ApiResponseListener<List<Client>, String>() {
            @Override
            public void onSuccess(List<Client> response) {
                allClients.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allClients.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allClients;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Order>> getAllOrders() {

        repo.getAllOrders(new ApiResponseListener<List<Order>, String>() {
            @Override
            public void onSuccess(List<Order> response) {
                allOrders.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allOrders.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allOrders;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Eod>> getAllEods() {

        repo.getAllEod(new ApiResponseListener<List<Eod>, String>() {
            @Override
            public void onSuccess(List<Eod> response) {
                allEods.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                allEods.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return allEods;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Location> getEmployeeCurrentLocation(Long empId) {
        MutableLiveData<Location> flag = new MutableLiveData<>();

        repo.getEmployeeCurrentLocationById(empId, new ApiResponseListener<Location, String>() {
            @Override
            public void onSuccess(Location response) {
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

    public LiveData<List<Location>> getEmployeeTodayLocationHistory(Long empId) {
        MutableLiveData<List<Location>> flag = new MutableLiveData<>();

        repo.getEmployeeTodaysLocationHistory(empId, new ApiResponseListener<List<Location>, String>() {
            @Override
            public void onSuccess(List<Location> response) {
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

//    ----------------------------------------------------------------------------------------------

    @Override
    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessage;
    }

}
