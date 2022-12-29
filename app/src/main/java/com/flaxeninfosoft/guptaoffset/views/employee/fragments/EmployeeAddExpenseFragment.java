package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddExpenseBinding;
import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddExpenseFragment extends Fragment {

    private FragmentEmployeeAddExpenseBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeeAddExpenseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_expense, container, false);
        binding.setExpense(new Expense());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Expense..");
        progressDialog.setMessage("Please wait.\n Adding expenses");
        progressDialog.setCancelable(false);

        binding.employeeAddExpenseBtn.setOnClickListener(this::onClickSubmit);

        return binding.getRoot();
    }

    private void onClickSubmit(View view) {
        if (isValidInput()) {
            binding.employeeAddExpenseBtn.setEnabled(false);
            progressDialog.show();
            viewModel.addExpense(binding.getExpense()).observe(getViewLifecycleOwner(), this::onResponse);
        }
    }

    private void onResponse(Boolean isSuccessful) {
        binding.employeeAddExpenseBtn.setEnabled(true);
        progressDialog.dismiss();
        if (isSuccessful) {
            navigateUp();
        }
    }

    private boolean isValidInput() {
        //TODO validate input
        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}