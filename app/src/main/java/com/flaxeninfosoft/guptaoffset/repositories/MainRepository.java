package com.flaxeninfosoft.guptaoffset.repositories;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.flaxeninfosoft.guptaoffset.api.AttendanceApiInterface;
import com.flaxeninfosoft.guptaoffset.api.ClientApiInterface;
import com.flaxeninfosoft.guptaoffset.api.EmployeeApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LeaveApiInterface;
import com.flaxeninfosoft.guptaoffset.api.LocationApiInterface;
import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainRepository {

    private static MainRepository instance;

    private final EmployeeApiInterface employeeApiInterface;
    private final ClientApiInterface clientApiInterface;
    private final LocationApiInterface locationApiInterface;
    private final LeaveApiInterface leaveApiInterface;
    private final AttendanceApiInterface attendanceApiInterface;

    private final int STATUS_NOT_FOUND = 404;

    private MainRepository(Context context) {
        Retrofit apiClient = RetrofitClient.getClient();

        employeeApiInterface = apiClient.create(EmployeeApiInterface.class);
        clientApiInterface = apiClient.create(ClientApiInterface.class);
        locationApiInterface = apiClient.create(LocationApiInterface.class);
        leaveApiInterface = apiClient.create(LeaveApiInterface.class);
        attendanceApiInterface = apiClient.create(AttendanceApiInterface.class);
    }

    public static MainRepository getInstance(Context context) {
        if (instance == null) {
            instance = new MainRepository(context);
        }

        return instance;
    }

    public void login(LoginModel credentials, ApiResponseListener<Employee, String> listener) {

        Call<Employee> loginCall = employeeApiInterface.loginByEmailAndPassword(credentials.getEmail(), credentials.getPassword());

        processEmployeeCall(loginCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getAllEmployees(ApiResponseListener<List<Employee>, String> listener) {
        Call<List<Employee>> getAllEmployeesCall = employeeApiInterface.getAllEmployees();

        processEmployeeListCall(getAllEmployeesCall, listener);
    }

    public void addEmployee(Employee employee, ApiResponseListener<Employee, String> listener) {
        Call<Employee> addEmployeeCall = employeeApiInterface.addEmployee(employee);

        processEmployeeCall(addEmployeeCall, listener);
    }

    public void getEmployeeById(Long empId, ApiResponseListener<Employee, String> listener) {

        Call<Employee> getEmployeeByIdCall = employeeApiInterface.getEmployeeById(empId);

        processEmployeeCall(getEmployeeByIdCall, listener);
    }

    public void updateEmployeeById(Long empId, Employee employee, ApiResponseListener<Employee, String> listener) {
        Call<Employee> updateEmployeeByIdCall = employeeApiInterface.updateEmployeeById(empId, employee);

        processEmployeeCall(updateEmployeeByIdCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getAllEmployeesLocation(ApiResponseListener<List<Location>, String> listener) {
        Call<List<Location>> getAllEmployeesLocationCall = locationApiInterface.getAllEmployeesLocation();

        processLocationListCall(getAllEmployeesLocationCall, listener);
    }

    public void getEmployeeLocationById(Long empId, ApiResponseListener<Location, String> listener) {
        Call<Location> getEmployeeLocationByIdCall = locationApiInterface.getEmployeeLocationById(empId);

        processLocationCall(getEmployeeLocationByIdCall, listener);
    }

    public void updateEmployeeLocationById(Long empId, Location location, ApiResponseListener<Location, String> listener) {
        Call<Location> updateEmployeeLocationByIdCall = locationApiInterface.updateEmployeeLocation(empId, location);

        processLocationCall(updateEmployeeLocationByIdCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getAllClients(ApiResponseListener<List<Client>, String> listener) {
        Call<List<Client>> getAllClientsCall = clientApiInterface.getAllClients();

        processClientListCall(getAllClientsCall, listener);
    }

    public void getEmployeeClients(Long empId, ApiResponseListener<List<Client>, String> listener) {
        Call<List<Client>> getEmployeeClientsCall = clientApiInterface.getEmployeeClientsById(empId);

        processClientListCall(getEmployeeClientsCall, listener);
    }

    public void getClientById(Long clientId, ApiResponseListener<Client, String> listener) {
        Call<Client> getClientByIdCall = clientApiInterface.getClientById(clientId);

        processClientCall(getClientByIdCall, listener);
    }

    public void updateClientById(Long clientId, Client client, ApiResponseListener<Client, String> listener) {
        Call<Client> updateClientByIdCall = clientApiInterface.updateClientById(clientId, client);

        processClientCall(updateClientByIdCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getAllLeaveRequests(ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> getAllLeaveRequestsCall = leaveApiInterface.getAllLeaveRequests();

        processLeaveListCall(getAllLeaveRequestsCall, listener);
    }

    public void getEmployeeLeaveRequests(Long empId, ApiResponseListener<List<Leave>, String> listener) {
        Call<List<Leave>> getEmployeeLeaveRequestsCall = leaveApiInterface.getEmployeeLeaveRequests(empId);

        processLeaveListCall(getEmployeeLeaveRequestsCall, listener);
    }

    public void getLeaveRequestById(Long leaveId, ApiResponseListener<Leave, String> listener) {
        Call<Leave> getLeaveRequestByIdCall = leaveApiInterface.getLeaveRequestById(leaveId);

        processLeaveCall(getLeaveRequestByIdCall, listener);
    }

    public void updateLeaveRequestById(Long leaveId, Leave leave, ApiResponseListener<Leave, String> listener) {
        Call<Leave> updateLeaveRequestByIdCall = leaveApiInterface.updateLeaveRequestById(leaveId, leave);

        processLeaveCall(updateLeaveRequestByIdCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    public void getEmployeeAttendance(Long empId, ApiResponseListener<List<Attendance>, String> listener) {
        Call<List<Attendance>> getEmployeeAttendanceCall = attendanceApiInterface.getEmployeeAttendance(empId);

        processAttendanceListCall(getEmployeeAttendanceCall, listener);
    }

    public void getAttendanceById(Long attendanceId, ApiResponseListener<Attendance, String> listener) {
        Call<Attendance> getAttendanceByIdCall = attendanceApiInterface.getAttendanceById(attendanceId);

        processAttendanceCall(getAttendanceByIdCall, listener);
    }

    public void addEmployeeAttendance(Long empId, Attendance attendance, ApiResponseListener<Attendance, String> listener) {
        Call<Attendance> addEmployeeAttendanceCall = attendanceApiInterface.addEmployeeAttendance(empId, attendance);

        processAttendanceCall(addEmployeeAttendanceCall, listener);
    }

//    ----------------------------------------------------------------------------------------------

    private void processAttendanceCall(Call<Attendance> call, ApiResponseListener<Attendance, String> listener) {
        call.enqueue(new Callback<Attendance>() {
            @Override
            public void onResponse(@NonNull Call<Attendance> call, @NonNull Response<Attendance> response) {
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
            public void onFailure(@NonNull Call<Attendance> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

    private void processAttendanceListCall(Call<List<Attendance>> call, ApiResponseListener<List<Attendance>, String> listener) {
        call.enqueue(new Callback<List<Attendance>>() {
            @Override
            public void onResponse(@NonNull Call<List<Attendance>> call, @NonNull Response<List<Attendance>> response) {
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
            public void onFailure(@NonNull Call<List<Attendance>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
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

    private void processClientCall(Call<Client> call, ApiResponseListener<Client, String> listener) {
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(@NonNull Call<Client> call, @NonNull Response<Client> response) {
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
            public void onFailure(@NonNull Call<Client> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
    }

    private void processClientListCall(Call<List<Client>> call, ApiResponseListener<List<Client>, String> listener) {
        call.enqueue(new Callback<List<Client>>() {
            @Override
            public void onResponse(@NonNull Call<List<Client>> call, @NonNull Response<List<Client>> response) {
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
            public void onFailure(@NonNull Call<List<Client>> call, @NonNull Throwable t) {
                t.printStackTrace();
                listener.onFailure("Unable to connect to server.");
            }
        });
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
}
