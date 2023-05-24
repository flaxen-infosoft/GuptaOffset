package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.Manifest;
import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddSchoolBinding;
import com.flaxeninfosoft.guptaoffset.models.PaymentStatus;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


public class EmployeeAddSchoolFragment extends Fragment {

    private FragmentEmployeeAddSchoolBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    private String pictureSpecimanImagePath;
    View view;

    RequestQueue requestQueue;
    String hindi_medium;
    String medium;
   String english_medium;

   String opt[] ={"English Medium, Hindi Medium"};

    private String pictureHoadingImagePath;

    Long empId;

    public EmployeeAddSchoolFragment() {
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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_school, container, false);
        binding.employeeAddSchoolBtn.setOnClickListener(this::onClickAddSchool);
        binding.employeeAddSchoolSpecimenImage.setOnClickListener(this::onClickSpecimenImage);
        binding.employeeAddSchoolHoadingImage.setOnClickListener(this::onClickHoadingImage);
     //   binding.medium.setOnClickListener(this::OnClickedRadioButton);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Adding School...");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(getContext());
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);
        binding.setSchool(viewModel.getNewSchool());
        getSchoolCount(empId);
        setHoadingImage();
        setSpecimenImage();
        setAddress();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        binding.dateTextId.setText(year + "/" + month + "/" + day);
        binding.tmTextId.setText(hour + ":" + minute + ":" + second);

        String[] schoolMedium = {"Select School Medium", "English Medium", "Hindi Medium"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dailyAllowanceList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, schoolMedium) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the starting item at position 0
                return position != 0;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.schoolMediumSpinner.setAdapter(adapter);

        binding.schoolMediumSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String medium = parent.getItemAtPosition(position).toString();
                sendDataToApi(medium);

           //     Toast.makeText(parent.getContext(), "Selected: " + medium,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return binding.getRoot();
    }

    private void sendDataToApi(String medium) {

        String apiUrl = "school/addSchool.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("medium", medium);
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("data",true);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, apiUrl, jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle API response
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle error
            }
        });

        requestQueue.add(request);
    }


    private void getSchoolCount(Long empId) {

        String url = ApiEndpoints.BASE_URL + "school/schoolcount.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("schoolCount", response.toString());

            if (response != null) {
                try {
                    String var = response.getString("data");
                    binding.schoolCount.setText(var);
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
                            Geocoder geocoder = new Geocoder(EmployeeAddSchoolFragment.this.getContext(), Locale.getDefault());
                            // initialising address list
                            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            Address currentAddress = addresses.get(0);
                            binding.getSchool().getLocation().setAddress(currentAddress.getAddressLine(0));
                            binding.employeeAddSchoolAddress.getEditText().setText(currentAddress.getAddressLine(0));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

    }

    private void setHoadingImage() {
        if (viewModel.getNewSchool().getHoadingImageUri() != null)
            Glide.with(getContext()).load(viewModel.getNewSchool().getHoadingImageUri()).placeholder(R.drawable.loading_image).into(binding.employeeAddSchoolHoadingImage);

    }

    public void setSpecimenImage() {
        if (viewModel.getNewSchool().getSpecimenImageUri() != null)
            Glide.with(getContext()).load(viewModel.getNewSchool().getSpecimenImageUri()).placeholder(R.drawable.loading_image).into(binding.employeeAddSchoolSpecimenImage);

    }

    private void onClickHoadingImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureHoadingImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureHoadingImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        hoadingImageIntent.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newHoadingImageIntent.launch(intent);
                    return null;
                });
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickSpecimenImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureSpecimanImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureSpecimanImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider", file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        specimenImageIntent.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newSpecimenImageIntent.launch(intent);
                    return null;
                });
    }

    ActivityResultLauncher<Intent> specimenImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        File imgFile = new File(pictureSpecimanImagePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            myBitmap = FileEncoder.rotateBitmap(myBitmap);
                            binding.employeeAddSchoolSpecimenImage.setImageBitmap(myBitmap);
                            binding.getSchool().setSpecimenImageUri(FileEncoder.getImageUri(getContext(), myBitmap));
                            viewModel.setNewSchoolSpecimenImageUri(binding.getSchool().getSpecimenImageUri());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> newSpecimenImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        viewModel.setNewSchoolSpecimenImageUri(data);
                        binding.employeeAddSchoolSpecimenImage.setImageURI(data);
                        binding.getSchool().setSpecimenImageUri(data);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> hoadingImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    try {
                        File imgFile = new File(pictureHoadingImagePath);
                        if (imgFile.exists()) {
                            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                            binding.employeeAddSchoolHoadingImage.setImageBitmap(myBitmap);
                            binding.getSchool().setHoadingImageUri(FileEncoder.getImageUri(getContext(), myBitmap));
                            viewModel.setNewSchoolHoadingImageUri(binding.getSchool().getHoadingImageUri());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
    );

    ActivityResultLauncher<Intent> newHoadingImageIntent = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        binding.employeeAddSchoolHoadingImage.setImageURI(data);
                        binding.getSchool().setHoadingImageUri(data);
                        viewModel.setNewSchoolHoadingImageUri(data);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });


    private void onClickAddSchool(View view) {
        if (isValidFields() && isImageAdded()) {

            progressDialog.show();
            try {
                viewModel.addSchool(binding.getSchool()).observe(getViewLifecycleOwner(), b -> {
                    if (b) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), "स्कूल ऐड हो गया है।\n", Toast.LENGTH_SHORT).show();
                        clearErrors();
                        navigateUp();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isImageAdded() {
        if (binding.getSchool().getSpecimenImageUri() == null) {
            Toast.makeText(getContext(), "Add Specimen Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (binding.getSchool().getHoadingImageUri() == null) {
            Toast.makeText(getContext(), "Add School Image", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {

        if (binding.getSchool().getName() == null || binding.getSchool().getName().trim().isEmpty()) {
            binding.employeeAddSchoolName.setError("**Enter name");
            return false;
        }
//        if (binding.getSchool().getTotalStudentStrength() == null || binding.getSchool().getTotalStudentStrength().trim().isEmpty()) {
//            binding.employeeAddSchoolStrength.setError("**Enter strength");
//            return false;
//        }
        if (binding.getSchool().getLocation().getAddress() == null || binding.getSchool().getLocation().getAddress().trim().isEmpty()) {
            binding.employeeAddSchoolAddress.setError("**Enter address");
            return false;
        }

     /*   if (binding.getSchool().getMedium() == null || binding.getSchool().getMedium().trim().isEmpty()) {
            binding.employeeAddSchoolContact.setError("**Enter medium");
            return false;
        }*/

        return true;
    }

    private void clearErrors() {
        binding.employeeAddSchoolName.setError(null);
//        binding.employeeAddSchoolStrength.setError(null);
        binding.employeeAddSchoolAddress.setError(null);
        binding.employeeAddSchoolContact.setError(null);
    }

}