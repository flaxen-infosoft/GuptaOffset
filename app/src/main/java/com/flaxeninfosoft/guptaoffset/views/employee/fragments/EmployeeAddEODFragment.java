package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentEmployeeAddEODBinding;
import com.flaxeninfosoft.guptaoffset.models.Attendance;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.models.Location;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.FileEncoder;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class EmployeeAddEODFragment extends Fragment {

    private FragmentEmployeeAddEODBinding binding;
    private EmployeeViewModel viewModel;
    private ProgressDialog progressDialog;

    private Uri expenseImage;
    private Uri petrolImage;
    String selectedDailyAllowance;

    private String picturePetrolExpenseImagePath;

    private String pictureOtherExpenseImagePath;

    List<String> dailyAllowanceList;

    public EmployeeAddEODFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_employee_add_e_o_d, container, false);
        binding.setEod(new Eod());
        binding.getEod().setDailyAllowance(viewModel.getCurrentEmployee().getDailyAllowance());

        binding.employeeAddEodBtn.setOnClickListener(this::onClickAddEod);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Fetching data...");
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        viewModel.getToastMessageLiveData().observe(getViewLifecycleOwner(), this::showToast);
        viewModel.getCurrentEmployeeTodaysAttendance().observe(getViewLifecycleOwner(), this::setAttendance);
        Long empId = viewModel.getCurrentEmployee().getId();

        binding.employeeAddEodExpenseImage.setOnClickListener(this::onClickExpenseImage);
        binding.employeeAddEodPetrolImage.setOnClickListener(this::onClickPetrolImage);

        dailyAllowanceList = new ArrayList<>();
        dailyAllowanceList.add(0, "Please Select Daily Allowance");
        getEodIsFilledOrNot(empId);
        getDailyAllowanceList(empId);
        getDaliyData(empId);


        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // Month starts from 0, so add 1
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        binding.dateTextId.setText(year + "-" + month + "-" + day);
        binding.tmTextId.setText(hour + ":" + minute + ":" + second);


