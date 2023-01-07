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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeClientProfileBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class EmployeeClientProfileFragment extends Fragment {

    private FragmentEmployeeClientProfileBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    public EmployeeClientProfileFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_client_profile, container, false);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();

        Long clientId = getArguments().getLong(getString(R.string.key_client_id));

        viewModel.getClientById(clientId).observe(getViewLifecycleOwner(), this::setClintDetails);

        return binding.getRoot();
    }

    private void setClintDetails(Client client) {
        progressDialog.dismiss();
        if (client == null) {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {
            binding.setClient(client);
        }
    }

}