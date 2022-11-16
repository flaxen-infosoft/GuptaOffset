package com.flaxeninfosoft.guptaoffset.views.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityAdminMainBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

public class AdminMainActivity extends AppCompatActivity {

    private ActivityAdminMainBinding binding;
    private AdminMainViewModel viewModel;

    private NavController navController;
    private AppBarConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_main);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AdminMainViewModel.class);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.admin_main_host_fragment);

        navController = hostFragment.getNavController();

        config = new AppBarConfiguration.Builder(R.id.adminHomeFragment).build();

        viewModel.getToastMessageLiveData().observe(this, this::showToast);

        setSupportActionBar(binding.adminMainToolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, config);
    }

    private void showToast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}