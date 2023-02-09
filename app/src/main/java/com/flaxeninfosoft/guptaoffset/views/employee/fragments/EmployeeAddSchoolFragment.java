package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
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
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddSchoolBinding;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


public class EmployeeAddSchoolFragment extends Fragment {

    private FragmentEmployeeAddSchoolBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeeAddSchoolFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_school, container, false);

        binding.employeeAddSchoolBtn.setOnClickListener(this::onClickAddSchool);
        binding.employeeAddSchoolSpecimenImage.setOnClickListener(this::onClickSpecimenImage);
        binding.employeeAddSchoolHoadingImage.setOnClickListener(this::onClickHoadingImage);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding School...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        binding.setSchool(viewModel.getNewSchool());
        setHoadingImage();
        setSpecimenImage();
        setAddress();

        return binding.getRoot();
    }

    private void setAddress() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLocationAvailability().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(fusedLocationTask -> {
                    Location location = fusedLocationTask.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(EmployeeAddSchoolFragment.this.getContext(), Locale.getDefault());
                            // initialising address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Address currentAddress = addresses.get(0);
                            binding.getSchool().getLocation().setAddress(currentAddress.getAddressLine(0));
                            binding.employeeAddSchoolAddress.getEditText().setText(currentAddress.getAddressLine(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void setHoadingImage() {
        if (viewModel.getNewSchool().getHoadingImageUri() != null)
            Glide.with(getContext()).load(viewModel.getNewSchool().getHoadingImageUri()).into(binding.employeeAddSchoolHoadingImage);

    }

    public void setSpecimenImage() {
        if (viewModel.getNewSchool().getSpecimenImageUri() != null)
            Glide.with(getContext()).load(viewModel.getNewSchool().getSpecimenImageUri()).into(binding.employeeAddSchoolSpecimenImage);

    }

    private void onClickHoadingImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        hoadingImageIntent.launch(intent);
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickSpecimenImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        specimenImageIntent.launch(intent);
    }

    ActivityResultLauncher<Intent> specimenImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Glide.with(getContext()).load(bitmap).into(binding.employeeAddSchoolSpecimenImage);

                        binding.getSchool().setSpecimenImageUri(FileEncoder.getImageUri(getContext(), bitmap));
                        viewModel.setNewSchoolSpecimenImageUri(binding.getSchool().getSpecimenImageUri());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );
    ActivityResultLauncher<Intent> hoadingImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Glide.with(getContext()).load(bitmap).into(binding.employeeAddSchoolHoadingImage);

                        binding.getSchool().setHoadingImageUri(FileEncoder.getImageUri(getContext(), bitmap));
                        viewModel.setNewSchoolHoadingImageUri(binding.getSchool().getHoadingImageUri());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );


    private void onClickAddSchool(View view) {
        if (isValidFields() && isImageAdded()) {

            progressDialog.show();
            try {
                viewModel.addSchool(binding.getSchool()).observe(getViewLifecycleOwner(), b -> {
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

    private boolean isImageAdded() {
        if (binding.getSchool().getSpecimenImageUri() == null) {
            Toast.makeText(getContext(), "Add Specimen Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.getSchool().getHoadingImageUri() == null) {
            Toast.makeText(getContext(), "Add School Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        if (binding.getSchool().getName() == null || binding.getSchool().getName().trim().isEmpty()) {
            binding.employeeAddSchoolName.setError("**Enter name");
            return false;
        }
        if (binding.getSchool().getStrength() == null || binding.getSchool().getStrength().trim().isEmpty()) {
            binding.employeeAddSchoolStrength.setError("**Enter strength");
            return false;
        }
        if (binding.getSchool().getLocation().getAddress() == null || binding.getSchool().getLocation().getAddress().trim().isEmpty()) {
            binding.employeeAddSchoolAddress.setError("**Enter address");
            return false;
        }

        if (binding.getSchool().getContact() == null || binding.getSchool().getContact().trim().isEmpty()) {
            binding.employeeAddSchoolContact.setError("**Enter contact number");
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddSchoolName.setError(null);
        binding.employeeAddSchoolStrength.setError(null);
        binding.employeeAddSchoolAddress.setError(null);
        binding.employeeAddSchoolContact.setError(null);
    }

}