package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

public class BaseViewModel extends AndroidViewModel {

    private final MainRepository repo;
    private final SharedPrefs sharedPrefs;

    private final MutableLiveData<String> toastMessage;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());
        sharedPrefs = SharedPrefs.getInstance(application.getApplicationContext());
        this.toastMessage = new MutableLiveData<>();

    }

//    ----------------------------------------------------------------------------------------------

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

//    ----------------------------------------------------------------------------------------------

    public LiveData<Expense> getExpenseById(Long expenseId) {
        MutableLiveData<Expense> flag = new MutableLiveData<>();

        repo.getExpenseById(expenseId, new ApiResponseListener<Expense, String>() {
            @Override
            public void onSuccess(Expense response) {
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

//    ----------------------------------------------------------------------------------------------

    public LiveData<Client> updateClientById(Client client) {
        MutableLiveData<Client> flag = new MutableLiveData<>();

        repo.updateClient(client, new ApiResponseListener<Client, String>() {
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

//    ----------------------------------------------------------------------------------------------

    public LiveData<Order> getOrderById(Long orderId) {
        MutableLiveData<Order> flag = new MutableLiveData<>();

        repo.getOrderById(orderId, new ApiResponseListener<Order, String>() {
            @Override
            public void onSuccess(Order response) {
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

//    ----------------------------------------------------------------------------------------------

    public LiveData<Eod> getEodById(Long eodId) {
        MutableLiveData<Eod> flag = new MutableLiveData<>();

        repo.getEodById(eodId, new ApiResponseListener<Eod, String>() {
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

    protected Long getCurrentEmployeeId() {
        return sharedPrefs.getCurrentEmployee().getId();
    }

//    ----------------------------------------------------------------------------------------------

    protected MutableLiveData<String> getToastMessageLiveData() {
        return toastMessage;
    }

    public void logout() {
        sharedPrefs.clearCredentials();
    }
}
