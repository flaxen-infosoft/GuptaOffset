package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleOrderCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;

import java.util.List;

public class OrderRecyclerAdapter extends RecyclerView.Adapter<OrderRecyclerAdapter.SingleOrderViewHolder> {

    private final List<Order> allOrders;
    private final SingleOrderCardOnClickListener onClickListener;

    public OrderRecyclerAdapter(List<Order> allOrders, SingleOrderCardOnClickListener onClickListener) {
        this.allOrders = allOrders;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SingleOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleOrderCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_order_card, parent, false);
        return new SingleOrderViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleOrderViewHolder holder, int position) {
        holder.setData(allOrders.get(position));
    }

    @Override
    public int getItemCount() {
        if (allOrders == null) {
            return 0;
        }
        return allOrders.size();
    }

    public static class SingleOrderViewHolder extends RecyclerView.ViewHolder {

        private final SingleOrderCardBinding binding;
        private final SingleOrderCardOnClickListener onClickListener;

        public SingleOrderViewHolder(@NonNull SingleOrderCardBinding binding, SingleOrderCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Order order) {
            binding.setOrder(order);

            binding.getRoot().setOnClickListener(view -> onClickListener.onCLickCard(order));
        }
    }

    public interface SingleOrderCardOnClickListener {

        void onCLickCard(Order order);
    }

//    Yeah Boiii!!!
}
