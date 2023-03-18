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
import com.flaxeninfosoft.guptaoffset.models.Lr;
import com.flaxeninfosoft.guptaoffset.models.Message;
import com.flaxeninfosoft.guptaoffset.models.Order;
import com.flaxeninfosoft.guptaoffset.models.PaymentRequest;
import com.flaxeninfosoft.guptaoffset.models.School;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminViewModel;
import com.flaxeninfosoft.guptaoffset.views.admin.AdminMainActivity;
import com.flaxeninfosoft.guptaoffset.views.superEmployee.SuperEmployeeMainActivity;

import java.util.List;

public class AdminEmployeeActivityFragment extends Fragment {

    private AdminViewModel viewModel;
    private FragmentAdminEmployeeActivityBinding binding;
    private Long empId;

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
        binding.setMessage(new Message());

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(true);
        binding.adminEmployeeActivityRecycler.setLayoutManager(lm);

        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0);
        binding.adminEmployeeActivitySendMessageFab.setOnClickListener(this::sendMessage);

        binding.adminEmployeeActivityAttachmentsBtn.setOnClickListener(listener -> {
            if (binding.superEmployeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.superEmployeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.superEmployeeHomeCard.setVisibility(View.VISIBLE);
            }
        });


        if (empId == 0) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(binding.getRoot()).navigateUp();
        } else {

            viewModel.getEmployeeHistoryById(empId).observe(getViewLifecycleOwner(), this::setHistory);
            viewModel.getEmployeeById(empId).observe(getViewLifecycleOwner(), employee -> {
                if (employee != null && employee.getName() != null) {
                  try{
                      ((AdminMainActivity) requireActivity()).setupActionBar(binding.adminEmployeeActivityToolbar, employee.getName());
                  }catch (Exception e){
                      ((SuperEmployeeMainActivity) requireActivity()).setupActionBar(binding.adminEmployeeActivityToolbar, employee.getName());
                  }
                }
            });

            binding.adminEmployeeActivitySendLr.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EMPLOYEE_ID, empId);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.sendLrFragment, bundle);
            });

        }

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void showToast(String s) {
        if (!s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void sendMessage(View view) {
        if (!binding.getMessage().getMessage().trim().isEmpty()) {
            binding.getMessage().setReceiverId(empId);
            viewModel.sendMessage(binding.getMessage()).observe(getViewLifecycleOwner(),
                    message -> viewModel.getEmployeeHistoryById(empId).observe(getViewLifecycleOwner(), employeeHistories -> setHistory(employeeHistories)));
            binding.setMessage(new Message());
        }
    }

    private void setHistory(List<EmployeeHistory> historyList) {
        EmployeeHomeRecyclerAdapter adapter = new EmployeeHomeRecyclerAdapter(historyList, getActivity().getApplication(), new EmployeeHomeRecyclerAdapter.EmployeeHomeClickListener() {
            @Override
            public void onClickCard(Attendance attendance) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ATN_ID, attendance.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.attendanceProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Leave leave) {

            }

            @Override
            public void onClickCard(School school) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.SCHOOL_ID, school.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminEmployeeActivityFragment_to_schoolProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Dealer dealer) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.DEALER_ID, dealer.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminEmployeeActivityFragment_to_dealerProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Order order) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ORDER_ID, order.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminEmployeeActivityFragment_to_orderProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Eod eod) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EOD_ID, eod.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminEmployeeActivityFragment_to_eodProfileFragment, bundle);
            }

            @Override
            public void onCLickCard(Employee employee) {

            }

            @Override
            public void onClickCard(PaymentRequest paymentRequest) {

            }

            @Override
            public void onClickCard(Message message) {

            }

            @Override
            public void onClickCard(Lr lr) {
                String inImage = ApiEndpoints.BASE_URL + lr.getImage();
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", inImage);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            }
        });

        binding.adminEmployeeActivityRecycler.setAdapter(adapter);
        if (historyList != null && historyList.size() > 1) {
            binding.adminEmployeeActivityRecycler.scrollToPosition(historyList.size() - 1);
        }
    }
}