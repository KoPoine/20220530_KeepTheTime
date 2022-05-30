package com.neppplus.a20220530_keepthetime.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.a20220530_keepthetime.fragments.InvitedAppointmentsFragment
import com.neppplus.a20220530_keepthetime.fragments.MyAppointmentsFragment
import com.neppplus.a20220530_keepthetime.fragments.SettingsFragment

class MainViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyAppointmentsFragment()
            1 -> InvitedAppointmentsFragment()
            else -> SettingsFragment()
        }
    }
}