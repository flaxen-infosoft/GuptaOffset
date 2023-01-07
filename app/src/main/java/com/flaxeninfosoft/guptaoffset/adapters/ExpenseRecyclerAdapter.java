package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleExpenseCardBinding;

import java.util.List;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseCardViewHolder> {

    private final SingleExpenseCardClickListener onClickListener;
    private final List<Expense> expenseList;

    public ExpenseRecyclerAdapter(List<Expense> expenseList, SingleExpenseCardClickListener onClickListener) {
        this.expenseList = expenseList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ExpenseCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleExpenseCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_expense_card, parent, false);
        return new ExpenseCardViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseCardViewHolder holder, int position) {
        holder.setData(expenseList.get(position));
    }

    @Override
    public int getItemCount() {
        if (expenseList == null) {
            return 0;
        }

        return expenseList.size();
    }

    public static class ExpenseCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleExpenseCardBinding binding;
        private final SingleExpenseCardClickListener onClickListener;

        public ExpenseCardViewHolder(@NonNull SingleExpenseCardBinding binding, SingleExpenseCardClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setData(Expense expense) {
            binding.setExpense(expense);

            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickListener.onClickCard(expense);
                }
            });
        }
    }

    public interface SingleExpenseCardClickListener {

        void onClickCard(Expense expense);
    }
}
