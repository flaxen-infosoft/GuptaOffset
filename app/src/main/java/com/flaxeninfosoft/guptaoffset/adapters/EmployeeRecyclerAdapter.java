package com.flaxeninfosoft.guptaoffset.adapters;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleEmployeeItemCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder> {

    private final List<Employee> allEmployeeList;
    private List<Employee> employeeList;
    private final SingleEmployeeCardOnClickListener onClickListener;

    public EmployeeRecyclerAdapter(List<Employee> employeeList, SingleEmployeeCardOnClickListener onClickListener) {
        this.employeeList = employeeList;
        this.onClickListener = onClickListener;
        this.allEmployeeList = employeeList;

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


    private Filter fRecords;

    //return the filter class object
    public Filter getFilter() {
        if(fRecords == null) {
            fRecords=new RecordFilter();
        }
        return fRecords;
    }

    //filter class
    private class RecordFilter extends Filter {

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults results = new FilterResults();

            //Implement filter logic
            // if edittext is null return the actual list
            if (constraint == null || constraint.length() == 0) {
                //No need for filter
                results.values = allEmployeeList;
                results.count = allEmployeeList.size();

            } else {
                //Need Filter
                // it matches the text  entered in the edittext and set the data in adapter list
                List<Employee> fRecords  = new ArrayList<>();

                for (Employee s : allEmployeeList) {
                    if (s.getName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        fRecords.add(s);
                    }
                }
                results.values = fRecords;
                results.count = fRecords.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            employeeList = (List<Employee>) results.values;
            notifyDataSetChanged();
        }
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

            binding.getRoot().setOnLongClickListener(v -> onClickListener.onLongClickCard(employee));

            try {
                int pendingMessages = Integer.parseInt(employee.getPendingMessages());

                if (pendingMessages == 0) {
//                    binding.employeeCardBackground.setBackgroundColor(Color.WHITE);
                    binding.employeeCardPendingMessageLayout.setVisibility(View.GONE);
                } else {
//                    binding.employeeCardBackground.setBackgroundColor(Color.parseColor("#DCF8C6"));
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

        boolean onLongClickCard(Employee employee);
    }
}
