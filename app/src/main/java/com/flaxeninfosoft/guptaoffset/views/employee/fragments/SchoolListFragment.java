package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolListBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBinding;
import com.flaxeninfosoft.guptaoffset.databinding.FragmentSchoolProfileBindingImpl;


public class SchoolListFragment extends Fragment {


    FragmentSchoolListBinding binding;

    public SchoolListFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_school_list, container, false);
        binding.schoolListBackImg.setOnClickListener(this::onClickBack);
        return binding.getRoot();
    }

    private void onClickBack(View view) {
        Navigation.findNavController(view).navigateUp();
    }
}