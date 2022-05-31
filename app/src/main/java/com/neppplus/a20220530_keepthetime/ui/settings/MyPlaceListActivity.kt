package com.neppplus.a20220530_keepthetime.ui.settings

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.a20220530_keepthetime.BaseActivity
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.PlaceRecyclerAdapter
import com.neppplus.a20220530_keepthetime.databinding.ActivityMyPlaceListBinding
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.models.PlaceData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyPlaceListActivity : BaseActivity() {

    lateinit var binding : ActivityMyPlaceListBinding

    lateinit var mPlaceRecyclerAdapter : PlaceRecyclerAdapter
    var mPlaceList = ArrayList<PlaceData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_place_list)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getMyPlaceListFromServer()
    }

    override fun setupEvents() {
        addBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditMyPlaceActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun setValues() {
        titleTxt.text = ""
        addBtn.visibility = View.VISIBLE

        mPlaceRecyclerAdapter = PlaceRecyclerAdapter(mContext, mPlaceList)
        binding.myPlaceRecyclerView.adapter = mPlaceRecyclerAdapter
        binding.myPlaceRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getMyPlaceListFromServer() {
        apiList.getRequestMyPlace().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!

                    mPlaceList.clear()
                    mPlaceList.addAll(br.data.places)

                    mPlaceRecyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}