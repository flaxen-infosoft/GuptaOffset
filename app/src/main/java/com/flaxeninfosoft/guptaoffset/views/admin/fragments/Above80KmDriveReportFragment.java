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
import com.flaxeninfosoft.guptaoffset.adapters.Above80kmRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.TodayNotWorkingEmployeeAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAbove80KmDriveReportBinding;
import com.flaxeninfosoft.guptaoffset.models.Attachment;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;


public class Above80KmDriveReportFragment extends Fragment {

    FragmentAbove80KmDriveReportBinding binding;

    List<Attendance> attendanceList;
    String selectedDate;

    static Long empId;

    String currentDate = "";

    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    Above80kmRecyclerAdapter above80kmRecyclerAdapter;

    Gson gson;


    public Above80KmDriveReportFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_above80_km_drive_report, container, false);
        binding.above80KmDriveReportBackImg.setOnClickListener(this::onClickBack);
        currentDate = Paper.book().read("currentDate");
        selectedDate = Paper.book().read("selectedDate");
        attendanceList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait...");
        above80kmRecyclerAdapter = new Above80kmRecyclerAdapter(attendanceList, this::onClickAbove80km);
        binding.above80KmDriveReportRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.above80kmSwipeRefresh.setOnRefreshListener(() -> getAbove80());
        binding.above80KmDriveReportRecycler.setAdapter(above80kmRecyclerAdapter);
        above80kmRecyclerAdapter.notifyDataSetChanged();
        getAbove80();


        return binding.getRoot();
    }

    private void onClickAbove80km(Attendance attendance) {

    }

    private void getAbove80() {
        attendanceList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "attendance/gettotodayeightyAttendance.php";
//        HashMap<String, Object> hashMap = new HashMap<>();
//        hashMap.put("empId", empId);
//        Toast.makeText(getContext(), ""+empId, Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.i("Above80km", response.toString());
            progressDialog.dismiss();

            if (binding.above80kmSwipeRefresh.isRefreshing()) {
                binding.above80kmSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Attendance attendance = gson.fromJson(jsonArray.getJSONObject(i).toString(), Attendance.class);
                        attendanceList.add(attendance);
                    }

                    binding.above80KmDriveReportRecycler.setAdapter(above80kmRecyclerAdapter);
                    above80kmRecyclerAdapter.notifyDataSetChanged();
                    if (attendanceList == null || attendanceList.isEmpty()) {
                        binding.above80KmDriveReportRecycler.setVisibility(View.GONE);
                        binding.above80KmDriveReportEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.above80KmDriveReportRecycler.setVisibility(View.VISIBLE);
                        binding.above80KmDriveReportEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        }, error -> {
            progressDialog.dismiss();
            if (binding.above80kmSwipeRefresh.isRefreshing()) {
                binding.above80kmSwipeRefresh.setRefreshing(false);
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