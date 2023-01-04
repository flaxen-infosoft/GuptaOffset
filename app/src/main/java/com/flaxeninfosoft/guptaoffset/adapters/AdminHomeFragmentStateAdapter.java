package com.flaxeninfosoft.guptaoffset.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllClientsFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllEmployeesFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllEodsFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllExpensesFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllLeavesFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllOrdersFragment;
import com.flaxeninfosoft.guptaoffset.views.admin.fragments.AdminAllSuperEmployeesFragment;
import com.flaxeninfosoft.guptaoffset.views.employee.fragments.EmployeeAllLeavesFragment;

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
            case 2:
                return new AdminAllOrdersFragment();
            case 3:
                return new AdminAllClientsFragment();
            case 4:
                return new AdminAllLeavesFragment();
            case 5:
                return new AdminAllEodsFragment();
            case 6:
                return new AdminAllExpensesFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 7;
    }
}
