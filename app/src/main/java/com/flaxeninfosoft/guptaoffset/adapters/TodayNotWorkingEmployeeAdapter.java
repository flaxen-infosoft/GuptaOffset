package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.EmployeeLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;

import java.util.List;

public class TodayNotWorkingEmployeeAdapter extends RecyclerView.Adapter<TodayNotWorkingEmployeeAdapter.ViewHolder> {


    List<Employee> employeeList;
    EmployeeLayoutClickListener employeeLayoutClickListener;

    public TodayNotWorkingEmployeeAdapter(List<Employee> employeeList, EmployeeLayoutClickListener employeeLayoutClickListener) {
        this.employeeList = employeeList;
        this.employeeLayoutClickListener = employeeLayoutClickListener;
    }

    @NonNull
    @Override
    public TodayNotWorkingEmployeeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmployeeLayoutBinding employeeLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_layout, parent, false);
        return new ViewHolder(employeeLayoutBinding, employeeLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayNotWorkingEmployeeAdapter.ViewHolder holder, int position) {
        holder.setData(employeeList.get(position));
    }

    @Override
    public int getItemCount() {
        if (employeeList == null) {
            return 0;
        }
        return employeeList.size();
    }

    public interface EmployeeLayoutClickListener {
        void onClickEmployee(Employee employee);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EmployeeLayoutClickListener employeeLayoutClickListener;
        EmployeeLayoutBinding binding;

        public ViewHolder(@NonNull  EmployeeLayoutBinding binding, EmployeeLayoutClickListener employeeLayoutClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.employeeLayoutClickListener = employeeLayoutClickListener;
        }


        public void setData(Employee employee) {
            binding.setEmployee(employee);
            binding.getRoot().setOnClickListener(v -> employeeLayoutClickListener.onClickEmployee(employee));
        }

    }
}
