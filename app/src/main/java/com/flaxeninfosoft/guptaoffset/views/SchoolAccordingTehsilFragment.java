package com.flaxeninfosoft.guptaoffset.views;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.SchoolAccordingTehsilAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolAccordingTehsilBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.models.TehsilData;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class SchoolAccordingTehsilFragment extends Fragment {


    public SchoolAccordingTehsilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentSchoolAccordingTehsilBinding binding;
    Gson gson;
    Long empId;
    Long district_id;
    Long tehsil_id;
    String tehsil_name;
    RequestQueue requestQueue;
    List<School> schoolList;
    SchoolAccordingTehsilAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_school_according_tehsil, container, false);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        district_id = getArguments().getLong(Constants.DISTRICT_ID, 0L);
        tehsil_id = getArguments().getLong(Constants.TEHSIL_ID, 0L);
        tehsil_name = getArguments().getString(Constants.TEHSIL_NAME);
        binding.topText.setText(tehsil_name);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        schoolList = new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        binding.schoolRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new SchoolAccordingTehsilAdapter(schoolList,this::onClickSchool);
        binding.schoolRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        binding.schoolSwipeRefresh.setOnRefreshListener(() -> getSchoolByTehsil());
        getSchoolByTehsil();
        if (schoolList.size()>0){
            binding.schoolEmptyTV.setVisibility(View.GONE);
        }
        else {
            binding.schoolEmptyTV.setVisibility(View.VISIBLE);
        }
        binding.backImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        return binding.getRoot();
    }

    private void onClickSchool(School school) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.SCHOOL_ID,school.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.schoolProfileFragment,bundle);
    }

    private void getSchoolByTehsil() {
        progressDialog.show();
        schoolList.clear();

        String url = ApiEndpoints.BASE_URL + ApiEndpoints.SCHOOL_BY_TEHSIL;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.EMPLOYEE_ID, empId);
        hashMap.put(Constants.DISTRICT_ID, district_id);
        hashMap.put(Constants.TEHSIL_ID, tehsil_id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("School", response.toString());
            if (binding.schoolSwipeRefresh.isRefreshing()) {
                binding.schoolSwipeRefresh.setRefreshing(false);
            }
            binding.schoolEmptyTV.setVisibility(View.GONE);
            progressDialog.dismiss();
            if (response != null) {
                try {
                    if (response.has("data")) {
                        if (response.getJSONArray("data").length()>0) {
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                School school = gson.fromJson(jsonArray.getJSONObject(i).toString(), School.class);
                                schoolList.add(school);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        else {
                            Toast.makeText(getContext(), "School Not Visited in "+tehsil_name, Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            Log.i("school", error.toString());
            if (binding.schoolSwipeRefresh.isRefreshing()) {
                binding.schoolSwipeRefresh.setRefreshing(false);
            }
            progressDialog.dismiss();
            binding.schoolEmptyTV.setVisibility(View.VISIBLE);
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}