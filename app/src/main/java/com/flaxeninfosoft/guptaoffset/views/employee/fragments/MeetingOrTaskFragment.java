package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeMeetingTaskAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.ShowNotesRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentMeetingOrTaskBinding;
import com.flaxeninfosoft.guptaoffset.models.MeetingTask;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.paperdb.Paper;

public class MeetingOrTaskFragment extends Fragment {

    FragmentMeetingOrTaskBinding binding;
    List<MeetingTask> meetingTaskList;
    Long empId;
    String datepicker;
    String time;
    RequestQueue requestQueue;
    Gson gson;
    ProgressDialog progressDialog;
    EmployeeMeetingTaskAdapter employeeMeetingTaskAdapter;

    public MeetingOrTaskFragment() {
        // Required empty public constructor
    }

    public static void deleteTaskDialog(Context context, Long empId, Long id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("Are you sure you want to delete note ?");
        builder.setTitle("Delete Note ");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            deleteTask(context, empId, id);
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private static void deleteTask(Context context, Long empId, Long id) {
        RequestQueue requestQueue1 = Volley.newRequestQueue(context);
        ProgressDialog progressDialog1 = new ProgressDialog(context);
        progressDialog1.setCancelable(false);
        progressDialog1.setTitle("Wait");
        progressDialog1.setMessage("Please wait ....");
        progressDialog1.show();

        String url = ApiEndpoints.BASE_URL + "notes/removemeetingTaskByempId.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        hashMap.put("meetingId", id);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog1.dismiss();

            if (response != null) {

                try {
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
        }, error -> {
            progressDialog1.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue1.add(jsonObjectRequest);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meeting_or_task, container, false);
        binding.meetingOrTaskBackImg.setOnClickListener(this::onClickBack);
        meetingTaskList = new ArrayList<>();
        empId = getArguments().getLong(Constants.EMPLOYEE_ID, 0L);
        Paper.init(getContext());
        Paper.book().write("EmpIDInMeetingTask", empId);
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        requestQueue = Volley.newRequestQueue(getContext());
//        employeeMeetingTaskAdapter = new EmployeeMeetingTaskAdapter(meetingTaskList, getContext(), meetingTask -> onClickNote(meetingTask));

        binding.meetingOrTaskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.addMeetingTaskTextview.setOnClickListener(view -> addMeetingTaskDialog(empId));
        binding.meetingTaskSwipeRefresh.setOnRefreshListener(() -> getAllMeetingTask(empId));
        employeeMeetingTaskAdapter = new EmployeeMeetingTaskAdapter(meetingTaskList, getContext(), meetingTask -> {
        });
        binding.meetingOrTaskRecycler.setAdapter(employeeMeetingTaskAdapter);
        getAllMeetingTask(empId);
        employeeMeetingTaskAdapter.notifyDataSetChanged();


        return binding.getRoot();
    }

    private void onClickNote(MeetingTask meetingTask) {
    }

    private void getAllMeetingTask(Long empId) {
        meetingTaskList.clear();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "notes/getemployeemeetingTask.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("meetingTask", response.toString());
            progressDialog.dismiss();
            if (binding.meetingTaskSwipeRefresh.isRefreshing()) {
                binding.meetingTaskSwipeRefresh.setRefreshing(false);
            }
            try {
                if (response != null) {
                    if (response.getJSONArray("data").length() > 0) {
                        JSONArray jsonArray = response.getJSONArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            MeetingTask meetingTask = gson.fromJson(jsonArray.getJSONObject(i).toString(), MeetingTask.class);
                            Log.e("LIST1:", meetingTask.getTask());
                            meetingTaskList.add(meetingTask);
                        }

                        employeeMeetingTaskAdapter = new EmployeeMeetingTaskAdapter(meetingTaskList, getContext(), meetingTask -> {

                        });
                        binding.meetingOrTaskRecycler.setAdapter(employeeMeetingTaskAdapter);
                        if (meetingTaskList == null || meetingTaskList.isEmpty()) {

                            binding.meetingOrTaskRecycler.setVisibility(View.GONE);
                            binding.meetingOrTaskEmptyTv.setVisibility(View.VISIBLE);
                        } else {
                            Log.e("LIST2:", meetingTaskList.toString());
                            binding.meetingOrTaskRecycler.setVisibility(View.VISIBLE);
                            binding.meetingOrTaskEmptyTv.setVisibility(View.GONE);
                        }
                    } else {
                        Log.e("LIST3:", meetingTaskList.toString());
                        binding.meetingOrTaskRecycler.setVisibility(View.GONE);
                        binding.meetingOrTaskEmptyTv.setVisibility(View.VISIBLE);
                    }
                } else {
                    try {
                        Toast.makeText(getContext(), response.getString("data"), Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            Log.e("LIST4:", meetingTaskList.toString());
            progressDialog.dismiss();
            if (binding.meetingTaskSwipeRefresh.isRefreshing()) {
                binding.meetingTaskSwipeRefresh.setRefreshing(false);
            }
//            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);

    }

    private void addMeetingTaskDialog(Long empId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        View mView = LayoutInflater.from(getContext()).inflate(R.layout.add_employee_meeting_dialog, null);

        EditText add_meeting_address_editTxt = (EditText) mView.findViewById(R.id.add_meeting_address_editTxt);
        EditText add_meeting_task_editTxt = (EditText) mView.findViewById(R.id.add_meeting_task_editTxt);
        TextView add_meeting_date = (TextView) mView.findViewById(R.id.add_meeting_date);
        TextView add_meeting_time = (TextView) mView.findViewById(R.id.add_meeting_task_time);
        Button btn_cancel = (Button) mView.findViewById(R.id.btn_cancel);
        Button btn_okay = (Button) mView.findViewById(R.id.btn_okay);

        builder.setView(mView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();
            }
        });

        btn_okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String msg = add_meeting_task_editTxt.getText().toString();
                String address = add_meeting_address_editTxt.getText().toString();

                if (!msg.isEmpty() || !address.isEmpty()) {
                    addMeetingTask(getContext(), msg, address, empId);
                } else {
                    Toast.makeText(getContext(), "Please Enter Address and Your Task", Toast.LENGTH_SHORT).show();
                    add_meeting_task_editTxt.setError("Please write something.");
                }
                alertDialog.dismiss();
            }
        });

        add_meeting_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                int y = c.get(Calendar.YEAR);
                int m = c.get(Calendar.MONTH);
                int d = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day); // Set selected date to calendar

