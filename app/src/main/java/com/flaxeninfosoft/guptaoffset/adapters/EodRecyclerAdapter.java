package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEodCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;

import java.util.List;

public class EodRecyclerAdapter extends RecyclerView.Adapter<EodRecyclerAdapter.EodCardViewHolder> {

    private final List<Eod> eodList;
    private final SingleEodCardOnClickListener onClickListener;

    public EodRecyclerAdapter(List<Eod> eodList, SingleEodCardOnClickListener onClickListener) {
        this.eodList = eodList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EodCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleEodCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_eod_card, parent, false);
        return new EodCardViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EodCardViewHolder holder, int position) {
        holder.setData(eodList.get(position));
    }

    @Override
    public int getItemCount() {
        if (eodList == null) {
            return 0;
        }
        return eodList.size();
    }

    public static class EodCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleEodCardBinding binding;
        private final SingleEodCardOnClickListener onClickListener;

        public EodCardViewHolder(@NonNull SingleEodCardBinding binding, SingleEodCardOnClickListener onClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Eod eod) {
            binding.setEod(eod);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickCard(eod);
                }
            });
        }
    }

    public interface SingleEodCardOnClickListener {
        void onClickCard(Eod eod);
    }

}
