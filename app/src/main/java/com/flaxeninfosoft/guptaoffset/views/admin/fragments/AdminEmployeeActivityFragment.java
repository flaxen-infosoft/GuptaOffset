package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeHomeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminEmployeeActivityBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.EmployeeHistory;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Leave;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;

import java.util.List;

public class AdminEmployeeActivityFragment extends Fragment {

    private AdminViewModel viewModel;
    private FragmentAdminEmployeeActivityBinding binding;

    public AdminEmployeeActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(AdminViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_employee_activity, container, false);

        binding.adminEmployeeActivityRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        Long empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0);

        if (empId == 0) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {

            viewModel.getEmployeeHistoryById(empId).observe(getViewLifecycleOwner(), this::setHistory);
        }

        return binding.getRoot();
    }

    private void setHistory(List<EmployeeHistory> historyList) {
        EmployeeHomeRecyclerAdapter adapter = new EmployeeHomeRecyclerAdapter(historyList, new EmployeeHomeRecyclerAdapter.EmployeeHomeClickListener() {
            @Override
            public void onClickCard(Attendance attendance) {

            }

            @Override
            public void onClickCard(Leave leave) {

            }

            @Override
            public void onClickCard(School school) {

            }

            @Override
            public void onClickCard(Dealer dealer) {

            }

            @Override
            public void onClickCard(Order order) {

            }

            @Override
            public void onClickCard(Eod eod) {

            }

            @Override
            public void onCLickCard(Employee employee) {

            }

            @Override
            public void onClickCard(PaymentRequest paymentRequest) {

            }
        });

        binding.adminEmployeeActivityRecycler.setAdapter(adapter);
    }
}