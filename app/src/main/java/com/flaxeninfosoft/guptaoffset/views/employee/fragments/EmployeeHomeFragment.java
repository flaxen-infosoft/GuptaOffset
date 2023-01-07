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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeHomeBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

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
        binding.employeeHomeCardAddClient.setOnClickListener(this::navigateToAddClient);
        binding.employeeHomeCardMap.setOnClickListener(this::navigateToMap);

//        setTabLayout();

        binding.employeeHomeViewFab.setOnClickListener(view -> {
            if (binding.employeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.employeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.employeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    private void navigateToMap(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeMapFragment);
    }

    private void navigateToAddClient(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddClientFragment);
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddLeaveFragment);
    }

    private void navigateToAddExpense(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeHomeFragment_to_employeeAddExpenseFragment);
    }

//    private void setTabLayout() {
//        EmployeeHomeFragmentStateAdapter adapter = new EmployeeHomeFragmentStateAdapter(getActivity());
//        binding.employeeHomeViewPager.setAdapter(adapter);
//        binding.employeeHomeViewPager.setCurrentItem(0);
//
//        new TabLayoutMediator(binding.employeeHomeTabLayout, binding.employeeHomeViewPager,
//                (tab, position) -> {
//
//                    switch (position) {
//                        case 0:
//                            tab.setText("My Orders");
//                            break;
//                        case 1:
//                            tab.setText("My Leaves");
//                            break;
//                        case 2:
//                            tab.setText("My Expenses");
//                            break;
//                        case 3:
//                            tab.setText("My Clients");
//                            break;
//                        case 4:
//                            tab.setText("My Attendance");
//                            break;
//                        case 5:
//                            tab.setText("My Map");
//                            break;
//                    }
//                }).attach();
//    }
}