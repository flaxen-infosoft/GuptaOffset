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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSeprateSchoolBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;


public class SeprateSchoolFragment extends Fragment {
    public SeprateSchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSeprateSchoolBinding binding;
    String selectedDate;
    Long empId;
    String currentDate = "";
    SchoolAdminRecyclerAdapter schoolAdminRecyclerAdapter;
    List<School> schoolList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seprate_school, container, false);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0);
        selectedDate = Paper.book().read("selectedDate2");
        schoolList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        binding.schoolBackImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        schoolAdminRecyclerAdapter = new SchoolAdminRecyclerAdapter(schoolList, this::onClickSchool);
        binding.schoolRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.schoolSwipeRefresh.setOnRefreshListener(() -> getSchool());
        binding.schoolRecycler.setAdapter(schoolAdminRecyclerAdapter);
        schoolAdminRecyclerAdapter.notifyDataSetChanged();

        getSchool();
        schoolAdminRecyclerAdapter.notifyDataSetChanged();
        return binding.getRoot();
    }

    private void onClickSchool(School school) {
        Paper.init(getContext());
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.SCHOOL_ID, school.getId());
        Paper.book().write("EmpId_School", school.getEmpId());
        Paper.book().write("Current_SchoolId", school.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_seprateSchoolFragment_to_schoolProfileFragment, bundle);
    }


    private void getSchool() {
        schoolList.clear();

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "school/getSchoolByempId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        if (selectedDate == null) {
            hashMap.put("date", currentDate);
        } else {
            hashMap.put("date", selectedDate);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
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

                    binding.schoolRecycler.setAdapter(schoolAdminRecyclerAdapter);
                    schoolAdminRecyclerAdapter.notifyDataSetChanged();
                    if (schoolList == null || schoolList.isEmpty()) {
                        binding.schoolRecycler.setVisibility(View.GONE);
                        binding.schoolEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.schoolRecycler.setVisibility(View.VISIBLE);
                        binding.schoolEmptyTV.setVisibility(View.GONE);
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
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}