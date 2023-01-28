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
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

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

        binding.adminAddSuperEmployeeBtn.setOnClickListener(this::onClickAddButton);

        return binding.getRoot();
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
        return true;
    }
}