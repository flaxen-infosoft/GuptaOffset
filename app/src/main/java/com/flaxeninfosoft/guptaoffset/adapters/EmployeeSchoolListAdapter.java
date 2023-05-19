package com.flaxeninfosoft.guptaoffset.adapters;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.EmployeeSchoolListBinding;
import com.flaxeninfosoft.guptaoffset.models.School;

import java.util.List;

import io.paperdb.Paper;

public class EmployeeSchoolListAdapter extends RecyclerView.Adapter<EmployeeSchoolListAdapter.ViewHolder> {

    List<School> schoolList;
    EmployeeSchoolListClickListener employeeSchoolListClickListener;

    Context context;

    public EmployeeSchoolListAdapter(List<School> schoolList,  EmployeeSchoolListClickListener employeeSchoolListClickListener) {
        this.schoolList = schoolList;
        this.employeeSchoolListClickListener = employeeSchoolListClickListener;
    }

    @Override
    public EmployeeSchoolListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        EmployeeSchoolListBinding employeeSchoolListBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.employee_school_list,parent,false);
        return new ViewHolder(employeeSchoolListBinding,employeeSchoolListClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeSchoolListAdapter.ViewHolder holder, int position) {
        holder.setData(schoolList.get(position));
        Long Id = schoolList.get(position).getId();
        if (context != null){
            Paper.init(context);
        }
        Paper.book().write("CurrentAbove80Id",Id);
    }

    @Override
    public int getItemCount() {
        if (schoolList == null){
            return 0;
        }
        return schoolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        EmployeeSchoolListBinding binding;
        EmployeeSchoolListClickListener employeeSchoolListClickListener;

        public ViewHolder(@NonNull EmployeeSchoolListBinding binding , EmployeeSchoolListClickListener employeeSchoolListClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.employeeSchoolListClickListener = employeeSchoolListClickListener;
        }


        public void setData(School school) {
            binding.setSchoolList(school);
            binding.getRoot().setOnClickListener(view -> employeeSchoolListClickListener.onClickSchoolList(school));
        }
    }

    public interface EmployeeSchoolListClickListener {
        void onClickSchoolList(School school);
    }
}
