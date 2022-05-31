package com.neppplus.a20220530_keepthetime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.MyFriendsRecyclerAdapter
import com.neppplus.a20220530_keepthetime.databinding.FragmentMyFriendsListBinding
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsListFragment : BaseFragment() {

    lateinit var binding : FragmentMyFriendsListBinding

    lateinit var mFriendAdapter : MyFriendsRecyclerAdapter
    var mFriendList = ArrayList<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_my_friends_list,
            container,
            false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getMyFriendsListFromServer()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mFriendAdapter = MyFriendsRecyclerAdapter(mContext, mFriendList, "my")
        binding.myFriendsRecyclerView.adapter = mFriendAdapter
        binding.myFriendsRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getMyFriendsListFromServer() {
        apiList.getRequestMyFriendsList("my").enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    mFriendList.clear()
                    mFriendList.addAll(br.data.friends)
                    mFriendAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}