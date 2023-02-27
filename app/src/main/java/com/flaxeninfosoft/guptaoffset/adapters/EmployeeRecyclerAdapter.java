package com.flaxeninfosoft.guptaoffset.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEmployeeItemCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;

import java.util.List;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder> {

    private final List<Employee> employeeList;
    private final SingleEmployeeCardOnClickListener onClickListener;

    public EmployeeRecyclerAdapter(List<Employee> employeeList, SingleEmployeeCardOnClickListener onClickListener) {
        this.employeeList = employeeList;
        this.onClickListener = onClickListener;

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleEmployeeItemCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_employee_item_card, parent, false);

        return new EmployeeViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.setEmployee(employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (employeeList == null) {
            return 0;
        }
        return employeeList.size();
    }


    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final SingleEmployeeItemCardBinding binding;

        private final SingleEmployeeCardOnClickListener onClickListener;

        public EmployeeViewHolder(@NonNull SingleEmployeeItemCardBinding binding, SingleEmployeeCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setEmployee(Employee employee) {
            binding.setEmployee(employee);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(employee));

            try {
                int pendingMessages = Integer.parseInt(employee.getPendingMessages());
//                Yeah Boii!!!
                if (pendingMessages == 0) {
                    binding.employeeCardPendingMessageLayout.setVisibility(View.GONE);
                } else {
                    binding.employeeCardPendingMessageLayout.setVisibility(View.VISIBLE);
                    binding.employeeCardPendingMessageCount.setText("" + pendingMessages);
                }

            } catch (Exception e) {
                binding.employeeCardPendingMessageLayout.setVisibility(View.GONE);
            }
        }
    }

    public interface SingleEmployeeCardOnClickListener {
        void onClickCard(Employee employee);
    }
}
