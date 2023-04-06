package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAttendanceProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class AttendanceProfileFragment extends Fragment {

    private FragmentAttendanceProfileBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;

    public AttendanceProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_attendance_profile, container, false);
        binding.setAttendance(new Attendance());

        binding.attendanceProfileToolbar.setNavigationOnClickListener(view -> navigateUp());
        binding.attendanceProfileToolbar.setNavigationIcon(R.drawable.ic_back);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        long atnId = getArguments().getLong(Constants.ATN_ID, -1);

        if (atnId == -1) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            viewModel.getAttendanceById(atnId).observe(getViewLifecycleOwner(), this::setAttendance);
        }


        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void setAttendance(Attendance attendance) {
        progressDialog.dismiss();
        if (attendance == null) {
            navigateUp();
        } else {
            binding.setAttendance(attendance);

            switch (attendance.getPunchStatus()) {
                case 0:
                    navigateUp();
                    break;
                case 2:
                    binding.attendanceProfileOutLayout.setVisibility(View.VISIBLE);
                    String outImage = ApiEndpoints.BASE_URL + attendance.getSnapOut();
                    Glide.with(binding.getRoot().getContext()).load(outImage).placeholder(R.drawable.loading_image).into(binding.employeeAttendanceProfileEndMeterImage);

                    binding.employeeAttendanceProfileEndMeterImage.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("IMAGE", outImage);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
                    });
                case 1:
                    binding.attendanceProfileInLayout.setVisibility(View.VISIBLE);
                    String inImage = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                    Glide.with(binding.getRoot().getContext()).load(inImage).placeholder(R.drawable.loading_image).into(binding.employeeAttendanceProfileStartMeterImage);

                    binding.employeeAttendanceProfileStartMeterImage.setOnClickListener(view -> {
                        Bundle bundle = new Bundle();
                        bundle.putString("IMAGE", inImage);
                        Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
                    });
                    break;
                default:
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                    navigateUp();
            }
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}