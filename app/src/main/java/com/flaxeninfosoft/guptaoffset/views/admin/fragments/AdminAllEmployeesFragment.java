package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllEmployeesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllEmployeesFragment extends Fragment {
    private FragmentAdminAllEmployeesBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAllEmployeesFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_employees, container, false);

        setupRecycler();
        setupSwipeRefresh();

        viewModel.getAllEmployees().observe(getViewLifecycleOwner(), this::updateEmployeeList);

        binding.adminAllEmployeeAddEmployeeFab.setOnClickListener(this::onCLickAddEmployee);

        return binding.getRoot();
    }

    private void onCLickAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminAllEmployeesFragment_to_adminAddEmployeeFragment);
    }

    private void updateEmployeeList(List<Employee> employees) {
        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, this::onClickEmployee);
        binding.adminAllEmployeeRecycler.setAdapter(adapter);
        stopSwipeRefreshing();
    }

    private void stopSwipeRefreshing() {
        binding.adminAllEmployeeSwipeRefresh.setRefreshing(false);
    }

    private void onClickEmployee(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(getString(R.string.key_employee_id), employee.getId());

        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminAllEmployeesFragment_to_employeeProfileFragment, bundle);
    }

    private void setupRecycler() {
        binding.adminAllEmployeeRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setupSwipeRefresh() {
        binding.adminAllEmployeeSwipeRefresh.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        viewModel.getAllEmployees();
    }
}