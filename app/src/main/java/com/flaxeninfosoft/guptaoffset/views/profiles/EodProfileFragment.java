package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEodProfileBinding;

import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class EodProfileFragment extends Fragment {

    private FragmentEodProfileBinding binding;
    private EmployeeViewModel viewModel;

    private OnMapReadyCallback mapReadyCallback;

    public EodProfileFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_eod_profile, container, false);

        long eodId = getArguments().getLong(Constants.EOD_ID, -1);

        if (eodId == -1){
            navigateUp();
        }

        viewModel.getEodById(eodId).observe(getViewLifecycleOwner(), this::setEod);

        return binding.getRoot();
    }

    private void setEod(Eod eod) {
        binding.setEod(eod);

        if (eod==null){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
            return;
        }

        mapReadyCallback = googleMap -> {
            LatLng latLng = new LatLng(eod.getLatitude(), eod.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
        };

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.eod_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }
}