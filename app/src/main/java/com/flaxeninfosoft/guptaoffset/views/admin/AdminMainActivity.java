package com.flaxeninfosoft.guptaoffset.views.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityAdminMainBinding;
import com.flaxeninfosoft.guptaoffset.viewModels.AdminMainViewModel;

public class AdminMainActivity extends AppCompatActivity {

    private ActivityAdminMainBinding binding;
    private AdminMainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_admin_main);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AdminMainViewModel.class);


    }
}