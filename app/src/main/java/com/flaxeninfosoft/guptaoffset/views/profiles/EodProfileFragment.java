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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEodProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
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

    Eod eod1;
    String otherExpense;
    String petrolExpense;
    long eodId;

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

        Employee employee = viewModel.getCurrentEmployee();
        if (employee.getDesignation().equals("admin")) {
            binding.updateLayout.setVisibility(View.VISIBLE);
        } else {
            binding.updateLayout.setVisibility(View.GONE);
        }
         eodId = getArguments().getLong(Constants.EOD_ID, -1);
        if (eodId == -1) {
            navigateUp();
        }else {
            viewModel.getEodById(eodId).observe(getViewLifecycleOwner(), this::setEod);
        }

        binding.eodProfileToolbar.setNavigationOnClickListener(view -> navigateUp());
        binding.eodProfileToolbar.setNavigationIcon(R.drawable.ic_back);
        binding.eodProfileOtherExpenseImage.setOnLongClickListener(this::onLongClickOtherExpense);
        binding.eodProfilePetrolExpenseImage.setOnLongClickListener(this::onLongClickPetrolExpnese);
        binding.updateLayout.setOnClickListener(this::onClickUpdate);

        return binding.getRoot();
    }

    private void onClickUpdate(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EOD_ID,eodId);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.updateEodFragment,bundle);
    }

    private boolean onLongClickPetrolExpnese(View view) {
        String textToSend = "Hello, This is petrol expense image :  " + petrolExpense;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        return true;
    }

    private boolean onLongClickOtherExpense(View view) {
        String textToSend = "Hello, This is other expense image :  " + otherExpense;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        return true;
    }

    private void setEod(Eod eod) {
        eod1 = eod;
        binding.setEod(eod);

        if (eod == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
            return;
        }

        if (eod.getPetrolExpenseImage() != null && !eod.getPetrolExpenseImage().isEmpty()) {
            String image = ApiEndpoints.BASE_URL + eod.getPetrolExpenseImage();
            petrolExpense = image;
            Glide.with(getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.eodProfilePetrolExpenseImage);

            binding.eodProfilePetrolExpenseImage.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", image);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            });
        } else {
              binding.eodProfilePetrolExpenseImage.setImageResource(R.drawable.image_not_available);
        }

        if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
            String image = ApiEndpoints.BASE_URL + eod.getExpenseImage();
            otherExpense = image;
            Glide.with(getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.eodProfileOtherExpenseImage);

            binding.eodProfileOtherExpenseImage.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", image);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            });
        } else {
            binding.eodProfileOtherExpenseImage.setImageResource(R.drawable.image_not_available);
        }

        mapReadyCallback = googleMap -> {
            try {
                LatLng latLng = new LatLng(eod.getLocation().getLatitude(), eod.getLocation().getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
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