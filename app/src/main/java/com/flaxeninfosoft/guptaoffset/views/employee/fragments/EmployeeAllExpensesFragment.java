package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flaxeninfosoft.guptaoffset.R;


public class EmployeeAllExpensesFragment extends Fragment {


    public EmployeeAllExpensesFragment() {
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
        return inflater.inflate(R.layout.fragment_employee_all_expenses, container, false);
    }
}