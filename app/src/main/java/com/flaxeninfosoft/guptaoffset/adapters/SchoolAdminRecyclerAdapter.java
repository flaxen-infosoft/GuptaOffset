package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleSchoolCardBinding;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;


public class SchoolAdminRecyclerAdapter extends RecyclerView.Adapter<SchoolAdminRecyclerAdapter.ViewHolder> {

    List<School> schoolList;
    SchoolLayoutClickListener schoolLayoutClickListener;

    public SchoolAdminRecyclerAdapter(List<School> schoolList, SchoolLayoutClickListener schoolLayoutClickListener) {
        this.schoolList = schoolList;
        this.schoolLayoutClickListener = schoolLayoutClickListener;
    }

    @NonNull
    @Override
    public SchoolAdminRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleSchoolCardBinding schoolCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_school_card, parent, false);
        return new ViewHolder(schoolCardBinding, schoolLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SchoolAdminRecyclerAdapter.ViewHolder holder, int position) {
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
        SchoolLayoutClickListener schoolLayoutClickListener;

        public ViewHolder(@NonNull SingleSchoolCardBinding binding, SchoolLayoutClickListener schoolLayoutClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.schoolLayoutClickListener = schoolLayoutClickListener;
        }


        public void setData(School school) {
            binding.setSchool(school);
            binding.getRoot().setOnClickListener(v -> schoolLayoutClickListener.onClickSchool(school));

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


    public interface SchoolLayoutClickListener {
        void onClickSchool(School school);

    }
}
