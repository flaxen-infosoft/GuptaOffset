package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.AdminLeaveAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.ShowNotesRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class AdminLeaveFragment extends Fragment {

    List<Leave> leaveList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    AdminLeaveAdapter adminLeaveAdapter;
    Gson gson;
    Long empId;
    String fromDate;
    String toDate;

    Calendar myCalendar = Calendar.getInstance();
    FragmentAdminLeaveBinding binding;

    public AdminLeaveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_leave, container, false);
        binding.adminLeaveBackImg.setOnClickListener(this::onClickBack);
        leaveList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        binding.adminLeaveFromDate.setOnClickListener(this::fromDate);
        binding.adminLeaveToDate.setOnClickListener(this::toDate);

        adminLeaveAdapter = new AdminLeaveAdapter(leaveList, getContext(), leave -> onClickLeave(leave));
        binding.adminLeaveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.adminLeaveSwipeRefresh.setOnRefreshListener(this::onSwipe);
        binding.adminLeaveRecycler.setAdapter(adminLeaveAdapter);
        adminLeaveAdapter.notifyDataSetChanged();
//        getAllLeave(empId);

        getLeave(empId);

        return binding.getRoot();

    }

    private void onSwipe() {
        binding.adminLeaveFromDate.setText("From Date");
        binding.adminLeaveToDate.setText("To Date");
//        getAllLeave(empId);
        getLeave(empId);
    }

    private void getAllLeave(Long empId) {
        leaveList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "/leave/getLeaveData.php";
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();
            if (binding.adminLeaveSwipeRefresh.isRefreshing()) {
                binding.adminLeaveSwipeRefresh.setRefreshing(false);
            }
            try {
                Log.e("", "inside try ");
                if (response != null) {
                    Log.e("leave" + response, "");
                    if (response.getJSONArray("data").length() > 0) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("leave", "inside for loop");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Leave leave = gson.fromJson(jsonArray.getJSONObject(i).toString(), Leave.class);
                            leaveList.add(leave);
                        }

                        Log.e("" + leaveList, "");

                        binding.adminLeaveRecycler.setAdapter(adminLeaveAdapter);
                        adminLeaveAdapter.notifyDataSetChanged();
                        if (leaveList == null || leaveList.isEmpty()) {
                            binding.adminLeaveRecycler.setVisibility(View.GONE);
                            binding.adminLeaveEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.adminLeaveRecycler.setVisibility(View.VISIBLE);
                            binding.adminLeaveEmptyTV.setVisibility(View.GONE);
                        }
                    } else {
                        binding.adminLeaveRecycler.setVisibility(View.GONE);
                        binding.adminLeaveEmptyTV.setVisibility(View.VISIBLE);
                    }
                } else {


                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.adminLeaveSwipeRefresh.isRefreshing()) {
                binding.adminLeaveSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    private void getLeave(Long empId) {
        leaveList.clear();
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + "leave/getLeaveData.php";
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


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();
            if (binding.adminLeaveSwipeRefresh.isRefreshing()) {
                binding.adminLeaveSwipeRefresh.setRefreshing(false);
            }

            if (response!=null){

                if (response.has("data")){
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.e("leave", "inside for loop");
                            Leave leave = gson.fromJson(jsonArray.getJSONObject(i).toString(), Leave.class);
                            leaveList.add(leave);
                        }
                        binding.adminLeaveRecycler.setAdapter(adminLeaveAdapter);
                        adminLeaveAdapter.notifyDataSetChanged();

                        if (leaveList == null || leaveList.isEmpty()) {
                            binding.adminLeaveRecycler.setVisibility(View.GONE);
                            binding.adminLeaveEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.adminLeaveRecycler.setVisibility(View.VISIBLE);
                            binding.adminLeaveEmptyTV.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        binding.adminLeaveRecycler.setVisibility(View.GONE);
                        binding.adminLeaveEmptyTV.setVisibility(View.VISIBLE);
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            else {

            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.adminLeaveSwipeRefresh.isRefreshing()) {
                binding.adminLeaveSwipeRefresh.setRefreshing(false);
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
                binding.adminLeaveFromDate.setText(format.format(date));
                fromDate = format.format(date);
//                getAllLeave(empId);

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
                java.util.Date date = new Date(year, month, day);
                Format format = new SimpleDateFormat("20yy-MM-dd");
                binding.adminLeaveToDate.setText(format.format(date));
                toDate = format.format(date);
//                getAllLeave(empId);
                getLeave(empId);
            }
        }, y, m, d);
        datePickerDialog.show();
        Calendar calendar = Calendar.getInstance();
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

    }

    private void onClickLeave(Leave leave) {

    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}