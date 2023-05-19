package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEmployeeAbsentLeaveBinding;
import com.flaxeninfosoft.guptaoffset.models.EmployeeAbsentLeave;
import com.flaxeninfosoft.guptaoffset.models.Leave;

import java.util.List;

public class EmployeeLeaveAdapter extends RecyclerView.Adapter<EmployeeLeaveAdapter.ViewHolder> {

    List<EmployeeAbsentLeave> employeeAbsentLeaves;

    Context context;
    SingleEmployeeAbsentLeaveBinding singleEmployeeAbsentLeaveBinding;

    EmployeeAbsentLeaveClickListener employeeAbsentLeaveClickListener;

    public EmployeeLeaveAdapter(List<EmployeeAbsentLeave> employeeAbsentLeaves, Context context, EmployeeAbsentLeaveClickListener employeeAbsentLeaveClickListener){
        this.employeeAbsentLeaves = employeeAbsentLeaves;
        this.employeeAbsentLeaveClickListener = employeeAbsentLeaveClickListener;
    }

    @NonNull
    @Override
    public EmployeeLeaveAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         SingleEmployeeAbsentLeaveBinding singleEmployeeAbsentLeaveBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_employee_absent_leave,parent,false);
        return new ViewHolder(singleEmployeeAbsentLeaveBinding,employeeAbsentLeaveClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeLeaveAdapter.ViewHolder holder, int position) {
        holder.setData(employeeAbsentLeaves.get(position));
        Long Id = employeeAbsentLeaves.get(position).getId();

    }

    @Override
    public int getItemCount() {
        if (employeeAbsentLeaves == null){
            return 0;

        }
        return employeeAbsentLeaves.size();
    }

    public interface EmployeeAbsentLeaveClickListener {
        void employeeLeave(EmployeeAbsentLeave employeeAbsentLeave);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleEmployeeAbsentLeaveBinding binding;
        EmployeeAbsentLeaveClickListener employeeLeaveclicklistener;


        public ViewHolder(SingleEmployeeAbsentLeaveBinding binding,EmployeeAbsentLeaveClickListener employeeLeaveclicklistener) {
            super(binding.getRoot());
            this.binding = binding;
            this.employeeLeaveclicklistener = employeeLeaveclicklistener;
        }
        public void setData(EmployeeAbsentLeave employeeAbsentLeave){
            binding.setAbsentleave(employeeAbsentLeave);
            binding.getRoot().setOnClickListener(view -> employeeAbsentLeaveClickListener.employeeLeave(employeeAbsentLeave));
        }
    }
}
