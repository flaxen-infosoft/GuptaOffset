package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_admin_all_employees,container,false);

        setUpRecycler();
        setUpSwipeRefresh();

        binding.adminAllEmployeeSwipeRefresh.setRefreshing(true);
        viewModel.getAllEmployeeListLiveData().observe(getViewLifecycleOwner(),this::updateEmployee);

        return binding.getRoot();
    }

    private void updateEmployee(List<Employee> employees){
        EmployeeRecyclerAdapter adapter=new EmployeeRecyclerAdapter(employees,this::onClickEmployee);
        binding.adminAllEmployeeRecyclerView.setAdapter(adapter);
        stopSwipeRefreshing();
    }

    private void stopSwipeRefreshing() {
        binding.adminAllEmployeeSwipeRefresh.setRefreshing(false);
    }

    private void onClickEmployee(Employee employee) {
        Bundle bundle=new Bundle();
        bundle.putLong(getString(R.string.key_employee_id),employee.getId());
        getParentFragmentManager().setFragmentResult(getString(R.string.key_employee_id),bundle);

//        TODO
//        Navigation.findNavController(binding.getRoot()).navigate(R.id.admin_all_empolyeeFRa);
    }

    private void setUpRecycler(){
        binding.adminAllEmployeeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void setUpSwipeRefresh(){
        binding.adminAllEmployeeSwipeRefresh.setOnRefreshListener(this::onRefresh);
    }

    private void onRefresh() {
        viewModel.fetchAllEmployees();
    }
}