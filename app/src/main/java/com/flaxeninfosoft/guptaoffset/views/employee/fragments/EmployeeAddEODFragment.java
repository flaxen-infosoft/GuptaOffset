package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddEODBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;


public class EmployeeAddEODFragment extends Fragment {

    private FragmentEmployeeAddEODBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeeAddEODFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_e_o_d, container, false);

        binding.employeeAddEodBtn.setOnClickListener(this::onClickAddEod);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        return binding.getRoot();
    }

    private void onClickAddEod(View view) {
        clearErrors();
        if (isValidFields()) {
            progressDialog.show();
            viewModel.addEod(binding.getEod()).observe(getViewLifecycleOwner(), b -> {
                if (b) {
                    progressDialog.dismiss();
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        return true;
    }

    private void clearErrors() {
        binding.employeeAddEodSchoolsVisits.setError(null);
        binding.employeeAddEodPetrolExpense.setError(null);
        binding.employeeAddEodOtherExpense.setError(null);
    }
}