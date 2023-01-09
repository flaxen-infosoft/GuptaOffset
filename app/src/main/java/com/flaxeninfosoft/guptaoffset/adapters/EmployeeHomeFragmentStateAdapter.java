package com.flaxeninfosoft.guptaoffset.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeAllAttendanceFragment;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeAllLeavesFragment;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeAllOrdersFragment;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeMapFragment;

public class EmployeeHomeFragmentStateAdapter extends FragmentStateAdapter {

    public EmployeeHomeFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new EmployeeAllOrdersFragment();
            case 1:
                return new EmployeeAllLeavesFragment();
            case 2:
                return new EmployeeAllAttendanceFragment();
            case 3:
                return new EmployeeMapFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
