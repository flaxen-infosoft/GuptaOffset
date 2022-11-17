package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEmployeeCardBinding;
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
        SingleEmployeeCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_employee_card, parent, false);

        return new EmployeeViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.setEmployee(employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (employeeList == null){
            return 0;
        }
        return employeeList.size();
    }


    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final SingleEmployeeCardBinding binding;
        private final SingleEmployeeCardOnClickListener onClickListener;

        public EmployeeViewHolder(@NonNull SingleEmployeeCardBinding binding, SingleEmployeeCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setEmployee(Employee employee) {
            binding.setEmployee(employee);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(employee));
        }
    }

    public interface SingleEmployeeCardOnClickListener{

        void onClickCard(Employee employee);
    }
}
