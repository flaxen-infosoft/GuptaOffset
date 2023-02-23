package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleLeaveCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;

import java.util.List;

public class LeaveRecyclerAdapter extends RecyclerView.Adapter<LeaveRecyclerAdapter.LeaveCardViewHolder> {

    private List<Leave> leaveList;
    private SingleLeaveCardOnClickListener leaveCardOnClickListener;

    public LeaveRecyclerAdapter(List<Leave> leaveList, SingleLeaveCardOnClickListener onClickListener) {
        this.leaveList = leaveList;
        this.leaveCardOnClickListener = onClickListener;
    }

    @NonNull
    @Override
    public LeaveCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleLeaveCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_leave_card, parent, false);
        return new LeaveCardViewHolder(binding, leaveCardOnClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull LeaveCardViewHolder holder, int position) {
        holder.setData(leaveList.get(position));
    }

    @Override
    public int getItemCount() {
        if (leaveList == null) {
            return 0;
        }
        return leaveList.size();
    }

    public static class LeaveCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleLeaveCardBinding binding;
        private final SingleLeaveCardOnClickListener onClickListener;

        public LeaveCardViewHolder(@NonNull SingleLeaveCardBinding binding, SingleLeaveCardOnClickListener onClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Leave leave) {
            binding.setLeave(leave);

            binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(leave));
        }
    }

    public interface SingleLeaveCardOnClickListener {

        void onClickCard(Leave leave);
    }
//    Yeah Boiii!!!
}
