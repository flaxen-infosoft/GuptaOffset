package com.flaxeninfosoft.guptaoffset.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.flaxeninfosoft.guptaoffset.api.AttendanceApiInterface;
import com.flaxeninfosoft.guptaoffset.api.AuthApiInterface;
import com.flaxeninfosoft.guptaoffset.api.DealerApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EmployeeApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EodApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LeaveApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LocationApiInterface;
import com.flaxeninfosoft.guptaoffset.api.OrderApiInterface;
import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainRepository {

    private static MainRepository instance;

    private final AuthApiInterface authApiInterface;
    private final DealerApiInterface dealerApiInterface;

    private final EmployeeApiInterface employeeApiInterface;
    private final LeaveApiInterface leaveApiInterface;
    private final EodApiInterface eodApiInterface;
    private final LocationApiInterface locationApiInterface;
    private final AttendanceApiInterface attendanceApiInterface;

    private final OrderApiInterface orderApiInterface;

    private final int STATUS_NOT_FOUND = 404;

    private MainRepository(Context context) {
        Retrofit apiClient = RetrofitClient.getClient();

        authApiInterface = apiClient.create(AuthApiInterface.class);
        dealerApiInterface = apiClient.create(DealerApiInterface.class);
        attendanceApiInterface = apiClient.create(AttendanceApiInterface.class);
        employeeApiInterface = apiClient.create(EmployeeApiInterface.class);
        leaveApiInterface = apiClient.create(LeaveApiInterface.class);
        eodApiInterface = apiClient.create(EodApiInterface.class);
        locationApiInterface = apiClient.create(LocationApiInterface.class);
        orderApiInterface = apiClient.create(OrderApiInterface.class);
    }

    public static MainRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MainRepository(context);
        }

        return instance;
    }

    public void login(LoginModel credentials, ApiResponseListener<Employee, String> listener) {

        Call<Employee> loginCall = authApiInterface.loginByEmailAndPassword(credentials.getEmail(), credentials.getPassword());

        processEmployeeCall(loginCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getEmployeeById(Long empId, ApiResponseListener<Employee, String> listener) {

        Call<Employee> getEmployeeByIdCall = employeeApiInterface.getEmployeeById(empId);

        processEmployeeCall(getEmployeeByIdCall, listener);
    }

    public void getAllEmployees(ApiResponseListener<List<Employee>, String> listener) {
        Call<List<Employee>> getAllEmployeesCall = employeeApiInterface.getAllEmployees();

        processEmployeeListCall(getAllEmployeesCall, listener);
    }

    public void getAllSuperEmployees(ApiResponseListener<List<Employee>, String> listener) {
        Call<List<Employee>> allSuperEmployeesCall = employeeApiInterface.getAllSuperEmployees();

        processEmployeeListCall(allSuperEmployeesCall, listener);
    }

    public void getEmployeesOfSuperEmployee(Long empId, ApiResponseListener<List<Employee>, String> listener) {
        Call<List<Employee>> employeesOfSuperEmployeeCall = employeeApiInterface.getEmployeesOfSuperEmployee(empId);

        processEmployeeListCall(employeesOfSuperEmployeeCall, listener);
    }

    public void addEmployee(Employee employee, ApiResponseListener<Employee, String> listener) {
        employee.setDesignation(Constants.DESIGNATION_EMPLOYEE);
        Call<Employee> addEmployeeCall = employeeApiInterface.addEmployee(employee);

        processEmployeeCall(addEmployeeCall, listener);
    }

    public void addSuperEmployee(Employee employee, ApiResponseListener<Employee, String> listener) {
        employee.setDesignation(Constants.DESIGNATION_SUPER_EMPLOYEE);
        Call<Employee> addSuperEmployeeCall = employeeApiInterface.addSuperEmployee(employee);

        processEmployeeCall(addSuperEmployeeCall, listener);
    }

    public void updateEmployee(Employee employee, ApiResponseListener<Employee, String> listener) {
        Call<Employee> updateEmployeeByIdCall = employeeApiInterface.updateEmployeeById(employee);

        processEmployeeCall(updateEmployeeByIdCall, listener);
    }

    public void suspendEmployeeById(Long empId, ApiResponseListener<Employee, String> listener) {
        Call<Employee> suspendEmployeeCall = employeeApiInterface.suspendEmployeeById(empId);

        processEmployeeCall(suspendEmployeeCall, listener);
    }

    public void activateEmployeeById(Long empId, ApiResponseListener<Employee, String> listener) {
        Call<Employee> activateEmployeeCall = employeeApiInterface.activateEmployeeById(empId);

        processEmployeeCall(activateEmployeeCall, listener);
    }

    private void processEmployeeCall(Call<Employee> call, ApiResponseListener<Employee, String> listener) {
        call.enqueue(new Callback<Employee>() {

            @Override
            public void onResponse(@NonNull Call<Employee> call, @NonNull Response<Employee> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Employee> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

    private void processEmployeeListCall(Call<List<Employee>> call, ApiResponseListener<List<Employee>, String> listener) {
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(@NonNull Call<List<Employee>> call, @NonNull Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Employee>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void getLeaveById(Long leaveId, ApiResponseListener<Leave, String> listener) {
        Call<Leave> getLeaveByIdCall = leaveApiInterface.getLeaveById(leaveId);

        processLeaveCall(getLeaveByIdCall, listener);
    }

    public void approveLeaveById(Long leaveId, ApiResponseListener<Leave, String> listener) {
        Call<Leave> approveLeaveCall = leaveApiInterface.approveLeaveById(leaveId);

        processLeaveCall(approveLeaveCall, listener);
    }

    public void rejectLeaveById(Long leaveId, ApiResponseListener<Leave, String> listener) {
        Call<Leave> rejectLeaveCall = leaveApiInterface.rejectLeaveById(leaveId);

        processLeaveCall(rejectLeaveCall, listener);
    }

    public void getAllLeaves(ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> allLeavesCall = leaveApiInterface.getAllLeaves();

        processLeaveListCall(allLeavesCall, listener);
    }

    public void getAllPendingLeaves(ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> pendingLeavesCall = leaveApiInterface.getAllPendingLeaves();

        processLeaveListCall(pendingLeavesCall, listener);
    }

    public void getAllApprovedLeaves(ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> approvedLeavesCall = leaveApiInterface.getAllApprovedLeaves();

        processLeaveListCall(approvedLeavesCall, listener);
    }

    public void getAllRejectedLeaves(ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> rejectedLeavesCall = leaveApiInterface.getAllRejectedLeaves();

        processLeaveListCall(rejectedLeavesCall, listener);
    }

    public void getEmployeeAllLeaves(Long empId, ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> employeeLeavesCall = leaveApiInterface.getEmployeeLeave(empId);

        processLeaveListCall(employeeLeavesCall, listener);
    }

    public void addLeave(Leave leave, ApiResponseListener<Leave, String> listener) {
        Call<Leave> addLeaveCall = leaveApiInterface.addLeave(leave);

        processLeaveCall(addLeaveCall, listener);
    }

    private void processLeaveListCall(Call<List<Leave>> call, ApiResponseListener<List<Leave>, String> listener) {
        call.enqueue(new Callback<List<Leave>>() {
            @Override
            public void onResponse(@NonNull Call<List<Leave>> call, @NonNull Response<List<Leave>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Leave>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

    private void processLeaveCall(Call<Leave> call, ApiResponseListener<Leave, String> listener) {
        call.enqueue(new Callback<Leave>() {
            @Override
            public void onResponse(@NonNull Call<Leave> call, @NonNull Response<Leave> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Leave> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void getEodById(Long eodId, ApiResponseListener<Eod, String> listener) {
        Call<Eod> eodByIdCall = eodApiInterface.getEodById(eodId);

        processEodCall(eodByIdCall, listener);
    }

    public void addEod(Long empId, Eod eod, ApiResponseListener<Eod, String> listener) {
        Call<Eod> addEodCall = eodApiInterface.addEod(empId, eod);

        processEodCall(addEodCall, listener);
    }

    public void getEmployeeAllEodsByEmpId(Long empId, ApiResponseListener<List<Eod>, String> listener) {
        Call<List<Eod>> employeeAllEodCall = eodApiInterface.getEmployeeAllEodsById(empId);

        processEodListCall(employeeAllEodCall, listener);
    }

    public void getAllEod(ApiResponseListener<List<Eod>, String> listener) {
        Call<List<Eod>> allEodCall = eodApiInterface.getAllEods();

        processEodListCall(allEodCall, listener);
    }

    public void getEmployeeTodayEod(Long empId, ApiResponseListener<Eod, String> listener) {
        Call<Eod> todaysEodCall = eodApiInterface.getEmployeeTodaysEod(empId);

        processEodCall(todaysEodCall, listener);
    }

    private void processEodCall(Call<Eod> call, ApiResponseListener<Eod, String> listener) {
        call.enqueue(new Callback<Eod>() {
            @Override
            public void onResponse(@NonNull Call<Eod> call, @NonNull Response<Eod> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Eod> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

    private void processEodListCall(Call<List<Eod>> call, ApiResponseListener<List<Eod>, String> listener) {
        call.enqueue(new Callback<List<Eod>>() {
            @Override
            public void onResponse(@NonNull Call<List<Eod>> call, @NonNull Response<List<Eod>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Eod>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void getEmployeeCurrentLocationById(Long empId, ApiResponseListener<Location, String> listener) {
        Call<Location> call = locationApiInterface.getEmployeeCurrentLocationById(empId);

        processLocationCall(call, listener);
    }

    public void getEmployeeTodaysLocationHistory(Long empId, ApiResponseListener<List<Location>, String> listener) {
        Call<List<Location>> call = locationApiInterface.getEmployeeTodaysLocationHistory(empId);

        processLocationListCall(call, listener);
    }

    public void addEmployeeLocation(Location location, ApiResponseListener<Location, String> listener) {
        Call<Location> call = locationApiInterface.addEmployeeLocation(location);

        processLocationCall(call, listener);
    }

    private void processLocationCall(Call<Location> call, ApiResponseListener<Location, String> listener) {
        call.enqueue(new Callback<Location>() {
            @Override
            public void onResponse(@NonNull Call<Location> call, @NonNull Response<Location> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Location> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

    private void processLocationListCall(Call<List<Location>> call, ApiResponseListener<List<Location>, String> listener) {
        call.enqueue(new Callback<List<Location>>() {
            @Override
            public void onResponse(@NonNull Call<List<Location>> call, @NonNull Response<List<Location>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Location>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void getOrderById(Long orderId, ApiResponseListener<Order, String> listener) {
        Call<Order> call = orderApiInterface.getOrderById(orderId);

        processOrderCall(call, listener);
    }

    public void getEmployeeOrders(Long empId, ApiResponseListener<List<Order>, String> listener) {
        Call<List<Order>> call = orderApiInterface.getEmployeeOrders(empId);

        processOrderListCall(call, listener);
    }

    public void getAllOrders(ApiResponseListener<List<Order>, String> listener) {
        Call<List<Order>> call = orderApiInterface.getAllOrders();

        processOrderListCall(call, listener);
    }

    public void addOrder(Order order, ApiResponseListener<Order, String> listener) {
        Call<Order> call = orderApiInterface.addOrder(order);

        processOrderCall(call, listener);
    }

    private void processOrderCall(Call<Order> call, ApiResponseListener<Order, String> listener) {
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(@NonNull Call<Order> call, @NonNull Response<Order> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Order> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

    private void processOrderListCall(Call<List<Order>> call, ApiResponseListener<List<Order>, String> listener) {
        call.enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(@NonNull Call<List<Order>> call, @NonNull Response<List<Order>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else if (response.code() == STATUS_NOT_FOUND) {
                    listener.onFailure("Api not found.");
                } else {
                    Log.e(Constants.LOG_TAG, response.message());
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Order>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void addDealer(Long empId, Dealer dealer, ApiResponseListener<Dealer, String> listener) {
        Call<Dealer> call = dealerApiInterface.addDealer(empId, dealer);

        processDealerCall(call, listener);
    }

    private void processDealerCall(Call<Dealer> call, ApiResponseListener<Dealer, String> listener) {
        call.enqueue(new Callback<Dealer>() {
            @Override
            public void onResponse(@NonNull Call<Dealer> call, @NonNull Response<Dealer> response) {
                if (response.isSuccessful()) {
                    try {
                        listener.onSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure("Invalid response from the server");
                    }
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Dealer> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }


//    ----------------------------------------------------------------------------------------------

    public void getEmployeeTodaysAttendance(Long empId, ApiResponseListener<Attendance, String> listener) {

        Call<Attendance> call = attendanceApiInterface.getEmployeeTodaysAttendance(empId);

        processAttendanceCall(call, listener);
    }

    public void punchAttendance(Long empId, Long reading, String encodedImage, ApiResponseListener<Attendance, String> listener) {

        Call<Attendance> call = attendanceApiInterface.punchAttendance(empId, reading, encodedImage);

        processAttendanceCall(call, listener);
    }

    private void processAttendanceCall(Call<Attendance> call, ApiResponseListener<Attendance, String> listener) {

        call.enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(@NonNull Call<Attendance> call, @NonNull Response<Attendance> response) {
                if (response.isSuccessful()) {
                    try {
                        listener.onSuccess(response.body());
                    } catch (Exception e) {
                        e.printStackTrace();
                        listener.onFailure("Invalid response from the server");
                    }
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Attendance> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }

}
