package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.graphics.fonts.FontStyle;
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
import com.flaxeninfosoft.guptaoffset.adapters.FirstAndLastSchoolVisitAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.SchoolAdminRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentFirsrAndLastSchoolVisitDurationBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
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

public class FirstAndLastSchoolVisitDurationFragment extends Fragment {

    FragmentFirsrAndLastSchoolVisitDurationBinding binding;
    FirstAndLastSchoolVisitAdapter firstAndLastSchoolVisitAdapter;
    Long empId;
    String selectedDate;
    String currentDate = "";
    List<School> schoolList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Gson gson;
    


    public FirstAndLastSchoolVisitDurationFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_firsr_and_last_school_visit_duration, container, false);
        binding.firstAndLastBackImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0);
        selectedDate = Paper.book().read("selectedDate2");
        schoolList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        firstAndLastSchoolVisitAdapter = new FirstAndLastSchoolVisitAdapter(schoolList, this::onClickFirstAndLast);
        binding.firstAndLastRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.firstAndLastSwipeRefresh.setOnRefreshListener(() -> getSchool());
        binding.firstAndLastRecycler.setAdapter(firstAndLastSchoolVisitAdapter);
        firstAndLastSchoolVisitAdapter.notifyDataSetChanged();
        getSchool();
        return  binding.getRoot();
    }

    private void onClickFirstAndLast(School school) {
    }

    private void getSchool() {
        Toast.makeText(getContext(), ""+selectedDate, Toast.LENGTH_SHORT).show();
        schoolList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "school/FirstAndLastSchoolByEmp.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        if (selectedDate==null) {
            hashMap.put("date", currentDate);
        } else {
            hashMap.put("date", selectedDate);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("FirstAndLastSchool", response.toString());
            progressDialog.dismiss();
            if (binding.firstAndLastSwipeRefresh.isRefreshing()) {
                binding.firstAndLastSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                if (response.has("data")) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            School school = gson.fromJson(jsonArray.getJSONObject(i).toString(), School.class);
                            schoolList.add(school);
                        }

                        binding.firstAndLastRecycler.setAdapter(firstAndLastSchoolVisitAdapter);
                        firstAndLastSchoolVisitAdapter.notifyDataSetChanged();
                        if (schoolList == null || schoolList.isEmpty()) {
                            binding.firstAndLastRecycler.setVisibility(View.GONE);
                            binding.firstAndLastEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.firstAndLastRecycler.setVisibility(View.VISIBLE);
                            binding.firstAndLastEmptyTV.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.firstAndLastSwipeRefresh.isRefreshing()) {
                binding.firstAndLastSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}