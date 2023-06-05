package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.DistrictListForTehsilAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.DistrictListRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentDistrictListForTehsilBinding;
import com.flaxeninfosoft.guptaoffset.models.DistrictData;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class
DistrictListForTehsilFragment extends Fragment {


    public DistrictListForTehsilFragment() {
        // Required empty public constructor
    }


    FragmentDistrictListForTehsilBinding binding;
    List<DistrictData> districtDataList;
    RequestQueue requestQueue;
    DistrictListForTehsilAdapter adapter;
    Gson gson;
    String selectedDate;
    Long empId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_district_list_for_tehsil, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        districtDataList = new ArrayList<>();
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        getAllDistrict(empId);
        binding.districtRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.districtSwipeRefresh.setOnRefreshListener(() -> getAllDistrict(empId));
        binding.backImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        adapter = new DistrictListForTehsilAdapter(districtDataList, this::onClickDistrict);
        binding.districtRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return binding.getRoot();
    }


    private void getAllDistrict(Long empId) {

        districtDataList.clear();
        String url = ApiEndpoints.BASE_URL + "tehsil/getDistrictById.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("District", response.toString());
            if (binding.districtSwipeRefresh.isRefreshing()) {
                binding.districtSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    if (response.has("data")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            DistrictData districtData = gson.fromJson(jsonArray.getJSONObject(i).toString(), DistrictData.class);
                            districtDataList.add(districtData);
                            adapter.notifyDataSetChanged();
                            Log.i("district", districtData.getDistrict_name());
                        }
                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            if (binding.districtSwipeRefresh.isRefreshing()) {
                binding.districtSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void onClickDistrict(DistrictData districtData) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID,empId);
        bundle.putLong(Constants.DISTRICT_ID,districtData.getDistrict_id());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_districtListForTehsilFragment_to_tehsilFragment, bundle);
    }
}