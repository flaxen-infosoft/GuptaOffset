package com.flaxeninfosoft.guptaoffset.views.profiles;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeOrderDetailsBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeOrderProfileFragment extends Fragment {

    private FragmentEmployeeOrderDetailsBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeeOrderProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_order_details, container, false);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Long orderId = getArguments().getLong(getString(R.string.key_order_id));

        viewModel.getOrderById(orderId).observe(getViewLifecycleOwner(), this::setOrderDetails);

        binding.employeeOrderDetailsDeleteOrderBtn.setOnClickListener(this::deleteOrder);

        return binding.getRoot();
    }

    private void deleteOrder(View view) {
        //TODO
    }

    private void setOrderDetails(Order order) {
        progressDialog.dismiss();
        if (order == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            binding.setOrder(order);
        }
    }
}