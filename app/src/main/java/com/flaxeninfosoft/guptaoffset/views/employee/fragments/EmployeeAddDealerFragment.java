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
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddDealerBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;


public class EmployeeAddDealerFragment extends Fragment {

    private FragmentEmployeeAddDealerBinding binding;
    private EmployeeViewModel viewModel;
    private Uri image;

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

        return binding.getRoot();
    }

    private void onClickAddImage(View view) {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        mLauncher.launch(intent);
    }

    ActivityResultLauncher<Intent> mLauncher=registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        image=result.getData().getData();
                        Glide.with(getContext()).load(image).into(binding.employeeAddDealerImage);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
    );

    private void onCLickAddDealer(View view) {

        if (isValidFields() && isImageAdd()) {
            viewModel.addDealer(binding.getDealer()).observe(getViewLifecycleOwner(), b->{
                if (b){
                    clearErrors();
                    navigateUp();
                }
            });
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