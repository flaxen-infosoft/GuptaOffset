package com.flaxeninfosoft.guptaoffset.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.Above80kmLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

import io.paperdb.Paper;

public class Above80kmRecyclerAdapter extends RecyclerView.Adapter<Above80kmRecyclerAdapter.ViewHolder> {


    List<Attendance> attendanceList;

    Above80kmClickListener above80kmClickListener;

    Context context;

    public Above80kmRecyclerAdapter(List<Attendance> attendanceList, Above80kmClickListener above80kmClickListener) {
        this.attendanceList = attendanceList;
        this.above80kmClickListener = above80kmClickListener;
    }

    @NonNull
    @Override
    public Above80kmRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Above80kmLayoutBinding above80kmLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.above_80km_layout, parent, false);
        return new ViewHolder(above80kmLayoutBinding, above80kmClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull Above80kmRecyclerAdapter.ViewHolder holder, int position) {

        holder.setData(attendanceList.get(position));
        Long Id = attendanceList.get(position).getId();
        if (context != null) {
            Paper.init(context);
        }
        Paper.book().write("CurrentAbove80Id", Id);

    }

    @Override
    public int getItemCount() {
        if (attendanceList == null) {
            return 0;
        }
        return attendanceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        Above80kmLayoutBinding binding;

        Above80kmClickListener above80kmClickListener;

        public ViewHolder(Above80kmLayoutBinding binding, Above80kmClickListener above80kmClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.above80kmClickListener = above80kmClickListener;

        }

        public void setData(Attendance attendance) {
            binding.setAbove80km(attendance);
            binding.getRoot().setOnClickListener(view -> above80kmClickListener.onClickAbove80kmDriveReport(attendance));
            if (attendance.getSnapIn() != null) {
                binding.startMeterImageview.setVisibility(View.VISIBLE);
                binding.startMeterTextview.setVisibility(View.VISIBLE);
                String url = ApiEndpoints.BASE_URL + attendance.getSnapIn();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.startMeterImageview);
            } else {
                binding.startMeterImageview.setVisibility(View.GONE);
                binding.startMeterTextview.setVisibility(View.GONE);
                binding.viewLine.setVisibility(View.GONE);
            }


            if (attendance.getSnapOut() != null) {
                binding.endMeterImageview.setVisibility(View.VISIBLE);
                binding.endMeterTextview.setVisibility(View.VISIBLE);
                String url = ApiEndpoints.BASE_URL + attendance.getSnapOut();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.endMeterImageview);
            } else {
                binding.endMeterImageview.setVisibility(View.GONE);
                binding.endMeterTextview.setVisibility(View.GONE);
                binding.viewLine.setVisibility(View.GONE);
            }
        }
    }

    public interface Above80kmClickListener {
        void onClickAbove80kmDriveReport(Attendance attendance);

    }
}
