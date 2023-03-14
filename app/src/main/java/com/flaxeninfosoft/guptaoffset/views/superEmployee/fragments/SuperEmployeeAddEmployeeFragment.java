package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

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
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeAddEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class SuperEmployeeAddEmployeeFragment extends Fragment {

    private SuperEmployeeViewModel viewModel;
    private FragmentSuperEmployeeAddEmployeeBinding binding;

    private ProgressDialog progressDialog;

    public SuperEmployeeAddEmployeeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_employee_add_employee, container, false);
        binding.setEmployee(new Employee());

        ((AdminMainActivity) requireActivity()).setupActionBar(binding.superEmployeeAddEmployeeToolbar, "Add Employee");

        binding.superEmployeeAddEmployeeBtn.setOnClickListener(this::onClickAddButton);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Employee...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (!s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddButton(View view) {
        if (isValidInput()) {
            progressDialog.show();
            try {
                viewModel.addEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), isAdded -> {
                    if (isAdded) {
                        progressDialog.dismiss();
                        clearErrors();
                        navigateUp();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

       private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
     }

        private void clearErrors() {
            binding.superEmployeeAddEmployeeName.setError(null);
            binding.superEmployeeAddEmployeeEmail.setError(null);
            binding.superEmployeeAddEmployeePassword.setError(null);
            binding.superEmployeeAddEmployeeDailyAllowance.setError(null);
            binding.superEmployeeAddEmployeePhone.setError(null);
            binding.superEmployeeAddEmployeeSalary.setError(null);
            binding.superEmployeeAddEmployeeFirm.setError(null);
            binding.superEmployeeAddEmployeeArea.setError(null);
        }

    private boolean isValidInput() {
        Employee employee = binding.getEmployee();

        if (employee.getName() == null || employee.getName().trim().isEmpty()){
            Toast.makeText(getContext(), "Name required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()){
            Toast.makeText(getContext(), "Email required", Toast.LENGTH_SHORT).show();;
            return false;
        }

        if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()){
            Toast.makeText(getContext(), "Password required", Toast.LENGTH_SHORT).show();;
            return false;
        }

        if (employee.getArea() == null || employee.getArea().trim().isEmpty()){
            Toast.makeText(getContext(), "Area required", Toast.LENGTH_SHORT).show();;
            return false;
        }

        if (employee.getDailyAllowance() == null || employee.getDailyAllowance().trim().isEmpty()){
            Toast.makeText(getContext(), "Daily allowance required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int da = Integer.parseInt(employee.getDailyAllowance());
            if (da<0){
                throw new Exception();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Enter valid daily allowance", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getSalary() == null || employee.getSalary().trim().isEmpty()){
            Toast.makeText(getContext(), "Salary required", Toast.LENGTH_SHORT).show();;
            return false;
        }

        try {
            int da = Integer.parseInt(employee.getSalary());
            if (da<0){
                throw new Exception();
            }
        }catch (Exception e){
            Toast.makeText(getContext(), "Enter valid Salary", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}