package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddClientBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddClientFragment extends Fragment {

    private FragmentEmployeeAddClientBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;

    public EmployeeAddClientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_client, container, false);
        binding.setClient(new Client());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding client..");
        progressDialog.setMessage("Please wait.\n Adding client");
        progressDialog.setCancelable(false);


        binding.employeeAddClientRegisterBtn.setOnClickListener(this::registerClient);

        return binding.getRoot();
    }

    private void registerClient(View view) {
        if (isValidateForm()) {
            progressDialog.show();
            viewModel.addClient(binding.getClient()).observe(getViewLifecycleOwner(), isSuccessful -> {
                progressDialog.dismiss();
                if (isSuccessful) {
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidateForm() {

        if (binding.getClient().getName().isEmpty()) {
            binding.employeeAddClientName.setError("Name is required");
            binding.employeeAddClientName.requestFocus();
            return false;
        }
        if (binding.getClient().getOrgName().isEmpty()) {
            binding.employeeAddClientOrganisationName.setError("Organization name is required");
            binding.employeeAddClientOrganisationName.requestFocus();
            return false;
        }
        if (binding.getClient().getAddress().isEmpty()) {
            binding.employeeAddClientAddress.setError("Address is required");
            binding.employeeAddClientAddress.requestFocus();
            return false;
        }
        if (binding.getClient().getContactNo().isEmpty()) {
            binding.employeeAddClientContact.setError("Contact number is required");
            binding.employeeAddClientContact.requestFocus();
            return false;
        }
        if (binding.getClient().getLatitude() == 0d || binding.getClient().getLongitude() == 0d) {
            Toast.makeText(getContext(), "Fetching location, Please wait", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}