package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentUpdateAttendanceBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class UpdateAttendanceFragment extends Fragment {



    public UpdateAttendanceFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    FragmentUpdateAttendanceBinding binding;
    private EmployeeViewModel viewModel;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding =  DataBindingUtil.inflate(inflater,R.layout.fragment_update_attendance, container, false);
        binding.buttonUpdateAttendance.setOnClickListener(this::onClickUpdateAttendance);
        requestQueue = Volley.newRequestQueue(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");

        long atnId = getArguments().getLong(Constants.ATN_ID, -1);
        if (atnId == -1) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            viewModel.getAttendanceById(atnId).observe(getViewLifecycleOwner(), this::setAttendance);
        }

        return binding.getRoot();
    }

    private void onClickUpdateAttendance(View view) {
        updateAttendance();
    }

    private void setAttendance(Attendance attendance) {

        if (attendance == null) {
            navigateUp();
        } else {
            binding.setAttendance(attendance);

            switch (attendance.getPunchStatus()) {
                case 0:
                    navigateUp();
                    break;
                case 2:
                    binding.attendanceProfileOutLayout.setVisibility(View.VISIBLE);
                    String outImage = ApiEndpoints.BASE_URL + attendance.getSnapOut();
                    Glide.with(binding.getRoot().getContext()).load(outImage).placeholder(R.drawable.loading_image).into(binding.employeeAttendanceProfileEndMeterImage);

                    binding.employeeAttendanceProfileEndMeterImage.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("IMAGE", outImage);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
                    });
                case 1:
                    binding.attendanceProfileInLayout.setVisibility(View.VISIBLE);
                    String inImage = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                    Glide.with(binding.getRoot().getContext()).load(inImage).placeholder(R.drawable.loading_image).into(binding.employeeAttendanceProfileStartMeterImage);

                    binding.employeeAttendanceProfileStartMeterImage.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("IMAGE", inImage);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
                    });
                    break;
                default:
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    navigateUp();
            }
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    public void updateAttendance() {
        Attendance attendance = binding.getAttendance();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "attendance/updateattByEmp.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", attendance.getId());
        hashMap.put("empId", attendance.getEmpId());
        hashMap.put("start_reading", attendance.getStartMeter());
        hashMap.put("end_reading", attendance.getEndMeter());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();

            if (response != null) {
                try {
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}