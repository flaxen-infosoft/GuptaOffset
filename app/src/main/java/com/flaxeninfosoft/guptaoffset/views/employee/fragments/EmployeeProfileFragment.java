package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeProfileBinding;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeProfileBinding binding;


    public EmployeeProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_profile, container, false);

        try {
            long empId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);

            if (empId == -1){
                setCurrentEmployeeData();
            }else{
                setEmployeeData(empId);
            }

        }catch (Exception e){

        }

        return binding.getRoot();
    }

    private void setEmployeeData(long empId) {
    }

    private void setCurrentEmployeeData() {
        
    }
}