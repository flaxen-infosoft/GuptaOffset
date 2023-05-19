package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.BookOrderListBinding;
import com.flaxeninfosoft.guptaoffset.models.EmployeeBookOrder;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class BookOrderListAdapter extends RecyclerView.Adapter<BookOrderListAdapter.ViewHolder> {

     List<EmployeeBookOrder> employeeBookOrderList;

     BookOrderListCardClickListener bookOrderCardListClickListener;

    public BookOrderListAdapter(List<EmployeeBookOrder> employeeBookOrderList, BookOrderListCardClickListener bookOrderCardListClickListener) {
        this.employeeBookOrderList = employeeBookOrderList;
        this.bookOrderCardListClickListener = bookOrderCardListClickListener;
    }

    @NonNull
    @Override
    public BookOrderListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BookOrderListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.book_order_list,parent,false);
        return new ViewHolder(binding,bookOrderCardListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull BookOrderListAdapter.ViewHolder holder, int position) {
            holder.setData(employeeBookOrderList.get(position));
            Long Id = employeeBookOrderList.get(position).getId();


    }
    @Override
    public int getItemCount() {
        if (employeeBookOrderList == null) {
            return 0;
        }
        return employeeBookOrderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        BookOrderListBinding binding;
        BookOrderListCardClickListener bookOrderCardListClickListener;

        public ViewHolder(@NonNull BookOrderListBinding binding, BookOrderListCardClickListener bookOrderCardListClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.bookOrderCardListClickListener = bookOrderCardListClickListener;
        }

        public void setData(EmployeeBookOrder employeeBookOrder) {
            binding.setBookOrderList(employeeBookOrder);
            binding.getRoot().setOnClickListener(view -> bookOrderCardListClickListener.singleBookOrder(employeeBookOrder));

            if (employeeBookOrder.getSnap() != null) {
                binding.bookOrderListImg.setVisibility(View.VISIBLE);
                String url = ApiEndpoints.BASE_URL + employeeBookOrder.getSnap();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.bookOrderListImg);
            } else {
                binding.bookOrderListImg.setVisibility(View.GONE);

            }
        }
    }

    public interface BookOrderListCardClickListener{
        void singleBookOrder(EmployeeBookOrder employeeBookOrder);
    }

}
