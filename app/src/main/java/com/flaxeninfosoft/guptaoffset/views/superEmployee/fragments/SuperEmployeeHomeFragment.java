package com.flaxeninfosoft.guptaoffset.views.superEmployee.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSuperEmployeeHomeBinding;
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
import com.flaxeninfosoft.guptaoffset.viewModels.SuperEmployeeViewModel;

import java.util.List;

public class SuperEmployeeHomeFragment extends Fragment {

    private FragmentSuperEmployeeHomeBinding binding;
    private SuperEmployeeViewModel viewModel;

    public SuperEmployeeHomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(SuperEmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_super_employee_home, container, false);
        binding.setMessage(new Message());

        binding.superEmployeeHomeCardAddAttendance.setOnClickListener(this::navigateToAddAttendance);
        binding.superEmployeeHomeCardAddLeave.setOnClickListener(this::navigateToAddLeave);
        binding.superEmployeeHomeCardAddSchool.setOnClickListener(this::navigateToAddSchool);
        binding.superEmployeeHomeCardAddShop.setOnClickListener(this::navigateToAddDealer);
        binding.superEmployeeHomeCardAddOrder.setOnClickListener(this::navigateToAddOrder);
        binding.superEmployeeHomeCardPaymentRequest.setOnClickListener(this::navigateToPaymentRequest);
        binding.superEmployeeHomeCardAddEod.setOnClickListener(this::navigateToAddEod);
        binding.superEmployeeHomeCardMyMap.setOnClickListener(this::navigateToMap);
        binding.superEmployeeHomeCardDailyReports.setOnClickListener(this::navigateToDailyReports);
        binding.superEmployeeHomeCardPaymentRequests.setOnClickListener(this::navigateToPaymentRequests);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setStackFromEnd(true);
        binding.superEmployeeHomeRecycler.setLayoutManager(lm);

        binding.superEmployeeHomeSendMessageFab.setOnClickListener(this::sendMessage);

        viewModel.getCurrentEmployeeHistory().observe(getViewLifecycleOwner(), this::setEmployeeHistory);

        binding.superEmployeeHomeViewFab.setOnClickListener(view -> {
            if (binding.superEmployeeHomeCard.getVisibility() == View.VISIBLE) {
                binding.superEmployeeHomeCard.setVisibility(View.GONE);
            } else {
                binding.superEmployeeHomeCard.setVisibility(View.VISIBLE);
            }
        });

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
            binding.getMessage().setReceiverId(viewModel.getCurrentEmployee().getId());
            viewModel.sendMessage(binding.getMessage());
            binding.setMessage(new Message());
        }
    }

    private void setEmployeeHistory(List<EmployeeHistory> historyList) {

        if (historyList == null || historyList.isEmpty()) {
            binding.superEmployeeHomeEmptyRecycler.setVisibility(View.VISIBLE);
            binding.superEmployeeHomeRecycler.setVisibility(View.GONE);
        } else {
            binding.superEmployeeHomeEmptyRecycler.setVisibility(View.GONE);
            binding.superEmployeeHomeRecycler.setVisibility(View.VISIBLE);
        }

        EmployeeHomeRecyclerAdapter adapter = new EmployeeHomeRecyclerAdapter(historyList, getActivity().getApplication(), new EmployeeHomeRecyclerAdapter.EmployeeHomeClickListener() {
            @Override
            public void onClickCard(Attendance attendance) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ATN_ID, attendance.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_attendanceProfileFragment, bundle);
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
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_dealerProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Order order) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.ORDER_ID, order.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_orderProfileFragment, bundle);
            }

            @Override
            public void onClickCard(Eod eod) {
                Bundle bundle = new Bundle();
                bundle.putLong(Constants.EOD_ID, eod.getId());
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_eodProfileFragment, bundle);
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
        binding.superEmployeeHomeRecycler.setAdapter(adapter);
    }

    private void navigateToAddEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_superEmployeeAddEmployeeFragment);
    }

    private void navigateToMap(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_superEmployeeMapFragment);
    }

    private void navigateToAddEod(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddEODFragment);
    }

    private void navigateToPaymentRequest(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeePaymentRequestFragment);
    }

    private void navigateToAddOrder(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddOrderFragment);
    }

    private void navigateToAddDealer(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddDealerFragment);
    }

    private void navigateToAddSchool(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddSchoolFragment);
    }

    private void navigateToAddLeave(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddLeaveFragment);
    }

    private void navigateToAddAttendance(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_superEmployeeHomeFragment_to_employeeAddAttendanceFragment);
    }

    private void navigateToDailyReports(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.employeeDailyReportsFragment);
    }

    private void navigateToPaymentRequests(View view) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, viewModel.getCurrentEmployee().getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.pendingPaymentRequestsFragment, bundle);
    }
}