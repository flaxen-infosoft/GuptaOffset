package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;

import java.util.List;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;
    private AdminViewModel viewModel;
    private List<Employee> employeeList;

    public AdminHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false);

        binding.adminHomeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        ((AdminMainActivity) requireActivity()).setupActionBar(binding.adminHomeToolbar, "Admin");

        viewModel.getAllEmployees().observe(getViewLifecycleOwner(), this::onChange);
        binding.adminHomeAddSuperEmployee.setOnClickListener(this::navigateToAddSuperEmployee);
        binding.adminHomeAddEmployee.setOnClickListener(this::navigateToAddEmployee);
        binding.adminHomePaymentRequests.setOnClickListener(this::navigateToPaymentRequests);
        binding.adminHomeAllOrders.setOnClickListener(this::onClickAllOrders);

        binding.adminAddIcon.setOnClickListener(view -> {
            if (binding.adminHomeCard.getVisibility() == View.VISIBLE) {
                binding.adminHomeCard.setVisibility(View.GONE);
            } else {
                binding.adminHomeCard.setVisibility(View.VISIBLE);
            }
        });

        binding.adminHomeSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (employeeList == null || employeeList.isEmpty()) {
                    binding.adminHomeEmptyRecycler.setVisibility(View.VISIBLE);
                    binding.adminHomeRecycler.setVisibility(View.GONE);
                } else {
                    binding.adminHomeEmptyRecycler.setVisibility(View.GONE);
                    binding.adminHomeRecycler.setVisibility(View.VISIBLE);
                }
                EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employeeList, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
                    @Override
                    public void onClickCard(Employee employee) {
                        onCLickEmployeeCard(employee);
                    }

                    @Override
                    public boolean onLongClickCard(Employee employee) {
                        onLongClickEmployeeCard(employee);
                        return false;
                    }
                });
                adapter.getFilter().filter(charSequence.toString());
                binding.adminHomeRecycler.setAdapter(adapter);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.adminHomeSwipeRefresh.setOnRefreshListener(this::getAllEmployees);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void onClickAllOrders(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAllOrderFragment);
    }

    private void getAllEmployees() {
        viewModel.fetchAllEmployees();
        if (binding.adminHomeSwipeRefresh.isRefreshing()) {
            binding.adminHomeSwipeRefresh.setRefreshing(false);
        }
    }

    private void navigateToPaymentRequests(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.pendingPaymentRequestsFragment);
    }

    private void navigateToAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_superEmployeeAddEmployeeFragment);
    }

    private void showToast(String s) {
        if (!s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAddSuperEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAddSuperEmployeeFragment);
    }

    private void onChange(List<Employee> employees) {
        employeeList = employees;
        if (employees == null || employees.isEmpty()) {
            binding.adminHomeEmptyRecycler.setVisibility(View.VISIBLE);
            binding.adminHomeRecycler.setVisibility(View.GONE);
        } else {
            binding.adminHomeEmptyRecycler.setVisibility(View.GONE);
            binding.adminHomeRecycler.setVisibility(View.VISIBLE);
        }
        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                onCLickEmployeeCard(employee);
            }

            @Override
            public boolean onLongClickCard(Employee employee) {
                onLongClickEmployeeCard(employee);
                return false;
            }
        });
        binding.adminHomeRecycler.setAdapter(adapter);
    }

    private void onLongClickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.adminEditEmployeeFragment, bundle);
    }

    private void onCLickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminEmployeeActivityFragment, bundle);
    }
}