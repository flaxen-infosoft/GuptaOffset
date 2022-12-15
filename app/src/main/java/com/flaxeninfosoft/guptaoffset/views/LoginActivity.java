package com.flaxeninfosoft.guptaoffset.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

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

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Logging in");
        progressDialog.setCancelable(false);

        viewModel.getToastMessage().observe(this, this::showToast);
        binding.loginSubmitBtn.setOnClickListener(this::login);
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()){
            Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        }
    }

    private void login(View view) {
        clearErrors();

        if (binding.getCredential().getEmail().trim().isEmpty()) {
            binding.loginEmailTtl.setError("Email required");
        } else if (binding.getCredential().getPassword().trim().isEmpty()) {
            binding.loginPasswordTtl.setError("Password required");
        }
        else {
            progressDialog.show();
            viewModel.loginUser(binding.getCredential()).observe(this, this::showProgressDialog);
        }
    }

    private void showProgressDialog(Boolean toShow) {
        progressDialog.dismiss();
        Log.e("TEST", toShow+" this is response");
        if (toShow) {
            Intent intent = new Intent(this, SplashActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }

    private void clearErrors() {
        binding.loginEmailTtl.setError(null);
        binding.loginPasswordTtl.setError(null);
    }
}