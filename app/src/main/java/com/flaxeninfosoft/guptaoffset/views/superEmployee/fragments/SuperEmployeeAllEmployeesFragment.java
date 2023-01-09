package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeAllEmployeesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

import java.util.List;

public class SuperEmployeeAllEmployeesFragment extends Fragment {

    private SuperEmployeeViewModel viewModel;
    private FragmentSuperEmployeeAllEmployeesBinding binding;

    public SuperEmployeeAllEmployeesFragment() {
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

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_employee_all_employees, container, false);

        binding.superEmployeeAllEmployeesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getCurrentSuperEmployeeEmployees().observe(getViewLifecycleOwner(), this::setEmployees);

        return binding.getRoot();
    }

    private void setEmployees(List<Employee> employees) {
        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putLong(getString(R.string.key_employee_id), employee.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeProfileFragment, bundle);
            }
        });

        binding.superEmployeeAllEmployeesRecycler.setAdapter(adapter);
    }
}