package com.flaxeninfosoft.guptaoffset.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SchoolSmallLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.School;

import java.util.List;

public class SchoolAccordingTehsilAdapter extends RecyclerView.Adapter<SchoolAccordingTehsilAdapter.ViewHolder> {

    List<School> schoolList;
    SchoolClickListener schoolClickListener;

    public SchoolAccordingTehsilAdapter(List<School> schoolList, SchoolClickListener schoolClickListener) {
        this.schoolList = schoolList;
        this.schoolClickListener = schoolClickListener;
    }

    @NonNull
    @Override
    public SchoolAccordingTehsilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SchoolSmallLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.school_small_layout,parent,false);
        return new ViewHolder(binding,schoolClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAccordingTehsilAdapter.ViewHolder holder, int position) {
        holder.setData(schoolList.get(position));
    }

    @Override
    public int getItemCount() {
        if (schoolList == null) {
            return 0;
        } else {
            return schoolList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SchoolSmallLayoutBinding binding;
        SchoolClickListener schoolClickListener;
        public ViewHolder(SchoolSmallLayoutBinding binding,SchoolClickListener schoolClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.schoolClickListener = schoolClickListener;
        }

        public void setData(School school) {
            binding.setSchool(school);
            binding.getRoot().setOnClickListener(view -> schoolClickListener.onClickSchool(school));
        }
    }

    public interface SchoolClickListener {
        void onClickSchool(School school);
    }
}
