package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;

public class EmployeeAddAttendanceFragment extends Fragment {

    private FragmentEmployeeAddAttendanceBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;
    private Uri image;

    private String pictureStartImagePath = "";

    private String pictureEndImagePath = "";

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
        progressDialog.dismiss();
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAttendance(Attendance attendance) {
        progressDialog.dismiss();
        if (attendance == null) {
            attendance = new Attendance();
            attendance.setPunchStatus(0);
        } else {
            binding.setAttendance(attendance);
        }


        if (attendance.getPunchStatus() == 0) {
            binding.employeeAddAttendanceStartMeter.setEnabled(true);
            binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
//            binding.employeeAddAttendanceStartAddress.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

            binding.employeeAddAttendanceEndMeter.setEnabled(false);
            binding.employeeAddAttendanceEndMeter.setVisibility(View.GONE);
            binding.employeeAddAttendanceEndAddress.setVisibility(View.GONE);
            binding.employeeAddAttendanceEndMeterImage.setVisibility(View.GONE);
            binding.endTimeEditText.setVisibility(View.GONE);
            binding.hindiText2.setVisibility(View.GONE);
            binding.eveningText.setVisibility(View.GONE);

            binding.startTimeEditText2.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartAddress2.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartMeter2.setVisibility(View.GONE);

            binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

            binding.employeeAddAttendanceStartMeterImage.setOnClickListener(this::selectStartImage);

            binding.employeeAddAttendanceBtn.setEnabled(true);
            binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                clearErrors();

                if (binding.getAttendance().getStartMeter() == null) {
                    binding.employeeAddAttendanceStartMeter.setError("**Enter Starting Meter");
                    return;
                }

                if (image == null) {
                    Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                    return;
                }

                punch(binding.getAttendance().getStartMeter(), image);

            });
        } else if (attendance.getPunchStatus() == 1) {

            binding.employeeAddAttendanceStartMeter.setEnabled(false);
            binding.employeeAddAttendanceStartMeter.setVisibility(View.VISIBLE);
            binding.employeeAddAttendanceStartAddress.setVisibility(View.VISIBLE);
            binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

            binding.employeeAddAttendanceStartMeter2.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartAddress2.setVisibility(View.GONE);
            binding.startTimeEditText2.setVisibility(View.GONE);
            binding.employeeAddAttendanceBtn.setVisibility(View.GONE);
            binding.employeeAddAttendanceEndMeter.setEnabled(false);
            binding.employeeAddAttendanceEndMeter.setVisibility(View.GONE);
            binding.employeeAddAttendanceEndAddress.setVisibility(View.GONE);
            binding.employeeAddAttendanceEndMeterImage.setVisibility(View.GONE);
            binding.endTimeEditText.setVisibility(View.GONE);
            binding.hindiText2.setVisibility(View.GONE);
            binding.eveningText.setVisibility(View.GONE);

            binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);

            String imageLink = ApiEndpoints.BASE_URL + attendance.getSnapIn();
            Glide.with(binding.getRoot().getContext()).load(imageLink).placeholder(R.drawable.loading_image).into(binding.employeeAddAttendanceStartMeterImage);

            if (attendance.getEvening_attendance_availability().equals("1")) {
                binding.employeeAddAttendanceStartMeter.setVisibility(View.GONE);
                binding.employeeAddAttendanceStartAddress.setVisibility(View.GONE);
                binding.startTimeEditText.setVisibility(View.GONE);


                binding.employeeAddAttendanceStartMeter2.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceStartMeter2.setEnabled(false);
                binding.employeeAddAttendanceStartAddress2.setVisibility(View.VISIBLE);
                binding.startTimeEditText2.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndMeter.setEnabled(true);
                binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
                binding.employeeAddAttendanceEndAddress.setVisibility(View.GONE);
                binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);
                binding.endTimeEditText.setVisibility(View.VISIBLE);
                binding.hindiText2.setVisibility(View.VISIBLE);
                binding.eveningText.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceTotalMeter.setVisibility(View.GONE);
                binding.employeeAddAttendanceBtn.setVisibility(View.VISIBLE);

                binding.employeeAddAttendanceEndMeterImage.setOnClickListener(this::selectEndImage);

                binding.employeeAddAttendanceBtn.setEnabled(true);
                binding.employeeAddAttendanceBtn.setOnClickListener(v -> {
                    clearErrors();

                    if (binding.getAttendance().getEndMeter() == null) {
                        binding.employeeAddAttendanceEndMeter.setError("**Enter Ending Meter");
                        return;
                    }

                    if (image == null) {
                        Toast.makeText(getContext(), "Insert image.", Toast.LENGTH_SHORT).show();
                        return;
                    }


                    binding.employeeAddAttendanceBtn.setEnabled(false);
                    punch(binding.getAttendance().getEndMeter(), image);

                });
            }
        } else if (attendance.getPunchStatus() == 2) {
            binding.employeeAddAttendanceStartMeter.setEnabled(false);
            binding.employeeAddAttendanceStartMeter2.setEnabled(false);
            binding.employeeAddAttendanceStartMeter.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartAddress.setVisibility(View.GONE);
            binding.startTimeEditText.setVisibility(View.GONE);
            binding.employeeAddAttendanceStartMeterImage.setVisibility(View.VISIBLE);

            String imageLink1 = ApiEndpoints.BASE_URL + attendance.getSnapOut();
            Glide.with(binding.getRoot().getContext()).load(imageLink1).placeholder(R.drawable.loading_image).into(binding.employeeAddAttendanceEndMeterImage);

            String imageLink2 = ApiEndpoints.BASE_URL + attendance.getSnapIn();
            Glide.with(binding.getRoot().getContext()).load(imageLink2).placeholder(R.drawable.loading_image).into(binding.employeeAddAttendanceStartMeterImage);

            binding.employeeAddAttendanceEndMeter.setEnabled(false);
            binding.employeeAddAttendanceEndMeter.setVisibility(View.VISIBLE);
            binding.employeeAddAttendanceEndAddress.setVisibility(View.VISIBLE);
            binding.employeeAddAttendanceEndMeterImage.setVisibility(View.VISIBLE);
            binding.endTimeEditText.setVisibility(View.VISIBLE);
            binding.hindiText2.setVisibility(View.VISIBLE);
            binding.eveningText.setVisibility(View.VISIBLE);
            binding.employeeAddAttendanceTotalMeter.setVisibility(View.VISIBLE);

            binding.employeeAddAttendanceBtn.setEnabled(false);
        } else {
            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
            navigateUp();
        }


        progressDialog.dismiss();
