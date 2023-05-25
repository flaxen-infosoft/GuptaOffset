package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminEditEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

public class AdminEditEmployeeFragment extends Fragment {

    private FragmentAdminEditEmployeeBinding binding;
    private AdminViewModel viewModel;

    public AdminEditEmployeeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_edit_employee, container, false);
        long empId;

        try {
            empId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);

            if (empId == -1) {
                throw new Exception("Something went wrong.");
            }

            viewModel.getEmployeeById(empId).observe(getViewLifecycleOwner(), this::setEmployee);


        } catch (Exception e) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigateUp();
        }


        return binding.getRoot();

    }

    private void setEmployee(Employee employee) {
        if (employee == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {

            if (!viewModel.getCurrentEmployee().getDesignation().equals(Constants.DESIGNATION_ADMIN)){
                binding.adminEditEmployeeSalary.setVisibility(View.GONE);
            }

            binding.setEmployee(employee);
            binding.adminUpdateEmployeeBtn.setEnabled(true);
            binding.adminUpdateEmployeeBtn.setOnClickListener(view -> {

                if (isValidFields()) {
                    viewModel.updateEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), employee1 -> {
                        Toast.makeText(getContext(), "Employee updated.", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(binding.getRoot()).navigateUp();
                    });
                }
            });

        }
    }

    private boolean isValidFields() {
        Employee employee = binding.getEmployee();

        if (employee.getName() == null || employee.getName().trim().isEmpty()){
            Toast.makeText(getContext(), "Name required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getEmail() == null || employee.getEmail().trim().isEmpty()){
            Toast.makeText(getContext(), "Email required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getPassword() == null || employee.getPassword().trim().isEmpty()){
            Toast.makeText(getContext(), "Password required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getArea() == null || employee.getArea().trim().isEmpty()){
            Toast.makeText(getContext(), "Area required", Toast.LENGTH_SHORT).show();
            return false;
        }

//        if (employee.getDailyAllowance() == null || employee.getDailyAllowance().trim().isEmpty()){
//            Toast.makeText(getContext(), "Daily allowance required", Toast.LENGTH_SHORT).show();
//            return false;
//        }

//        try {
//            int da = Integer.parseInt(employee.getDailyAllowance());
//            if (da<0){
//                throw new Exception();
//            }
//        }catch (Exception e){
//            Toast.makeText(getContext(), "Enter valid daily allowance", Toast.LENGTH_SHORT).show();
//            return false;
//        }

        if (employee.getSalary() == null || employee.getSalary().trim().isEmpty()){
            Toast.makeText(getContext(), "Salary required", Toast.LENGTH_SHORT).show();
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

        if (employee.getDaily_allowance_description1() == null || employee.getDaily_allowance_description1().trim().isEmpty()) {
            Toast.makeText(getContext(), "Minimum 1 DA Description required", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getDailyAllowance1() == null || employee.getDailyAllowance1().trim().isEmpty()) {
            Toast.makeText(getContext(), "Minimum 1 DA Amount required", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            int da = Integer.parseInt(employee.getDailyAllowance1());
            if (da < 0) {
                throw new Exception();
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Enter valid daily allowance", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (employee.getDaily_allowance_description2() == null || employee.getDaily_allowance_description2().isEmpty()) {
            employee.setDaily_allowance_description2("NO Allowance");
        }
        if (employee.getDaily_allowance_description3() == null || employee.getDaily_allowance_description3().isEmpty()) {
            employee.setDaily_allowance_description3("NO Allowance");
        }
        if (employee.getDaily_allowance_description4() == null || employee.getDaily_allowance_description4().isEmpty()) {
            employee.setDaily_allowance_description4("NO Allowance");
        }
        if (employee.getDailyAllowance2() == null || employee.getDailyAllowance2().isEmpty()) {
            employee.setDailyAllowance2("0");
        }
        if (employee.getDailyAllowance3() == null || employee.getDailyAllowance3().isEmpty()) {
            employee.setDailyAllowance3("0");
        }
        if (employee.getDailyAllowance4() == null || employee.getDailyAllowance4().isEmpty()) {
            employee.setDailyAllowance4("0");
        }

        return true;
    }
}