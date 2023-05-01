package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;
    private AdminViewModel viewModel;
    private List<Employee> employeeList;
    String selectedDate = "";

    String currentDate = "";

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false);

        binding.adminHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ((AdminMainActivity) requireActivity()).setupActionBar(binding.adminHomeToolbar, "Admin");

        viewModel.getAllEmployees().observe(getViewLifecycleOwner(), this::onChange);
        binding.adminHomeAddSuperEmployee.setOnClickListener(this::navigateToAddSuperEmployee);
        binding.adminHomeAddEmployee.setOnClickListener(this::navigateToAddEmployee);
        binding.adminHomePaymentRequests.setOnClickListener(this::navigateToPaymentRequests);
        binding.adminHomeAllOrders.setOnClickListener(this::onClickAllOrders);
        binding.paymentReqText.setOnClickListener(this::navigateToPaymentRequests);
        binding.bookOrderText.setOnClickListener(this::onClickAllOrders);
        binding.calenderTextId.setOnClickListener(this::onClickCalender);
        binding.todayNotWorkingEmployeeText.setOnClickListener(this::onClickTodayNotWorking);
        binding.flagEmployeeText.setOnClickListener(this::onClickFlagEmployee);

        binding.adminAddIcon.setOnClickListener(view -> {
            if (binding.adminHomeCard.getVisibility() == View.VISIBLE) {
                binding.adminHomeCard.setVisibility(View.GONE);
            } else {
                binding.adminHomeCard.setVisibility(View.VISIBLE);
            }
        });

        binding.adminHomeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (employeeList == null || employeeList.isEmpty()) {
                    binding.adminHomeEmptyRecycler.setVisibility(View.VISIBLE);
                    binding.adminHomeRecycler.setVisibility(View.GONE);
                } else {
                    binding.adminHomeEmptyRecycler.setVisibility(View.GONE);
                    binding.adminHomeRecycler.setVisibility(View.VISIBLE);
                }
                EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employeeList, getContext(), new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
                    @Override
                    public void onClickCard(Employee employee) {
                        onCLickEmployeeCard(employee);
                    }

                    @Override
                    public boolean onLongClickCard(Employee employee) {
                        onLongClickEmployeeCard(employee);
                        return false;
                    }
                });
                adapter.getFilter().filter(charSequence.toString());
                binding.adminHomeRecycler.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.adminHomeSwipeRefresh.setOnRefreshListener(this::getAllEmployees);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        String formattedDateTime = "";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDateTime = now.format(formatter);
            Paper.init(getContext());
            Paper.book().write("currentDate", formattedDateTime);
        }


        if (selectedDate.isEmpty()) {
            binding.dateTextId.setText(formattedDateTime);
        } else {
            binding.dateTextId.setText(selectedDate);
        }


        return binding.getRoot();
    }

    private void onClickFlagEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_flagEmployeeFragment);
    }

    private void onClickTodayNotWorking(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_todayNotWokingEmployeeFragment);
    }

    private void onClickCalender(View view) {

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
            datePickerDialog.show();
        }
    }

    private void onClickAllOrders(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAllOrderFragment);
    }

    private void getAllEmployees() {
        viewModel.fetchAllEmployees();
        if (binding.adminHomeSwipeRefresh.isRefreshing()) {
            binding.adminHomeSwipeRefresh.setRefreshing(false);
        }
    }

    private void navigateToPaymentRequests(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.pendingPaymentRequestsFragment);
    }

    private void navigateToAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_superEmployeeAddEmployeeFragment);
    }

    private void showToast(String s) {
        if (!s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAddSuperEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAddSuperEmployeeFragment);
    }

    private void onChange(List<Employee> employees) {
        employeeList = employees;
        if (employees == null || employees.isEmpty()) {
            binding.adminHomeEmptyRecycler.setVisibility(View.VISIBLE);
            binding.adminHomeRecycler.setVisibility(View.GONE);
        } else {
            binding.adminHomeEmptyRecycler.setVisibility(View.GONE);
            binding.adminHomeRecycler.setVisibility(View.VISIBLE);
        }
        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, getContext(), new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                onCLickEmployeeCard(employee);
            }

            @Override
            public boolean onLongClickCard(Employee employee) {
                onLongClickEmployeeCard(employee);
                return false;
            }
        });
        binding.adminHomeRecycler.setAdapter(adapter);
    }

    private void onLongClickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.adminEditEmployeeFragment, bundle);
    }

    private void onCLickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminEmployeeActivityFragment, bundle);
    }

    public static void showDialog(Context context,Long empId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Are you sure you want to add employee in flag");
        builder.setTitle("Add Employee Flag");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            addToFlag(context,empId);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    private static void addToFlag(Context context,Long empId) {
//        String empId = Paper.book().read("CurrentEmployeeId");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + "employee/addEmployeetoFlag.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();

            if (response != null) {

                try {
                    Toast.makeText(context, response.getString("data"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}