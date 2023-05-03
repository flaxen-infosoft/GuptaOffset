package com.flaxeninfosoft.guptaoffset.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ShowNotesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminHomeFragment;

import java.util.List;

public class ShowNotesRecyclerAdapter extends RecyclerView.Adapter<ShowNotesRecyclerAdapter.ViewHolder> {

    List<ShowNotes> showNotesList ;
    
    NotesLayoutClickListener notesLayoutClickListener;

    public ShowNotesRecyclerAdapter(List<ShowNotes> showNotesList, NotesLayoutClickListener notesLayoutClickListener) {
        this.showNotesList = showNotesList;
        this.notesLayoutClickListener = notesLayoutClickListener;
    }

    @NonNull
    @Override
    public ShowNotesRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ShowNotesBinding showNotesBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.show_notes,parent, false);
        return new ViewHolder(showNotesBinding, notesLayoutClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setData(showNotesList.get(position));


    }

    @Override
    public int getItemCount() {
        if (showNotesList == null) {
            return 0;
        }
        return showNotesList.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder {

        ShowNotesBinding binding;

        NotesLayoutClickListener notesLayoutClickListener;

        public ViewHolder(ShowNotesBinding binding , NotesLayoutClickListener notesLayoutClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.notesLayoutClickListener = notesLayoutClickListener;
        }

        public void setData(ShowNotes showNotes) {
            binding.setShowNotes(showNotes);
            binding.getRoot().setOnClickListener(v -> notesLayoutClickListener.onClickNotes(showNotes));
        }
    }

    public interface NotesLayoutClickListener {
        void onClickNotes(ShowNotes showNotes);


    }
}