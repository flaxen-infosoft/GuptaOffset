package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.DataBinderMapperImpl;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminMapBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class AdminMapFragment extends Fragment {

    private AdminMainViewModel viewModel;
    private FragmentAdminMapBinding binding;

    private OnMapReadyCallback mapReadyCallback;

    public AdminMapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_map, container, false);

        mapReadyCallback = googleMap -> viewModel.getAllEmployees().observe(getViewLifecycleOwner(), employees -> {
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

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.employee_map_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);

        return binding.getRoot();
    }
}