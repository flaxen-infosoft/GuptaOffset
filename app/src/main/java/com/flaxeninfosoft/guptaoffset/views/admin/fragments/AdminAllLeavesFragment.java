package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.LeaveRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllLeavesBinding;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllLeavesFragment extends Fragment {

    private FragmentAdminAllLeavesBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAllLeavesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_leaves, container, false);

        binding.adminAllLeavesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllLeaves().observe(getViewLifecycleOwner(), this::setLeaves);

        return binding.getRoot();
    }

    private void setLeaves(List<Leave> leaveList) {
        LeaveRecyclerAdapter adapter = new LeaveRecyclerAdapter(leaveList, new LeaveRecyclerAdapter.SingleLeaveCardOnClickListener() {
            @Override
            public void onClickCard(Leave leave) {
                //TODO
            }
        });

        binding.adminAllLeavesRecycler.setAdapter(adapter);
    }
}