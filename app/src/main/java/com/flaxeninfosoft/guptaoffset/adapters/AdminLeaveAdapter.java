package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.AdminLeaveBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;

import java.util.List;

import io.paperdb.Paper;

public class AdminLeaveAdapter extends RecyclerView.Adapter<AdminLeaveAdapter.ViewHolder> {


    List<Leave> leaveList;

    Context context;

    AdminLeaveClickListener adminLeaveClickListener;

    public AdminLeaveAdapter(List<Leave> leaveList, Context context, AdminLeaveClickListener adminLeaveClickListener) {
        this.leaveList = leaveList;
        this.context = context;
        this.adminLeaveClickListener = adminLeaveClickListener;
    }  

    @NonNull
    @Override
    public AdminLeaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdminLeaveBinding adminLeaveBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.admin_leave, parent ,false);
        return new ViewHolder(adminLeaveBinding,adminLeaveClickListener);

    }




    @Override
    public void onBindViewHolder(@NonNull AdminLeaveAdapter.ViewHolder holder, int position) {
        Log.e("","ViewHolder");
        holder.setData(leaveList.get(position));
        Long Id = leaveList.get(position).getId();
        Log.e(""+Id,"");
        Log.e(""+Id,"id 2");
    }

    @Override
    public int getItemCount() {
        if (leaveList == null){
            return 0;
        }
        return leaveList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AdminLeaveBinding binding;

        AdminLeaveClickListener adminLeaveClickListener;

        public ViewHolder(AdminLeaveBinding binding , AdminLeaveClickListener adminLeaveClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.adminLeaveClickListener = adminLeaveClickListener;
        }

        public void setData(Leave leave){
            binding.setLeave(leave);
            binding.getRoot().setOnClickListener(view -> adminLeaveClickListener.onClickAdminLeave(leave));
        }
    }

    public interface AdminLeaveClickListener {
        void onClickAdminLeave(Leave leave);
    }
}
