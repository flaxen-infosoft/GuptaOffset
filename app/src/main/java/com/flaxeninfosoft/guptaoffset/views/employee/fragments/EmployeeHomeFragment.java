package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeHomeBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeHomeFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeHomeBinding binding;

    public EmployeeHomeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_home, container, false);

        binding.employeeHomeCardAddExpense.setOnClickListener(this::navigateToAddExpense);
        binding.employeeHomeCardAddLeave.setOnClickListener(this::navigateToAddLeave);
        binding.employeeHomeCardAddOrder.setOnClickListener(this::navigateToAddOrder);
        binding.employeeHomeCardMap.setOnClickListener(this::navigateToMap);
        binding.employeeHomeCardAddEod.setOnClickListener(this::navigateToAddEod);
        binding.employeeHomeCardAddClient.setOnClickListener(this::navigateToAddClient);
        binding.employeeHomeCardLeaves.setOnClickListener(this::navigateToLeaves);
        binding.employeeHomeCardExpenses.setOnClickListener(this::navigateToExpenses);
        binding.employeeHomeCardAttendances.setOnClickListener(this::navigateToAttendances);
        binding.employeeHomeCardClients.setOnClickListener(this::navigateToClients);

        return binding.getRoot();
    }

    private void navigateToClients(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAllClientsFragment);
    }

    private void navigateToAttendances(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAttendanceListFragment);
    }

    private void navigateToExpenses(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeExpenseListFragment);
    }

    private void navigateToLeaves(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeLeaveRequestsFragment);
    }

    private void navigateToAddClient(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddClientFragment);
    }

    private void navigateToAddEod(View view) {
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.);
    }

    private void navigateToMap(View view) {
//        Navigation.findNavController(binding.getRoot());
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeApplyLeaveFragment);
    }

    private void navigateToAddExpense(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddExpenseFragment);
    }
}