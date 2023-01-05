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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllSuperEmployeesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllSuperEmployeesFragment extends Fragment {

    private AdminMainViewModel viewModel;
    private FragmentAdminAllSuperEmployeesBinding binding;

    public AdminAllSuperEmployeesFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_super_employees, container, false);

        binding.adminAllSuperEmployeesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllSuperEmployees().observe(getViewLifecycleOwner(), this::setSuperEmployees);

        return binding.getRoot();
    }

    private void setSuperEmployees(List<Employee> employees) {
        EmployeeRecyclerAdapter adapter = new EmployeeRecyclerAdapter(employees, new EmployeeRecyclerAdapter.SingleEmployeeCardOnClickListener() {
            @Override
            public void onClickCard(Employee employee) {
                Bundle bundle = new Bundle();
                bundle.putLong(getString(R.string.key_employee_id), employee.getId());
//                Navigation.findNavController(binding.getRoot()).navigate();
            }
        });

        binding.adminAllSuperEmployeesRecycler.setAdapter(adapter);
    }
}