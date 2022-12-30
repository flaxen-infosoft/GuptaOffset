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
import com.flaxeninfosoft.guptaoffset.adapters.OrderRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllOrdersBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllOrdersFragment extends Fragment {

    private FragmentAdminAllOrdersBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAllOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel  = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminMainViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_orders, container, false);
        binding.adminAllOrdersRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getAllOrders().observe(getViewLifecycleOwner(), this::updateOrderList);

        return binding.getRoot();
    }

    private void updateOrderList(List<Order> orders) {
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(orders, new OrderRecyclerAdapter.SingleOrderCardOnClickListener() {
            @Override
            public void onCLickCard(Order order) {
                onClickOrder(order);
            }
        });
        binding.adminAllOrdersRecycler.setAdapter(adapter);
    }

    private void onClickOrder(Order order) {
        //TODO
    }

}