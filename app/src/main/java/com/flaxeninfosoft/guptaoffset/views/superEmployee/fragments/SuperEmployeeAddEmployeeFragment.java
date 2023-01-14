package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeAddEmployeeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

public class SuperEmployeeAddEmployeeFragment extends Fragment {

    private SuperEmployeeViewModel viewModel;
    private FragmentSuperEmployeeAddEmployeeBinding binding;

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

        binding.superEmployeeAddEmployeeBtn.setOnClickListener(this::onClickAddButton);

        return binding.getRoot();
    }

    private void onClickAddButton(View view) {
        if(isValidInput()){
            viewModel.addEmployee(binding.getEmployee()).observe(getViewLifecycleOwner(), isAdded->{
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
        //TODO
        return true;
    }
}