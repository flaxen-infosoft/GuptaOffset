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
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddSchoolBinding;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;


public class EmployeeAddSchoolFragment extends Fragment {

    private FragmentEmployeeAddSchoolBinding binding;
    private EmployeeViewModel viewModel;
    private Uri specimenImage;
    private Uri hoadingImage;
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
        binding.setSchool(new School());

        binding.employeeAddSchoolBtn.setOnClickListener(this::onClickAddSchool);
        binding.employeeAddSchoolSpecimenImage.setOnClickListener(this::onClickSpecimenImage);
        binding.employeeAddSchoolHoadingImage.setOnClickListener(this::onClickHoadingImage);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding School...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void onClickHoadingImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        hoadingImageIntent.launch(intent);
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()){
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

                        specimenImage = FileEncoder.getImageUri(getContext(), bitmap);
                    }catch (Exception e){
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

                        hoadingImage = FileEncoder.getImageUri(getContext(), bitmap);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
    );


    private void onClickAddSchool(View view) {
        if (isValidFields() && isImageAdded()) {

            Location location = viewModel.getCurrentEmployeeLocation().getValue();
            if (location.getLongitude()==0d || location.getLatitude()==0d){
                Toast.makeText(getContext(),"Fetching location.", Toast.LENGTH_SHORT).show();
                return;
            }

            progressDialog.show();
            try {
                viewModel.addSchool(binding.getSchool(), specimenImage, hoadingImage).observe(getViewLifecycleOwner(), b->{
                    if (b){
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
        if(specimenImage ==null){
            Toast.makeText(getContext(), "Add Specimen Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (hoadingImage==null){
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
        if (binding.getSchool().getAddress() == null || binding.getSchool().getAddress().trim().isEmpty()) {
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