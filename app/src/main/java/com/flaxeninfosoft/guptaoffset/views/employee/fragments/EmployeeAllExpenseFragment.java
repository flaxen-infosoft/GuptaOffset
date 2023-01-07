package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllExpensesBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;


public class EmployeeAllExpenseFragment extends Fragment {

    private FragmentEmployeeAllExpensesBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAllExpenseFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_expenses, container, false);
        binding.employeeExpenseListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getCurrentEmployeeExpenses().observe(getViewLifecycleOwner(), this::updateExpenses);

        return binding.getRoot();
    }

    private void updateExpenses(List<Expense> expenses) {
        ExpenseRecyclerAdapter adapter = new ExpenseRecyclerAdapter(expenses, new ExpenseRecyclerAdapter.SingleExpenseCardClickListener() {
            @Override
            public void onClickCard(Expense expense) {
                //TODO
            }
        });

        binding.employeeExpenseListRecycler.setAdapter(adapter);
    }
}