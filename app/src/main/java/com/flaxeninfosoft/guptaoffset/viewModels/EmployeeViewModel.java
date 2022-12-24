package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private final MainRepository repo;
    private final SharedPrefs sharedPrefs;

    private final MutableLiveData<String> toastMessage;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());
        sharedPrefs = SharedPrefs.getInstance(getApplication().getApplicationContext());

        toastMessage = new MutableLiveData<>();

    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Client>> getEmployeeClients(Long empId) {
        MutableLiveData<List<Client>> flag = new MutableLiveData<>();
        repo.getEmployeeClientsById(empId, new ApiResponseListener<List<Client>, String>() {
            @Override
            public void onSuccess(List<Client> response) {
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

    public LiveData<List<Client>> getCurrentEmployeeClients() {

        MutableLiveData<List<Client>> flag = new MutableLiveData<>();
        Long empId = getCurrentEmployeeId();

        repo.getEmployeeClientsById(empId, new ApiResponseListener<List<Client>, String>() {
            @Override
            public void onSuccess(List<Client> response) {
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

    public LiveData<Client> getClientById(Long clientId) {
        MutableLiveData<Client> flag = new MutableLiveData<>();

        repo.getClientById(clientId, new ApiResponseListener<Client, String>() {
            @Override
            public void onSuccess(Client response) {
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

    public LiveData<Boolean> addClient(Client client) {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        client.setAssignToId(getCurrentEmployeeId());
        repo.addClient(client, new ApiResponseListener<Client, String>() {
            @Override
            public void onSuccess(Client response) {
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

    public Employee getCurrentEmployee() {
        return sharedPrefs.getCurrentEmployee();
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Boolean> addExpense(Expense expense) {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        expense.setEmpId(getCurrentEmployeeId());
        repo.addExpense(expense, new ApiResponseListener<Expense, String>() {
            @Override
            public void onSuccess(Expense response) {
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

    public LiveData<List<Expense>> getCurrentEmployeeExpenses() {
        MutableLiveData<List<Expense>> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();

        repo.getEmployeeExpenses(empId, new ApiResponseListener<List<Expense>, String>() {
            @Override
            public void onSuccess(List<Expense> response) {
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

    public LiveData<List<Eod>> getCurrentEmployeeAllEods() {
        MutableLiveData<List<Eod>> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();

        repo.getEmployeeAllEodsByEmpId(empId, new ApiResponseListener<List<Eod>, String>() {
            @Override
            public void onSuccess(List<Eod> response) {
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

    public LiveData<Eod> getCurrentEmployeeTodaysEod() {
        MutableLiveData<Eod> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeTodayEod(empId, new ApiResponseListener<Eod, String>() {
            @Override
            public void onSuccess(Eod response) {
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

    public LiveData<Boolean> addOrder(Order order) {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        order.setEmpId(getCurrentEmployeeId());
        repo.addOrder(order, new ApiResponseListener<Order, String>() {
            @Override
            public void onSuccess(Order response) {
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

    public LiveData<List<Order>> getCurrentEmployeeOrders() {
        MutableLiveData<List<Order>> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeOrders(empId, new ApiResponseListener<List<Order>, String>() {
            @Override
            public void onSuccess(List<Order> response) {
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

    public LiveData<Leave> getLeaveById(Long leaveId) {
        MutableLiveData<Leave> flag = new MutableLiveData<>();

        repo.getLeaveById(leaveId, new ApiResponseListener<Leave, String>() {
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

    public LiveData<Boolean> addLeave(Leave leave) {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        repo.addLeave(leave, new ApiResponseListener<Leave, String>() {
            @Override
            public void onSuccess(Leave response) {
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

    public LiveData<List<Leave>> getCurrentEmployeeLeaves() {
        MutableLiveData<List<Leave>> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeAllLeaves(empId, new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
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

    public LiveData<Location> addCurrentEmployeeLocation(Location location) {
        MutableLiveData<Location> flag = new MutableLiveData<>();

        Long empId = getCurrentEmployeeId();
        location.setEmpId(empId);
        repo.addEmployeeLocation(location, new ApiResponseListener<Location, String>() {
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

//    ----------------------------------------------------------------------------------------------

    public LiveData<String> getToastMessageLiveData() {
        return toastMessage;
    }

    private Long getCurrentEmployeeId() {
        return sharedPrefs.getCurrentEmployee().getId();
    }
}
