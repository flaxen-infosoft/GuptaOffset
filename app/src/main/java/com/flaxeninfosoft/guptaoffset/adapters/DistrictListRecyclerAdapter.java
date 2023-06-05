package com.flaxeninfosoft.guptaoffset.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.DistrictListLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.DistrictData;
import com.flaxeninfosoft.guptaoffset.models.EmployeeBookOrder;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class DistrictListRecyclerAdapter extends RecyclerView.Adapter<DistrictListRecyclerAdapter.ViewHolder> {

    List<DistrictData> districtLists;
    DistrictClickListener districtClickListener;

    public DistrictListRecyclerAdapter(List<DistrictData> districtLists, DistrictClickListener districtClickListener) {
        this.districtLists = districtLists;
        this.districtClickListener = districtClickListener;
    }

    @NonNull
    @Override
    public DistrictListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DistrictListLayoutBinding districtListLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.district_list_layout, parent, false);
        return new ViewHolder(districtListLayoutBinding, districtClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictListRecyclerAdapter.ViewHolder holder, int position) {
        holder.setData(districtLists.get(position));
    }

    @Override
    public int getItemCount() {
        if (districtLists == null) {
            return 0;
        }
        else {
            Log.i("akshat",districtLists.size()+"");
            return districtLists.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        DistrictListLayoutBinding binding;
        DistrictClickListener districtClickListener;

        public ViewHolder(@NonNull DistrictListLayoutBinding binding, DistrictClickListener districtClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.districtClickListener = districtClickListener;
        }

        public void setData(DistrictData data) {
            binding.setData(data);
            binding.getRoot().setOnClickListener(view -> districtClickListener.onClickDistrict(data));

            if (data.getDistrict_image() != null) {
                String url = ApiEndpoints.BASE_URL + data.getDistrict_image();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.districtImageview);
            } else {
                binding.districtImageview.setImageResource(R.drawable.image_not_available);
            }
        }
    }


    public interface DistrictClickListener {
        void onClickDistrict(DistrictData districtData);
    }

}
