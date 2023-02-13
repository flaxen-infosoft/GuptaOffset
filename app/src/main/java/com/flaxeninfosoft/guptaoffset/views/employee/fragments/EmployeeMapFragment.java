package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeMapBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class EmployeeMapFragment extends Fragment {

    private FragmentEmployeeMapBinding binding;
    private EmployeeViewModel viewModel;

    private OnMapReadyCallback mapReadyCallback;

    public EmployeeMapFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_map, container, false);

        mapReadyCallback = googleMap -> {

            viewModel.getCurrentEmployeeOrders().observe(getViewLifecycleOwner(), orders -> {
               if (orders!= null){
                   for (Order order:orders){
                       LatLng latLng = new LatLng(order.getLocation().getLatitude(), order.getLocation().getLongitude());
                       googleMap.addMarker(
                               new MarkerOptions()
                                       .position(latLng)
                                       .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                       .title("Order id: "+ order.getId())
                       );

                       googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                   }
               }
            });

            viewModel.getCurrentEmployeeDealers().observe(getViewLifecycleOwner(), dealers -> {
                if (dealers!= null){
                    for (Dealer dealer:dealers){
                        LatLng latLng = new LatLng(dealer.getLocation().getLatitude(), dealer.getLocation().getLongitude());
                        googleMap.addMarker(
                                new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE))
                                        .title(dealer.getName())
                        );

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    }
                }
            });

            viewModel.getCurrentEmployeeLeaves().observe(getViewLifecycleOwner(), leaves -> {
                if (leaves!=null){
                    for (Leave leave: leaves){
                        LatLng latLng = new LatLng(leave.getLocation().getLatitude(), leave.getLocation().getLongitude());
                        googleMap.addMarker(
                                new MarkerOptions()
                                        .position(latLng)
                                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))
                                        .title("Leave id: "+ leave.getId())
                        );

                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
                    }
                }
            });

            if (ActivityCompat.checkSelfPermission(EmployeeMapFragment.this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Location permission denied.", Toast.LENGTH_LONG).show();
                return;
            }
            googleMap.setMyLocationEnabled(true);
        };

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.employee_map_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()){
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}