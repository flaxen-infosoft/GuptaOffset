package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleDailyReportCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;

import java.util.List;

public class DailyReportRecyclerAdapter extends RecyclerView.Adapter<DailyReportRecyclerAdapter.DailyReportViewHolder> {

    private List<Eod> eodList;
    private SingleDailyReportOnClickListener listener;

    public DailyReportRecyclerAdapter(List<Eod> eodList, SingleDailyReportOnClickListener listener){
        this.eodList = eodList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DailyReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleDailyReportCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_daily_report_card, parent, false);
        return new DailyReportRecyclerAdapter.DailyReportViewHolder(binding, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull DailyReportViewHolder holder, int position) {
        holder.setData(eodList.get(position));
    }

    @Override
    public int getItemCount() {
       if (eodList == null){
           return 0;
       }
       return eodList.size();
    }

    public static class DailyReportViewHolder extends RecyclerView.ViewHolder {

        private final SingleDailyReportCardBinding binding;
        private final SingleDailyReportOnClickListener onClickListener;

        public DailyReportViewHolder(@NonNull SingleDailyReportCardBinding binding, SingleDailyReportOnClickListener onClickListener) {
            super(binding.getRoot());

            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Eod eod) {
            binding.setEod(eod);
            binding.getRoot().setOnClickListener(view -> onClickListener.onClickCard(eod));
            long ta = 0;
            try {
                ta+= Integer.parseInt(eod.getPetrolExpense());
                ta+= Integer.parseInt(eod.getOtherExpense());
            }catch (Exception e){
                //ignored
            }

            binding.dailyReportTa.setText(ta+"");
        }
    }

    public interface SingleDailyReportOnClickListener {
        void onClickCard(Eod eod);
    }
}
