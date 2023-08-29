package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Lr;
import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class EmployeeHomeFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeHomeBinding binding;
    static String selectedDate = "";
    static String currentDate = "";
    private Employee employee;
    int leaveStatus;

    public EmployeeHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_home, container, false);
        binding.setMessage(new Message());
        employee = viewModel.getCurrentEmployee();
        Paper.init(getContext());
        binding.getMessage().setReceiverId(employee.getId());
        binding.employeeName.setText(employee.getName());

        viewModel.getCurrentEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);

        binding.employeeHomeCardAddAttendance.setOnClickListener(this::navigateAddAttendance);
        binding.employeeHomeCardAddLeave.setOnClickListener(this::navigateToAddLeave);
        binding.employeeHomeCardAddSchool.setOnClickListener(this::navigateToAddSchool);
        binding.employeeHomeCardAddShop.setOnClickListener(this::navigateToAddShop);
        binding.employeeHomeCardAddOrder.setOnClickListener(this::navigateToAddOrder);
        binding.employeeHomeCardPaymentRequest.setOnClickListener(this::navigateToPaymentRequest);
        binding.employeeHomeCardAddEod.setOnClickListener(this::navigateToAddEod);
        binding.employeeHomeCardMyMap.setOnClickListener(this::navigateToMap);
        binding.paymentReceiveTextview.setOnClickListener(this::navigateToPaymentReceive);
        binding.bookOrderListTextview.setOnClickListener(this::navigateToBookOrderList);
        binding.absentOrLeaveTextview.setOnClickListener(this::navigateToAbsentOrLeave);
        binding.meetingTaskTextview.setOnClickListener(this::navigateToMeetingOrTask);
        binding.schoolListTextview.setOnClickListener(this::navigateToSchoolList);
        binding.myAccount.setOnClickListener(this::navigateToDailyReports);
        binding.selectDateLinear.setOnClickListener(this::onSelectDate);
        binding.todayDataTextview.setOnClickListener(this::onClickTodayData);
        binding.districtMap.setOnClickListener(this::onClickDistrictMap);
        binding.tehsilList.setOnClickListener(this::onClickTehsil);
        // binding.employeeHomeCardDailyReport.setOnClickListener(this::navigateToDailyReports);

        String formattedDateTime = "";

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        Paper.init(getContext());
        Paper.book().write("currentDate", currentDate);
        Paper.book().write("selectedDate", currentDate);


        if (selectedDate.isEmpty()) {
            binding.dateTextId.setText(currentDate);
            Paper.book().write("selectedDate", currentDate);
        } else {
            binding.dateTextId.setText(selectedDate);
            Paper.book().write("selectedDate", selectedDate);
        }

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(true);
        binding.employeeHomeRecycler.setLayoutManager(lm);

