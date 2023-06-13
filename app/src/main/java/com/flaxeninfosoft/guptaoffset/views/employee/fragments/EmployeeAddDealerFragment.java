package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.BuildConfig;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddDealerBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class EmployeeAddDealerFragment extends Fragment {

    private FragmentEmployeeAddDealerBinding binding;
    private EmployeeViewModel viewModel;
    private Uri image;
    private ProgressDialog progressDialog;

    private String pictureImagePath;
    Long empId;
    RequestQueue requestQueue;

    public EmployeeAddDealerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_dealer, container, false);
        binding.setDealer(new Dealer());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID,0L);
        requestQueue = Volley.newRequestQueue(getContext());
        binding.employeeAddDealerBtn.setOnClickListener(this::onCLickAddDealer);
        binding.employeeAddDealerImage.setOnClickListener(this::onClickAddImage);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding Dealer...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        getDealerCount(empId);
        setAddress();

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);

        return binding.getRoot();
    }

    private void getDealerCount(Long empId) {

            String url = ApiEndpoints.BASE_URL + "dealer/dealercount.php";
            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("empId", empId);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
                Log.i("schoolCount", response.toString());

                if (response != null) {
                    try {
                        String var = response.getString("data");
                        binding.dealerCount.setText(var);
                    } catch (JSONException e) {
                        progressDialog.dismiss();
                        throw new RuntimeException(e);
                    }
                }
            }, error -> {
                progressDialog.dismiss();
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            });

//        int timeout = 10000; // 10 seconds
//        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);


    }

    private void setAddress() {
        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLocationAvailability().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                mFusedLocationClient.getLastLocation().addOnCompleteListener(fusedLocationTask -> {
                    Location location = fusedLocationTask.getResult();
                    if (location != null) {

                        try {
                            Geocoder geocoder = new Geocoder(EmployeeAddDealerFragment.this.getContext(), Locale.getDefault());
                            // initialising address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Address currentAddress = addresses.get(0);
                            binding.getDealer().getLocation().setAddress(currentAddress.getAddressLine(0));
                            binding.employeeAddDealerAddress.getEditText().setText(currentAddress.getAddressLine(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickAddImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        mLauncher.launch(cameraIntent);

        ImagePicker.with(this)
//                .compress(512)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(512, 512)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newCamIntent.launch(intent);
                    return null;
                });
    }

    ActivityResultLauncher<Intent> newCamIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        image = data;
                        binding.employeeAddDealerImage.setImageURI(image);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> mLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        try {

                            File imgFile = new File(pictureImagePath);
                            if (imgFile.exists()) {
                                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                                myBitmap = FileEncoder.rotateBitmap(myBitmap);
                                binding.employeeAddDealerImage.setImageBitmap(myBitmap);
                                image = FileEncoder.getImageUri(getContext(), myBitmap);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
                    }

                }
            }
    );

    private void onCLickAddDealer(View view) {

        if (isValidFields() && isImageAdd()) {
            progressDialog.show();
            try {

                viewModel.addDealer(binding.getDealer(), image).observe(getViewLifecycleOwner(), b -> {
                    if (b) {
                        progressDialog.dismiss();
                        clearErrors();
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.data_submit_dialog_layout);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button button = dialog.findViewById(R.id.okButton);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        navigateUp();

                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private boolean isImageAdd() {
        if (image == null) {
            Toast.makeText(getContext(), "Please Add Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        if (binding.getDealer().getName() == null || binding.getDealer().getName().trim().isEmpty()) {
            binding.employeeAddDealerName.setError("Enter name");
            return false;
        }

        if (binding.getDealer().getContact() == null || binding.getDealer().getContact().trim().isEmpty()) {
            binding.employeeAddDealerContact.setError("Enter contact number");
            return false;
        }

        if (binding.getDealer().getLocation().getAddress() == null || binding.getDealer().getLocation().getAddress().trim().isEmpty()) {
            if (!binding.addressEdittext.getText().toString().isEmpty()){
                binding.getDealer().getLocation().setAddress(binding.addressEdittext.getText().toString());
                return true;
            }
        }

        if (binding.addressEdittext.getText().toString().isEmpty()){
            binding.employeeAddDealerAddress.setError("**Enter address");
            binding.employeeAddDealerAddress.requestFocus();
            return false;
        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddDealerName.setError(null);
        binding.employeeAddDealerContact.setError(null);
    }

}