package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleDealerCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class DealerAdminRecyclerAdapter extends RecyclerView.Adapter<DealerAdminRecyclerAdapter.ViewHolder> {


    List<Dealer> dealerList;
    DealerLayoutClickListener dealerLayoutClickListener;

    public DealerAdminRecyclerAdapter(List<Dealer> dealerList, DealerLayoutClickListener dealerLayoutClickListener) {
        this.dealerList = dealerList;
        this.dealerLayoutClickListener = dealerLayoutClickListener;
    }

    @NonNull
    @Override
    public DealerAdminRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleDealerCardBinding dealerCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.single_dealer_card,parent,false);
        return new ViewHolder(dealerCardBinding,dealerLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DealerAdminRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(dealerList.get(position));
    }

    @Override
    public int getItemCount() {
        if (dealerList == null) {
            return 0;
        }
        return dealerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleDealerCardBinding binding;
        DealerLayoutClickListener dealerLayoutClickListener;
        public ViewHolder(@NonNull SingleDealerCardBinding binding,DealerLayoutClickListener dealerLayoutClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.dealerLayoutClickListener = dealerLayoutClickListener;
        }


        public void setData(Dealer dealer) {
            binding.setDealer(dealer);
            binding.getRoot().setOnClickListener(v -> dealerLayoutClickListener.onClickDealer(dealer));
            Glide.with(binding.getRoot().getContext()).load(dealer.getImage()).placeholder(R.drawable.loading_image).into(binding.singleDealerCardImage);
        }
    }

    public interface DealerLayoutClickListener {
        void onClickDealer(Dealer dealer);

    }
}
