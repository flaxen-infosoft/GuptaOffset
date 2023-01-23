package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeProfileBinding;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

public class EmployeeProfileFragment extends Fragment {

    private SuperEmployeeViewModel viewModel;
    private FragmentEmployeeProfileBinding binding;


    public EmployeeProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(SuperEmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_profile, container, false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        if (viewModel.getCurrentEmployee().getDesignation().equals(Constants.DESIGNATION_SUPER_EMPLOYEE) || viewModel.getCurrentEmployee().getDesignation().equals(Constants.DESIGNATION_ADMIN)) {
            enableAdminView();
        }

        try {
            long empId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);

            if (empId == -1) {
                setCurrentEmployeeData();
            } else {
                setEmployeeData(empId);
            }

        } catch (Exception e) {
            setCurrentEmployeeData();
        }

        return binding.getRoot();
    }

    private void enableAdminView() {
        binding.employeeProfileUpdateLayout.setVisibility(View.VISIBLE);

        binding.employeeProfileUpdateBtn.setOnClickListener(this::updateEmployee);

        binding.employeeProfileSuspendBtn.setOnClickListener(this::suspendEmployee);
    }

    private void suspendEmployee(View view) {
        viewModel.suspendEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), employee -> {
            if (employee != null) {
                Toast.makeText(getContext(), "Employee suspended", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(binding.getRoot()).navigateUp();
            }
        });
    }

    private void updateEmployee(View view) {
        viewModel.updateEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), employee -> {
            if (employee != null) {
                Toast.makeText(getContext(), "Employee updated", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(binding.getRoot()).navigateUp();
            }
        });
    }

    private void showToast(String s) {
        Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
    }

    private void setEmployeeData(long empId) {
        viewModel.getEmployeeById(empId).observe(getViewLifecycleOwner(), employee -> {
            if (employee == null) {
                Navigation.findNavController(binding.getRoot()).navigateUp();
            } else {
                binding.setEmployee(employee);
            }
        });
    }

    private void setCurrentEmployeeData() {
        binding.setEmployee(viewModel.getCurrentEmployee());
    }
}