package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
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
import com.flaxeninfosoft.guptaoffset.adapters.DealerAdminRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSeprateDealerBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
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


public class SeprateDealerFragment extends Fragment {


    public SeprateDealerFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    Long empId;
    String selectedDate;
    String currentDate="";
    FragmentSeprateDealerBinding binding;
    DealerAdminRecyclerAdapter dealerAdminRecyclerAdapter;
    List<Dealer> dealerList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_seprate_dealer, container, false);
        empId = Paper.book().read("CurrentEmployeeId", 0L);
        currentDate = Paper.book().read("currentDate");
        selectedDate = Paper.book().read("selectedDate");
        dealerList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        binding.dealerBackImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        dealerAdminRecyclerAdapter = new DealerAdminRecyclerAdapter(dealerList, this::onClickDealer);
        binding.dealerRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.dealerSwipeRefresh.setOnRefreshListener(() -> getDealer());
        binding.dealerRecycler.setAdapter(dealerAdminRecyclerAdapter);
        dealerAdminRecyclerAdapter.notifyDataSetChanged();
        getDealer();
        dealerAdminRecyclerAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }

    private void onClickDealer(Dealer dealer) {
        Paper.init(getContext());
        Paper.book().write("EmpId_Dealer",dealer.getEmpId());
        Paper.book().write("Current_DealerId",dealer.getId());
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.DEALER_ID,dealer.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_seprateDealerFragment_to_dealerProfileFragment,bundle);
    }


    private void getDealer() {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "dealer/getDealerByEmployeeId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        if (selectedDate==null) {
            hashMap.put("date", currentDate);
        } else {
            hashMap.put("date", selectedDate);
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();
            if (binding.dealerSwipeRefresh.isRefreshing()) {
                binding.dealerSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Dealer dealer = gson.fromJson(jsonArray.getJSONObject(i).toString(), Dealer.class);
                        dealerList.add(dealer);
                    }

                    binding.dealerRecycler.setAdapter(dealerAdminRecyclerAdapter);
                    dealerAdminRecyclerAdapter.notifyDataSetChanged();
                    if (dealerList == null || dealerList.isEmpty()) {
                        binding.dealerRecycler.setVisibility(View.GONE);
                        binding.delaerEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.dealerRecycler.setVisibility(View.VISIBLE);
                        binding.delaerEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.dealerSwipeRefresh.isRefreshing()) {
                binding.dealerSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}