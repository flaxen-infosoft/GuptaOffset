package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddAttendanceBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;


    private FragmentEmployeeAddAttendanceBinding binding;
    private EmployeeViewModel viewModel;
    ProgressDialog progressDialog;

    public EmployeeAddAttendanceFragment(){

    }

    @Override
    public void onCreate(Bundle savedInsatnceState){
        super.onCreate(savedInsatnceState);
        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_employee_add_attendance,container,false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Getting attendance");
        progressDialog.setCancelable(false);

        binding.employeeAddAttendancePunchBtn.setOnClickListener(this::onClickAttendanceBtn);
        return binding.getRoot();
    }

    private void onClickAttendanceBtn(View view) {
            //TODO
    }
}