                        java.util.Date date = calendar.getTime();
                        Format format = new SimpleDateFormat("20yy-MM-dd");
                        add_meeting_date.setText(format.format(date));
                        datepicker = format.format(date);
//            getAllLeave(empId);
                    }
                }, y, m, d);

                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000); // Set minimum date to today
                datePickerDialog.show();
            }
        });

        add_meeting_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                // on below line we are getting our hour, minute.
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        Time tme = new Time(hour, minute, 0);//seconds by default set to zero
                        Format formatter = new SimpleDateFormat("hh:mm a");
                        add_meeting_time.setText(String.valueOf(formatter.format(tme)));
                        time = formatter.format(tme);
                    }
                }, hour, minute, false);

                timePickerDialog.show();
            }
        });

        alertDialog.show();

    }

    private void addMeetingTask(Context context, String msg, String address, Long empId) {
//              String empId = Paper.book().read("CurrentEmployeeId");
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        progressDialog.show();

        String url = ApiEndpoints.BASE_URL + "notes/addemployeemeetingTask.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("empId", empId);
        hashMap.put("note", msg);
        hashMap.put("address", address);
        hashMap.put("date", datepicker);
        hashMap.put("time", time);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            Log.i("Dealer", response.toString());
            progressDialog.dismiss();

            if (response != null) {

                try {
                    Toast.makeText(context, response.getString("message"), Toast.LENGTH_SHORT).show();
                    getAllMeetingTask(empId);


                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}