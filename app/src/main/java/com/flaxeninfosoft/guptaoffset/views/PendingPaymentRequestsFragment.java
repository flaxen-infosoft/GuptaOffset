package com.flaxeninfosoft.guptaoffset.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.paperdb.Paper;

public class PendingPaymentRequestsFragment extends Fragment {

    private FragmentPendingPaymentRequestsBinding binding;
    private AdminViewModel viewModel;

    private long superEmployeeId;
    String selectedDate;
    String currentDate = "";
    ProgressDialog progressDialog;

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
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date = new Date();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        currentDate = dateFormat.format(date);
        selectedDate = Paper.book().read("selectedDate2");

        try {
            superEmployeeId = getArguments().getLong(Constants.EMPLOYEE_ID, -1);
        } catch (Exception e) {
            superEmployeeId = -1;
        }

        loadRequests();

        binding.pendingPaymentSwipeRefresh.setOnRefreshListener(this::loadRequests);

        return binding.getRoot();
    }

    private void loadRequests() {
        Toast.makeText(getContext(), "Loading please wait...", Toast.LENGTH_SHORT).show();
        if (superEmployeeId == -1) {
            if (selectedDate == null) {
                viewModel.getAllPendingPaymentRequests(currentDate).observe(getViewLifecycleOwner(), this::setRequestsList);
            } else {
                viewModel.getAllPendingPaymentRequests(selectedDate).observe(getViewLifecycleOwner(), this::setRequestsList);
            }

        } else {
            viewModel.getAllPendingPaymentRequestsToEmployee().observe(getViewLifecycleOwner(), this::setRequestsList);
        }
        if (binding.pendingPaymentSwipeRefresh.isRefreshing()) {
            binding.pendingPaymentSwipeRefresh.setRefreshing(false);
        }
    }

    private void setRequestsList(List<PaymentRequest> paymentRequests) {

        if (paymentRequests == null || paymentRequests.isEmpty()) {
            binding.pendingPaymentRequestsRecycler.setVisibility(View.GONE);
            binding.pendingPaymentEmptyRecycler.setVisibility(View.VISIBLE);
        } else {
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