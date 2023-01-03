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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeExpenseProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeExpenseProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeExpenseProfileBinding binding;
    private ProgressDialog progressDialog;


    public EmployeeExpenseProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_expense_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Long expenseId = getArguments().getLong(getString(R.string.key_expense_id));

        viewModel.getExpenseById(expenseId).observe(getViewLifecycleOwner(), this::setExpense);

        return binding.getRoot();
    }

    private void setExpense(Expense expense) {
        progressDialog.dismiss();
        if (expense == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            binding.setExpense(expense);
        }
    }
}