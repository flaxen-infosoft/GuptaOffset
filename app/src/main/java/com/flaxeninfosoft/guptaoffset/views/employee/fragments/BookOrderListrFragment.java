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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.BookOrderListAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.OrderRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.PaymentRecieveAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentBookOrderListrBinding;
import com.flaxeninfosoft.guptaoffset.models.EmployeeBookOrder;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentStatus;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookOrderListrFragment extends Fragment {

    FragmentBookOrderListrBinding binding;
    String selectedDate;
    Long empId;
    String currentDate = "";
    List<EmployeeBookOrder> employeeBookOrders;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    BookOrderListAdapter bookOrderListAdapter;
    Gson gson;

    public BookOrderListrFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_book_order_listr, container, false);
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0L);
        binding.bookOrderListBackImg.setOnClickListener(this::onClickBack);
        employeeBookOrders = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("please wait...");
        bookOrderListAdapter = new BookOrderListAdapter(employeeBookOrders,this::onClickBookOrder);
        binding.orderListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.orderRecieveSwipeRefresh.setOnRefreshListener(() -> getAllOrderRecieve(empId));
        binding.orderListRecycler.setAdapter(bookOrderListAdapter);
        bookOrderListAdapter.notifyDataSetChanged();
        getAllOrderRecieve(empId);
        return binding.getRoot();
    }

    private void onClickBookOrder(EmployeeBookOrder employeeBookOrder) {

    }


    private void getAllOrderRecieve(Long empId) {

        employeeBookOrders.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "order/getOrdersByEmpId.php" ;
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("bookOrder", response.toString());
            progressDialog.dismiss();
            if (binding.orderRecieveSwipeRefresh.isRefreshing()) {
                binding.orderRecieveSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    if (response.has("data")){
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            EmployeeBookOrder employeeBookOrder = gson.fromJson(jsonArray.getJSONObject(i).toString(), EmployeeBookOrder.class);
                            employeeBookOrders.add(new EmployeeBookOrder());

                        }

                    }

                    binding.orderListRecycler.setAdapter(bookOrderListAdapter);
                    bookOrderListAdapter.notifyDataSetChanged();
                    if (employeeBookOrders == null || employeeBookOrders.isEmpty()) {
                        binding.orderListRecycler.setVisibility(View.GONE);
                        binding.orderReportEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.orderListRecycler.setVisibility(View.VISIBLE);
                        binding.orderReportEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.orderRecieveSwipeRefresh.isRefreshing()) {
                binding.orderRecieveSwipeRefresh.setRefreshing(false);
            }
//            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
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