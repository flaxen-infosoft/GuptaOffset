package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
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

        mapReadyCallback = googleMap -> {
            LatLng latLng = new LatLng(school.getLatitude(), school.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
        };


        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.school_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);
    }
}