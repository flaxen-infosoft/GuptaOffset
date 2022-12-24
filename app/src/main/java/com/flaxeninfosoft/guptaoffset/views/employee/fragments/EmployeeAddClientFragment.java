package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddClientBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddClientFragment extends Fragment{

    private FragmentEmployeeAddClientBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAddClientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_employee_add_client,container,false);
        binding.setClient(new Client());

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(),this::toastMessage);
        binding.empolyeeAddClientRegisterBtn.setOnClickListener(this::registerClient);

        return binding.getRoot();
    }

    private void registerClient(View view) {
        if(isValidateForm()){
            //TODO
        }
    }

    private boolean isValidateForm() {

        if (binding.getClient().getName().isEmpty()) {
            binding.employeeAddClientName.setError("Name is required");
            binding.employeeAddClientName.requestFocus();
            return false;
        }
        if (binding.getClient().getOrgName().isEmpty()) {
            binding.employeeAddClientOrganisationName.setError("Address is required");
            binding.employeeAddClientOrganisationName.requestFocus();
            return false;
        }
        if (binding.getClient().getAddress().isEmpty()) {
            binding.employeeAddClientAddress.setError("Domain is required");
            binding.employeeAddClientAddress.requestFocus();

            return false;
        }
        if (binding.getClient().getContactNo().toString().isEmpty()) {
            binding.employeeAddClientContact.setError("SubDomain is required");
            binding.employeeAddClientContact.requestFocus();
            return false;
        }
        return true;

    }

    private void toastMessage(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}