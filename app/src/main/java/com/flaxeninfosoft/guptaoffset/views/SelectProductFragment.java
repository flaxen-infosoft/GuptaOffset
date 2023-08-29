package com.flaxeninfosoft.guptaoffset.views;

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
import com.flaxeninfosoft.guptaoffset.adapters.ProductsAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSelectProductBinding;
import com.flaxeninfosoft.guptaoffset.models.Products;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SelectProductFragment extends Fragment {


    public SelectProductFragment() {
        // Required empty public constructor
    }

    FragmentSelectProductBinding binding;
    List<Products> productsList;
    RequestQueue requestQueue;
    Gson gson;
    Long empId;
    Long district_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_select_product, container, false);
        requestQueue = Volley.newRequestQueue(getContext());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        district_id = getArguments().getLong(Constants.DISTRICT_ID, 0L);
        gson = new Gson();
        productsList = new ArrayList<>();
        productsList.add(new Products(1L,"Maths Textbook"));
        productsList.add(new Products(2L,"English Textbook"));
        productsList.add(new Products(3L,"Hindi Textbook"));
        binding.backImg.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());
        binding.productRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        ProductsAdapter adapter = new ProductsAdapter(productsList,this::onClickProduct);
        binding.productRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return binding.getRoot();
    }

    private void onClickProduct(Products products) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID,empId);
        bundle.putLong(Constants.DISTRICT_ID,district_id);
        bundle.putLong(Constants.PRODUCT_ID, products.getProduct_id());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_selectProductFragment_to_tehsilFragment, bundle);
    }

    private void getAllProducts(Long empId) {

        productsList.clear();
        String url = ApiEndpoints.BASE_URL + "";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("District", response.toString());
            if (binding.productSwipeRefresh.isRefreshing()) {
                binding.productSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    if (response.has("data")) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Products products = gson.fromJson(jsonArray.getJSONObject(i).toString(), Products.class);
                            productsList.add(products);
//                            adapter.notifyDataSetChanged();

                        }
                    } else {
                        Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            if (binding.productSwipeRefresh.isRefreshing()) {
                binding.productSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}