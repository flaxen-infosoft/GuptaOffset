package com.flaxeninfosoft.guptaoffset.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.ActivityLoginBinding;
import com.flaxeninfosoft.guptaoffset.models.LoginModel;
import com.flaxeninfosoft.guptaoffset.viewModels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private LoginViewModel viewModel;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        binding.setCredential(new LoginModel());

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(LoginViewModel.class);

        progressDialog=new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Loading in");
        progressDialog.setCancelable(false);

        binding.loginSubmitBtn.setOnClickListener(this::login);


        //TODO
//        viewModel.getToastMsg().observe(this, this::showMsgToast);
//
//        viewModel.isLoading().observe(this, this::progressDialog);
//
//        viewModel.isLoggedIn().observe(this, this::loggedIn);
    }

    private void login(View view) {
        clearErrors();

        if (binding.getCredential().getEmail().trim().isEmpty()){
            binding.loginEmailTtl.setError("Email required");
        }else if (binding.getCredential().getPassword().trim().isEmpty()){
            binding.loginPasswordTtl.setError("Password required");
        }else{
            //TODO
        }
    }

    private void clearErrors(){
        binding.loginEmailTtl.setError(null);
        binding.loginPasswordTtl.setError(null);

    }
}