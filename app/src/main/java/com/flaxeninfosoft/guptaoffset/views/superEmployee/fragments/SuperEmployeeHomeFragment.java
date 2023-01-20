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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.SuperEmployeeHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

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
        binding.superEmployeeHomeCardAddEmployee.setOnClickListener(this::navigateToAddEmployee);

        binding.superEmployeeHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getCurrentEmployeeHistory().observe(getViewLifecycleOwner(), this::setEmployeeHistory);

        binding.superEmployeeHomeViewFab.setOnClickListener(view -> {
            if (binding.superEmployeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.superEmployeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.superEmployeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

        return binding.getRoot();
    }

    private void setEmployeeHistory(List<EmployeeHistory> historyList) {
        EmployeeHomeRecyclerAdapter adapter = new EmployeeHomeRecyclerAdapter(historyList, new EmployeeHomeRecyclerAdapter.EmployeeHomeClickListener() {
            @Override
            public void onClickCard(Attendance attendance) {
                //TODO
            }

            @Override
            public void onClickCard(Leave leave) {

            }

            @Override
            public void onClickCard(School school) {

            }

            @Override
            public void onClickCard(Dealer dealer) {

            }

            @Override
            public void onClickCard(Order order) {

            }

            @Override
            public void onClickCard(Eod eod) {

            }

            @Override
            public void onCLickCard(Employee employee) {

            }
        });
        binding.superEmployeeHomeRecycler.setAdapter(adapter);
    }

    private void navigateToAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_superEmployeeAddEmployeeFragment);
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