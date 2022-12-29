package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddClientBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddOrderBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddOrderFragment extends Fragment {

    private FragmentEmployeeAddOrderBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;


    public EmployeeAddOrderFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_order, container, false);
        binding.setOrder(new Order());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Order..");
        progressDialog.setMessage("Please wait.\n Adding Order");
        progressDialog.setCancelable(false);

        binding.fragmentEmployeeAddOrderImage.setOnClickListener(this::chooseImage);

        viewModel.getImageUri().observe(getViewLifecycleOwner(), this::updateImage);

        binding.employeeAddEODBtn.setOnClickListener(this::registerOrder);
        return binding.getRoot();

    }

    private void registerOrder(View view) {
        if (isValidateForm()) {
            progressDialog.show();
            viewModel.addOrder(binding.getOrder()).observe(getViewLifecycleOwner(), isSuccessful -> {
                progressDialog.dismiss();
                if (isSuccessful) {
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidateForm() {
        if (binding.getOrder().getName()==null || binding.getOrder().getName().isEmpty()  ) {
            binding.employeeAddOrderName.setError("Name is required");
            binding.employeeAddOrderName.requestFocus();
            return false;
        }
        if (binding.getOrder().getEmail()==null || binding.getOrder().getEmail().isEmpty()) {
            binding.employeeAddOrderEmail.setError("Order is required");
            binding.employeeAddOrderEmail.requestFocus();
            return false;
        }
        if (binding.getOrder().getAddress()==null || binding.getOrder().getAddress().isEmpty()) {
            binding.employeeAddOrderAddress.setError("Address is required");
            binding.employeeAddOrderAddress.requestFocus();
            return false;
        }
        if (binding.getOrder().getContact()==0) {
            binding.employeeAddOrderContact.setError("Contact is required");
            binding.employeeAddOrderContact.requestFocus();
            return false;
        }
        return true;
    }

    private void updateImage(Uri uri) {
        Glide.with(getContext()).load(uri).into(binding.fragmentEmployeeAddOrderImage);
    }

    public void chooseImage(View view) {
        Intent pickImageIntent = new Intent(Intent.ACTION_PICK);

        pickImageIntent.setType("image/*");

        mLauncher.launch(pickImageIntent);
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    viewModel.onImagePickerResult(result.getResultCode(),result);
                }
            });


}