package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.graphics.Bitmap;
import android.net.Uri;
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

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SchoolProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;
    private FragmentSchoolProfileBinding binding;

    private OnMapReadyCallback mapReadyCallback;

    public SchoolProfileFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_school_profile,container,false);

        long schoolId = getArguments().getLong(Constants.SCHOOL_ID, -1);

        if (schoolId == -1){
            Navigation.findNavController(binding.getRoot()).navigateUp();
        }

        viewModel.getSchoolById(schoolId).observe(getViewLifecycleOwner(), this::setSchool);
        return binding.getRoot();
    }

    private void setSchool(School school) {
        binding.setSchool(school);

        if (school == null){
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigateUp();
            return;
        }

        String image = ApiEndpoints.BASE_URL + school.getSnap();
        Glide.with(getContext()).load(image).into(binding.schoolProfileHoadingImage);

        String image1 = ApiEndpoints.BASE_URL + school.getSpecimen();
        Glide.with(getContext()).load(image1).into(binding.schoolProfileSpecimenImage);



        mapReadyCallback = googleMap -> {
            LatLng latLng = new LatLng(school.getLocation().getLatitude(), school.getLocation().getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        };


        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.school_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);
    }
}