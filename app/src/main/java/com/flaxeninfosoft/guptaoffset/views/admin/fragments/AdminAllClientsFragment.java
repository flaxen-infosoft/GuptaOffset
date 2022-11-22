package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.ClientRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminAllClientsBinding;
import com.flaxeninfosoft.guptaoffset.models.Client;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

import java.util.List;

public class AdminAllClientsFragment extends Fragment {

    private FragmentAdminAllClientsBinding binding;
    private AdminMainViewModel viewModel;

    public AdminAllClientsFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_all_clients, container, false);

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
        binding.adminAllClientsRecycler.setAdapter(adapter);
    }

    private void setUpSwipeRefresh() {
        binding.adminAllClientsSwipeRefresh.setOnRefreshListener(this::onRefresh);
    }

    private void onClickClient(Client client){
        Bundle bundle = new Bundle();
        bundle.putLong(getString(R.string.key_client_id), client.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminAllClientsFragment_to_employeeClientProfileFragment, bundle);

    }

    private void onRefresh() {
        viewModel.fetchAllClients().observe(getViewLifecycleOwner(), f -> stopSwipeRefreshing());
    }

    private void stopSwipeRefreshing() {
        binding.adminAllClientsSwipeRefresh.setRefreshing(false);
    }

    private void setupRecycler() {
         binding.adminAllClientsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}