//        String[] dailyAllowanceList = {"Select Daily Allowance", "1 Day = 200", "2 Day = 350", "3 Day = 450", "Night Stay = 500"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, dailyAllowanceList);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, dailyAllowanceList) {
            @Override
            public boolean isEnabled(int position) {
                // Disable the starting item at position 0
                return position != 0;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.dailyAllowanceSpinner.setAdapter(adapter);

        binding.dailyAllowanceSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDailyAllowance = parent.getItemAtPosition(position).toString();
                selectedDailyAllowance = selectedDailyAllowance.substring(selectedDailyAllowance.lastIndexOf("/") + 1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(parent.getContext(), "Please Select School Medium ", Toast.LENGTH_SHORT).show();
            }
        });


        return binding.getRoot();
    }

    private void getDaliyData(Long empId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new Gson();
        String url = ApiEndpoints.BASE_URL + "eod/geteodContent.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        JSONObject jsonObject = response.getJSONObject("data");
                        String morning_attendance = jsonObject.getString("morning_attendance");
                        String evening_attendance = jsonObject.getString("evening_attendance");
                        String total_km = jsonObject.getString("km");
                        String school_visit = jsonObject.getString("school_count");
                        String delaer_visit = jsonObject.getString("dealer_count");

                        binding.morningAttendanceTime.setText(morning_attendance);
                        binding.eveningAttendanceTime.setText(evening_attendance);
                        binding.kmTextview.setText(total_km);
                        binding.schoolVisitTextview.setText(school_visit);
                        binding.delaerCountEdittext.setText(delaer_visit);
                        binding.schoolVisitEdittext.setText(school_visit);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Toast.makeText(getContext(), response.getString("message").toString(), Toast.LENGTH_SHORT).show();
                    } catch (JSONException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void getDailyAllowanceList(Long empId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        Gson gson = new Gson();
        String url = ApiEndpoints.BASE_URL + "employee/getDailyAllowanceByempId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response != null) {
                        JSONObject jsonObject = response.getJSONObject("data");
                        Employee employee = gson.fromJson(jsonObject.toString(), Employee.class);
                        String da1, da2, da3, da4;
                        if (employee.getDaily_allowance_description1() != null || employee.getDaily_allowance_description1().isEmpty()
                                && employee.getDailyAllowance1() != null || employee.getDailyAllowance1().isEmpty()) {
                            da1 = employee.getDaily_allowance_description1() + " / " + employee.getDailyAllowance1();
                            if (!employee.getDailyAllowance1().equals("0")|| employee.getDaily_allowance_description1().equals("NO Allowance")) {
                                dailyAllowanceList.add(da1);
                            }
                        }
                        if (employee.getDaily_allowance_description2() != null || employee.getDaily_allowance_description2().isEmpty()
                                && employee.getDailyAllowance2() != null || employee.getDailyAllowance2().isEmpty()) {
                            da2 = employee.getDaily_allowance_description2() + " / " + employee.getDailyAllowance2();
                            if (!employee.getDailyAllowance2().equals("0")|| employee.getDaily_allowance_description2().equals("NO Allowance")) {
                                dailyAllowanceList.add(da2);
                            }
                        }
                        if (employee.getDaily_allowance_description3() != null || employee.getDaily_allowance_description3().isEmpty()
                                && employee.getDailyAllowance3() != null || employee.getDailyAllowance3().isEmpty()) {
                            da3 = employee.getDaily_allowance_description3() + " / " + employee.getDailyAllowance3();
                            if (!employee.getDailyAllowance3().equals("0")|| employee.getDaily_allowance_description3().equals("NO Allowance")) {
                                dailyAllowanceList.add(da3);
                            }
                        }
                        if (employee.getDaily_allowance_description4() != null || employee.getDaily_allowance_description4().isEmpty()
                                && employee.getDailyAllowance4() != null || employee.getDailyAllowance4().isEmpty()) {
                            da4 = employee.getDaily_allowance_description4() + " / " + employee.getDailyAllowance4();
                            if (!employee.getDailyAllowance4().equals("0") || employee.getDaily_allowance_description4().equals("NO Allowance")) {
                                dailyAllowanceList.add(da4);
                            }
                        }


                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void setAttendance(Attendance attendance) {
        String punchStatus = "0";
        if (attendance == null) {
            punchStatus = "0";
        } else {
            punchStatus = String.valueOf(attendance.getPunchStatus());
        }


        if (punchStatus.equals("0")) {
            showDialog();
        } else if (punchStatus.equals("1")) {
            showDialog();
        } else {
            //submit eod
        }
    }

    private void showDialog() {
        LayoutInflater factory = LayoutInflater.from(getContext());

        final View view = factory.inflate(R.layout.attendance_alert_dialog_layout, null);
        AlertDialog attendaceDialog = new AlertDialog.Builder(getContext()).create();
        attendaceDialog.setView(view);
        attendaceDialog.setCancelable(false);
        attendaceDialog.show();
        Button buttonCancel = view.findViewById(R.id.button2);
        buttonCancel.setVisibility(View.VISIBLE);
        try {
           buttonCancel.setOnClickListener(view1 -> {
               Navigation.findNavController(binding.getRoot()).navigateUp();
               attendaceDialog.dismiss();
           });
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button buttonPunch = view.findViewById(R.id.button1);
        buttonPunch.setOnClickListener(view12 -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.employeeAddAttendanceFragment);
            attendaceDialog.dismiss();
        });
    }

    ActivityResultLauncher<Intent> expenseImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                try {

                    File imgFile = new File(pictureOtherExpenseImagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        binding.employeeAddEodExpenseImage.setImageBitmap(myBitmap);
                        expenseImage = FileEncoder.getImageUri(getContext(), myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    });

    ActivityResultLauncher<Intent> newExpenseImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        expenseImage = data;
                        binding.employeeAddEodExpenseImage.setImageURI(expenseImage);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    ActivityResultLauncher<Intent> petrolImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {

            if (result.getResultCode() == Activity.RESULT_OK) {
                try {

                    File imgFile = new File(picturePetrolExpenseImagePath);
                    if (imgFile.exists()) {
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        myBitmap = FileEncoder.rotateBitmap(myBitmap);
                        binding.employeeAddEodPetrolImage.setImageBitmap(myBitmap);
                        petrolImage = FileEncoder.getImageUri(getContext(), myBitmap);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getContext(), "Action cancelled", Toast.LENGTH_SHORT).show();
            }
        }
    });

    ActivityResultLauncher<Intent> newPetrolImageLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resCode = result.getResultCode();
                    Uri data = result.getData().getData();

                    if (resCode == Activity.RESULT_OK) {
                        petrolImage = data;
                        binding.employeeAddEodPetrolImage.setImageURI(petrolImage);
                    } else if (resCode == ImagePicker.RESULT_ERROR) {
                        Toast.makeText(getContext(), ImagePicker.getError(result.getData()), Toast.LENGTH_SHORT).show();
                    }
                }
            });

    private void onClickPetrolImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        picturePetrolExpenseImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(picturePetrolExpenseImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        petrolImageLauncher.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newPetrolImageLauncher.launch(intent);
                    return null;
                });
    }

    private void onClickExpenseImage(View view) {
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = timeStamp + ".jpg";
//        File storageDir = Environment.getExternalStoragePublicDirectory(
//                Environment.DIRECTORY_PICTURES);
//        pictureOtherExpenseImagePath = storageDir.getAbsolutePath() + "/" + imageFileName;
//        File file = new File(pictureOtherExpenseImagePath);
//        Uri uri = FileProvider.getUriForFile(getContext(), BuildConfig.APPLICATION_ID + ".provider",file);
//        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//        cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        expenseImageLauncher.launch(cameraIntent);

        ImagePicker.with(this)
                .compress(1024)         //Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)  //Final image resolution will be less than 1080 x 1080(Optional)
                .cameraOnly()
                .createIntent(intent -> {
                    newExpenseImageLauncher.launch(intent);
                    return null;
                });
    }

    private void onClickAddEod(View view) {
        clearErrors();

        if (isValidFields()) {
            progressDialog.show();
            try {
                Eod eod = binding.getEod();
                eod.setDailyAllowance(selectedDailyAllowance);

                LiveData<Boolean> liveData = viewModel.addEod(eod, expenseImage, petrolImage);
                Observer<Boolean> observer = aBoolean -> {
                    if (aBoolean != null) {
                        if (aBoolean) {
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
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Something went wrong.", Toast.LENGTH_SHORT).show();
                        }
                    }
                };
                liveData.observe(getViewLifecycleOwner(), observer);
////
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


//        if (isValidFields()) {
//            progressDialog.show();
//            try {
//                viewModel.addEod(binding.getEod(), expenseImage, petrolImage).observe(getViewLifecycleOwner(), b -> {
//                    if (b) {
//                        progressDialog.dismiss();
//                        navigateUp();
//                        Toast.makeText(getContext(), "रिपोर्ट ऐड हो गई है।\n", Toast.LENGTH_SHORT).show();
//                    } else {
//                        progressDialog.dismiss();
//                    }
//                });
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
    }

    private void showToast(String s) {
        if (s != null && !s.isEmpty()) {
            Toast.makeText(getContext(), s, Toast.LENGTH_SHORT).show();
        }
    }

    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private boolean isValidFields() {
        if (binding.getEod().getSchoolVisits() == null) {
            binding.employeeAddEodSchoolsVisits.setError("Enter schools visits");
            binding.employeeAddEodSchoolsVisits.requestFocus();
            return false;
        }

        if (binding.dailyAllowanceSpinner.getSelectedItem().equals("Please Select Daily Allowance")) {
            Toast.makeText(getContext(), "Please Select Daily Allowance.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (binding.getEod().getPetrolExpense() != null) {
            if (!binding.petrolExpenseEdittext.getText().toString().isEmpty()) {
                if (petrolImage == null) {
                    Toast.makeText(getContext(), "Add Petrol Expense Image", Toast.LENGTH_SHORT).show();
                    return false;
                }
            }
        }

        if (binding.getEod().getOtherExpense() != null) {
            if (!binding.otherExpenseEdittext.getText().toString().isEmpty()) {
                if (!binding.otherExpenseDescriptionEdittext.getText().toString().isEmpty()) {
                    if (expenseImage == null) {
                        Toast.makeText(getContext(), "Add other Expense Image", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                } else {
                    binding.otherExpenseDescriptionEdittext.setError("Description Required");
                    return false;
                }
            }
        }

//        if (binding.getEod().getPetrolExpense() == null) {
//            binding.employeeAddEodPetrolExpense.setError("Enter petrol expense");
//            return false;
//        }
//        if (binding.getEod().getOtherExpense() != null && !binding.getEod().getOtherExpense().isEmpty()){
//            if (binding.getEod().getExpenseDescription() == null || binding.getEod().getExpenseDescription().isEmpty()){
//                binding.employeeAddEodExpenseDescription.setError("Enter expense description");
//                return false;
//            }
//        }
//        if (binding.getEod().getOtherExpense() == null) {
//            binding.employeeAddEodOtherExpense.setError("Enter other expenses");
//            return false;
//        }
//        if (expenseImage == null) {
//            Toast.makeText(getContext(), "Add expense Image", Toast.LENGTH_LONG).show();
//            return false;
//        }
//        if (petrolImage == null) {
//            Toast.makeText(getContext(), "Add petrol expense Image", Toast.LENGTH_LONG).show();
//            return false;
//        }

        return true;
    }

    private void clearErrors() {
        binding.employeeAddEodSchoolsVisits.setError(null);
        binding.employeeAddEodPetrolExpense.setError(null);
        binding.employeeAddEodOtherExpense.setError(null);
    }

    private void getEodIsFilledOrNot(Long empId) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = ApiEndpoints.BASE_URL + "eod/EodsubmitByempId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("schoolCount", response.toString());

            if (response != null) {
                try {

                    String status = response.get("submited_status").toString();
                    int status_value = Integer.parseInt(status);

                    if (status_value == 1) {
                        Dialog dialog = new Dialog(getContext());
                        dialog.setContentView(R.layout.eod_already_submitted_dialog);
                        dialog.setCancelable(false);
                        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        Button button = dialog.findViewById(R.id.okButton);
                        button.setOnClickListener(view -> {
                            dialog.dismiss();
                            navigateUp();
                        });
                        dialog.show();
                    } else if (status_value == 0) {
                        //do nothingn
                    } else {
                        //do nothing
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);


    }
}