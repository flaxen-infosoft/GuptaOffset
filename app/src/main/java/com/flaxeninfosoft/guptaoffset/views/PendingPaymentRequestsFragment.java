package com.flaxeninfosoft.guptaoffset.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.PaymentRequestRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentPendingPaymentRequestsBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

import java.util.List;

public class PendingPaymentRequestsFragment extends Fragment {

    private FragmentPendingPaymentRequestsBinding binding;
    private AdminViewModel viewModel;

    private long superEmployeeId;

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

        binding.pendingPaymentRequestsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        try {
            superEmployeeId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);
        } catch (Exception e) {
            superEmployeeId = -1;
        }

        loadRequests();

        return binding.getRoot();
    }

    private void loadRequests() {
        if (superEmployeeId != -1) {
            viewModel.getAllPendingPaymentRequests().observe(getViewLifecycleOwner(), this::setRequestsList);
        } else {
            viewModel.getAllPendingPaymentRequestsToEmployee().observe(getViewLifecycleOwner(), this::setRequestsList);
        }
    }

    private void setRequestsList(List<PaymentRequest> paymentRequests) {

        if (paymentRequests == null || paymentRequests.isEmpty()){
            binding.pendingPaymentRequestsRecycler.setVisibility(View.GONE);
            binding.pendingPaymentEmptyRecycler.setVisibility(View.VISIBLE);
        }else {
            binding.pendingPaymentRequestsRecycler.setVisibility(View.VISIBLE);
            binding.pendingPaymentEmptyRecycler.setVisibility(View.GONE);
        }

        PaymentRequestRecyclerAdapter adapter = new PaymentRequestRecyclerAdapter(paymentRequests, request -> {
            CustomDialogFragment dialog = new CustomDialogFragment(request, getViewLifecycleOwner(), this::loadRequests);
            dialog.show(requireActivity().getSupportFragmentManager(), "CustomDialogFragment");
        });

        binding.pendingPaymentRequestsRecycler.setAdapter(adapter);
    }


}