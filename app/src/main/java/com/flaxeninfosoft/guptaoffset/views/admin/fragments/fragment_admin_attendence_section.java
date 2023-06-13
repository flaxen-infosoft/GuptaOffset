package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
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
import com.flaxeninfosoft.guptaoffset.adapters.AdminAttendenceAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAttendenceSectionBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
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

public class fragment_admin_attendence_section extends Fragment {

    FragmentAdminAttendenceSectionBinding binding;
    Long empId;
    List<Attendance> attendanceList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    String currentDate = "";
    AdminAttendenceAdapter adminAttendenceAdapter;
    Gson gson;
    String selectedDate;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_attendence_section, container, false);
        binding.attendenceListBackImg.setOnClickListener(this::onClickBack);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        selectedDate = Paper.book().read("selectedDate2");
        attendanceList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        adminAttendenceAdapter = new
                AdminAttendenceAdapter(attendanceList, this::onClickAttendenceList);
        binding.attendenceAdminRecycle.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.attendenceSwipeRefresh.setOnRefreshListener(() -> getAllAttendenceList());
        binding.attendenceAdminRecycle.setAdapter(adminAttendenceAdapter);
        adminAttendenceAdapter.notifyDataSetChanged();
        getAllAttendenceList();

        return binding.getRoot();
    }

    private void onClickAttendenceList(Attendance attendance) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.ATN_ID,attendance.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.attendanceProfileFragment,bundle);
    }

    private void getAllAttendenceList() {
        attendanceList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "attendance/getEmpAttendanceByDate.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        hashMap.put("date", selectedDate);
        Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();

            if (binding.attendenceSwipeRefresh.isRefreshing()) {
                binding.attendenceSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    if (response.has("data")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Attendance attendance = gson.fromJson(jsonArray.getJSONObject(i).toString(), Attendance.class);
                            attendanceList.add(attendance);
                        }

                        binding.attendenceAdminRecycle.setAdapter(adminAttendenceAdapter);
                        adminAttendenceAdapter.notifyDataSetChanged();
                        if (attendanceList == null || attendanceList.isEmpty()) {
                            binding.attendenceAdminRecycle.setVisibility(View.GONE);
                            binding.attendenceListEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.attendenceAdminRecycle.setVisibility(View.VISIBLE);
                            binding.attendenceListEmptyTV.setVisibility(View.GONE);
                        }
                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }

        }, error -> {
            progressDialog.dismiss();
            if (binding.attendenceSwipeRefresh.isRefreshing()) {
                binding.attendenceSwipeRefresh.setRefreshing(false);
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



