package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ReportingCompletedLayoutBinding;
import com.flaxeninfosoft.guptaoffset.databinding.SingleDailyReportCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;

import java.util.List;

public class DailyReportRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Eod> eodList;
    private final SingleDailyReportOnClickListener singleDailyReportOnClickListener;

    private static final int VIEW_TYPE_LAYOUT1 = 1;
    private static final int VIEW_TYPE_LAYOUT2 = 2;

    public DailyReportRecyclerAdapter(List<Eod> eodList, SingleDailyReportOnClickListener listener) {
        this.eodList = eodList;
        this.singleDailyReportOnClickListener = listener;
    }


    @Override
    public int getItemViewType(int position) {
        String type = eodList.get(position).getType();

        if (type.equals("1")) {
            return VIEW_TYPE_LAYOUT2;
        } else {
            return VIEW_TYPE_LAYOUT1;
        }
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SingleDailyReportCardBinding singleDailyReportCardBinding;
        ReportingCompletedLayoutBinding reportingCompletedLayoutBinding;
        switch (viewType) {
            case VIEW_TYPE_LAYOUT1:
                singleDailyReportCardBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_daily_report_card, parent, false);
                return new DailyReportRecyclerAdapter.DailyReportViewHolder(singleDailyReportCardBinding, singleDailyReportOnClickListener);
            case VIEW_TYPE_LAYOUT2:
                reportingCompletedLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.reporting_completed_layout, parent, false);
                return new DailyReportRecyclerAdapter.ReportCompleteViewHolder(reportingCompletedLayoutBinding);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (holder.getItemViewType()) {
            case VIEW_TYPE_LAYOUT1:
                DailyReportViewHolder dailyReportViewHolder = (DailyReportViewHolder) holder;
                dailyReportViewHolder.setData(eodList.get(position));
                break;
            case VIEW_TYPE_LAYOUT2:
                ReportCompleteViewHolder reportCompleteViewHolder = (ReportCompleteViewHolder) holder;
                reportCompleteViewHolder.setData(eodList.get(position));
                break;
        }

    }

    @Override
    public int getItemCount() {
        if (eodList == null) {
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
                ta += Integer.parseInt(eod.getPetrolExpense());
                ta += Integer.parseInt(eod.getOtherExpense());
            } catch (Exception e) {
                //ignored
            }

//            Yeah Boiii!!!
            binding.dailyReportTa.setText(ta + "");
        }
    }

    public static class ReportCompleteViewHolder extends RecyclerView.ViewHolder {

        ReportingCompletedLayoutBinding reportingCompletedLayoutBinding;

        public ReportCompleteViewHolder(@NonNull ReportingCompletedLayoutBinding reportingCompletedLayoutBinding) {
            super(reportingCompletedLayoutBinding.getRoot());
            this.reportingCompletedLayoutBinding = reportingCompletedLayoutBinding;
        }

        public void setData(Eod eod) {
            reportingCompletedLayoutBinding.setEod(eod);
        }
    }

    public interface SingleDailyReportOnClickListener {
        void onClickCard(Eod eod);
    }
}
