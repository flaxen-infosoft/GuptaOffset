package com.flaxeninfosoft.guptaoffset.views.profiles;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentDealerProfileBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DealerProfileFragment extends Fragment {

    private EmployeeViewModel viewModel;

    private FragmentDealerProfileBinding binding;

    private OnMapReadyCallback mapReadyCallBack;

    public DealerProfileFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel=new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_dealer_profile,container,false);
        long dealerId=getArguments().getLong(Constants.DEALER_ID,-1);

        if(dealerId==-1){
            Navigation.findNavController(binding.getRoot()).navigateUp();
        }

        viewModel.getDealerById(dealerId).observe(getViewLifecycleOwner(),this::setDealer);
        return binding.getRoot();
    }

    private void setDealer(Dealer dealer){
        binding.setDealer(dealer);

        mapReadyCallBack=googleMap ->{
            LatLng latLng=new LatLng(dealer.getLatitude(),dealer.getLongitude());
            googleMap.addMarker(new MarkerOptions()
                    .position(latLng));
        };
        SupportMapFragment mapFragment=SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.dealer_profile_map,mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallBack);
    }
}