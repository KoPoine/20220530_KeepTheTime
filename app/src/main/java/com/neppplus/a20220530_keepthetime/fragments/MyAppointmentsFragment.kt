package com.neppplus.a20220530_keepthetime.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.MyAppointmentRecyclerViewAdapter
import com.neppplus.a20220530_keepthetime.databinding.FragmentMyAppointmentsBinding
import com.neppplus.a20220530_keepthetime.models.AppointmentData
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import com.neppplus.a20220530_keepthetime.ui.appointment.EditAppointmentActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyAppointmentsFragment : BaseFragment() {

    lateinit var binding : FragmentMyAppointmentsBinding

    lateinit var mAppointAdapter : MyAppointmentRecyclerViewAdapter
    var mAppointmentList = ArrayList<AppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_appointments, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.addAppointmentBtn.setOnClickListener {
            val myIntent = Intent(mContext, EditAppointmentActivity::class.java)
            startActivity(myIntent)
        }
    }

    override fun onResume() {
        super.onResume()
        getMyAppointmentListFromServer()
    }

    override fun setValues() {
        mAppointAdapter = MyAppointmentRecyclerViewAdapter(mContext, mAppointmentList, false)
        binding.myAppointmentRecyclerView.adapter = mAppointAdapter
        binding.myAppointmentRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getMyAppointmentListFromServer() {
        apiList.getRequestMyAppointment().enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mAppointmentList.clear()
                    mAppointmentList.addAll(br.data.appointments)

                    mAppointAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }

}