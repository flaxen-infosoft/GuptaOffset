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
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddDealerBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;


public class EmployeeAddDealerFragment extends Fragment {

    private FragmentEmployeeAddDealerBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAddDealerFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_dealer, container, false);
        binding.setDealer(new Dealer());

        binding.employeeAddDealerBtn.setOnClickListener(this::onCLickAddDealer);

        return binding.getRoot();
    }

    private void onCLickAddDealer(View view) {
        clearErrors();

        if (isValidFields()) {
            viewModel.addDealer(binding.getDealer()).observe(getViewLifecycleOwner(), b->{
                if (b){
                    navigateUp();
                }
            });
        }

    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        if (binding.getDealer().getName() == null || binding.getDealer().getName().trim().isEmpty()) {
            binding.employeeAddDealerName.setError("Enter name");
            return false;
        }

        if (binding.getDealer().getContact() == null || binding.getDealer().getContact().trim().isEmpty()) {
            binding.employeeAddDealerContact.setError("Enter contact number");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddDealerName.setError(null);
        binding.employeeAddDealerContact.setError(null);
    }

}