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
import com.flaxeninfosoft.guptaoffset.adapters.EODForKmPetrolTotalAmountAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEODForKmTAandPetrolBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
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

public class EODForKm_TA_AndPetrolFragment extends Fragment {
    
    FragmentEODForKmTAandPetrolBinding binding;
    Long empId;
    EODForKmPetrolTotalAmountAdapter eodForKmPetrolTotalAmountAdapter;
    List<Eod> eodList;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    String selectedDate;
    String currentDate = "";


    public EODForKm_TA_AndPetrolFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_e_o_d_for_km__t_aand_petrol, container, false);
        binding.backImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0);
        selectedDate = Paper.book().read("selectedDate2");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        eodList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        eodForKmPetrolTotalAmountAdapter = new EODForKmPetrolTotalAmountAdapter(eodList , this::onClickEodKmTaPetrol);
        binding.eodPetrolTaKmRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.eodPetrolTaKmSwipeRefresh.setOnRefreshListener(() -> getEod());
        binding.eodPetrolTaKmRecycler.setAdapter(eodForKmPetrolTotalAmountAdapter);
        eodForKmPetrolTotalAmountAdapter.notifyDataSetChanged();
        getEod();

        return binding.getRoot();
    }

    private void getEod() {
        Toast.makeText(getContext(), ""+selectedDate, Toast.LENGTH_SHORT).show();
        eodList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "eod/EodByEmpDate.php";
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
            if (binding.eodPetrolTaKmSwipeRefresh.isRefreshing()) {
                binding.eodPetrolTaKmSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                if(response.has("data")) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Eod eod = gson.fromJson(jsonArray.getJSONObject(i).toString(), Eod.class);
                            eodList.add(eod);
                        }

                        binding.eodPetrolTaKmRecycler.setAdapter(eodForKmPetrolTotalAmountAdapter);
                        eodForKmPetrolTotalAmountAdapter.notifyDataSetChanged();
                        if (eodList == null || eodList.isEmpty()) {
                            binding.eodPetrolTaKmRecycler.setVisibility(View.GONE);
                            binding.eodPetrolTaKmEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.eodPetrolTaKmRecycler.setVisibility(View.VISIBLE);
                            binding.eodPetrolTaKmEmptyTV.setVisibility(View.GONE);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
                else {
                    try {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }


            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.eodPetrolTaKmSwipeRefresh.isRefreshing()) {
                binding.eodPetrolTaKmSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void onClickEodKmTaPetrol(Eod eod) {
    }
}