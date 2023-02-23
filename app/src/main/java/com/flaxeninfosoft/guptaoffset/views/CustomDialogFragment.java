package com.flaxeninfosoft.guptaoffset.views;


import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.LayoutCustomDialogBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

public class CustomDialogFragment extends DialogFragment {

    private SuperEmployeeViewModel viewModel;
    private LayoutCustomDialogBinding binding;

    private PaymentRequest request;
    private OnCompleteListener listener;
    private LifecycleOwner lifecycleOwner;

    public CustomDialogFragment(PaymentRequest request, LifecycleOwner viewLifecycleOwner, OnCompleteListener listener) {
        this.request = request;
        this.listener = listener;
        this.lifecycleOwner = viewLifecycleOwner;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_custom_dialog, null, false);
        //setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(SuperEmployeeViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_custom_dialog, null);
        binding.setPayment(new PaymentRequest());

        builder.setView(dialogView);

        binding.customDialogOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateFields()) {
                    binding.getPayment().setId(request.getId());
                    viewModel.updatePaymentRequest(binding.getPayment()).observe(lifecycleOwner,
                            request -> listener.onComplete());
                    dismiss();
                }
            }
        });

        binding.customDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        builder.setView(binding.getRoot());
        return builder.create();
    }

    private boolean validateFields() {
        if (binding.getPayment().getReceived() == null || binding.getPayment().getReceived().trim().isEmpty()) {
            binding.layoutCustomDialogAmountEditText.setError("**Enter Amount");
            return false;
        }
        return true;
    }

    public interface OnCompleteListener {
        void onComplete();
    }
}
