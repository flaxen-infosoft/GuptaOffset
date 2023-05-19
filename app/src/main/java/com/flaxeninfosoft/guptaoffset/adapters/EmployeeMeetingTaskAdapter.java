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
import com.flaxeninfosoft.guptaoffset.databinding.MeetingTaskLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.MeetingTask;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminHomeFragment;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.MeetingOrTaskFragment;

import java.util.List;

import io.paperdb.Paper;

public class EmployeeMeetingTaskAdapter extends RecyclerView.Adapter<EmployeeMeetingTaskAdapter.ViewHolder> {

    List<MeetingTask> meetingTaskList;
    Context context;
    EmployeeMeetingTaskClickListener employeeMeetingTaskClickListener;

    public EmployeeMeetingTaskAdapter(List<MeetingTask> meetingTaskList, Context context, EmployeeMeetingTaskClickListener employeeMeetingTaskClickListener) {
        this.meetingTaskList = meetingTaskList;
        this.context = context;
        this.employeeMeetingTaskClickListener = employeeMeetingTaskClickListener;
    }

    @NonNull
    @Override
    public EmployeeMeetingTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MeetingTaskLayoutBinding meetingTaskLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.meeting_task_layout, parent, false);
        return new ViewHolder(meetingTaskLayoutBinding, employeeMeetingTaskClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeMeetingTaskAdapter.ViewHolder holder, int position) {
        holder.setData(meetingTaskList.get(position));
        Long empId = Paper.book().read("EmpIDInMeetingTask");
        Long Id = meetingTaskList.get(position).getId();
        if (context != null) {
            Paper.init(context);
        }
        Paper.book().write("CurrentTaskId", Id);

//        holder.binding.addMeetingTextview.setOnClickListener(view -> AdminHomeFragment.addMeetingTaskDialog(context, empId));

        holder.binding.deleteMeetingImg.setOnClickListener(view -> {
            MeetingOrTaskFragment.deleteTaskDialog(context, empId, Id);
        });
    }

    @Override
    public int getItemCount() {
        return meetingTaskList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        MeetingTaskLayoutBinding binding;
        EmployeeMeetingTaskClickListener employeeMeetingTaskClickListener;


        public ViewHolder(MeetingTaskLayoutBinding binding, EmployeeMeetingTaskClickListener employeeMeetingTaskClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.employeeMeetingTaskClickListener = employeeMeetingTaskClickListener;

        }

        public void setData(MeetingTask meetingTask) {
            binding.setMeetingTask(meetingTask);
            binding.getRoot().setOnClickListener(view -> employeeMeetingTaskClickListener.onClickMeetingTask(meetingTask));
        }
    }

    public interface EmployeeMeetingTaskClickListener {
        void onClickMeetingTask(MeetingTask meetingTask);
    }
}
