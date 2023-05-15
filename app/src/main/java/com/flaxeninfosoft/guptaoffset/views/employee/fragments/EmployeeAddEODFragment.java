package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.Activity;
import android.app.AlertDialog;
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
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddEODBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.io.File;
import java.io.IOException;

public class EmployeeAddEODFragment extends Fragment {

    private FragmentEmployeeAddEODBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    private Uri expenseImage;
    private Uri petrolImage;

    private String picturePetrolExpenseImagePath;

    private String pictureOtherExpenseImagePath;

    public EmployeeAddEODFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_e_o_d, container, false);
        binding.setEod(new Eod());
        binding.getEod().setDailyAllowance(viewModel.getCurrentEmployee().getDailyAllowance());

        binding.employeeAddEodBtn.setOnClickListener(this::onClickAddEod);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);
        viewModel.getCurrentEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);

        binding.employeeAddEodExpenseImage.setOnClickListener(this::onClickExpenseImage);
        binding.employeeAddEodPetrolImage.setOnClickListener(this::onClickPetrolImage);

        return binding.getRoot();
    }

    private void setAttendance(Attendance attendance) {
        String punchStatus = "0";
        if (attendance == null) {
            punchStatus = "0";
        } else {
            punchStatus = String.valueOf(attendance.getPunchStatus());
        }


        if (punchStatus.equals("0")) {
            showDialog();
        } else if (punchStatus.equals("1")) {
            showDialog();
        } else {
            //submit eod
        }
    }

    private void showDialog() {
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View view = factory.inflate(R.layout.attendance_alert_dialog_layout, null);
        AlertDialog attendaceDialog = new AlertDialog.Builder(getContext()).create();
        attendaceDialog.setView(view);
        attendaceDialog.setCancelable(false);
        attendaceDialog.show();
//        Button buttonCancel = view.findViewById(R.id.button2);
        Button buttonPunch = view.findViewById(R.id.button1);
        buttonPunch.setOnClickListener(view12 -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.employeeAddAttendanceFragment);
            attendaceDialog.dismiss();
        });
    }

    ActivityResultLauncher<Intent> expenseImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                try {

                    File imgFile = new File(pictureOtherExpenseImagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        binding.employeeAddEodExpenseImage.setImageBitmap(myBitmap);
                        expenseImage = FileEncoder.getImageUri(getContext(), myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    });

    ActivityResultLauncher<Intent> newExpenseImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        expenseImage = data;
                        binding.employeeAddEodExpenseImage.setImageURI(expenseImage);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> petrolImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                try {

                    File imgFile = new File(picturePetrolExpenseImagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        myBitmap = FileEncoder.rotateBitmap(myBitmap);
                        binding.employeeAddEodPetrolImage.setImageBitmap(myBitmap);
                        petrolImage = FileEncoder.getImageUri(getContext(), myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    });

    ActivityResultLauncher<Intent> newPetrolImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        petrolImage = data;
                        binding.employeeAddEodPetrolImage.setImageURI(petrolImage);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void onClickPetrolImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        picturePetrolExpenseImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(picturePetrolExpenseImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        petrolImageLauncher.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newPetrolImageLauncher.launch(intent);
                    return null;
                });
    }

    private void onClickExpenseImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureOtherExpenseImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureOtherExpenseImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        expenseImageLauncher.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newExpenseImageLauncher.launch(intent);
                    return null;
                });
    }

    private void onClickAddEod(View view) {
        clearErrors();
        if (isValidFields()) {
            progressDialog.show();
            try {
                viewModel.addEod(binding.getEod(), expenseImage, petrolImage).observe(getViewLifecycleOwner(), b -> {
                    if (b) {
                        progressDialog.dismiss();
                        navigateUp();
                        Toast.makeText(getContext(), "रिपोर्ट ऐड हो गई है।\n", Toast.LENGTH_SHORT).show();
                    } else {
                        progressDialog.dismiss();
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {
        if (binding.getEod().getSchoolVisits() == null) {
            binding.employeeAddEodSchoolsVisits.setError("Enter schools visits");
            return false;
        }
//        if (binding.getEod().getPetrolExpense() == null) {
//            binding.employeeAddEodPetrolExpense.setError("Enter petrol expense");
//            return false;
//        }
//        if (binding.getEod().getOtherExpense() != null && !binding.getEod().getOtherExpense().isEmpty()){
//            if (binding.getEod().getExpenseDescription() == null || binding.getEod().getExpenseDescription().isEmpty()){
//                binding.employeeAddEodExpenseDescription.setError("Enter expense description");
//                return false;
//            }
//        }
//        if (binding.getEod().getOtherExpense() == null) {
//            binding.employeeAddEodOtherExpense.setError("Enter other expenses");
//            return false;
//        }
//        if (expenseImage == null) {
//            Toast.makeText(getContext(), "Add expense Image", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (petrolImage == null) {
//            Toast.makeText(getContext(), "Add petrol expense Image", Toast.LENGTH_LONG).show();
//            return false;
//        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddEodSchoolsVisits.setError(null);
        binding.employeeAddEodPetrolExpense.setError(null);
        binding.employeeAddEodOtherExpense.setError(null);
    }
}