package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeMapBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SuperEmployeeMapFragment extends Fragment {

    private FragmentSuperEmployeeMapBinding binding;
    private SuperEmployeeViewModel viewModel;

    private OnMapReadyCallback mapReadyCallback;

    public SuperEmployeeMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(SuperEmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_employee_map, container, false);

        mapReadyCallback = googleMap -> {
            viewModel.getCurrentEmployeeClients().observe(getViewLifecycleOwner(), clients -> {
                if (clients != null) {
                    for (Client c : clients) {
                        if (c.getLatitude() != 0d && c.getLongitude() != 0d) {
                            LatLng latLng = new LatLng(c.getLatitude(), c.getLongitude());
                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(c.getOrgName()));
                        }
                    }
                }
            });

            viewModel.getCurrentSuperEmployeeEmployees().observe(getViewLifecycleOwner(), employees -> {
                if (employees != null) {
                    for (Employee e : employees) {
                        if (e.getLatitude() != 0d && e.getLongitude() != 0d) {
                            LatLng latLng = new LatLng(e.getLatitude(), e.getLongitude());
                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(e.getName()));
                        }
                    }
                }
            });
        };


        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.super_employee_map_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);

        return binding.getRoot();
    }
}