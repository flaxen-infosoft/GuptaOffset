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

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.AdminHomeFragmentStateAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentAdminHomeBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class AdminHomeFragment extends Fragment {

    private AdminMainViewModel viewModel;
    private FragmentAdminHomeBinding binding;

    public AdminHomeFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_admin_home, container, false);

        binding.adminHomeCardMap.setOnClickListener(this::navigateToMap);
        binding.adminHomeCardAddSuperEmployee.setOnClickListener(this::navigateToAddSuperEmployee);

        binding.adminHomeViewFab.setOnClickListener(v -> {
            if(binding.adminHomeCard.getVisibility() == View.VISIBLE){
                binding.adminHomeCard.setVisibility(View.GONE);
            }else{
                binding.adminHomeCard.setVisibility(View.VISIBLE);
            }
        });

        setTabLayout();

        return binding.getRoot();
    }

    private void navigateToAddSuperEmployee(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminAddSuperEmployeeFragment);
    }

    private void navigateToMap(View view) {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_adminMapFragment);
    }

    private void setTabLayout() {
        AdminHomeFragmentStateAdapter adapter = new AdminHomeFragmentStateAdapter(getActivity());
        binding.adminHomeViewPager.setAdapter(adapter);
        binding.adminHomeViewPager.setCurrentItem(0);

        new TabLayoutMediator(binding.adminHomeTabLayout, binding.adminHomeViewPager,
                (tab, position) -> {

                    switch (position) {
                        case 0:
                            tab.setText("All Employees");
                            break;
                        case 1:
                            tab.setText("All Super Employees");
                            break;
                        case 2:
                            tab.setText("All Orders");
                            break;
                        case 3:
                            tab.setText("All Clients");
                            break;
                        case 4:
                            tab.setText("All Expenses");
                            break;
                        case 5:
                            tab.setText("All Eods");
                            break;
                        case 6:
                            tab.setText("All Leaves");
                    }
                }).attach();
    }
}