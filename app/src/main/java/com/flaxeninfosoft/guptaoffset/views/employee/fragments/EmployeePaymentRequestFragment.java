package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeePaymentRequestBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.CustomDialogFragment;

public class EmployeePaymentRequestFragment extends Fragment {

    private FragmentEmployeePaymentRequestBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeePaymentRequestFragment()    {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_employee_payment_request,container,false);
        binding.setPayment(new PaymentRequest());
        binding.employeeAddPaymentBtn.setOnClickListener(this::onClickPayment);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Add Payment...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickPayment(View view) {
            clearErrors();

        if(validateForm()){
                progressDialog.show();
                viewModel.addPayment(binding.getPayment()).observe(getViewLifecycleOwner(),b->{
                    if(b){
                        progressDialog.dismiss();
                        navigateUp();
                    }
                });
            }
    }

    private boolean validateForm() {
        try{
            if (Integer.parseInt(binding.getPayment().getAmount())<0) {
                binding.employeeAddPayment.setError("Enter valid amount");
                return false;
            }
        }catch (Exception e){
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
}