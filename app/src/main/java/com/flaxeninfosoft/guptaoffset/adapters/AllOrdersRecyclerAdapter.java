package com.flaxeninfosoft.guptaoffset.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.AllOrdersLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.AllOrder;
import com.flaxeninfosoft.guptaoffset.models.Order;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class AllOrdersRecyclerAdapter extends RecyclerView.Adapter<AllOrdersRecyclerAdapter.ViewHolder> {

    List<AllOrder> orderList;
    AllOrderCardOnClickListener allOrderCardOnClickListener;

    public AllOrdersRecyclerAdapter(List<AllOrder> orderList, AllOrderCardOnClickListener allOrderCardOnClickListener) {
        this.orderList = orderList;
        this.allOrderCardOnClickListener = allOrderCardOnClickListener;
    }


    @NonNull
    @Override
    public AllOrdersRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AllOrdersLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.all_orders_layout, parent, false);
        return new ViewHolder(binding, allOrderCardOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AllOrdersRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(orderList.get(position));
    }

    @Override
    public int getItemCount() {
        if (orderList == null) {
            return 0;
        }
        return orderList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AllOrdersLayoutBinding binding;
        AllOrderCardOnClickListener allOrderCardOnClickListener;

        public ViewHolder(AllOrdersLayoutBinding binding, AllOrderCardOnClickListener allOrderCardOnClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.allOrderCardOnClickListener = allOrderCardOnClickListener;
        }

        public void setData(AllOrder allOrder) {

            try {
                String orderDate = allOrder.getDate();
                DateFormat currentDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date currentDate = new Date();
                String dateNow = currentDateFormat.format(currentDate);
                // create a SimpleDateFormat object with the desired format
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                // parse the input string into a Date object
                Date date = inputFormat.parse(orderDate);
                // create a new SimpleDateFormat object for the output format
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                // format the Date object into a string in the desired output format
                String dateOnlyString = outputFormat.format(date);

                if (dateOnlyString.equals(dateNow)) {
                    binding.allOrderLinear.setBackgroundColor(Color.parseColor("#DCF8C6"));
                } else {
                    binding.allOrderLinear.setBackgroundColor(Color.WHITE);
                }
            } catch (Exception e) {
                Log.i("order", "something went wrong.");
            }
            binding.setOrder(allOrder);
            binding.getRoot().setOnClickListener(view -> allOrderCardOnClickListener.onCLickCard(allOrder));


        }
    }

    public interface AllOrderCardOnClickListener {

        void onCLickCard(AllOrder allOrder);
    }
}
