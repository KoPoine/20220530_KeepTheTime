package com.neppplus.a20220530_keepthetime.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.api.APIList
import com.neppplus.a20220530_keepthetime.api.ServerApi
import com.neppplus.a20220530_keepthetime.databinding.ListItemUserBinding
import com.neppplus.a20220530_keepthetime.fragments.RequestFriendsListFragment
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.models.UserData
import com.neppplus.a20220530_keepthetime.ui.settings.MyFriendsActivity
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendsRecyclerAdapter(
    val mContext : Context,
    val mList : List<UserData>,
    val type : String
) : RecyclerView.Adapter<MyFriendsRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(val binding : ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item : UserData) {

            val apiList = ServerApi.getRetrofit(mContext).create(APIList::class.java)

            Glide.with(mContext).load(item.profileImg).into(binding.profileImg)
            binding.nicknameTxt.text = item.nickname

            when (type) {
                "add" -> {
                    binding.addFriendBtn.visibility = View.VISIBLE
                    binding.requestBtnLayout.visibility = View.GONE
                }
                "requested" -> {
                    binding.addFriendBtn.visibility = View.GONE
                    binding.requestBtnLayout.visibility = View.VISIBLE
                }
                "my" -> {
                    binding.addFriendBtn.visibility = View.GONE
                    binding.requestBtnLayout.visibility = View.GONE
                }
            }

            when (item.provider) {
                "kakao" -> {
                    binding.socialLoginImg.setImageResource(R.drawable.kakao_login_icon)
                }
                "facebook" -> {
                    binding.socialLoginImg.setImageResource(R.drawable.facebook_login_icon)
                }
                else -> {binding.socialLoginImg.visibility = View.GONE}
            }

//            수락 / 거절 버튼 둘다 하는 일이 동일 => type에 들어갈 값만 다르다
//            버튼에 태그달아놓고 꺼내쓰자 => 동일한 작업

            val ocl = object : View.OnClickListener{
                override fun onClick(p0: View?) {
                    val okOrNO = p0!!.tag.toString()

//                    어댑터에서 API 서비스 사용법
//                    1. 직접 만들자
                    apiList.putRequestAnswerRequest(item.id, okOrNO).enqueue(object : Callback<BasicResponse>{
                        override fun onResponse(
                            call: Call<BasicResponse>,
                            response: Response<BasicResponse>
                        ) {
                            if (!response.isSuccessful) {
                                val errorBodyStr = response.errorBody()!!.string()
                                val jsonObj = JSONObject(errorBodyStr)
                                val message = jsonObj.getString("message")

                                Log.e("승인_거절 실패", message)
                            }

//                            프래그먼트의 요청목록(requested Friends List) 새로 받아오는 함수를 실행?
//                            어댑터 -> 액티비티 : context 변수 활용


//                            ViewPager2에서 fragment를 찾아서 새로 받아오는 함수를 실행
//                            ViewPager2에서는 내부의 fragment를 지정할 수 없다. => tag 값으로 찾아온다.
                            ((mContext as MyFriendsActivity)
                                .supportFragmentManager
                                .findFragmentByTag("f1") as RequestFriendsListFragment)
                                .getRequestedFriendsListFromServer()
                        }

                        override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                        }
                    })
                }
            }

            binding.acceptBtn.setOnClickListener(ocl)
            binding.denyBtn.setOnClickListener(ocl)

            binding.addFriendBtn.setOnClickListener {
                apiList.postRequestAddFriend(item.id).enqueue(object : Callback<BasicResponse>{
                    override fun onResponse(
                        call: Call<BasicResponse>,
                        response: Response<BasicResponse>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                mContext,
                                "${item.nickname}님에게 친구요청을 보냈습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                    }
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemUserBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}