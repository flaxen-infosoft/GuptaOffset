package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeEodProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeEodProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentEmployeeEodProfileBinding binding;
    private ProgressDialog progressDialog;


    public EmployeeEodProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_eod_profile, container, false);

        Long eodId = getArguments().getLong(getString(R.string.key_eod_id));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        viewModel.getEodById(eodId).observe(getViewLifecycleOwner(), this::setEod);

        return binding.getRoot();
    }

    private void setEod(Eod eod) {
        progressDialog.dismiss();
        if (eod == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            binding.setEod(eod);
        }
    }
}