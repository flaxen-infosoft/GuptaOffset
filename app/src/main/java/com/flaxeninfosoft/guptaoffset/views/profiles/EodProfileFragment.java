package com.flaxeninfosoft.guptaoffset.views.profiles;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEodProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
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

        if (eodId == -1) {
            navigateUp();
        }else {

            viewModel.getEodById(eodId).observe(getViewLifecycleOwner(), this::setEod);
        }


        return binding.getRoot();
    }

    private void setEod(Eod eod) {
        binding.setEod(eod);

        if (eod == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
            return;
        }

        if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
            String image = ApiEndpoints.BASE_URL + eod.getExpenseImage();
            Glide.with(getContext()).load(image).into(binding.eodProfileImage);

            binding.eodProfileImage.setOnClickListener(view->{
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", image);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            });
        } else {
            binding.eodProfileImage.setVisibility(View.GONE);
        }

        mapReadyCallback = googleMap -> {
            LatLng latLng = new LatLng(eod.getLocation().getLatitude(), eod.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
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