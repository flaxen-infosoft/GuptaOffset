package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SinglePaymentRequestCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SinglePaymentRequestIndividualCardBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;

import java.util.List;

public class PaymentRequestRecyclerAdapter extends RecyclerView.Adapter<PaymentRequestRecyclerAdapter.PaymentRequestCardViewHolder> {

    private final List<PaymentRequest> requestList;
    private final SinglePaymentRequestIndividualCardListener listener;

    public PaymentRequestRecyclerAdapter(List<PaymentRequest> requestList, SinglePaymentRequestIndividualCardListener listener){
        this.requestList = requestList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PaymentRequestCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SinglePaymentRequestIndividualCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_payment_request_individual_card, parent, false);
        return new PaymentRequestCardViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentRequestCardViewHolder holder, int position) {
        holder.setData(requestList.get(position));
    }

    @Override
    public int getItemCount() {
        if (requestList == null){
            return 0;
        }else {
            return requestList.size();
        }
    }

    public static class PaymentRequestCardViewHolder extends RecyclerView.ViewHolder{

        private final SinglePaymentRequestIndividualCardBinding binding;
        private final SinglePaymentRequestIndividualCardListener listener;

        public PaymentRequestCardViewHolder(@NonNull SinglePaymentRequestIndividualCardBinding binding, SinglePaymentRequestIndividualCardListener listener) {
            super(binding.getRoot());
            this.binding = binding;
            this.listener = listener;
        }

        public void setData(PaymentRequest request){
            binding.setPayment(request);
            binding.getRoot().setOnClickListener(view -> listener.onCardClick(request));
        }
    }

    public interface SinglePaymentRequestIndividualCardListener{
        void onCardClick(PaymentRequest request);
    }
}
