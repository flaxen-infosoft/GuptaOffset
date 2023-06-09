package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeLeaveAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAbsentOrLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.EmployeeAbsentLeave;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class AbsentOrLeaveFragment extends Fragment {

    List<EmployeeAbsentLeave> employeeAbsentLeaves;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EmployeeLeaveAdapter employeeLeaveAdapter;
    Gson gson;
    Long empId;
    String fromDate;
    String toDate;

    Calendar myCalendar = Calendar.getInstance();

    FragmentAbsentOrLeaveBinding binding;

    public AbsentOrLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_absent_or_leave, container, false);
        binding.absentOrLeaveBackImg.setOnClickListener(this::onClickBack);
        employeeAbsentLeaves = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        binding.leaveFromDate.setOnClickListener(this::fromDate);
        binding.leaveToDate.setOnClickListener(this::toDate);

        employeeLeaveAdapter = new EmployeeLeaveAdapter(employeeAbsentLeaves, getContext(), employeeAbsentLeave -> onClickAbsentLeave(employeeAbsentLeave));
        binding.absentOrLeaveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.absentLeaveSwipeRefresh.setOnRefreshListener(this::onSwipe);
        binding.absentOrLeaveRecycler.setAdapter(employeeLeaveAdapter);
        employeeLeaveAdapter.notifyDataSetChanged();
        getAllAbsentLeaves(empId);
        return binding.getRoot();
    }

    private void onSwipe() {
        toDate = "";
        fromDate = "";
        getAllAbsentLeaves(empId);
    }


    public void getAllAbsentLeaves(Long empId) {
        employeeAbsentLeaves.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "attendance/getLeaveAndAbsentByempId.php";

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        if (fromDate == null || fromDate.isEmpty()) {
            hashMap.put("fromDate", "");
        } else {
            hashMap.put("fromDate", fromDate);
        }
        if (toDate == null || toDate.isEmpty()) {
            hashMap.put("toDate", "");
        } else {
            hashMap.put("toDate", toDate);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                progressDialog.dismiss();

                if (binding.absentLeaveSwipeRefresh.isRefreshing()) {
                    binding.absentLeaveSwipeRefresh.setRefreshing(false);
                }


                try {
                    binding.totalLeaveTexview.setText(response.getString("total_leave"));
                    binding.totalAbsentTexview.setText(response.getString("total_absent"));
                    JSONArray jsonArray = response.getJSONArray("data");
                    Log.i("akshat jsonarray", jsonArray.toString());

                    for (int i = 0; i < jsonArray.length(); i++) {

                        EmployeeAbsentLeave employeeAbsentLeave = gson.fromJson(jsonArray.getJSONObject(i).toString(), EmployeeAbsentLeave.class);
                        employeeAbsentLeaves.add(employeeAbsentLeave);
                    }

                    employeeLeaveAdapter.notifyDataSetChanged();
                    if (employeeAbsentLeaves == null || employeeAbsentLeaves.isEmpty() || employeeAbsentLeaves.size() == 0) {
                        binding.absentOrLeaveRecycler.setVisibility(View.GONE);
                        binding.absentOrLeaveEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.absentOrLeaveRecycler.setVisibility(View.VISIBLE);
                        binding.absentOrLeaveEmptyTV.setVisibility(View.GONE);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                if (binding.absentLeaveSwipeRefresh.isRefreshing()) {
                    binding.absentLeaveSwipeRefresh.setRefreshing(false);
                }
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }


    private void fromDate(View view) {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                java.util.Date date = new Date(year, month, day);
                Format format = new SimpleDateFormat("20yy-MM-dd");
                binding.leaveFromDate.setText(format.format(date));
                fromDate = format.format(date);
//                    fromDate = format.format(date);
            }
        }, y, m, d);
        datePickerDialog.show();
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }

    private void toDate(View view) {
        final Calendar c = Calendar.getInstance();
        int y = c.get(Calendar.YEAR);
        int m = c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day); // Set selected date to calendar

                java.util.Date date = calendar.getTime();
                Format format = new SimpleDateFormat("20yy-MM-dd");
                binding.leaveToDate.setText(format.format(date));
                toDate = format.format(date);
                getAllAbsentLeaves(empId);
            }
        }, y, m, d);

        // Set minimum date for selection based on fromDate value
        if (fromDate != null) {
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("20yy-MM-dd");
                Date fromDateObj = dateFormat.parse(fromDate);
                long minDate = fromDateObj.getTime();
                datePickerDialog.getDatePicker().setMinDate(minDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.show();
    }

    private void onClickAbsentLeave(EmployeeAbsentLeave employeeAbsentLeave) {
    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}