package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
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

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeApplyLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.sql.Date;
import java.util.Calendar;

public class EmployeeApplyLeaveFragment extends Fragment {

    private FragmentEmployeeApplyLeaveBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;

    public EmployeeApplyLeaveFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_apply_leave, container, false);
        binding.setLeave(new Leave());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Applying..");
        progressDialog.setMessage("Applying for the leave.");
        progressDialog.setCancelable(false);

        binding.employeeApplyLeaveSubmitBtn.setOnClickListener(this::onClickSubmit);

        binding.employeeApplyLeaveDateFromTv.setOnClickListener(this::selectFromDate);
        binding.employeeApplyLeaveDateToTv.setOnClickListener(this::selectToDate);

        return binding.getRoot();
    }

    private void selectFromDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            Date date = (Date) calendar.getTime();

            binding.getLeave().setFromDate(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void selectToDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            Date date = (Date) calendar.getTime();

            binding.getLeave().setToDate(date);
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void onClickSubmit(View view) {
        if (isValid()) {
            progressDialog.show();
            viewModel.addLeave(binding.getLeave()).observe(getViewLifecycleOwner(), isSuccess -> {
                progressDialog.dismiss();
                if (isSuccess) {
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValid() {
        Leave leave = binding.getLeave();
        clearErrors();

        if (leave.getFromDate() == null) {
            Toast.makeText(getContext(), "Select leave from date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (leave.getToDate() == null) {
            Toast.makeText(getContext(), "Select leave to date.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (leave.getMessage() == null || leave.getMessage().isEmpty()) {
            binding.employeeApplyLeaveMessageTil.setError("Enter message");
            return false;
        }

        if (leave.getSubject() == null || leave.getSubject().isEmpty()) {
            binding.employeeApplyLeaveSubjectTil.setError("Enter subject");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.employeeApplyLeaveSubjectTil.setError(null);
        binding.employeeApplyLeaveMessageTil.setError(null);
    }
}