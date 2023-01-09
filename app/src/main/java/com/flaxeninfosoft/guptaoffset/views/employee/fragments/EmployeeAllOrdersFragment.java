package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.OrderRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllOrdersBinding;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;

public class EmployeeAllOrdersFragment extends Fragment {

    private FragmentEmployeeAllOrdersBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAllOrdersFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_orders, container, false);

        viewModel.getCurrentEmployeeOrders().observe(getViewLifecycleOwner(), this::updateOrdersList);

        return binding.getRoot();
    }

    private void updateOrdersList(List<Order> orders) {
        OrderRecyclerAdapter adapter = new OrderRecyclerAdapter(orders, new OrderRecyclerAdapter.SingleOrderCardOnClickListener() {
            @Override
            public void onCLickCard(Order order) {
                Bundle bundle = new Bundle();
                bundle.putLong(getString(R.string.key_leave_id), order.getId());
//                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeAllOrdersFragment_to_employeeOrderDetailsFragment, bundle);
            }
        });
        binding.employeeOrderListRecycler.setAdapter(adapter);
    }
}