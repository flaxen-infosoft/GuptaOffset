package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddOrderBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;

public class EmployeeAddOrderFragment extends Fragment {

    private FragmentEmployeeAddOrderBinding binding;
    private EmployeeViewModel viewModel;

    private Uri image;

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

        binding.employeeAddOrderBtn.setOnClickListener(this::onClickAddOrder);

        return binding.getRoot();

    }

    private void onClickAddOrder(View view) {
        if (image == null) {
            Toast.makeText(getContext(), "Please add image.", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            viewModel.addOrder(binding.getOrder(), image).observe(getViewLifecycleOwner(), b -> {
                if (b) {
                    navigateUp();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
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
                    viewModel.onImagePickerResult(result.getResultCode(), result);
                }
            });


}