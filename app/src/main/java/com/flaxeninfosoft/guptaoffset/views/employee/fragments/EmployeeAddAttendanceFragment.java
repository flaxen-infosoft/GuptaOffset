package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddAttendanceBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.io.IOException;

public class EmployeeAddAttendanceFragment extends Fragment {

    private FragmentEmployeeAddAttendanceBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;
    private Uri image;

    public EmployeeAddAttendanceFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_attendance, container, false);
        binding.setAttendance(new Attendance());


        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewModel.getCurrentEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAttendance(Attendance attendance) {

        if (attendance == null) {
            attendance = new Attendance();
            attendance.setPunchStatus(0);
        }else{
            binding.setAttendance(attendance);
        }

        switch (attendance.getPunchStatus()) {
            case 0:
                binding.employeeAddAttendanceStartMeter.setEnabled(true);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceEndMeter.setEnabled(false);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.GONE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.GONE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

                binding.employeeAddAttendanceStartMeterImage.setOnClickListener(this::selectStartImage);

                binding.employeeAddAttendanceBtn.setEnabled(true);
                binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                    clearErrors();
                    if (image == null) {
                        Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    punch(binding.getAttendance().getStartMeter(), image);

                });

                break;

            case 1:
                binding.employeeAddAttendanceStartMeter.setEnabled(false);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                String imageLink = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                Glide.with(binding.getRoot().getContext()).load(imageLink).into(binding.employeeAddAttendanceStartMeterImage);

                binding.employeeAddAttendanceEndMeter.setEnabled(true);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

                binding.employeeAddAttendanceEndMeterImage.setOnClickListener(this::selectEndImage);

                binding.employeeAddAttendanceBtn.setEnabled(true);
                binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                    clearErrors();
                    if (image == null) {
                        Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    punch(binding.getAttendance().getEndMeter(), image);

                });
                break;
            case 2:
                binding.employeeAddAttendanceStartMeter.setEnabled(false);
                binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

                String imageLink1 = ApiEndpoints.BASE_URL + attendance.getSnapOut();
                Glide.with(binding.getRoot().getContext()).load(imageLink1).into(binding.employeeAddAttendanceEndMeterImage);

                String imageLink2 = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                Glide.with(binding.getRoot().getContext()).load(imageLink2).into(binding.employeeAddAttendanceStartMeterImage);

                binding.employeeAddAttendanceEndMeter.setEnabled(false);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceBtn.setEnabled(false);
                break;
            default:
                Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                navigateUp();
        }

        progressDialog.dismiss();
    }

    private void selectEndImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setType("image/*");
        endImage.launch(intent);
    }

    private void selectStartImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intent.setType("image/*");
        startImage.launch(intent);
    }

    ActivityResultLauncher<Intent> startImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Glide.with(getContext()).load(bitmap).into(binding.employeeAddAttendanceStartMeterImage);

                        image = FileEncoder.getImageUri(getContext(), bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> endImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        Glide.with(getContext()).load(bitmap).into(binding.employeeAddAttendanceEndMeterImage);

                        image = FileEncoder.getImageUri(getContext(), bitmap);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void punch(String reading, Uri uri) {

        try {
            viewModel.punchAttendance(reading, uri).observe(getViewLifecycleOwner(), attendance -> {
                if(validFilds()){
                    if (attendance != null) {
                        navigateUp();
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean validFilds() {
        if(binding.getAttendance().getStartMeter()==null){
            binding.employeeAddAttendanceStartMeter.setError("**Enter Starting Meter");
            return false;
        }
        if(binding.getAttendance().getEndMeter()==null){
            binding.employeeAddAttendanceEndMeter.setError("**Enter Ending Meter");
            return false;
        }
        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void clearErrors() {
        binding.employeeAddAttendanceStartMeter.setError(null);
        binding.employeeAddAttendanceEndMeter.setError(null);
        binding.employeeAddAttendanceTotalMeter.setError(null);
    }
}