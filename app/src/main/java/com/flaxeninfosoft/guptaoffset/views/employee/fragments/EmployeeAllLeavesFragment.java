package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.LeaveRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllLeavesBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;

public class EmployeeAllLeavesFragment extends Fragment {

    private FragmentEmployeeAllLeavesBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAllLeavesFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_leaves, container, false);
        binding.employeeLeaveRequestsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getCurrentEmployeeLeaves().observe(getViewLifecycleOwner(), this::updateLeaves);

        return binding.getRoot();
    }

    private void updateLeaves(List<Leave> leaveList) {
        LeaveRecyclerAdapter adapter = new LeaveRecyclerAdapter(leaveList, new LeaveRecyclerAdapter.SingleLeaveCardOnClickListener() {
            @Override
            public void onClickCard(Leave leave) {
                //TODO on click card
            }
        });

        binding.employeeLeaveRequestsRecycler.setAdapter(adapter);

    }
}