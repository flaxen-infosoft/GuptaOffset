package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeSchoolListAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.PaymentRecieveAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolListBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBindingImpl;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.PaymentStatus;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SchoolListFragment extends Fragment {


    FragmentSchoolListBinding binding;
    Long empId;
    List<School> schoolList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    EmployeeSchoolListAdapter employeeSchoolListAdapter;
    Gson gson;

    public SchoolListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_school_list, container, false);
        binding.schoolListBackImg.setOnClickListener(this::onClickBack);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0L);
        schoolList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        employeeSchoolListAdapter = new EmployeeSchoolListAdapter(schoolList, this::onClickSchoolList);
        binding.schoolListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.schoolSwipeRefresh.setOnRefreshListener(() -> getAllSchoolList());
        binding.schoolListRecycler.setAdapter(employeeSchoolListAdapter);
        employeeSchoolListAdapter.notifyDataSetChanged();
        getAllSchoolList();
        return binding.getRoot();
    }

    private void onClickSchoolList(School school) {
    }

    private void getAllSchoolList() {
        schoolList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "school/getSchoolListByempId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url , new JSONObject(hashMap) , response -> {
            Log.i("Above80km", response.toString());
            progressDialog.dismiss();

            if (binding.schoolSwipeRefresh.isRefreshing()) {
                binding.schoolSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        School school = gson.fromJson(jsonArray.getJSONObject(i).toString(), School.class);
                        schoolList.add(school);
                    }

                    binding.schoolListRecycler.setAdapter(employeeSchoolListAdapter);
                    employeeSchoolListAdapter.notifyDataSetChanged();
                    if (schoolList == null || schoolList.isEmpty()) {
                        binding.schoolListRecycler.setVisibility(View.GONE);
                        binding.schoolListEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.schoolListRecycler.setVisibility(View.VISIBLE);
                        binding.schoolListEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        }, error -> {
            progressDialog.dismiss();
            if (binding.schoolSwipeRefresh.isRefreshing()) {
                binding.schoolSwipeRefresh.setRefreshing(false);
            }
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}