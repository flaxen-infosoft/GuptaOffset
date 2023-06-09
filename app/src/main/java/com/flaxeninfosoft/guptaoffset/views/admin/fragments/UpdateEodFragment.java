package com.flaxeninfosoft.guptaoffset.views.admin.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentUpdateEodBinding;
import com.flaxeninfosoft.guptaoffset.models.Dealer;
import com.flaxeninfosoft.guptaoffset.models.Eod;
import com.flaxeninfosoft.guptaoffset.utils.ApiEndpoints;
import com.flaxeninfosoft.guptaoffset.utils.Constants;
import com.flaxeninfosoft.guptaoffset.viewModels.EmployeeViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;


public class UpdateEodFragment extends Fragment {


    public UpdateEodFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider.AndroidViewModelFactory(getActivity().getApplication()).create(EmployeeViewModel.class);
    }

    FragmentUpdateEodBinding binding;
    private EmployeeViewModel viewModel;
    private OnMapReadyCallback mapReadyCallback;
    String otherExpense, petrolExpense;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    Gson gson;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_update_eod, container, false);
        binding.buttonUpdateEod.setOnClickListener(this::onClickUpdateEod);
        requestQueue = Volley.newRequestQueue(getContext());
        gson = new Gson();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Wait");
        progressDialog.setMessage("Please wait ....");
        binding.eodProfileOtherExpenseImage.setOnLongClickListener(this::onLongClickOtherExpenseImage);
        binding.eodProfilePetrolExpenseImage.setOnLongClickListener(this::onLongClickPetroExpenseImage);
        long eodId = getArguments().getLong(Constants.EOD_ID, -1);
        if (eodId == -1) {
            navigateUp();
        } else {
            viewModel.getEodById(eodId).observe(getViewLifecycleOwner(), this::setEod);
        }

        return binding.getRoot();
    }

    private boolean onLongClickPetroExpenseImage(View view) {
        String textToSend = "Hello, This is petrol expense image :  " + petrolExpense;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        return true;
    }

    private boolean onLongClickOtherExpenseImage(View view) {
        String textToSend = "Hello, This is other expense image :  " + otherExpense;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, textToSend);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
        return true;
    }

    private void onClickUpdateEod(View view) {
        updateEod();
    }


    private void navigateUp() {
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

    private void setEod(Eod eod) {
        binding.setEod(eod);

        if (eod == null) {
            Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            navigateUp();
            return;
        }

        if (eod.getPetrolExpenseImage() != null && !eod.getPetrolExpenseImage().isEmpty()) {
            String image = ApiEndpoints.BASE_URL + eod.getPetrolExpenseImage();
            petrolExpense = image;
            Glide.with(getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.eodProfilePetrolExpenseImage);

            binding.eodProfilePetrolExpenseImage.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", image);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            });
        } else {
            binding.eodProfilePetrolExpenseImage.setVisibility(View.GONE);
        }

        if (eod.getExpenseImage() != null && !eod.getExpenseImage().isEmpty()) {
            String image = ApiEndpoints.BASE_URL + eod.getExpenseImage();
            otherExpense = image;
            Glide.with(getContext()).load(image).placeholder(R.drawable.loading_image).into(binding.eodProfileOtherExpenseImage);

            binding.eodProfileOtherExpenseImage.setOnClickListener(view -> {
                Bundle bundle = new Bundle();
                bundle.putString("IMAGE", image);
                Navigation.findNavController(binding.getRoot()).navigate(R.id.imageViewFragment, bundle);
            });
        } else {
            binding.eodProfileOtherExpenseImage.setVisibility(View.GONE);
        }

        mapReadyCallback = googleMap -> {
            try {
                LatLng latLng = new LatLng(eod.getLocation().getLatitude(), eod.getLocation().getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.eod_profile_map, mapFragment)
                .commit();
        mapFragment.getMapAsync(mapReadyCallback);
    }


    public void updateEod() {
        Eod eod = binding.getEod();
        progressDialog.show();
        String url = ApiEndpoints.BASE_URL + "eod/updateEodByEmp.php";
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id", eod.getId());
        hashMap.put("empId", eod.getEmpId());
        hashMap.put("petrolExpenses", eod.getPetrolExpense());
        hashMap.put("otherExpenses", eod.getOtherExpense());
        hashMap.put("expense_description", eod.getExpenseDescription());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(hashMap), response -> {
            progressDialog.dismiss();

            if (response != null) {
                try {
                    Toast.makeText(getContext(), response.getString("message"), Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
        }, error -> {
            progressDialog.dismiss();
            Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
        });

        int timeout = 10000; // 10 seconds
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }
}