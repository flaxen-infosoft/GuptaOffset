package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentPaymentReceiveBinding;

public class PaymentReceiveFragment extends Fragment {

    FragmentPaymentReceiveBinding binding;

    public PaymentReceiveFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_payment_receive, container, false);

        binding.paymentReceiveBackImg.setOnClickListener(this::onCLickBack);
        return binding.getRoot();
    }

    private void onCLickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}