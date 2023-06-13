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
import com.flaxeninfosoft.guptaoffset.databinding.SingleEodCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;

import java.util.List;

public class EODForKmPetrolTotalAmountAdapter extends RecyclerView.Adapter<EODForKmPetrolTotalAmountAdapter.ViewHolder> {

    List<Eod> eodList;
    EodForKmTaPetrolClickListener eodForKmTaPetrolClickListener;

    public EODForKmPetrolTotalAmountAdapter(List<Eod> eodList, EodForKmTaPetrolClickListener eodForKmTaPetrolClickListener) {
        this.eodList = eodList;
        this.eodForKmTaPetrolClickListener = eodForKmTaPetrolClickListener;
    }

    @NonNull
    @Override
    public EODForKmPetrolTotalAmountAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleEodCardBinding singleEodCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_eod_card,parent,false);
        return new ViewHolder(singleEodCardBinding , eodForKmTaPetrolClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull EODForKmPetrolTotalAmountAdapter.ViewHolder holder, int position) {
        holder.setData(eodList.get(position));
    }

    @Override
    public int getItemCount() {
        if (eodList == null) {
            return 0;
        }
        return eodList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        SingleEodCardBinding binding;

        EodForKmTaPetrolClickListener eodForKmTaPetrolClickListener;

        public ViewHolder(SingleEodCardBinding binding, EodForKmTaPetrolClickListener eodForKmTaPetrolClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.eodForKmTaPetrolClickListener = eodForKmTaPetrolClickListener;

        }

        public void setData(Eod eod) {
            if (eod == null) {
                return;
            }
            binding.setEod(eod);
            if (eod.getOtherExpense() == null) {
                eod.setOtherExpense("0");
            }

            Log.i("expense",eod.getExpenseImage());
            Log.i("petrol",eod.getPetrolExpenseImage());

            if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
                String url = ApiEndpoints.BASE_URL + eod.getExpenseImage();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.otherExpenseImageview);
            }
            else {
                binding.otherExpenseImageview.setImageResource(R.drawable.image_not_available);
            }

            if (eod.getPetrolExpenseImage() != null && !eod.getPetrolExpenseImage().isEmpty()) {
                String url = ApiEndpoints.BASE_URL + eod.getPetrolExpenseImage();
                Glide.with(binding.getRoot().getContext()).load(url).placeholder(R.drawable.loading_image).into(binding.petrolExpenseImageview);
            } else {
//                binding.petrolExpenseImageview.setVisibility(View.GONE);
//                binding.textPetrolexpenseImage.setVisibility(View.GONE);
                binding.petrolExpenseImageview.setImageResource(R.drawable.image_not_available);
            }

            binding.getRoot().setOnClickListener(v -> eodForKmTaPetrolClickListener.onClickCard(eod));
        }
    }

    public interface EodForKmTaPetrolClickListener {

        void onClickCard(Eod eod);
    }
}
