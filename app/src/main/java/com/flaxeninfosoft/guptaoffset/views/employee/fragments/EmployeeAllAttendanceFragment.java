package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EodRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllAttendancesBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;


public class EmployeeAllAttendanceFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeAllAttendancesBinding binding;

    public EmployeeAllAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_attendances, container, false);

        viewModel.getCurrentEmployeeAllEods().observe(getViewLifecycleOwner(), this::updateAttendanceList);

        return binding.getRoot();
    }

    private void updateAttendanceList(List<Eod> list) {
        EodRecyclerAdapter adapter = new EodRecyclerAdapter(list, new EodRecyclerAdapter.SingleEodCardOnClickListener() {
            @Override
            public void onClickCard(Eod eod) {
                //TODO
            }
        });

        binding.employeeAttendanceListRecycler.setAdapter(adapter);
    }
}