package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EodRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllEodsBinding;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllEodsFragment extends Fragment {

    private AdminMainViewModel viewModel;
    private FragmentAdminAllEodsBinding binding;

    public AdminAllEodsFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_eods, container, false);

        binding.adminAllEodsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllEods().observe(getViewLifecycleOwner(), this::setEods);

        return binding.getRoot();
    }

    private void setEods(List<Eod> eodList) {
        EodRecyclerAdapter adapter = new EodRecyclerAdapter(eodList, new EodRecyclerAdapter.SingleEodCardOnClickListener() {
            @Override
            public void onClickCard(Eod eod) {
                //TODO
            }
        });

        binding.adminAllEodsRecycler.setAdapter(adapter);
    }
}