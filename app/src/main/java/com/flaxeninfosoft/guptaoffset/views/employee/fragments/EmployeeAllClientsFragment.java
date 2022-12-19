package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.ClientRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAllClientsBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeMainViewModel;

import java.util.List;

public class EmployeeAllClientsFragment extends Fragment {

    private FragmentEmployeeAllClientsBinding binding;
    private EmployeeMainViewModel viewModel;

    public EmployeeAllClientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeMainViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_all_clients, container, false);

        setupRecycler();
        setUpSwipeRefresh();

        viewModel.getAllClientListLiveData().observe(getViewLifecycleOwner(), this::updateClientList);

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

    private void setUpSwipeRefresh() {
        binding.employeeAllClientsSwipeRefresh.setOnRefreshListener(this::onRefresh);
    }

    private void onClickClient(Client client){
        Bundle bundle = new Bundle();
        bundle.putLong(getString(R.string.key_client_id), client.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_employeeAllClientsFragment_to_employeeClientProfileFragment2, bundle);

    }

    private void onRefresh() {
        viewModel.fetchEmployeeClients().observe(getViewLifecycleOwner(), f -> stopSwipeRefreshing());
    }

    private void stopSwipeRefreshing() {
        binding.employeeAllClientsSwipeRefresh.setRefreshing(false);
    }
    private void setupRecycler() {
        binding.employeeAllClientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}