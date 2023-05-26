package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.sql.Date;
import java.util.Calendar;

public class EmployeeAddLeaveFragment extends Fragment {

    private FragmentEmployeeAddLeaveBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;

    public EmployeeAddLeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_leave, container, false);
        binding.setLeave(new Leave());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Applying..");
        progressDialog.setMessage("Applying for the leave.");
        progressDialog.setCancelable(false);

        binding.employeeApplyLeaveSubmitBtn.setOnClickListener(this::onClickSubmit);

        binding.employeeApplyLeaveDateFromTv.setOnClickListener(this::selectFromDate);
        binding.employeeApplyLeaveDateToTv.setOnClickListener(this::selectToDate);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void selectFromDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            Date date = new Date(calendar.getTime().getTime());

            binding.getLeave().setFromDate(date.toString());
            binding.employeeApplyLeaveDateFromTv.setText(date.toString());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void selectToDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            Date date = new Date(calendar.getTime().getTime());

            binding.getLeave().setToDate(date.toString());
            binding.employeeApplyLeaveDateToTv.setText(date.toString());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void onClickSubmit(View view) {
        if (isValid()) {
            progressDialog.show();
            binding.employeeApplyLeaveSubmitBtn.setEnabled(false);
            viewModel.addLeave(binding.getLeave()).observe(getViewLifecycleOwner(), isSuccess -> {
                progressDialog.dismiss();
                if (isSuccess) {
                    navigateUp();
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.data_submit_dialog_layout);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button button = dialog.findViewById(R.id.okButton);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();
                } else {
                    binding.employeeApplyLeaveSubmitBtn.setEnabled(true);
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

        return true;
    }

    private void clearErrors() {
        binding.employeeApplyLeaveMessageTil.setError(null);
    }
}