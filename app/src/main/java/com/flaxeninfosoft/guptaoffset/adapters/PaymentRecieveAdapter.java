package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SinglePaymentRecieveCardBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentStatus;

import java.util.List;

public class PaymentRecieveAdapter extends RecyclerView.Adapter<PaymentRecieveAdapter.ViewHolder> {

    List<PaymentStatus> paymentStatusList;
    PaymentStatusLayoutClickListener paymentStatusLayoutClickListener;

    public PaymentRecieveAdapter (List<PaymentStatus> paymentStatusList, PaymentStatusLayoutClickListener paymentStatusLayoutClickListener) {
        this.paymentStatusList = paymentStatusList;
        this.paymentStatusLayoutClickListener = paymentStatusLayoutClickListener;
    }

    @NonNull
    @Override
    public PaymentRecieveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglePaymentRecieveCardBinding singlePaymentRecieveCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_payment_recieve_card,parent, false);
        return new ViewHolder(singlePaymentRecieveCardBinding,paymentStatusLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRecieveAdapter.ViewHolder holder, int position) {
        holder.setData(paymentStatusList.get(position));

    }

    @Override
    public int getItemCount() {
        if (paymentStatusList == null) {
            return 0;
        }
        return paymentStatusList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        SinglePaymentRecieveCardBinding binding;
        PaymentStatusLayoutClickListener paymentStatusLayoutClickListener;

        public ViewHolder(SinglePaymentRecieveCardBinding binding, PaymentStatusLayoutClickListener paymentStatusLayoutClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.paymentStatusLayoutClickListener = paymentStatusLayoutClickListener;
        }

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
        public void setData(PaymentStatus paymentStatus) {
            binding.setPaymentrecieve(paymentStatus);
            binding.getRoot().setOnClickListener(v -> paymentStatusLayoutClickListener.onClickPaymentRecieve(paymentStatus));
        }

    }

    public interface PaymentStatusLayoutClickListener {
        void onClickPaymentRecieve(PaymentStatus paymentStatus);
    }
}
