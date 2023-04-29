package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.SchoolAdminRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.TodayNotWorkingEmployeeAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentTodayNotWokingEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class TodayNotWokingEmployeeFragment extends Fragment {


    public TodayNotWokingEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTodayNotWokingEmployeeBinding binding;
    String selectedDate;
    String empId;
    String currentDate = "";
    List<Employee> employeeList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    TodayNotWorkingEmployeeAdapter todayNotWorkingEmployeeAdapter;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_today_not_woking_employee, container, false);
        binding.backImg.setOnClickListener(this::onClickBack);
        currentDate = Paper.book().read("currentDate");
        selectedDate = Paper.book().read("selectedDate");
        employeeList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        todayNotWorkingEmployeeAdapter = new TodayNotWorkingEmployeeAdapter(employeeList, this::onClickEmployee);
        binding.notWorkingRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.notWorkingSwipeRefresh.setOnRefreshListener(() -> getEmployee());
        binding.notWorkingRecycler.setAdapter(todayNotWorkingEmployeeAdapter);
        todayNotWorkingEmployeeAdapter.notifyDataSetChanged();
        getEmployee();
        todayNotWorkingEmployeeAdapter.notifyDataSetChanged();
        return binding.getRoot();
    }

    private void onClickEmployee(Employee employee) {

    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }


    private void getEmployee() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "attendance/getTodaynotWorking.php";


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();
            if (binding.notWorkingSwipeRefresh.isRefreshing()) {
                binding.notWorkingSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Employee employee = gson.fromJson(jsonArray.getJSONObject(i).toString(), Employee.class);
                        employeeList.add(employee);
                    }

                    binding.notWorkingRecycler.setAdapter(todayNotWorkingEmployeeAdapter);
                    todayNotWorkingEmployeeAdapter.notifyDataSetChanged();
                    if (employeeList == null || employeeList.isEmpty()) {
                        binding.notWorkingRecycler.setVisibility(View.GONE);
                        binding.notWorkingEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.notWorkingRecycler.setVisibility(View.VISIBLE);
                        binding.notWorkingEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.notWorkingSwipeRefresh.isRefreshing()) {
                binding.notWorkingSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}