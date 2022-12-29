package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddClientBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.concurrent.TimeUnit;

public class EmployeeAddClientFragment extends Fragment {

    private FragmentEmployeeAddClientBinding binding;
    private EmployeeViewModel viewModel;

    private ProgressDialog progressDialog;

    private OnMapReadyCallback mapReadyCallback;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    public EmployeeAddClientFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_client, container, false);
        binding.setClient(new Client());

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding client..");
        progressDialog.setMessage("Please wait.\n Adding client");
        progressDialog.setCancelable(false);

        mapReadyCallback = new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {

                locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());

                locationRequest = com.google.android.gms.location.LocationRequest.create();
                locationRequest.setInterval(TimeUnit.MICROSECONDS.toMillis(0));
                locationRequest.setFastestInterval(TimeUnit.SECONDS.toMillis(10));
                locationRequest.setMaxWaitTime(TimeUnit.SECONDS.toMillis(30));

                locationCallback = new LocationCallback() {
                    @Override
                    public void onLocationResult(@NonNull LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                        Location location =  locationResult.getLastLocation();
                        binding.getClient().setLatitude(location.getLatitude());
                        binding.getClient().setLongitude(location.getLongitude());
                        Toast.makeText(getContext(), "Location updated", Toast.LENGTH_SHORT).show();
                    }
                };

                if (ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                googleMap.setMyLocationEnabled(true);

            }
        };

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.employee_add_client_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);

        binding.employeeAddClientRegisterBtn.setOnClickListener(this::registerClient);

        return binding.getRoot();
    }

    private void registerClient(View view) {
        if (isValidateForm()) {
            progressDialog.show();
            viewModel.addClient(binding.getClient()).observe(getViewLifecycleOwner(), isSuccessful -> {
                progressDialog.dismiss();
                if (isSuccessful) {
                    navigateUp();
                }
            });
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidateForm() {

        if (binding.getClient().getName() == null || binding.getClient().getName().isEmpty()) {
            binding.employeeAddClientName.setError("Name is required");
            binding.employeeAddClientName.requestFocus();
            return false;
        }
        if (binding.getClient().getOrgName() == null || binding.getClient().getOrgName().isEmpty()) {
            binding.employeeAddClientOrganisationName.setError("Organization name is required");
            binding.employeeAddClientOrganisationName.requestFocus();
            return false;
        }
        if (binding.getClient().getAddress() == null || binding.getClient().getAddress().isEmpty()) {
            binding.employeeAddClientAddress.setError("Address is required");
            binding.employeeAddClientAddress.requestFocus();
            return false;
        }
        if (binding.getClient().getContactNo() == null || binding.getClient().getContactNo().isEmpty()) {
            binding.employeeAddClientContact.setError("Contact number is required");
            binding.employeeAddClientContact.requestFocus();
            return false;
        }
        if (binding.getClient().getLatitude() == 0d || binding.getClient().getLongitude() == 0d) {
            Toast.makeText(getContext(), "Fetching location, Please wait", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }
}