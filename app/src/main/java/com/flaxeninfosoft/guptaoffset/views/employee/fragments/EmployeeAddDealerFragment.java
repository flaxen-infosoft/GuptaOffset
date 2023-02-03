package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddDealerBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;


public class EmployeeAddDealerFragment extends Fragment {

    private FragmentEmployeeAddDealerBinding binding;
    private EmployeeViewModel viewModel;
    private Uri image;
    private ProgressDialog progressDialog;

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
        binding.employeeAddDealerImage.setOnClickListener(this::onClickAddImage);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Dealer...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {

                                Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                                Glide.with(getContext()).load(bitmap).into(binding.employeeAddDealerImage);

                                image = FileEncoder.getImageUri(getContext(), bitmap);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );

    private void onCLickAddDealer(View view) {

        if (isValidFields() && isImageAdd()) {
            progressDialog.show();
            try {

                viewModel.addDealer(binding.getDealer(), image).observe(getViewLifecycleOwner(), b -> {
                    if (b) {
                        progressDialog.dismiss();
                        clearErrors();
                        navigateUp();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isImageAdd() {
        if (image == null) {
            Toast.makeText(getContext(), "Please Add Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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