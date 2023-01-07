package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.SuperEmployeeHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeHomeBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class SuperEmployeeHomeFragment extends Fragment {

    private FragmentSuperEmployeeHomeBinding binding;
    private SuperEmployeeViewModel viewModel;

    public SuperEmployeeHomeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_employee_home, container, false);

        binding.superEmployeeHomeCardAddExpense.setOnClickListener(this::navigateToAddExpense);
        binding.superEmployeeHomeCardAddLeave.setOnClickListener(this::navigateToAddLeave);
        binding.superEmployeeHomeCardAddOrder.setOnClickListener(this::navigateToAddOrder);
        binding.superEmployeeHomeCardAddClient.setOnClickListener(this::navigateToAddClient);
        binding.superEmployeeHomeCardMap.setOnClickListener(this::navigateToMap);

        setTabLayout();

        binding.superEmployeeHomeViewFab.setOnClickListener(view -> {
            if (binding.superEmployeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.superEmployeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.superEmployeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    private void navigateToMap(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_superEmployeeMapFragment);
    }

    private void navigateToAddClient(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddClientFragment);
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddLeaveFragment);
    }

    private void navigateToAddExpense(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddExpenseFragment);
    }

    private void setTabLayout() {
        SuperEmployeeHomeFragmentStateAdapter adapter = new SuperEmployeeHomeFragmentStateAdapter(getActivity());
        binding.superEmployeeHomeViewPager.setAdapter(adapter);
        binding.superEmployeeHomeViewPager.setCurrentItem(0);
        new TabLayoutMediator(binding.superEmployeeHomeTabLayout, binding.superEmployeeHomeViewPager,
                (tab, position) -> {

                    switch (position) {
                        case 0:
                            tab.setText("My Clients");
                            break;
                        case 1:
                            tab.setText("My Orders");
                            break;
                        case 2:
                            tab.setText("My Expenses");
                            break;
                        case 3:
                            tab.setText("My Leaves");
                            break;
                        case 4:
                            tab.setText("My Attendance");
                            break;
                        case 5:
                            tab.setText("My Employees");
                            break;
                    }
                }).attach();
    }
}