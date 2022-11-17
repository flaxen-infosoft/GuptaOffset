package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.SingleClientCardBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;

import java.util.List;

public class ClientRecyclerAdapter extends RecyclerView.Adapter<ClientRecyclerAdapter.SingleClientCardViewHolder> {

    private final List<Client> clientList;
    private final SingleClientCardOnClickListener onClickListener;

    public ClientRecyclerAdapter(List<Client> clientList, SingleClientCardOnClickListener onClickListener) {
        this.clientList = clientList;
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public SingleClientCardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SingleClientCardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.single_client_card, parent, false);
        return new SingleClientCardViewHolder(binding, onClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleClientCardViewHolder holder, int position) {
        holder.setClient(clientList.get(position));
    }

    @Override
    public int getItemCount() {
        if (clientList == null) {
            return 0;
        }
        return clientList.size();
    }

    public static class SingleClientCardViewHolder extends RecyclerView.ViewHolder {

        private final SingleClientCardBinding binding;
        private final SingleClientCardOnClickListener onClickListener;

        public SingleClientCardViewHolder(@NonNull SingleClientCardBinding binding, SingleClientCardOnClickListener onClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onClickListener = onClickListener;
        }

        public void setClient(Client client) {
            binding.setClient(client);
            binding.getRoot().setOnClickListener(v -> onClickListener.onClickCard(client));
        }
    }

    public interface SingleClientCardOnClickListener {

        void onClickCard(Client client);
    }

}
