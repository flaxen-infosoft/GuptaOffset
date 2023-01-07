package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

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
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllClientsBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;

import java.util.List;

public class EmployeeAllClientsFragment extends Fragment {

    private FragmentEmployeeAllClientsBinding binding;
    private EmployeeViewModel viewModel;

    public EmployeeAllClientsFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_clients, container, false);
        binding.employeeAllClientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel.getCurrentEmployeeClients().observe(getViewLifecycleOwner(), this::updateClientList);

        return binding.getRoot();
    }

    private void updateClientList(List<Client> clientList) {
        ClientRecyclerAdapter adapter = new ClientRecyclerAdapter(clientList, new ClientRecyclerAdapter.SingleClientCardOnClickListener() {
            @Override
            public void onClickCard(Client client) {
                onClickClient(client);
            }
        });
        binding.employeeAllClientsRecycler.setAdapter(adapter);
    }

    private void onClickClient(Client client) {
        Bundle bundle = new Bundle();
        bundle.putLong(getString(R.string.key_client_id), client.getId());

    }
}