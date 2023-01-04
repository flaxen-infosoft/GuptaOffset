package com.flaxeninfosoft.guptaoffset.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllEmployeesFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllSuperEmployeesFragment;

public class AdminHomeFragmentStateAdapter extends FragmentStateAdapter {

    public AdminHomeFragmentStateAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new AdminAllEmployeesFragment();
            case 1:
                return new AdminAllSuperEmployeesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