//        viewModel.getCurrentEmployeeHistory().observe(getViewLifecycleOwner(), this::setEmployeeHistory);

        binding.employeeHomeViewFab.setOnClickListener(view -> {
            if (binding.employeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.employeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.employeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

        getAllHistory();
        startLocationService();
        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        binding.employeeHomeSendMessageFab.setOnClickListener(this::sendMessage);

        return binding.getRoot();
    }

    private void onClickTodayData(View view) {
        selectedDate = "";
        getAllHistory();
    }

    private void onSelectDate(View view) {
        getDate(view, true);
    }

    private void getDate(View view, boolean b) {
        if (b) {
            final Calendar c = Calendar.getInstance();
            int y = c.get(Calendar.YEAR);
            int m = c.get(Calendar.MONTH);
            int d = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    Date date = new Date(year, month, day);
                    Format format = new SimpleDateFormat("20yy-MM-dd");
                    binding.dateTextId.setText(format.format(date));
                    selectedDate = format.format(date);
                    Paper.init(getContext());
                    Paper.book().write("selectedDate", selectedDate);

                }
            }, y, m, d);
            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                datePickerDialog.getDatePicker().setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        Date date = new Date(i, i1, i2);
                        Format format = new SimpleDateFormat("20yy-MM-dd");
                        binding.dateTextId.setText(format.format(date));
                        selectedDate = format.format(date);

                        Paper.book().write("selectedDate", selectedDate);
                        getAllHistory();
                    }
                });
            }
            datePickerDialog.show();
        }
    }


    private void getAllHistory() {
        if (selectedDate.isEmpty()) {
            Toast.makeText(getContext(), currentDate + " History", Toast.LENGTH_SHORT).show();
            viewModel.getCurrentEmployeeHistory(currentDate).observe(getViewLifecycleOwner(), this::setEmployeeHistory);
            binding.dateTextId.setText(currentDate);
        } else {
            Toast.makeText(getContext(), selectedDate + " History", Toast.LENGTH_SHORT).show();
            viewModel.getCurrentEmployeeHistory(selectedDate).observe(getViewLifecycleOwner(), this::setEmployeeHistory);
            binding.dateTextId.setText(selectedDate);
        }

    }

    private void setAttendance(Attendance attendance) {

        String punchStatus = "0";
        if (attendance == null) {
            punchStatus = "0";
            Log.i("getEmpId", "0");
            Log.i("getPunchStatus", "0");
        } else {
            punchStatus = String.valueOf(attendance.getPunchStatus());
            Log.i("getEmpId", String.valueOf(attendance.getEmpId()));
            Log.i("getPunchStatus", String.valueOf(attendance.getPunchStatus()));
        }

        DateFormat dateFormat = new SimpleDateFormat("HH");
        Date date = new Date();
        int time = Integer.parseInt(dateFormat.format(date));
        Log.i("time", String.valueOf(time));


        if (punchStatus.equals("0")) {
//            showDialog();
            getLeaveStatus();
        } else {

        }
//        else if (punchStatus.equals("1") && time >= 19) { //for attendance mandatory after 7 pm
//            showDialog();
//        }


    }


    private void showDialog() {
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View view = factory.inflate(R.layout.attendance_alert_dialog_layout, null);
        AlertDialog attendaceDialog = new AlertDialog.Builder(getContext()).create();
        attendaceDialog.setView(view);
        attendaceDialog.setCancelable(false);
        attendaceDialog.show();
//        Button buttonCancel = view.findViewById(R.id.button2);
        Button leaveButton = view.findViewById(R.id.leave_button);
        Button buttonPunch = view.findViewById(R.id.button1);
        buttonPunch.setOnClickListener(view12 -> {
            attendaceDialog.dismiss();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddAttendanceFragment);
        });

        if (leaveStatus==0) {
            leaveButton.setVisibility(View.VISIBLE);// if leave not applied
        }
        else {
            leaveButton.setVisibility(View.GONE);// if leave applied
        }
        leaveButton.setOnClickListener(view1 -> {
            attendaceDialog.dismiss();
            Navigation.findNavController(binding.getRoot()).navigate(R.id.employeeAddLeaveFragment);
        });
