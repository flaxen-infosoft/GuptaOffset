package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleSchoolCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class FirstAndLastSchoolVisitAdapter extends RecyclerView.Adapter<FirstAndLastSchoolVisitAdapter.ViewHolder> {

    List<School> schoolList;

    FirstAndLastSchoolVisitClickListener firstAndLastSchoolVisitClickListener;

    public FirstAndLastSchoolVisitAdapter(List<School> schoolList, FirstAndLastSchoolVisitClickListener firstAndLastSchoolVisitClickListener) {
        this.schoolList = schoolList;
        this.firstAndLastSchoolVisitClickListener = firstAndLastSchoolVisitClickListener;
    }

    @NonNull
    @Override
    public FirstAndLastSchoolVisitAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleSchoolCardBinding schoolCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_school_card, parent, false);
        return new ViewHolder(schoolCardBinding , firstAndLastSchoolVisitClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FirstAndLastSchoolVisitAdapter.ViewHolder holder, int position) {
        holder.setData(schoolList.get(position));
    }

    @Override
    public int getItemCount() {
        if (schoolList == null) {
            return 0;
        }
        return schoolList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleSchoolCardBinding binding;
        FirstAndLastSchoolVisitClickListener firstAndLastSchoolVisitClickListener;


        public ViewHolder(SingleSchoolCardBinding binding, FirstAndLastSchoolVisitClickListener firstAndLastSchoolVisitClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.firstAndLastSchoolVisitClickListener = firstAndLastSchoolVisitClickListener;
        }

        public void setData(School school) {
            binding.setSchool(school);
            binding.getRoot().setOnClickListener(v -> firstAndLastSchoolVisitClickListener.onClickSchool(school));

            try {
                String schoolImage = ApiEndpoints.BASE_URL + school.getSnap();
                Glide.with(binding.getRoot().getContext()).load(schoolImage).placeholder(R.drawable.loading_image).into(binding.singleSchoolCardSchoolImage);

                String specimen = ApiEndpoints.BASE_URL + school.getSpecimen();
                Glide.with(binding.getRoot().getContext()).load(specimen).placeholder(R.drawable.loading_image).into(binding.singleSchoolCardSpecimenOrderImage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public interface FirstAndLastSchoolVisitClickListener {
        void onClickSchool(School school);
    }
}
