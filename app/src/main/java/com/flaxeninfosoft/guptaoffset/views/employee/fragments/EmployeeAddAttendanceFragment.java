package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddAttendanceBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;


public class EmployeeAddAttendanceFragment extends Fragment {
    private FragmentEmployeeAddAttendanceBinding binding;
    private EmployeeViewModel viewModel;
    ProgressDialog progressDialog;

    public EmployeeAddAttendanceFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_attendance, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading.....");
        progressDialog.setMessage("Getting Attendance");
        progressDialog.setCancelable(false);

        viewModel.fetchEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);
    }
}