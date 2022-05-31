package com.neppplus.a20220530_keepthetime.ui.settings

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.MyFriendsRecyclerAdapter
import com.neppplus.a20220530_keepthetime.databinding.ActivityAddFriendsBinding
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.models.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddFriendsActivity : BaseActivity() {

    lateinit var binding : ActivityAddFriendsBinding
    lateinit var mFriendAdapter : MyFriendsRecyclerAdapter

    var mFriendList = ArrayList<UserData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_friends)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.searchBtn.setOnClickListener {
//        2. 서버 검색한 친구 목록 받아오기
            val inputNick = binding.searchEdt.text.toString()

//            이 글자가 2글자 이상이냐? > 통과
            if (inputNick.length < 2) {
                Toast.makeText(mContext, "검색어는 2자 이상 작성해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            서버에 실제로 검색어로 검색
            apiList.getRequestSearchUser(inputNick).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {
                    if (response.isSuccessful) {
                        val br = response.body()!!

//                        리스트를 전면 삭제하고
                        mFriendList.clear()
//                        새로운 (서버에서 내려준) 리스트로 다시 덮어주고
                        mFriendList.addAll(br.data.users)
//                        어댑터에 리스트가 바뀌었다는 사실 통보
//                        RecyclerView의 모든 뷰를 삭제하고 다시 뷰를 생성 비효율적인 코드
                        mFriendAdapter.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }
            })
        }
    }

    override fun setValues() {
//        1. 어댑터 초기화 (init) => 빈 껍데기로
        mFriendAdapter = MyFriendsRecyclerAdapter(mContext, mFriendList, "add")
        binding.findUserRecyclerView.adapter = mFriendAdapter
        binding.findUserRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }
}