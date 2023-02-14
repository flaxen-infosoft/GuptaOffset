package com.flaxeninfosoft.guptaoffset.viewModels;

import android.app.Application;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.flaxeninfosoft.guptaoffset.listeners.ApiResponseListener;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.repositories.MainRepository;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.utils.SharedPrefs;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class EmployeeViewModel extends AndroidViewModel {
    private final MainRepository repo;
    private final SharedPrefs sharedPrefs;
    private final MutableLiveData<List<Eod>> currentEmployeeAllEods;
    private final MutableLiveData<List<Order>> currentEmployeeOrders;
    private final MutableLiveData<List<Dealer>> currentEmployeeDealers;
    private final MutableLiveData<Eod> currentEmployeeTodaysEod;
    private final MutableLiveData<List<Leave>> currentEmployeeLeaves;
    private final MutableLiveData<String> toastMessage;
    private final MutableLiveData<Location> currentLocation;

    private final MutableLiveData<Uri> addSchoolHoadingUri;
    private final MutableLiveData<Uri> addSchoolSpecimenUri;

    public EmployeeViewModel(@NonNull Application application) {
        super(application);
        repo = MainRepository.getInstance(application.getApplicationContext());
        sharedPrefs = SharedPrefs.getInstance(application.getApplicationContext());
        toastMessage = new MutableLiveData<>();

        currentEmployeeAllEods = new MutableLiveData<>();
        currentEmployeeOrders = new MutableLiveData<>();
        currentEmployeeDealers = new MutableLiveData<>();
        currentEmployeeTodaysEod = new MutableLiveData<>();
        currentEmployeeLeaves = new MutableLiveData<>();
        currentLocation = new MutableLiveData<>(new Location());

        addSchoolHoadingUri = new MutableLiveData<>();
        addSchoolSpecimenUri = new MutableLiveData<>();
    }

//    ----------------------------------------------------------------------------------------------

    public Employee getCurrentEmployee() {
        return sharedPrefs.getCurrentEmployee();
    }

    Long getCurrentEmployeeId() {
        return sharedPrefs.getCurrentEmployee().getId();
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
        leave.setEmpId(getCurrentEmployeeId());

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

    public LiveData<Boolean> addOrder(Order order, Uri uri) throws IOException {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();
        order.setEmpId(getCurrentEmployeeId());
        order.setSnap(FileEncoder.encodeImage(getApplication().getContentResolver(), uri));
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

    public LiveData<Attendance> getCurrentEmployeeTodaysAttendance() {
        MutableLiveData<Attendance> flag = new MutableLiveData<>();

        repo.getEmployeeTodaysAttendance(getCurrentEmployeeId(), new ApiResponseListener<Attendance, String>() {
            @Override
            public void onSuccess(Attendance response) {
                Log.i("CRM-LOG", response.toString());
                if (response == null) {
                    Attendance attendance = new Attendance();
                    attendance.setEmpId(getCurrentEmployeeId());
//                    attendance.setTimeIn()(new Date(System.currentTimeMillis()));
                    response = attendance;
                }

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

    public LiveData<Attendance> punchAttendance(String reading, Uri imageUri) throws IOException {
        MutableLiveData<Attendance> flag = new MutableLiveData<>();

        String encodedImage = FileEncoder.encodeImage(getApplication().getContentResolver(), imageUri);

        repo.punchAttendance(getCurrentEmployeeId(), reading, encodedImage, new ApiResponseListener<Attendance, String>() {
            @Override
            public void onSuccess(Attendance response) {
                flag.postValue(new Attendance());
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
                flag.postValue(null);
            }
        });

        return flag;
    }

    public LiveData<Attendance> getAttendanceById(Long atnId){
        MutableLiveData<Attendance> flag = new MutableLiveData<>();

        repo.getAttendanceById(atnId, new ApiResponseListener<Attendance, String>() {
            @Override
            public void onSuccess(Attendance response) {
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

    public LiveData<Boolean> addDealer(Dealer dealer, Uri uri) throws IOException {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        Location location = currentLocation.getValue();
        dealer.setEmpId(getCurrentEmployeeId());
        dealer.setImage(FileEncoder.encodeImage(getApplication().getContentResolver(), uri));
        repo.addDealer(getCurrentEmployeeId(), dealer, new ApiResponseListener<Dealer, String>() {
            @Override
            public void onSuccess(Dealer response) {
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

    public LiveData<Dealer> getDealerById(Long dealerId) {
        MutableLiveData<Dealer> flag = new MutableLiveData<>();

        repo.getDealerById(dealerId, new ApiResponseListener<Dealer, String>() {
            @Override
            public void onSuccess(Dealer response) {
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
    
    public LiveData<List<Dealer>> getCurrentEmployeeDealers(){
        Long empId = getCurrentEmployeeId();
        repo.getDealerByEmpId(empId, new ApiResponseListener<List<Dealer>, String>() {
            @Override
            public void onSuccess(List<Dealer> response) {
                currentEmployeeDealers.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                currentEmployeeDealers.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return currentEmployeeDealers;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Boolean> addPayment(PaymentRequest paymentRequest) {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();
        paymentRequest.setEmpId(getCurrentEmployeeId());
        repo.addPayment(getCurrentEmployeeId(), paymentRequest, new ApiResponseListener<PaymentRequest, String>() {
            @Override
            public void onSuccess(PaymentRequest response) {
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

    public LiveData<Boolean> addSchool(School school) throws IOException {

        MutableLiveData<Boolean> flag = new MutableLiveData<>();


        String encodedImage = FileEncoder.encodeImage(getApplication().getContentResolver(), school.getHoadingImageUri());
        school.setSnap(encodedImage);

        String encodedSpecimen = FileEncoder.encodeImage(getApplication().getContentResolver(), school.getSpecimenImageUri());
        school.setSpecimen(encodedSpecimen);

        school.setEmpId(getCurrentEmployeeId());
        repo.addSchool(getCurrentEmployeeId(), school, new ApiResponseListener<School, String>() {
            @Override
            public void onSuccess(School response) {
                flag.postValue(true);
                repo.resetNewSchool();
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(false);
                toastMessage.postValue(error);
            }
        });

        return flag;
    }

    public void setAddSchoolHoadingUri(Uri uri){
        try {
            addSchoolHoadingUri.setValue(uri);
        }catch (Exception e){
            addSchoolHoadingUri.postValue(uri);
        }
    }

    public void setAddSchoolSpecimenUri(Uri uri){
        try{
            addSchoolSpecimenUri.setValue(uri);
        }catch (Exception e){
            addSchoolSpecimenUri.postValue(uri);
        }
    }

    public School getNewSchool(){
        return repo.getNewSchool();
    }

    public LiveData<School> getSchoolById(Long schoolId) {
        MutableLiveData<School> flag = new MutableLiveData<>();

        repo.getSchoolById(schoolId, new ApiResponseListener<School, String>() {
            @Override
            public void onSuccess(School response) {
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

        Long empId = getCurrentEmployeeId();

        repo.getEmployeeAllEodsByEmpId(empId, new ApiResponseListener<List<Eod>, String>() {
            @Override
            public void onSuccess(List<Eod> response) {
                currentEmployeeAllEods.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                currentEmployeeAllEods.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return currentEmployeeAllEods;
    }

    public LiveData<List<Eod>> getEmployeeAllEods(Long empId) {
        MutableLiveData<List<Eod>> flag = new MutableLiveData<>();

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

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeTodayEod(empId, new ApiResponseListener<Eod, String>() {
            @Override
            public void onSuccess(Eod response) {
                currentEmployeeTodaysEod.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                currentEmployeeTodaysEod.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return currentEmployeeTodaysEod;
    }

    public LiveData<Boolean> addEod(Eod eod, Uri expenseImage, Uri petrolImage) throws IOException {
        MutableLiveData<Boolean> flag = new MutableLiveData<>();

        eod.setEmpId(getCurrentEmployeeId());
        eod.setExpenseImage(FileEncoder.encodeImage(getApplication().getContentResolver(), expenseImage));
        eod.setPetrolExpenseImage(FileEncoder.encodeImage(getApplication().getContentResolver(), petrolImage));
         repo.addEod(getCurrentEmployeeId(), eod, new ApiResponseListener<Eod, String>() {
            @Override
            public void onSuccess(Eod response) {
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

    public LiveData<List<Order>> getCurrentEmployeeOrders() {

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeOrders(empId, new ApiResponseListener<List<Order>, String>() {
            @Override
            public void onSuccess(List<Order> response) {
                currentEmployeeOrders.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                currentEmployeeOrders.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return currentEmployeeOrders;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<List<Leave>> getCurrentEmployeeLeaves() {

        Long empId = getCurrentEmployeeId();
        repo.getEmployeeAllLeaves(empId, new ApiResponseListener<List<Leave>, String>() {
            @Override
            public void onSuccess(List<Leave> response) {
                currentEmployeeLeaves.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                currentEmployeeLeaves.postValue(null);
                toastMessage.postValue(error);
            }
        });

        return currentEmployeeLeaves;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Location> addCurrentEmployeeLocation(Location location) {

        Long empId = getCurrentEmployeeId();
        location.setEmpId(empId);
        repo.addEmployeeLocation(location, new ApiResponseListener<Location, String>() {
            @Override
            public void onSuccess(Location response) {
                currentLocation.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
            }
        });

        return currentLocation;
    }

    public LiveData<Location> getCurrentEmployeeLocation(){

        return currentLocation;
    }

//    ----------------------------------------------------------------------------------------------

    public MutableLiveData<String> getToastMessageLiveData() {
        return toastMessage;
    }

//    ----------------------------------------------------------------------------------------------

    private final MutableLiveData<List<EmployeeHistory>> history = new MutableLiveData<>();
    public LiveData<List<EmployeeHistory>> getCurrentEmployeeHistory() {

        repo.getEmployeeHomeHistory(getCurrentEmployeeId(), new ApiResponseListener<List<EmployeeHistory>, String>() {
            @Override
            public void onSuccess(List<EmployeeHistory> response) {
                history.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                toastMessage.postValue(error);
                history.postValue(null);
            }
        });

        return history;
    }

//    ----------------------------------------------------------------------------------------------

    public LiveData<Message> sendMessage(Message message){
        MutableLiveData<Message> flag = new MutableLiveData<>();
        message.setSenderId(getCurrentEmployeeId());
        repo.sendMessage(getCurrentEmployeeId(), message, new ApiResponseListener<Message, String>() {
            @Override
            public void onSuccess(Message response) {
                flag.postValue(response);
            }

            @Override
            public void onFailure(String error) {
                flag.postValue(null);
                toastMessage.postValue(error);
            }
        });
        getCurrentEmployeeHistory();
        return flag;
    }

//    ----------------------------------------------------------------------------------------------

    public void logout() {
        SharedPrefs.getInstance(getApplication().getApplicationContext()).clearCredentials();
    }

    public void setNewSchoolSpecimenImageUri(Uri specimenImageUri) {
        repo.getNewSchool().setSpecimenImageUri(specimenImageUri);
    }

    public void setNewSchoolHoadingImageUri(Uri uri){
        repo.getNewSchool().setHoadingImageUri(uri);
    }
}
