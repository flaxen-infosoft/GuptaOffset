package com.flaxeninfosoft.guptaoffset.views;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.LayoutCustomDialogBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

public class CustomDialogFragment extends DialogFragment {

    private AdminViewModel viewModel;
    private LayoutCustomDialogBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.layout_custom_dialog, null, false);
        //setContentView(binding.getRoot());
        viewModel = new ViewModelProvider(this).get(AdminViewModel.class);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_custom_dialog, null);
        binding.setPayment(new PaymentRequest());
//        final String amount= binding.layoutCustomDialogAmountEditText.getText().toString();
//        final String message= binding.layoutCustomDialogAmountEditText.getText().toString();

//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                viewModel.getEditTextValue().setValue(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {}
//        });

        builder.setView(dialogView);

        binding.customDialogOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO
                if(validateFields()){
                    Toast.makeText(getContext(), "OK Button Clicked", Toast.LENGTH_SHORT).show();
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

    private boolean validateFields(){
        if (binding.getPayment().getAmount() == null || binding.getPayment().getAmount().trim().isEmpty()) {
            binding.layoutCustomDialogAmountEditText.setError("**Enter Amount");
            return false;
        }
        if (binding.getPayment() == null||binding.getPayment().getMessage() == null || binding.getPayment().getMessage().trim().isEmpty()) {
            binding.layoutCustomDialogMessageEditText.setError("**Enter Message");
            return false;
        }
        return true;
    }
}
