package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.TehsilDataLayoutBinding;
import com.flaxeninfosoft.guptaoffset.models.TehsilData;

import java.util.List;

public class EmployeeTehsilAdpter extends RecyclerView.Adapter<EmployeeTehsilAdpter.ViewHolder> {

    List<TehsilData> tehsilDataList;
    TehsilClicklistener tehsilClicklistener;

    public EmployeeTehsilAdpter(List<TehsilData> tehsilDataList, TehsilClicklistener tehsilClicklistener) {
        this.tehsilDataList = tehsilDataList;
        this.tehsilClicklistener = tehsilClicklistener;
    }

    @NonNull
    @Override
    public EmployeeTehsilAdpter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TehsilDataLayoutBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.tehsil_data_layout, parent, false);
        return new ViewHolder(binding, tehsilClicklistener);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployeeTehsilAdpter.ViewHolder holder, int position) {
        holder.setData(tehsilDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if (tehsilDataList == null) {
            return 0;
        }
        return tehsilDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TehsilDataLayoutBinding binding;
        TehsilClicklistener tehsilClicklistener;

        public ViewHolder(@NonNull TehsilDataLayoutBinding binding, TehsilClicklistener tehsilClicklistener) {
            super(binding.getRoot());
            this.binding = binding;
            this.tehsilClicklistener = tehsilClicklistener;
        }

        public void setData(TehsilData tehsilData) {
            if (tehsilData.getVisited_tehsil() == 1) {
                binding.circleIndicatorImageview.setBackgroundResource(R.drawable.green_circle_icon);
            } else {
                binding.circleIndicatorImageview.setBackgroundResource(R.drawable.red_circle_icon);
            }
            binding.setTehsil(tehsilData);
            binding.getRoot().setOnClickListener(view -> tehsilClicklistener.onClickTehsil(tehsilData));
        }
    }

    public interface TehsilClicklistener {
        void onClickTehsil(TehsilData tehsilData);
    }
}
