package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

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
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class EmployeeHomeFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeHomeBinding binding;
    static String selectedDate = "";
    static String currentDate = "";
    private Employee employee;

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
        binding.getMessage().setReceiverId(viewModel.getCurrentEmployee().getId());

         employee = viewModel.getCurrentEmployee();
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
        binding.myAccount.setOnClickListener(this::navigateToMyAccount);
        binding.dateTextId.setOnClickListener(this::onSelectDate);

        String formattedDateTime = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            currentDate = now.format(formatter);
            Paper.init(getContext());
            Paper.book().write("currentDate", formattedDateTime);
        }

        if (selectedDate.isEmpty()) {
            binding.dateTextId.setText(currentDate);
        } else {
            binding.dateTextId.setText(selectedDate);
        }

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(true);
        binding.employeeHomeRecycler.setLayoutManager(lm);

        viewModel.getCurrentEmployeeHistory().observe(getViewLifecycleOwner(), this::setEmployeeHistory);

        binding.employeeHomeViewFab.setOnClickListener(view -> {
            if (binding.employeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.employeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.employeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        binding.employeeHomeSendMessageFab.setOnClickListener(this::sendMessage);

        return binding.getRoot();
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

                    }
                });
            }
            datePickerDialog.show();
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


//        if (punchStatus.equals("0")) {
//            showDialog();
//        } else if (punchStatus.equals("1") && time >= 19) {
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
        Button buttonPunch = view.findViewById(R.id.button1);
        buttonPunch.setOnClickListener(view12 -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddAttendanceFragment);
            attendaceDialog.dismiss();
        });
//        buttonCancel.setOnClickListener(view1 -> attendaceDialog.dismiss());
    }

    private void sendMessage(View view) {
        if (!binding.getMessage().getMessage().trim().isEmpty()) {
            binding.getMessage().setReceiverId(viewModel.getCurrentEmployee().getId());
            viewModel.sendMessage(binding.getMessage()).observe(getViewLifecycleOwner(), message -> {
                if (message != null) {
                    viewModel.getCurrentEmployeeHistory();
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
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddDealerFragment);
    }

    private void navigateToAddSchool(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddSchoolFragment);
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
        bundle.putLong(Constants.EMPLOYEE_ID,employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_paymentReceiveFragment,bundle);
    }

    private void navigateToBookOrderList(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID,employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_bookOrderListrFragment,bundle);
    }

    private void navigateToAbsentOrLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_absentOrLeaveFragment);
    }

    private void navigateToMeetingOrTask(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_meetingOrTaskFragment);
    }

    private void navigateToSchoolList(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_schoolListFragment);
    }

    private void navigateToMyAccount(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_myAccountFragment);
    }
}