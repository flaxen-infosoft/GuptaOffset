package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddAttendanceBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddAttendanceFragment extends Fragment {

    private FragmentEmployeeAddAttendanceBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;
    private Uri uri;

    public EmployeeAddAttendanceFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_attendance, container, false);
        binding.setAttendance(new Attendance());


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewModel.getCurrentEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);

        return binding.getRoot();
    }

    private void setAttendance(Attendance attendance) {

        switch (attendance.getPunchStatus()) {
            case 0:
                binding.employeeAddAttendanceStartMeter.setEnabled(true);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceEndMeter.setEnabled(false);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.GONE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.GONE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

                binding.employeeAddAttendanceBtn.setEnabled(true);
                binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                    clearErrors();
                    if (uri == null) {
                        Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        long reading = Long.getLong(binding.employeeAddAttendanceStartMeter.getEditText().getText().toString());
                        punch(reading, uri);
                    } catch (Exception e) {
                        binding.employeeAddAttendanceStartMeter.setError("Enter valid reading.");
                        return;
                    }
                });

                break;

            case 1:
                binding.employeeAddAttendanceStartMeter.setEnabled(false);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceEndMeter.setEnabled(true);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

                binding.employeeAddAttendanceBtn.setEnabled(true);
                binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                    clearErrors();
                    if (uri == null) {
                        Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    try {
                        long reading = Long.getLong(binding.employeeAddAttendanceEndMeter.getEditText().getText().toString());
                        punch(reading, uri);
                    } catch (Exception e) {
                        binding.employeeAddAttendanceEndMeter.setError("Enter valid reading.");
                        return;
                    }
                });
                break;
            case 2:
                binding.employeeAddAttendanceStartMeter.setEnabled(false);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceEndMeter.setEnabled(false);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceBtn.setEnabled(false);
                break;
            default:
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                navigateUp();
        }

        progressDialog.dismiss();
    }

    private void punch(long reading, Uri uri) {
        viewModel.punchAttendance(reading, uri).observe(getViewLifecycleOwner(), attendance -> {
            if (attendance != null) {
                navigateUp();
            }
        });
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void clearErrors() {
        binding.employeeAddAttendanceStartMeter.setError(null);
        binding.employeeAddAttendanceEndMeter.setError(null);
        binding.employeeAddAttendanceTotalMeter.setError(null);
    }
}