package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.adapters.EmployeeRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.adapters.ShowNotesRecyclerAdapter;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentShowNotesBinding;
import com.flaxeninfosoft.guptaoffset.models.Employee;
import com.flaxeninfosoft.guptaoffset.models.ShowNotes;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ShowNotesFragment extends Fragment {

    List<ShowNotes> showNotesList;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    ShowNotesRecyclerAdapter showNotesRecyclerAdapter;
    Gson gson;


    public ShowNotesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    FragmentShowNotesBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.show_notes, container, false);
        binding.backImg.setOnClickListener(this::onClickBack);
        showNotesList = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        showNotesRecyclerAdapter = new ShowNotesRecyclerAdapter(showNotesList, new ShowNotesRecyclerAdapter.NotesLayoutClickListener() {


            @Override
            public void onClickNotes(ShowNotes showNotes) {
                onClickNote(showNotes);

            }
        });

        binding.showNotesRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.showNotesSwipeRefresh.setOnRefreshListener(() -> getAllNotes(Long empId));
        binding.showNotesRecycler.setAdapter(showNotesRecyclerAdapter);
        showNotesRecyclerAdapter.notifyDataSetChanged();
        getAllNotes(Long empId);
        showNotesRecyclerAdapter.notifyDataSetChanged();

        return binding.getRoot();
    }

    public static void getAllNotes(Long empId)   {

        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "employee/getallFlagEmployee.php";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, response -> {
            Log.i("notes", response.toString());
            progressDialog.dismiss();
            if (binding.showNotesSwipeRefresh.isRefreshing()) {
                binding.showNotesSwipeRefresh.setRefreshing(false);
            }
            if (response != null) {
                try {
                    JSONArray jsonArray = response.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        ShowNotes showNotes = gson.fromJson(jsonArray.getJSONObject(i).toString(), ShowNotes.class);
                        showNotesList.add(showNotes);
                    }

                    binding.showNotesRecycler.setAdapter(showNotesRecyclerAdapter);
                    showNotesRecyclerAdapter.notifyDataSetChanged();
                    if (showNotesList == null || showNotesList.isEmpty()) {
                        binding.showNotesRecycler.setVisibility(View.GONE);
                        binding.showNotesEmptyTV.setVisibility(View.VISIBLE);
                    } else {
                        binding.showNotesRecycler.setVisibility(View.VISIBLE);
                        binding.showNotesEmptyTV.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, error -> {
            progressDialog.dismiss();
            if (binding.showNotesSwipeRefresh.isRefreshing()) {
                binding.showNotesSwipeRefresh.setRefreshing(false);
            }
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }

    private void onClickNote(ShowNotes showNotes) {
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.EMPLOYEE_ID, ShowNotes.getId());
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_adminHomeFragment_to_showNotesFragment, bundle);
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}