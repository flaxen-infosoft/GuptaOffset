package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeTehsilAdpter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentTehsilBinding;
import com.flaxeninfosoft.guptaoffset.models.DistrictData;
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

import io.paperdb.Paper;


public class TehsilFragment extends Fragment {


    public TehsilFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentTehsilBinding binding;
    Gson gson;
    Long empId;
    Long district_id;
    List<TehsilData> tehsilDataList;
    RequestQueue requestQueue;
    EmployeeTehsilAdpter tehsilAdpter;
    String selectedDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tehsil, container, false);
        Paper.init(getContext());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        district_id = getArguments().getLong(Constants.DISTRICT_ID, 0L);
        tehsilDataList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        selectedDate = Paper.book().read("selectedDate");
        binding.tehsilRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        tehsilAdpter = new EmployeeTehsilAdpter(tehsilDataList, this::onClickTehsil);
        binding.tehsilRecycler.setAdapter(tehsilAdpter);
        tehsilAdpter.notifyDataSetChanged();
        binding.tehsilSwipeRefresh.setOnRefreshListener(() -> {
            getTehsilsData();
        });
        binding.backImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        getTehsilsData();

        return binding.getRoot();
    }

    private void onClickTehsil(TehsilData tehsilData) {

    }

    private void getTehsilsData() {
        tehsilDataList.clear();

        String url = ApiEndpoints.BASE_URL + "/tehsil/getTehsilDataByEmpId.php\n";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put(Constants.EMPLOYEE_ID, empId);
        hashMap.put(Constants.DISTRICT_ID, district_id);
     //   hashMap.put("date", selectedDate);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Tehsil", response.toString());
            if (binding.tehsilSwipeRefresh.isRefreshing()) {
                binding.tehsilSwipeRefresh.setRefreshing(false);
            }

            if (response != null) {
                try {
                    if (response.has("data")) {
                        binding.topText.setText(response.getString("district_name"));
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            TehsilData tehsilData = gson.fromJson(jsonArray.getJSONObject(i).toString(), TehsilData.class);
                            tehsilDataList.add(tehsilData);
                            tehsilAdpter.notifyDataSetChanged();

                        }
                    }
                    else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Log.i("Tehsil", error.toString());
            if (binding.tehsilSwipeRefresh.isRefreshing()) {
                binding.tehsilSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}