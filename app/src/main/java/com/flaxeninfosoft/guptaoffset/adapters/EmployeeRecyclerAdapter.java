package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ItemAdmineHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminHomeFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.ShowNotesFragment;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class EmployeeRecyclerAdapter extends RecyclerView.Adapter<EmployeeRecyclerAdapter.EmployeeViewHolder> {

    private final List<Employee> allEmployeeList;
    private List<Employee> employeeList;
    private final SingleEmployeeCardOnClickListener onClickListener;
    Context context;
    ItemAdmineHomeBinding binding;

    public EmployeeRecyclerAdapter(List<Employee> employeeList, Context context, SingleEmployeeCardOnClickListener onClickListener) {
        this.employeeList = employeeList;
        this.onClickListener = onClickListener;
        this.allEmployeeList = employeeList;
        this.context = context;

    }

    public EmployeeRecyclerAdapter(List<Employee> employeeList, SingleEmployeeCardOnClickListener onClickListener) {
        this.employeeList = employeeList;
        this.onClickListener = onClickListener;
        this.allEmployeeList = employeeList;

    }

    @NonNull
    @Override
    public EmployeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_admine_home, parent, false);

        return new EmployeeViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeViewHolder holder, int position) {
        holder.setEmployee(employeeList.get(position));
        Long empId = employeeList.get(position).getId();
        if (context != null) {
            Paper.init(context);
        }
        Paper.book().write("CurrentEmployeeId", empId);
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, empId);
        holder.binding.linearLayoutSchool.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_seprateSchoolFragment, bundle));
        holder.binding.linearLayoutDealer.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_seprateDealerFragment, bundle));
        holder.binding.addToFlagTextview.setOnClickListener(view -> AdminHomeFragment.addToFlagDialog(context, empId));

        holder.binding.removeFromFlagTextview.setOnClickListener(view -> {
            AdminHomeFragment.removeFromFlagDialog(context, empId);
        });
        holder.binding.addNotesTextview.setOnClickListener(view -> AdminHomeFragment.notesDialog(context, empId));

        holder.binding.showNotesTextview.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_showNotesFragment, bundle));
        holder.binding.leaveTextview.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_adminLeaveFragment, bundle));
        holder.binding.districtLayout.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_districtListFragment, bundle));
        holder.binding.linearLayoutAttendence.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_fragment_admin_attendence_section, bundle));
        holder.binding.adminTehsil.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_adminHomeFragment_to_districtListForTehsilFragment,bundle));
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
        if (fRecords == null) {
            fRecords = new RecordFilter();
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
                List<Employee> fRecords = new ArrayList<>();

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
        protected void publishResults(CharSequence constraint, FilterResults results) {

            //it set the data from filter to adapter list and refresh the recyclerview adapter
            employeeList = (List<Employee>) results.values;
            notifyDataSetChanged();
        }
    }


    public static class EmployeeViewHolder extends RecyclerView.ViewHolder {

        private final ItemAdmineHomeBinding binding;

        private final SingleEmployeeCardOnClickListener onClickListener;

        public EmployeeViewHolder(@NonNull ItemAdmineHomeBinding binding, SingleEmployeeCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setEmployee(Employee employee) {
            binding.setEmployee(employee);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(employee));

            try {
                if (employee.getFlag().equals("1")) {
                    binding.removeFromFlagTextview.setVisibility(View.VISIBLE);
                    binding.addToFlagTextview.setVisibility(View.GONE);
                } else {
                    binding.removeFromFlagTextview.setVisibility(View.GONE);
                    binding.addToFlagTextview.setVisibility(View.VISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            binding.getRoot().setOnLongClickListener(v -> onClickListener.onLongClickCard(employee));

//            try {
//                int pendingMessages = Integer.parseInt(employee.getPendingMessages());
//
//                if (pendingMessages == 0) {
//                    binding.constraintLayout.setBackgroundColor(Color.WHITE);
//                    binding.employeeCardPendingMessageLayout.setVisibility(View.GONE);
//                } else {
//                    binding.constraintLayout.setBackgroundColor(Color.parseColor("#DCF8C6"));
//                    binding.employeeCardPendingMessageLayout.setVisibility(View.VISIBLE);
//                    binding.employeeCardPendingMessageCount.setText("" + pendingMessages);
//                }
//
//            } catch (Exception e) {
//                binding.employeeCardPendingMessageLayout.setVisibility(View.GONE);
//            }
        }
    }

    public interface SingleEmployeeCardOnClickListener {
        void onClickCard(Employee employee);

        boolean onLongClickCard(Employee employee);
    }
}
