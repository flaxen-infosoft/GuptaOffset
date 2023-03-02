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
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeAllEmployeesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
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
        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (!s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void setEmployees(List<Employee> employees) {

        if (employees==null || employees.isEmpty()){
            binding.superEmployeeAllEmployeesEmptyRecycler.setVisibility(View.VISIBLE);
            binding.superEmployeeAllEmployeesRecycler.setVisibility(View.GONE);
        }else {
            binding.superEmployeeAllEmployeesEmptyRecycler.setVisibility(View.GONE);
            binding.superEmployeeAllEmployeesRecycler.setVisibility(View.VISIBLE);
        }

        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeAllEmployeesFragment_to_adminEmployeeActivityFragment2, bundle);
            }

            @Override
            public boolean onLongClickCard(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.adminEditEmployeeFragment, bundle);
                return true;
            }
        });

        binding.superEmployeeAllEmployeesRecycler.setAdapter(adapter);
    }
}