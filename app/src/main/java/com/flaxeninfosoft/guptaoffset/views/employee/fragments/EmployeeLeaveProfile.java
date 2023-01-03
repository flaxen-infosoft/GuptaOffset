package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeLeaveProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeLeaveProfile extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeLeaveProfileBinding binding;
    private ProgressDialog progressDialog;

    public EmployeeLeaveProfile() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_leave_profile, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Long leaveId = getArguments().getLong(getString(R.string.key_leave_id));

        viewModel.getLeaveById(leaveId).observe(getViewLifecycleOwner(), this::setLeave);


        return binding.getRoot();
    }

    private void setLeave(Leave leave) {
        progressDialog.dismiss();
        if (leave == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            binding.setLeave(leave);
        }
    }
}