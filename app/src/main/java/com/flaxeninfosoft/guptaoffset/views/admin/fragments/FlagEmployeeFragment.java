package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.FlagEmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentFlagEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class FlagEmployeeFragment extends Fragment {


    List<Employee> employeeList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    FlagEmployeeRecyclerAdapter flagEmployeeRecyclerAdapter;
    Gson gson;

    public FlagEmployeeFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentFlagEmployeeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_flag_employee, container, false);
        binding.backImg.setOnClickListener(this::onClickBack);
        employeeList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");

        flagEmployeeRecyclerAdapter = new FlagEmployeeRecyclerAdapter(employeeList,getContext(), new FlagEmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                onCLickEmployeeCard(employee);
            }

            @Override
            public boolean onLongClickCard(Employee employee) {
                return false;
            }
        });
        binding.flagEmployeeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.flagEmployeeSwipeRefresh.setOnRefreshListener(() -> getEmployee());
        binding.flagEmployeeRecycler.setAdapter(flagEmployeeRecyclerAdapter);
        flagEmployeeRecyclerAdapter.notifyDataSetChanged();
        getEmployee();
        flagEmployeeRecyclerAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }


    private void onCLickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_flagEmployeeFragment_to_adminEmployeeActivityFragment, bundle);
    }


    private void getEmployee() {

        employeeList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "employee/getallFlagEmployee.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.i("employee", response.toString());
            progressDialog.dismiss();
            if (binding.flagEmployeeSwipeRefresh.isRefreshing()) {
                binding.flagEmployeeSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Employee employee = gson.fromJson(jsonArray.getJSONObject(i).toString(), Employee.class);
                        employeeList.add(employee);
                    }

                    binding.flagEmployeeRecycler.setAdapter(flagEmployeeRecyclerAdapter);
                    flagEmployeeRecyclerAdapter.notifyDataSetChanged();
                    if (employeeList == null || employeeList.isEmpty()) {
                        binding.flagEmployeeRecycler.setVisibility(View.GONE);
                        binding.flagEmployeeEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.flagEmployeeRecycler.setVisibility(View.VISIBLE);
                        binding.flagEmployeeEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.flagEmployeeSwipeRefresh.isRefreshing()) {
                binding.flagEmployeeSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public static void removeFromFlagDialog(Context context,Long empId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Are you sure you want to remove employee from flag ?");
        builder.setTitle("Remove Employee From Flag");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            removeFromFlag(context, empId);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void removeFromFlag(Context context,Long empId) {
//        String empId = Paper.book().read("CurrentEmployeeId");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + "employee/Deleteflagemployee.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();

            if (response != null) {

                try {
                    Toast.makeText(context, response.getString("data"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    public static void notesDialog(Context context, Long empId) {


        System.out.println(empId);
        System.out.println(empId);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        View mView = LayoutInflater.from(context).inflate(R.layout.add_notes_dialogue, null);

        EditText txt_inputText = (EditText) mView.findViewById(R.id.txt_input);
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);

        builder.setView(mView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = txt_inputText.getText().toString();

                if (!msg.isEmpty()) {
                    addNotes(context, msg, empId);
                } else {
                    Toast.makeText(context, "Please Enter Something in note.", Toast.LENGTH_SHORT).show();
                    txt_inputText.setError("Please write something.");
                }
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private static void addNotes(Context context, String message, Long empId) {
//              String empId = Paper.book().read("CurrentEmployeeId");
        Toast.makeText(context, "getNote() method", Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + "notes/addemployeenote.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        hashMap.put("note", message);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();

            if (response != null) {

                try {
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}