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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddEODBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeAddEODFragment extends Fragment {

    private FragmentEmployeeAddEODBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    private Uri image;

    public EmployeeAddEODFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_e_o_d, container, false);
        binding.setEod(new Eod());
        binding.getEod().setDailyAllowance(viewModel.getCurrentEmployee().getDailyAllowance());

        binding.employeeAddEodBtn.setOnClickListener(this::onClickAddEod);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        binding.employeeAddEodExpenseImage.setOnClickListener(this::onClickImage);

        return binding.getRoot();
    }

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {

                            Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                            Glide.with(getContext()).load(bitmap).into(binding.employeeAddEodExpenseImage);

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

    private void onClickImage(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mLauncher.launch(intent);
    }

    private void onClickAddEod(View view) {
        clearErrors();
        if (isValidFields()) {
            progressDialog.show();
            viewModel.addEod(binding.getEod()).observe(getViewLifecycleOwner(), b -> {
                if (b) {
                    progressDialog.dismiss();
                    navigateUp();
                } else {
                    progressDialog.dismiss();
                }
            });
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
        if (binding.getEod().getPetrolExpense() == null) {
            binding.employeeAddEodPetrolExpense.setError("Enter petrol expense");
            return false;
        }
//        if (binding.getEod().getOtherExpense() == null) {
//            binding.employeeAddEodOtherExpense.setError("Enter other expenses");
//            return false;
//        }
//        if (image == null) {
//            Toast.makeText(getContext(), "Add Image", Toast.LENGTH_LONG).show();
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