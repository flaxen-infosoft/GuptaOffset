package com.flaxeninfosoft.guptaoffset.views.employee;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityEmployeeMainBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeMainViewModel;

public class EmployeeMainActivity extends AppCompatActivity {

    private ActivityEmployeeMainBinding binding;
    private EmployeeMainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_employee_main);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(EmployeeMainViewModel.class);

    }
}