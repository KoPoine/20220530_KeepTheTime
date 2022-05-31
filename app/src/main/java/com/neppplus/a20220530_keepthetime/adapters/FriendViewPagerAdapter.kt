package com.neppplus.a20220530_keepthetime.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.neppplus.a20220530_keepthetime.fragments.MyFriendsListFragment
import com.neppplus.a20220530_keepthetime.fragments.RequestFriendsListFragment

class FriendViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> MyFriendsListFragment()
            else -> RequestFriendsListFragment()
        }
    }
}