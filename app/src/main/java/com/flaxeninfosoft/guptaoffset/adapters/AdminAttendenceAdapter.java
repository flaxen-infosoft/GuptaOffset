package com.flaxeninfosoft.guptaoffset.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleAttendanceCardBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleSchoolCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class AdminAttendenceAdapter extends RecyclerView.Adapter<AdminAttendenceAdapter.ViewHolder> {

    List<Attendance> attendanceList;
    AttendenceLayoutClickListener attendenceLayoutClickListener;

    public AdminAttendenceAdapter(List<Attendance> attendanceList, AttendenceLayoutClickListener attendenceLayoutClickListener) {
        this.attendanceList = attendanceList;
        this.attendenceLayoutClickListener = attendenceLayoutClickListener;
    }


    @NonNull
    @Override
    public AdminAttendenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleAttendanceCardBinding singleAttendanceCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_attendance_card, parent, false);
        return new ViewHolder(singleAttendanceCardBinding, attendenceLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAttendenceAdapter.ViewHolder holder, int position) {
        holder.setData(attendanceList.get(position));

    }

    @Override
    public int getItemCount() {
        if (attendanceList == null) {
            return 0;
        }
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SingleAttendanceCardBinding binding;
        AttendenceLayoutClickListener AttendenceLayoutClickListener;

        public ViewHolder(SingleAttendanceCardBinding singleAttendanceCardBinding, AttendenceLayoutClickListener attendenceLayoutClickListener) {
            super(singleAttendanceCardBinding.getRoot());
            this.binding = singleAttendanceCardBinding;
            this.AttendenceLayoutClickListener = attendenceLayoutClickListener;
        }

        public void setData(Attendance attendance) {
            binding.setAttendance(attendance);
            binding.getRoot().setOnClickListener(v -> attendenceLayoutClickListener.onClickAttendence(attendance));
            try {
                if (!attendance.getSnapIn().isEmpty()) {
                    String startMeterImage = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                    Log.i("start",startMeterImage);
                    Glide.with(binding.getRoot().getContext()).load(startMeterImage).placeholder(R.drawable.loading_image).into(binding.singleAttendanceCardTimeInImage);
                } else {
                    binding.singleAttendanceCardTimeInImage.setImageResource(R.drawable.image_not_available);
                }

                if (!attendance.getSnapOut().isEmpty()) {
                    String endMeterimage = ApiEndpoints.BASE_URL + attendance.getSnapOut();
                    Log.i("end",endMeterimage);
                    Glide.with(binding.getRoot().getContext()).load(endMeterimage).placeholder(R.drawable.loading_image).into(binding.singleAttendanceCardTimeOutImage);
                } else {
                    binding.singleAttendanceCardTimeOutImage.setImageResource(R.drawable.image_not_available);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface AttendenceLayoutClickListener {
        void onClickAttendence(Attendance attendance);
    }


}

