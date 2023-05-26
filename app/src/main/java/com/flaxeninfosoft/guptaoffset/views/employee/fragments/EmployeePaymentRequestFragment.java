package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeePaymentRequestBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.CustomDialogFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EmployeePaymentRequestFragment extends Fragment {

    private FragmentEmployeePaymentRequestBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeePaymentRequestFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_payment_request, container, false);
        binding.setPayment(new PaymentRequest());
        binding.employeeAddPaymentBtn.setOnClickListener(this::onClickPayment);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Add Payment...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);
        getPaymentRequestIsAvailableForToday(viewModel.getCurrentEmployee().getId());


        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickPayment(View view) {
        clearErrors();

        if (validateForm()) {
            progressDialog.show();
            viewModel.addPayment(binding.getPayment()).observe(getViewLifecycleOwner(), b -> {
                if (b) {
                    progressDialog.dismiss();
                    navigateUp();
                }
            });
        }
    }

    private boolean validateForm() {
        try {
            if (Integer.parseInt(binding.getPayment().getAmount()) < 0) {
                binding.employeeAddPayment.setError("Enter valid amount");
                return false;
            }
        } catch (Exception e) {
            binding.employeeAddPayment.setError("Enter valid amount");
            return false;
        }

        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }


    private void clearErrors() {
        binding.employeeAddPayment.setError(null);
    }

    private void getPaymentRequestIsAvailableForToday(Long empId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = ApiEndpoints.BASE_URL + "payment/paymentRequestsubmitByempId.php\n";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("schoolCount", response.toString());

            if (response != null) {
                try {

                    String status = response.get("submited_status").toString();
                    int status_value = Integer.parseInt(status);

                    if (status_value == 1) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.payment_request_already_submitted_dialog);
                        dialog.setCancelable(false);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button button = dialog.findViewById(R.id.okButton);
                        button.setOnClickListener(view -> {
                            dialog.dismiss();
                            navigateUp();
                        });
                        dialog.show();
                    } else if (status_value == 0) {
                        //do nothingn
                    } else {
                        //do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }
}