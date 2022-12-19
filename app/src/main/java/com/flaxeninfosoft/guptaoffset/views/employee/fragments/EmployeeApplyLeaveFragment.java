package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeApplyLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeMainViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EmployeeApplyLeaveFragment extends Fragment {

    private FragmentEmployeeApplyLeaveBinding binding;
    private EmployeeMainViewModel viewModel;

    public EmployeeApplyLeaveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeMainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_employee_apply_leave,container,false);
        binding.setLeave(new Leave());

        binding.employeeApplyLeaveSubmitBtn.setOnClickListener(this::onClickSubmit);
        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(),this::showToastMessgae);

        binding.employeeApplyLeaveDateFromTv.setOnClickListener(this::selectFromDate);
        binding.employeeApplyLeaveDateToTv.setOnClickListener(this::selectToDate);

        return  binding.getRoot();
    }

    private void selectFromDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
            String toDate = dateFormat.format(calendar.getTime());
            binding.getLeave().setFromDate(toDate);
            binding.employeeApplyLeaveDateToTv.setText(binding.getLeave().getFromDate());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void selectToDate(View view) {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(getContext(), (datePicker, y, m, d) -> {
            calendar.set(Calendar.YEAR, y);
            calendar.set(Calendar.MONTH, m);
            calendar.set(Calendar.DAY_OF_MONTH, d);

            String myFormat = "dd-MM-yyyy";
            SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
            String toDate = dateFormat.format(calendar.getTime());
            binding.getLeave().setToDate(toDate);
            binding.employeeApplyLeaveDateToTv.setText(binding.getLeave().getToDate());
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void showToastMessgae(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickSubmit(View view) {
        if(isValid()){
            //TODO
        }
    }

    private boolean isValid() {
        //TODO validate from
        return true;
    }
}