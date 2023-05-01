package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentFlagEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class FlagEmployeeFragment extends Fragment {


    List<Employee> employeeList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EmployeeRecyclerAdapter employeeRecyclerAdapter;
    Gson gson;

    public FlagEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentFlagEmployeeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flag_employee, container, false);
        binding.backImg.setOnClickListener(this::onClickBack);
        employeeList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        employeeRecyclerAdapter = new EmployeeRecyclerAdapter(employeeList, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                onCLickEmployeeCard(employee);
            }

            @Override
            public boolean onLongClickCard(Employee employee) {
                return false;
            }
        });
        binding.flagEmployeeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.flagEmployeeSwipeRefresh.setOnRefreshListener(() -> getEmployee());
        binding.flagEmployeeRecycler.setAdapter(employeeRecyclerAdapter);
        employeeRecyclerAdapter.notifyDataSetChanged();
        getEmployee();
        employeeRecyclerAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }


    private void onCLickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminEmployeeActivityFragment, bundle);
    }


    private void getEmployee() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.i("employee", response.toString());
            progressDialog.dismiss();
            if (binding.flagEmployeeSwipeRefresh.isRefreshing()) {
                binding.flagEmployeeSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Employee employee = gson.fromJson(jsonArray.getJSONObject(i).toString(), Employee.class);
                        employeeList.add(employee);
                    }

                    binding.flagEmployeeRecycler.setAdapter(employeeRecyclerAdapter);
                    employeeRecyclerAdapter.notifyDataSetChanged();
                    if (employeeList == null || employeeList.isEmpty()) {
                        binding.flagEmployeeRecycler.setVisibility(View.GONE);
                        binding.flagEmployeeEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.flagEmployeeRecycler.setVisibility(View.VISIBLE);
                        binding.flagEmployeeEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.flagEmployeeSwipeRefresh.isRefreshing()) {
                binding.flagEmployeeSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}