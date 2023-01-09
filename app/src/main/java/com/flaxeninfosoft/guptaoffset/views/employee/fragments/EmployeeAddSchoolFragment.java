package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddDealerBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddOrderBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddSchoolBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;


public class EmployeeAddSchoolFragment extends Fragment {

    private FragmentEmployeeAddSchoolBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAddSchoolFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_school, container, false);
        binding.setSchool(new School());

        binding.employeeAddSchoolBtn.setOnClickListener(this::onClickAddSchool);

        return binding.getRoot();
    }

    private void onClickAddSchool(View view) {
        clearErrors();

        if (isValidFields()) {
            viewModel.addSchool(binding.getSchool()).observe(getViewLifecycleOwner(), b->{
                if (b){
                    navigateUp();
                }
            });
        }

    }
    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        if (binding.getSchool().getName() == null || binding.getSchool().getName().trim().isEmpty()) {
            binding.employeeAddSchoolName.setError("Enter name");
            return false;
        }
        if (binding.getSchool().getStrength() == null || binding.getSchool().getStrength().trim().isEmpty()) {
            binding.employeeAddSchoolStrength.setError("Enter strength");
            return false;
        }
        if (binding.getSchool().getAddress() == null || binding.getSchool().getAddress().trim().isEmpty()) {
            binding.employeeAddSchoolAddress.setError("Enter address");
            return false;
        }

        if (binding.getSchool().getContact() == null || binding.getSchool().getContact().trim().isEmpty()) {
            binding.employeeAddSchoolContact.setError("Enter contact number");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddSchoolName.setError(null);
        binding.employeeAddSchoolStrength.setError(null);
        binding.employeeAddSchoolAddress.setError(null);
        binding.employeeAddSchoolContact.setError(null);
    }

}