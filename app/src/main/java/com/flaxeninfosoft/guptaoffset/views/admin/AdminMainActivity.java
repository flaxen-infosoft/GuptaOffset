package com.flaxeninfosoft.guptaoffset.views.admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
import com.flaxeninfosoft.guptaoffset.views.SplashActivity;
import com.flaxeninfosoft.guptaoffset.views.employee.EmployeeMainActivity;

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_admin_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_admin_home_logout){

            viewModel.logout();

            Intent intent = new Intent(AdminMainActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
        }
    }
}