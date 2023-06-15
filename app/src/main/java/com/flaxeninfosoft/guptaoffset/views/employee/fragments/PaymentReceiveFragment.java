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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.PaymentRecieveAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.TodayNotWorkingEmployeeAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentPaymentReceiveBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentStatus;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
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

public class PaymentReceiveFragment extends Fragment {

    FragmentPaymentReceiveBinding binding;
    String selectedDate;
    Long empId;
    String currentDate = "";
    List<PaymentStatus> paymentStatusList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    PaymentRecieveAdapter paymentRecieveAdapter;
    Gson gson;

    public PaymentReceiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_payment_receive, container, false);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0L);
        binding.paymentReceiveBackImg.setOnClickListener(this::onCLickBack);
       /* currentDate = Paper.book().read("currentDate");
        selectedDate = Paper.book().read("selectdate");*/
       // selectedDate = Paper.book().read("selectedDate");
        paymentStatusList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        paymentRecieveAdapter = new PaymentRecieveAdapter(paymentStatusList, this::onClickPaymentRecieve);
        binding.paymentReceiveRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.paymentRecieveSwipeRefresh.setOnRefreshListener(() -> getAllPaymentRecieve(empId));
        binding.paymentReceiveRecycler.setAdapter(paymentRecieveAdapter);
        getAllPaymentRecieve(empId);

        return binding.getRoot();


    }


private void onClickPaymentRecieve(PaymentStatus paymentStatus) {
}


    private void getAllPaymentRecieve(Long empId) {
        paymentStatusList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "payment/getpaymentReceivedByEmpId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        /*hashMap.put("date", selectedDate);
        Toast.makeText(getContext(), selectedDate, Toast.LENGTH_SHORT).show();*/
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("paymentrecieve", response.toString());
            progressDialog.dismiss();
            if (binding.paymentRecieveSwipeRefresh.isRefreshing()) {
                binding.paymentRecieveSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    if (response.has("data")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            PaymentStatus paymentStatus = gson.fromJson(jsonArray.getJSONObject(i).toString(), PaymentStatus.class);
                            paymentStatusList.add(paymentStatus);

                    }

                    }

                    binding.paymentReceiveRecycler.setAdapter(paymentRecieveAdapter);
                    paymentRecieveAdapter.notifyDataSetChanged();
                    if (paymentStatusList == null || paymentStatusList.isEmpty()) {
                        binding.paymentReceiveRecycler.setVisibility(View.GONE);
                        binding.paymentReceiveEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.paymentReceiveRecycler.setVisibility(View.VISIBLE);
                        binding.paymentReceiveEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.paymentRecieveSwipeRefresh.isRefreshing()) {
                binding.paymentRecieveSwipeRefresh.setRefreshing(false);
            }
//            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

/*
    private void onClickPaymentRecieve(PaymentStatus paymentStatus) {
    }
*/



    private void onCLickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}