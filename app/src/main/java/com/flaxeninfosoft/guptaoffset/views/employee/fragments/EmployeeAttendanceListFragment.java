package com.flaxeninfosoft.guptaoffset.views.employee.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.flaxeninfosoft.guptaoffset.R;


public class EmployeeAttendanceListFragment extends Fragment {

    public EmployeeAttendanceListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_attendance_list, container, false);
    }
}