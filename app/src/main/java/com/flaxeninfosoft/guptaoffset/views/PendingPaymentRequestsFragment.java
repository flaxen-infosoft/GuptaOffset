package com.flaxeninfosoft.guptaoffset.views;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.PaymentRequestRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentPendingPaymentRequestsBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

import java.util.List;

public class PendingPaymentRequestsFragment extends Fragment {

    private FragmentPendingPaymentRequestsBinding binding;
    private AdminViewModel viewModel;

    public PendingPaymentRequestsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pending_payment_requests, container, false);

        long superEmployeeId;
        binding.pendingPaymentRequestsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        try{
            superEmployeeId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);
        }catch (Exception e){
            superEmployeeId = -1;
        }

        if (superEmployeeId != -1){
            viewModel.getAllPendingPaymentRequests().observe(getViewLifecycleOwner(), this::setRequestsList);
        }else {
            viewModel.getAllPendingPaymentRequests().observe(getViewLifecycleOwner(), this::setRequestsList);
        }

        return binding.getRoot();
    }

    private void setRequestsList(List<PaymentRequest> paymentRequests) {
        PaymentRequestRecyclerAdapter adapter = new PaymentRequestRecyclerAdapter(paymentRequests, request -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.pendingPaymentRequestsFragment);
        });

        binding.pendingPaymentRequestsRecycler.setAdapter(adapter);
    }


}