//        buttonCancel.setOnClickListener(view1 -> attendaceDialog.dismiss());
    }

    private void sendMessage(View view) {
        if (!binding.getMessage().getMessage().trim().isEmpty()) {
            binding.getMessage().setReceiverId(viewModel.getCurrentEmployee().getId());
            viewModel.sendMessage(binding.getMessage()).observe(getViewLifecycleOwner(), message -> {
                if (message != null) {
                    getAllHistory();
                }
            });
            binding.setMessage(new Message());

        }
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void setEmployeeHistory(List<EmployeeHistory> historyList) {

        if (historyList == null || historyList.isEmpty()) {
            binding.employeeHomeEmptyRecycler.setVisibility(View.VISIBLE);
            binding.employeeHomeRecycler.setVisibility(View.GONE);
        } else {
            binding.employeeHomeEmptyRecycler.setVisibility(View.GONE);
            binding.employeeHomeRecycler.setVisibility(View.VISIBLE);
        }
        EmployeeHomeRecyclerAdapter adapter = new EmployeeHomeRecyclerAdapter(historyList, getActivity().getApplication(), new EmployeeHomeRecyclerAdapter.EmployeeHomeClickListener() {
            @Override
            public void onClickCard(Attendance attendance) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ATN_ID, attendance.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_attendanceProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Leave leave) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.LEAVE_ID, leave.getId());
//                Navigation.findNavController(binding.getRoot()).navigate(  ,bundle);
            }

            @Override
            public void onClickCard(School school) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.SCHOOL_ID, school.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_schoolProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Dealer dealer) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.DEALER_ID, dealer.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_dealerProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Order order) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ORDER_ID, order.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_orderProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Eod eod) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EOD_ID, eod.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_eodProfileFragment, bundle);
            }

            @Override
            public void onCLickCard(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.employeeProfileFragment, bundle);
            }

            @Override
            public void onClickCard(PaymentRequest paymentRequest) {

            }

            @Override
            public void onClickCard(Message message) {

            }

            @Override
            public void onClickCard(Lr lr) {
                String inImage = ApiEndpoints.BASE_URL + lr.getImage();
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", inImage);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            }
        });
        binding.employeeHomeRecycler.setAdapter(adapter);

        if (historyList != null && !historyList.isEmpty()) {
            binding.employeeHomeRecycler.scrollToPosition(historyList.size() - 1);
        }

    }

    private void navigateToPaymentRequest(View view) {

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeePaymentRequestFragment);
    }

    private void navigateToAddEod(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddEODFragment);
    }

    private void navigateToAddShop(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddDealerFragment, bundle);
    }

    private void navigateToAddSchool(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddSchoolFragment, bundle);
    }

    private void navigateAddAttendance(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddAttendanceFragment);
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToMap(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeMapFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddLeaveFragment);
    }

    private void navigateToDailyReports(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeDailyReportsFragment);
    }

    private void navigateToPaymentReceive(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_paymentReceiveFragment, bundle);
    }

    private void navigateToBookOrderList(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_bookOrderListrFragment, bundle);
    }

    private void navigateToAbsentOrLeave(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_absentOrLeaveFragment, bundle);
    }

    private void navigateToMeetingOrTask(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_meetingOrTaskFragment, bundle);
    }

    private void navigateToSchoolList(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_schoolListFragment, bundle);
    }

    private void navigateToMyAccount(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_myAccountFragment);
    }

    private void onClickDistrictMap(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_districtMapFragment);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_districtListFragment2, bundle);
    }

    private void onClickTehsil(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(view).navigate(R.id.action_employeeHomeFragment_to_districtListForTehsilFragment,bundle);
    }


    private void getLeaveStatus() {
        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url = ApiEndpoints.BASE_URL + ApiEndpoints.LEAVE_STATUS;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", employee.getId());


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());


            if (response != null) {
                try {
                    Log.i("leave_status",response.toString());
                    leaveStatus = Integer.parseInt(response.getString("leave_status"));

                    showDialog();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                Toast.makeText(getContext(), "Something Went Wrong.", Toast.LENGTH_SHORT).show();
                showDialog();
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    int check = 0;
    private void startLocationService() {

        if (isLocationPermissionGranted()) {
            Toast.makeText(getActivity(), "Permission Granted.", Toast.LENGTH_SHORT).show();
        }
        else {

            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(getActivity());
            builder.setTitle("Permission Denied")
                    .setCancelable(false)
                    .setMessage("आपने लोकेशन की परमिशन नही दी है कृप्या सेटिंग में जाकर परमिशन दे")
                    .setNegativeButton(null, null)
                    .setPositiveButton("Ok", (dialog, which) -> {

                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        intent.setData(Uri.fromParts("package", getActivity().getPackageName(), null));
                        startActivity(intent);

                    });


            androidx.appcompat.app.AlertDialog alertDialog = builder.create();
            alertDialog.show();
            Toast.makeText(getActivity(), "Permission Denied.", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean isLocationPermissionGranted() {

        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
        ) {

            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        check++;
        Log.i("TAG","Resume Called...");
        if (check>2) {
        try {
            startLocationService();
        }
        catch (Exception e){
            Log.i("Problem",e.getMessage());
        }

        }
    }
}