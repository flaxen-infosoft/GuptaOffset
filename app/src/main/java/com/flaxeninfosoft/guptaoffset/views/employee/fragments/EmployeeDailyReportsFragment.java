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
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.DailyReportRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeDailyReportsBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;

public class EmployeeDailyReportsFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeDailyReportsBinding binding;

    public EmployeeDailyReportsFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_daily_reports, container, false);

        binding.employeeDailyReportsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            long empId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);

            if (empId==-1){
                viewModel.getCurrentEmployeeAllEods().observe(getViewLifecycleOwner(), this::setEods);
            }else {
                viewModel.getEmployeeAllEods(empId).observe(getViewLifecycleOwner(), this::setEods);
            }
        }catch (Exception e){
            viewModel.getCurrentEmployeeAllEods().observe(getViewLifecycleOwner(), this::setEods);
        }



        return binding.getRoot();
    }

    private void setEods(List<Eod> eods) {

        if (eods == null || eods.isEmpty()){
            binding.employeeDailyReportsRecycler.setVisibility(View.GONE);
            binding.employeeDailyReportsEmptyRecycler.setVisibility(View.VISIBLE);
        }else {
            binding.employeeDailyReportsRecycler.setVisibility(View.VISIBLE);
            binding.employeeDailyReportsEmptyRecycler.setVisibility(View.GONE);
        }

        DailyReportRecyclerAdapter adapter = new DailyReportRecyclerAdapter(eods, eod -> {
            Bundle bundle = new Bundle();
            bundle.putLong(Constants.EOD_ID, eod.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.eodProfileFragment, bundle);
        });

        binding.employeeDailyReportsRecycler.setAdapter(adapter);
    }
}