package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleBookOrderCardBinding;
import com.flaxeninfosoft.guptaoffset.models.EmployeeBookOrder;
import com.flaxeninfosoft.guptaoffset.models.Order;

import java.util.List;

import io.paperdb.Paper;

public class BookOrderListAdapter extends RecyclerView.Adapter<BookOrderListAdapter.ViewHolder> {

     List<EmployeeBookOrder> employeeBookOrders;

     SingleBookOrderCardClickListener singleBookOrderCardClickListener;

    public BookOrderListAdapter(List<EmployeeBookOrder> employeeBookOrders, SingleBookOrderCardClickListener onClickListener) {
        this.employeeBookOrders = employeeBookOrders;
        this.singleBookOrderCardClickListener = onClickListener;
    }

    @NonNull
    @Override
    public BookOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleBookOrderCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_book_order_card,parent,false);
        return new ViewHolder(binding,singleBookOrderCardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookOrderListAdapter.ViewHolder holder, int position) {
            holder.setData(employeeBookOrders.get(position));
        Long Id = employeeBookOrders.get(position).getId();


    }
    @Override
    public int getItemCount() {
        if (employeeBookOrders == null) {
            return 0;
        }
        return employeeBookOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleBookOrderCardBinding binding;
        SingleBookOrderCardClickListener singleBookOrderCardClickListener;

        public ViewHolder(@NonNull SingleBookOrderCardBinding binding, SingleBookOrderCardClickListener singleBookOrderCardClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.singleBookOrderCardClickListener = singleBookOrderCardClickListener;
        }

        public void setData(EmployeeBookOrder employeeBookOrder) {
            binding.setBookOrderList(employeeBookOrder);
            binding.getRoot().setOnClickListener(view -> singleBookOrderCardClickListener.singleBookOrder(employeeBookOrder));
        }
    }

    public interface SingleBookOrderCardClickListener{
        void singleBookOrder(EmployeeBookOrder employeeBookOrder);
    }

}
