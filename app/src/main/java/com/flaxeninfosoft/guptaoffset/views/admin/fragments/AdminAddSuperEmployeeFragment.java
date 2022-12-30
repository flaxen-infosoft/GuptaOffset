package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAddSuperEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

public class AdminAddSuperEmployeeFragment extends Fragment {

    private FragmentAdminAddSuperEmployeeBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAddSuperEmployeeFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_add_super_employee, container, false);
        binding.setEmployee(new Employee());

        binding.adminAddSuperEmployeeAddBtn.setOnClickListener(this::onClickAddSuperEmployeeAddBtn);

        return binding.getRoot();
    }

    private void onClickAddSuperEmployeeAddBtn(View view) {
        clearErrors();
        binding.adminAddSuperEmployeeAddBtn.setEnabled(false);
        if (isValidEntries()) {
            viewModel.addSuperEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), isSuccessful -> {
                if (isSuccessful) {
                    navigateUp();
                } else {
                    binding.adminAddSuperEmployeeAddBtn.setEnabled(true);
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void clearErrors() {
        //TODO clear ui errors
    }

    private boolean isValidEntries() {
        //TODO add validation
        return true;
    }
}