//        }
    }

    private void selectEndImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureEndImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureEndImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        endImage.launch(cameraIntent);

        ImagePicker.with(this)
//                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(512, 512)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newEndImage.launch(intent);
                    return null;
                });
    }

    private void selectStartImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureStartImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureStartImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startImage.launch(cameraIntent);

        ImagePicker.with(this)
//                .compress(512)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(512, 512)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newStartImage.launch(intent);
                    return null;
                });
    }

    ActivityResultLauncher<Intent> startImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        File imgFile = new File(pictureStartImagePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            myBitmap = FileEncoder.rotateBitmap(myBitmap);
                            binding.employeeAddAttendanceStartMeterImage.setImageBitmap(myBitmap);
                            image = FileEncoder.getImageUri(getContext(), myBitmap);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> newStartImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        image = data;
                        binding.employeeAddAttendanceStartMeterImage.setImageURI(image);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> newEndImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        image = data;
                        binding.employeeAddAttendanceEndMeterImage.setImageURI(image);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> endImage = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        File imgFile = new File(pictureEndImagePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            myBitmap = FileEncoder.rotateBitmap(myBitmap);
                            binding.employeeAddAttendanceEndMeterImage.setImageBitmap(myBitmap);
                            image = FileEncoder.getImageUri(getContext(), myBitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    private void punch(String reading, Uri uri) {
        binding.employeeAddAttendanceBtn.setEnabled(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        try {
            viewModel.punchAttendance(reading, uri).observe(getViewLifecycleOwner(), attendance -> {
                if (attendance != null) {
                    progressDialog.dismiss();
                    Dialog dialog = new Dialog(getContext());
                    dialog.setContentView(R.layout.data_submit_dialog_layout);
                    dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    Button button = dialog.findViewById(R.id.okButton);
                    button.setOnClickListener(view -> {
                        dialog.dismiss();
                    });
                    dialog.show();
                    navigateUp();
                } else {
                    progressDialog.dismiss();
                    binding.employeeAddAttendanceBtn.setEnabled(true);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
        }
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