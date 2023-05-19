package com.flaxeninfosoft.guptaoffset.adapters;

import static android.media.CamcorderProfile.get;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.EmployeeSchoolListBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

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
            if (school.getSpecimen() != null) {
                binding.employeeSchoolListSpecimenOrderImage.setVisibility(View.VISIBLE);
                binding.startMeterTextview.setVisibility(View.VISIBLE);
                String url = ApiEndpoints.BASE_URL + school.getSpecimen();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.person_vector).into(binding.employeeSchoolListSpecimenOrderImage);
            } else {
                binding.employeeSchoolListSpecimenOrderImage.setVisibility(View.GONE);
                binding.startMeterTextview.setVisibility(View.GONE);
            }


            if (school.getSnap() != null) {
                binding.employeeSchoolListSchoolImage.setVisibility(View.VISIBLE);
                binding.endMeterTextview.setVisibility(View.VISIBLE);
                String url = ApiEndpoints.BASE_URL + school.getSnap();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.person_vector).into(binding.employeeSchoolListSchoolImage);
            } else {
                binding.employeeSchoolListSchoolImage.setVisibility(View.GONE);
                binding.endMeterTextview.setVisibility(View.GONE);
            }
        }
    }

    public interface EmployeeSchoolListClickListener {
        void onClickSchoolList(School school);
    }
}
