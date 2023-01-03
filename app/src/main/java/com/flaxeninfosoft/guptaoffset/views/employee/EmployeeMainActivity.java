package com.flaxeninfosoft.guptaoffset.views.employee;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityEmployeeMainBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.flaxeninfosoft.guptaoffset.views.SplashActivity;

public class EmployeeMainActivity extends AppCompatActivity {

    private ActivityEmployeeMainBinding binding;
    private EmployeeViewModel viewModel;

    private NavController navController;
    private AppBarConfiguration config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_main);

        NavHostFragment hostFragment =
                (NavHostFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.employee_main_host_fragment);

        navController = hostFragment.getNavController();

        config = new AppBarConfiguration.Builder(R.id.employeeHomeFragment).build();

        setSupportActionBar(binding.employeeMainToolbar);
        NavigationUI.setupActionBarWithNavController(this, navController, config);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EmployeeViewModel.class);
        viewModel.getToastMessageLiveData().observe(this, this::showToastMessage);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_employee_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);

        if (item.getItemId() == R.id.menu_employee_home_profile){
            navController.navigate(R.id.employeeProfileFragment);
            return true;
        }
        if (item.getItemId() == R.id.menu_employee_home_logout){

            viewModel.logout();

            Intent intent = new Intent(EmployeeMainActivity.this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }

    private void showToastMessage(String s) {
        Log.d("CRM-LOG", s);
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
        }
    }
}