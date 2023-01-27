package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddOrderBinding;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;

public class EmployeeAddOrderFragment extends Fragment {

    private FragmentEmployeeAddOrderBinding binding;
    private EmployeeViewModel viewModel;

    private Uri image;
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
        progressDialog.setTitle("Adding Order...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        binding.employeeAddOrderImage.setOnClickListener(this::chooseImage);

        binding.employeeAddOrderBtn.setOnClickListener(this::onClickAddOrder);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();

    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddOrder(View view) {
        if (image == null) {
            Toast.makeText(getContext(), "Please add image.", Toast.LENGTH_SHORT).show();
            return;
        }

        Location location = viewModel.getCurrentEmployeeLocation().getValue();
        if (location.getLongitude()==0d || location.getLatitude()==0d){
            Toast.makeText(getContext(),"Fetching location.", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.show();

        try {
            viewModel.addOrder(binding.getOrder(), image).observe(getViewLifecycleOwner(), b -> {
                if (b) {
                    progressDialog.dismiss();
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
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            mLauncher.launch(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Glide.with(getContext()).load(bitmap).into(binding.employeeAddOrderImage);

                        image = FileEncoder.getImageUri(getContext(), bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


}