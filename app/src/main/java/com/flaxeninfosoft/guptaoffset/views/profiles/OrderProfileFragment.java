package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.content.Intent;
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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentOrderProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class OrderProfileFragment extends Fragment {
    private FragmentOrderProfileBinding binding;
    private EmployeeViewModel viewModel;

    private OnMapReadyCallback mapReadyCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_profile, container, false);

        long orderId = getArguments().getLong(Constants.ORDER_ID, -1);

        binding.orderProfileToolbar.setNavigationOnClickListener(view -> navigateUp());
        binding.orderProfileToolbar.setNavigationIcon(R.drawable.ic_back);

        if (orderId == -1) {
            navigateUp();
        }else{
            viewModel.getOrderById(orderId).observe(getViewLifecycleOwner(), this::setOrder);
        }


        return binding.getRoot();
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void setOrder(Order order) {
        binding.setOrder(order);

        if (order == null) {
            navigateUp();
            return;
        }

        String image = ApiEndpoints.BASE_URL+order.getSnap();
        Glide.with(getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.orderProfileSpecimenImage);

        binding.orderProfileSpecimenImage.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putString("IMAGE", image);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
        });

        binding.orderProfileSpecimenImage.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                String textToSend = "Hello, this is order image :  " +image;
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                return true;
            }
        });

        mapReadyCallback = googleMap -> {
            LatLng latLng = new LatLng(order.getLocation().getLatitude(), order.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        };

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.order_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);
    }
}