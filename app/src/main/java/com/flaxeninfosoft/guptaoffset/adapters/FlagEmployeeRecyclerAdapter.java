package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ItemAdmineHomeBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.FlagEmployeeFragment;

import java.util.List;

import io.paperdb.Paper;

public class FlagEmployeeRecyclerAdapter extends RecyclerView.Adapter<FlagEmployeeRecyclerAdapter.ViewHolder> {


    private List<Employee> employeeList;
    private final SingleEmployeeCardOnClickListener onClickListener;
    Context context;

    public FlagEmployeeRecyclerAdapter(List<Employee> employeeList, Context context, SingleEmployeeCardOnClickListener onClickListener) {
        this.employeeList = employeeList;
        this.onClickListener = onClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public FlagEmployeeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAdmineHomeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_admine_home, parent, false);
        return new ViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FlagEmployeeRecyclerAdapter.ViewHolder holder, int position) {
        holder.setEmployee(employeeList.get(position));
        Long empId = employeeList.get(position).getId();
        if (context != null) {
            Paper.init(context);
            Paper.book().write("CurrentEmployeeId", employeeList.get(position).getId());
        }

        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID,empId);
        holder.binding.removeFromFlagTextview.setOnClickListener(view -> {
            FlagEmployeeFragment.removeFromFlagDialog(context, empId);
        });
        holder.binding.linearLayoutSchool.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_seprateSchoolFragment,bundle));
        holder.binding.linearLayoutDealer.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_seprateDealerFragment,bundle));
        holder.binding.addNotesTextview.setOnClickListener(view -> FlagEmployeeFragment.notesDialog(context, empId));
        holder.binding.showNotesTextview.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_showNotesFragment,bundle));
        holder.binding.leaveTextview.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_adminLeaveFragment,bundle));
        holder.binding.firstLastSchoolVisitDuration.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_firstAndLastSchoolVisitDurationFragment,bundle));
        holder.binding.linearLayoutAttendence.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_fragment_admin_attendence_section,bundle));
        holder.binding.adminTehsil.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_districtListForTehsilFragment,bundle));
        holder.binding.kilometerAdmin.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_EODForKm_TA_AndPetrolFragment,bundle));
        holder.binding.totalAmountAdmin.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_EODForKm_TA_AndPetrolFragment,bundle));
        holder.binding.petrolAdmin.setOnClickListener(view ->
                Navigation.findNavController(view).navigate(R.id.action_flagEmployeeFragment_to_EODForKm_TA_AndPetrolFragment,bundle));

    }

    @Override
    public int getItemCount() {
        if (employeeList == null) {
            return 0;
        }
        return employeeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemAdmineHomeBinding binding;

        private final SingleEmployeeCardOnClickListener onClickListener;

        public ViewHolder(@NonNull ItemAdmineHomeBinding binding, SingleEmployeeCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }


        public void setEmployee(Employee employee) {
            binding.setEmployee(employee);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(employee));

            if (employee.getFlag().equals("1")) {
                binding.removeFromFlagTextview.setVisibility(View.VISIBLE);
                binding.addToFlagTextview.setVisibility(View.GONE);
            } else {
                binding.removeFromFlagTextview.setVisibility(View.GONE);
                binding.addToFlagTextview.setVisibility(View.VISIBLE);
            }

            binding.getRoot().setOnLongClickListener(v -> onClickListener.onLongClickCard(employee));

        }
    }


    public interface SingleEmployeeCardOnClickListener {
        void onClickCard(Employee employee);

        boolean onLongClickCard(Employee employee);
    }
}
