package com.flaxeninfosoft.guptaoffset.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.flaxeninfosoft.guptaoffset.api.AttendanceApiInterface;
import com.flaxeninfosoft.guptaoffset.api.AuthApiInterface;
import com.flaxeninfosoft.guptaoffset.api.DealerApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EmployeeApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EodApiInterface;
import com.flaxeninfosoft.guptaoffset.api.HistoryApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LeaveApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LocationApiInterface;
import com.flaxeninfosoft.guptaoffset.api.MessageApiInterface;
import com.flaxeninfosoft.guptaoffset.api.OrderApiInterface;
import com.flaxeninfosoft.guptaoffset.api.PaymentApiInterface;
import com.flaxeninfosoft.guptaoffset.api.SchoolApiInterface;
import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
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
    private final SchoolApiInterface schoolApiInterface;
    private final PaymentApiInterface paymentApiInterface;
    private final HistoryApiInterface historyApiInterface;
    private final EmployeeApiInterface employeeApiInterface;
    private final LeaveApiInterface leaveApiInterface;
    private final EodApiInterface eodApiInterface;
    private final LocationApiInterface locationApiInterface;
    private final AttendanceApiInterface attendanceApiInterface;
    private final MessageApiInterface messageApiInterface;
    private final OrderApiInterface orderApiInterface;
    private final int STATUS_NOT_FOUND = 404;

    private School newSchool;

    private MainRepository(Context context) {
        Retrofit apiClient = RetrofitClient.getClient();

        authApiInterface = apiClient.create(AuthApiInterface.class);
        dealerApiInterface = apiClient.create(DealerApiInterface.class);
        paymentApiInterface = apiClient.create(PaymentApiInterface.class);
//        Yeah Boii!
        schoolApiInterface = apiClient.create(SchoolApiInterface.class);
        attendanceApiInterface = apiClient.create(AttendanceApiInterface.class);
        employeeApiInterface = apiClient.create(EmployeeApiInterface.class);
        leaveApiInterface = apiClient.create(LeaveApiInterface.class);
        eodApiInterface = apiClient.create(EodApiInterface.class);
        locationApiInterface = apiClient.create(LocationApiInterface.class);
        orderApiInterface = apiClient.create(OrderApiInterface.class);
        historyApiInterface = apiClient.create(HistoryApiInterface.class);
        messageApiInterface = apiClient.create(MessageApiInterface.class);

        newSchool = new School();
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
        Call<Employee> addEmployeeCall = employeeApiInterface.addEmployee(employee);

        processEmployeeCall(addEmployeeCall, listener);
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
                Log.i("CRM-LOG", t.getCause().getLocalizedMessage());
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

    public void getDealerById(Long dealerId, ApiResponseListener<Dealer, String> listener) {
        Call<Dealer> dealerByIdCall = dealerApiInterface.getDealerById(dealerId);

        processDealerCall(dealerByIdCall, listener);
    }

    public void getDealerByEmpId(Long empId, ApiResponseListener<List<Dealer>, String> listener) {
        Call<List<Dealer>> employeeDealersCall = dealerApiInterface.getEmployeeDealers(empId);

        processDealerListCall(employeeDealersCall, listener);
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

    private void processDealerListCall(Call<List<Dealer>> call, ApiResponseListener<List<Dealer>, String> listener) {
        call.enqueue(new Callback<List<Dealer>>() {
            @Override
            public void onResponse(@NonNull Call<List<Dealer>> call, @NonNull Response<List<Dealer>> response) {
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
            public void onFailure(@NonNull Call<List<Dealer>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }


    //    ----------------------------------------------------------------------------------------------
    public void addPayment(Long empId, PaymentRequest paymentRequest, ApiResponseListener<PaymentRequest, String> listener) {
        Call<PaymentRequest> call = paymentApiInterface.addPayment(empId, paymentRequest);

        processPaymentCall(call, listener);
    }

    private void processPaymentCall(Call<PaymentRequest> call, ApiResponseListener<PaymentRequest, String> listener) {
        call.enqueue(new Callback<PaymentRequest>() {
            @Override
            public void onResponse(@NonNull Call<PaymentRequest> call, @NonNull Response<PaymentRequest> response) {
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
            public void onFailure(@NonNull Call<PaymentRequest> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void addSchool(Long empId, School school, ApiResponseListener<School, String> listener) {
        Call<School> call = schoolApiInterface.addSchool(empId, school);

        processSchoolCall(call, listener);
    }

    private void processSchoolCall(Call<School> call, ApiResponseListener<School, String> listener) {
        call.enqueue(new Callback<School>() {
            @Override
            public void onResponse(@NonNull Call<School> call, @NonNull Response<School> response) {
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
            public void onFailure(@NonNull Call<School> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }

    public void getSchoolById(Long schoolId, ApiResponseListener<School, String> listener) {
        Call<School> schoolByIdCall = schoolApiInterface.getSchoolById(schoolId);

        processSchoolCall(schoolByIdCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void sendMessage(Long empId, Message message, ApiResponseListener<Message, String> listener) {

//        Call<Message> sendMessageCall = messageApiInterface.sendMessage(empId, message);
        Call<Message> sendMessageCall = messageApiInterface.sendMessage(message.getReceiverId(), message);

        processMessageCall(sendMessageCall, listener);
    }

    public void getMessage(Long empId, ApiResponseListener<List<Message>, String> listener) {

        Call<List<Message>> getMessagesCall = messageApiInterface.getMessages(empId);

        processMessageListCall(getMessagesCall, listener);
    }

    private void processMessageListCall(Call<List<Message>> getMessagesCall, ApiResponseListener<List<Message>, String> listener) {
        getMessagesCall.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(@NonNull Call<List<Message>> call, @NonNull Response<List<Message>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Message>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

    private void processMessageCall(Call<Message> messageCall, ApiResponseListener<Message, String> listener) {

        messageCall.enqueue(new Callback<Message>() {
            @Override
            public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure(t.getLocalizedMessage());
            }
        });
    }

//    ----------------------------------------------------------------------------------------------

    public void getEmployeeHomeHistory(Long empId, Long currentEmpId, ApiResponseListener<List<EmployeeHistory>, String> listener) {
        Call<List<EmployeeHistory>> historyCall = historyApiInterface.getEmployeeHistory(empId, currentEmpId);

        historyCall.enqueue(new Callback<List<EmployeeHistory>>() {
            @Override
            public void onResponse(@NonNull Call<List<EmployeeHistory>> call, @NonNull Response<List<EmployeeHistory>> response) {
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
            public void onFailure(@NonNull Call<List<EmployeeHistory>> call, @NonNull Throwable t) {
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

    public void punchAttendance(Long empId, String reading, String encodedImage, ApiResponseListener<Attendance, String> listener) {

        Log.i("CRM-LOG-1", reading + " " + empId + " " + encodedImage);
        Call<Attendance> call = attendanceApiInterface.punchAttendance(empId, reading, encodedImage);

        processAttendanceCall(call, listener);
    }

    public void getAttendanceById(Long atnId, ApiResponseListener<Attendance, String> listener) {
        Call<Attendance> call = attendanceApiInterface.getAttendanceById(atnId);

        processAttendanceCall(call, listener);
    }

    private void processAttendanceCall(Call<Attendance> call, ApiResponseListener<Attendance, String> listener) {

        try {

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
        } catch (Exception e) {
            listener.onFailure("Something went wrong");
        }
    }


    public School getNewSchool() {
        return newSchool;
    }

    public void resetNewSchool() {
        newSchool = new School();
    }

    public void updateEmployeeBatteryStatus(Long currentEmployeeId, String status) {
        Call<Object> call = employeeApiInterface.updateEmployeeBatteryStatus(currentEmployeeId, status);
        call.enqueue(new Callback<Object>() {
            @Override
            public void onResponse(@NonNull Call<Object> call, @NonNull Response<Object> response) {
                //Ignored
            }

            @Override
            public void onFailure(@NonNull Call<Object> call, @NonNull Throwable t) {
                //Ignored
            }
        });
    }

    public void getAllPendingPaymentRequests(ApiResponseListener<List<PaymentRequest>, String> listener) {
        Call<List<PaymentRequest>> call = paymentApiInterface.getAllPendingPaymentRequests();

        processRequestsListCall(call, listener);
    }

    public void getPendingRequestsToEmployee(Long empId, ApiResponseListener<List<PaymentRequest>, String> listener) {
        Call<List<PaymentRequest>> call = paymentApiInterface.getPendingRequestsToEmployee(empId);

        processRequestsListCall(call, listener);
    }

    public void processRequestsListCall(Call<List<PaymentRequest>> call, ApiResponseListener<List<PaymentRequest>, String> listener) {
        call.enqueue(new Callback<List<PaymentRequest>>() {
            @Override
            public void onResponse(@NonNull Call<List<PaymentRequest>> call, @NonNull Response<List<PaymentRequest>> response) {
                if (response.isSuccessful()) {
                    listener.onSuccess(response.body());
                } else {
                    listener.onFailure(response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<PaymentRequest>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server");
            }
        });
    }

    public void updatePaymentRequest(PaymentRequest payment, ApiResponseListener<PaymentRequest, String> listener) {
        Call<PaymentRequest> call = paymentApiInterface.updatePaymentRequest(payment.getId(), payment);

        processPaymentCall(call, listener);
    }
}
