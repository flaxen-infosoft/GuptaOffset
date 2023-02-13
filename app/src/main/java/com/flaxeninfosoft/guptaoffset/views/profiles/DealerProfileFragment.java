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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentDealerProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DealerProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;

    private FragmentDealerProfileBinding binding;

    private OnMapReadyCallback mapReadyCallBack;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dealer_profile, container, false);
        long dealerId = getArguments().getLong(Constants.DEALER_ID, -1);

        if (dealerId == -1) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
        }else {
            viewModel.getDealerById(dealerId).observe(getViewLifecycleOwner(), this::setDealer);
        }

        return binding.getRoot();
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void setDealer(Dealer dealer) {
        binding.setDealer(dealer);

        if (dealer == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
            return;
        }

        String image=ApiEndpoints.BASE_URL+dealer.getImage();
        Glide.with(getContext()).load(image).into(binding.dealerProfileSpecimenImage);

        binding.dealerProfileSpecimenImage.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE", image);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
        });

        mapReadyCallBack = googleMap -> {
            LatLng latLng = new LatLng(dealer.getLocation().getLatitude(), dealer.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));

            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        };
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.dealer_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallBack);
    }
}