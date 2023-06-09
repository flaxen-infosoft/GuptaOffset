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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.AllOrdersRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.OrderRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllOrderBinding;
import com.flaxeninfosoft.guptaoffset.models.AllOrder;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;


public class AdminAllOrderFragment extends Fragment {


    public AdminAllOrderFragment() {
        // Required empty public constructor
    }

    FragmentAdminAllOrderBinding binding;
    RequestQueue requestQueue;
    List<AllOrder> orderList;
    Gson gson;
    AllOrdersRecyclerAdapter adapter;
    ProgressDialog progressDialog;
    String selectedDate;
    String currentDate = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_order, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        orderList = new ArrayList<>();
        gson = new Gson();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        currentDate = dateFormat.format(date);
        selectedDate = Paper.book().read("selectedDate2");
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        getAllOrder();


        adapter = new AllOrdersRecyclerAdapter(orderList, new AllOrdersRecyclerAdapter.AllOrderCardOnClickListener() {
            @Override
            public void onCLickCard(AllOrder allOrder) {
//                Toast.makeText(getContext(), String.valueOf(allOrder.getId()),Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ORDER_ID, allOrder.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminAllOrderFragment_to_orderProfileFragment, bundle);
            }
        });

        binding.allOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.allOrdersRecycler.setAdapter(adapter);
        binding.allOrdersSwipeRefresh.setOnRefreshListener(this::getAllOrder);


        return binding.getRoot();
    }


    private void getAllOrder() {
        orderList.clear();
        String url;
        progressDialog.show();
        if (selectedDate == null) {
            url = ApiEndpoints.BASE_URL + "order/getAllOrders.php" + "?date=" + currentDate;
        } else {
            url = ApiEndpoints.BASE_URL + "order/getAllOrders.php" + "?date=" + selectedDate;
        }
//        String url = ApiEndpoints.BASE_URL + "order/getAllOrders.php";
        Log.i("order",url);
        HashMap<String, Object> hashMap = new HashMap<>();


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {
            Log.i("allorder rsp", response.toString());
            progressDialog.dismiss();

            if (binding.allOrdersSwipeRefresh.isRefreshing()) {
                binding.allOrdersSwipeRefresh.setRefreshing(false);
            }

            if (response != null) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        AllOrder order = new AllOrder();
                        order.setId(Long.valueOf(jsonObject.getString("id")));
                        order.setEmpId(Long.valueOf(jsonObject.getString("empId")));
                        order.setSnap(ApiEndpoints.BASE_URL + jsonObject.getString("snap"));
                        order.setDate(jsonObject.getString("date"));
                        order.setAddress(jsonObject.getString("address"));
                        order.setDbo(jsonObject.getString("dbo"));
                        order.setName(jsonObject.getString("name"));
                        order.setDbo(jsonObject.getString("dbo"));

                        Log.i("name", jsonObject.getString("name"));

                        orderList.add(order);
//                        binding.allOrdersRecycler.scrollToPosition(orderList.size()-1);
                        adapter.notifyDataSetChanged();
                        if (orderList == null || orderList.isEmpty()) {
                            binding.allOrdersRecycler.setVisibility(View.GONE);
                            binding.allOrdersEmptyTV.setVisibility(View.VISIBLE);
                        } else {
                            binding.allOrdersRecycler.setVisibility(View.VISIBLE);
                            binding.allOrdersEmptyTV.setVisibility(View.GONE);
                        }
                    }

                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Something went wrong in orders.1", Toast.LENGTH_SHORT).show();
                }

            }


        }, error -> {
            if (binding.allOrdersSwipeRefresh.isRefreshing()) {
                binding.allOrdersSwipeRefresh.setRefreshing(false);
            }
            progressDialog.dismiss();
            Log.i("allorder err", error.toString());
//            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });


        requestQueue.add(jsonArrayRequest);
    }
}