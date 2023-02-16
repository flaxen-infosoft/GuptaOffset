package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAddSuperEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

import java.util.Arrays;
import java.util.List;

public class AdminAddSuperEmployeeFragment extends Fragment {

    private AdminViewModel viewModel;
    private FragmentAdminAddSuperEmployeeBinding binding;

    public AdminAddSuperEmployeeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_super_employee, container, false);
        binding.setEmployee(new Employee());

        binding.adminAddSuperEmployeeFirmAutocomplete.setAdapter(
                new ArrayAdapter<>(getContext(), R.layout.layout_dropdown, Constants.FIRMS)
        );

        binding.adminAddSuperEmployeeBtn.setOnClickListener(this::onClickAddButton);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (!s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddButton(View view) {
        if(isValidInput()){
            viewModel.addSuperEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), isAdded ->{
                if(isAdded){
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
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

        List<String> firms = Arrays.asList(Constants.FIRMS);
        if (!firms.contains(employee.getFirm().trim().toUpperCase())){
            Toast.makeText(getContext(), "Enter valid firm", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}