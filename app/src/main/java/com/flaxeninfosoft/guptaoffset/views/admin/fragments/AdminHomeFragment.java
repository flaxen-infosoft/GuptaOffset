package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

import java.util.List;

public class AdminHomeFragment extends Fragment {

    private FragmentAdminHomeBinding binding;
    private AdminViewModel viewModel;

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

        viewModel.getAllEmployees().observe(getViewLifecycleOwner(), this::onChange);
        binding.adminHomeAddEmployee.setOnClickListener(this::navigateToAddEmployee);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (!s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateToAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAddSuperEmployeeFragment);
    }

    private void onChange(List<Employee> employees) {
        binding.adminHomeRecycler.setAdapter(new EmployeeRecyclerAdapter(employees, this::onCLickEmployeeCard));
    }

    private void onCLickEmployeeCard(Employee employee) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, employee.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminEmployeeActivityFragment, bundle);
    }
}