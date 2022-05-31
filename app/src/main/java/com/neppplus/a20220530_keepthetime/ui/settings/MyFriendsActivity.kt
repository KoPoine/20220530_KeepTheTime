package com.neppplus.a20220530_keepthetime.ui.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayoutMediator
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.FriendViewPagerAdapter
import com.neppplus.a20220530_keepthetime.databinding.ActivityMyFriendsBinding

class MyFriendsActivity : BaseActivity() {

    lateinit var binding : ActivityMyFriendsBinding

    lateinit var mFriendsPagerAdapter : FriendViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_friends)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        addBtn.setOnClickListener {

        }
    }

    override fun setValues() {
        titleTxt.text = "친구 목록 관리"
        addBtn.visibility = View.VISIBLE

        mFriendsPagerAdapter = FriendViewPagerAdapter(this)
        binding.friendListViewPager.adapter = mFriendsPagerAdapter

//        ViewPager2 + TabLayout의 결합 코드
//        파라미터 ( tablayout의 변수 ,  viewpager2의 변수
        TabLayoutMediator(binding.tabLayout, binding.friendListViewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "내 친구 목록"
                else -> tab.text = "친구 추가 요청청"
           }
        }.attach()
    }
}