package com.flaxeninfosoft.guptaoffset.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.DistrictNameLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.DistrictData;

import java.util.List;

public class DistrictListForTehsilAdapter extends RecyclerView.Adapter<DistrictListForTehsilAdapter.ViewHolder> {
    List<DistrictData> districtLists;
    DistrictNameClickListener districtClickListener;


    public DistrictListForTehsilAdapter(List<DistrictData> districtLists, DistrictNameClickListener districtClickListener) {
        this.districtLists = districtLists;
        this.districtClickListener = districtClickListener;
    }

    @NonNull
    @Override
    public DistrictListForTehsilAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DistrictNameLayoutBinding districtNameLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.district_name_layout, parent, false);
        return new ViewHolder(districtNameLayoutBinding, districtClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictListForTehsilAdapter.ViewHolder holder, int position) {
        holder.setData(districtLists.get(position));
    }

    @Override
    public int getItemCount() {
        if (districtLists == null) {
            return 0;
        } else {
            return districtLists.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        DistrictNameLayoutBinding binding;
        DistrictNameClickListener districtClickListener;

        public ViewHolder(@NonNull DistrictNameLayoutBinding binding, DistrictNameClickListener districtClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.districtClickListener = districtClickListener;
        }


        public void setData(DistrictData data) {
            binding.setData(data);
            binding.getRoot().setOnClickListener(view -> districtClickListener.onClickDistrict(data));

        }
    }


    public interface DistrictNameClickListener {
        void onClickDistrict(DistrictData districtName);
    }
}

