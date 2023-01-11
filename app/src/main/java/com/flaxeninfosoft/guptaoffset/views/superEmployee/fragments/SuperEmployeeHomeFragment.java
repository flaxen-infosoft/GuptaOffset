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

        binding.superEmployeeHomeCardAddAttendance.setOnClickListener(this::navigateToAddAttendance);
        binding.superEmployeeHomeCardAddLeave.setOnClickListener(this::navigateToAddLeave);
        binding.superEmployeeHomeCardAddSchool.setOnClickListener(this::navigateToAddSchool);
        binding.superEmployeeHomeCardAddShop.setOnClickListener(this::navigateToAddDealer);
        binding.superEmployeeHomeCardAddOrder.setOnClickListener(this::navigateToAddOrder);
        binding.superEmployeeHomeCardPaymentRequest.setOnClickListener(this::navigateToPaymentRequest);
        binding.superEmployeeHomeCardAddEod.setOnClickListener(this::navigateToAddEod);
        binding.superEmployeeHomeCardMyMap.setOnClickListener(this::navigateToMap);

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

    private void navigateToAddEod(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddEODFragment);
    }

    private void navigateToPaymentRequest(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeePaymentRequestFragment);
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToAddDealer(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddDealerFragment);
    }

    private void navigateToAddSchool(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddSchoolFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddLeaveFragment);
    }

    private void navigateToAddAttendance(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddAttendanceFragment);
    }

}