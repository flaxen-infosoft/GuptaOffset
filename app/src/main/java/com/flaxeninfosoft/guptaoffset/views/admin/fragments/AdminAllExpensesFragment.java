package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.ExpenseRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllExpensesBinding;
import com.flaxeninfosoft.guptaoffset.models.Expense;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllExpensesFragment extends Fragment {
    private FragmentAdminAllExpensesBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAllExpensesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_expenses, container, false);

        setupRecycler();
        setupSwipeRefresh();

        viewModel.getAllExpenses().observe(getViewLifecycleOwner(), this::updateExpenseList);

        return binding.getRoot();
    }

    private void updateExpenseList(List<Expense> expenses) {
        ExpenseRecyclerAdapter adapter = new ExpenseRecyclerAdapter(expenses, new ExpenseRecyclerAdapter.SingleExpenseCardClickListener() {
            @Override
            public void onClickCard(Expense expense) {
                onClickExpense(expense);
            }
        });
        binding.adminAllExpensesRecycler.setAdapter(adapter);
        stopSwipeRefreshing();
    }

    private void stopSwipeRefreshing() {
        binding.adminAllExpensesSwipeRefresh.setRefreshing(false);
    }

    private void onClickExpense(Expense expense) {
        //TODO
    }

    private void setupRecycler() {
        binding.adminAllExpensesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSwipeRefresh() {
        binding.adminAllExpensesSwipeRefresh.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        viewModel.getAllExpenses();
    }

}