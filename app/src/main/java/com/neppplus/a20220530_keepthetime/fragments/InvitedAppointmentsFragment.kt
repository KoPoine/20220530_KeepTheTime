package com.neppplus.a20220530_keepthetime.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.adapters.MyAppointmentRecyclerViewAdapter
import com.neppplus.a20220530_keepthetime.databinding.FragmentInvitedAppointmentsBinding
import com.neppplus.a20220530_keepthetime.models.AppointmentData
import com.neppplus.a20220530_keepthetime.models.BasicResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class InvitedAppointmentsFragment : BaseFragment() {

    lateinit var binding : FragmentInvitedAppointmentsBinding

    lateinit var mAppointmentAdapter : MyAppointmentRecyclerViewAdapter
    var mAppointmentsList = ArrayList<AppointmentData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_invited_appointments, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupEvents()
        setValues()
    }

    override fun onResume() {
        super.onResume()
        getMyAppointmentListFromServer()
    }

    override fun setupEvents() {

    }

    override fun setValues() {
        mAppointmentAdapter = MyAppointmentRecyclerViewAdapter(mContext, mAppointmentsList, true)
        binding.invitedAppointmentRecyclerView.adapter = mAppointmentAdapter
        binding.invitedAppointmentRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getMyAppointmentListFromServer() {
        apiList.getRequestMyAppointment().enqueue(object : Callback<BasicResponse> {
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
                if (response.isSuccessful) {
                    val br = response.body()!!
                    mAppointmentsList.clear()
                    mAppointmentsList.addAll(br.data.invitedAppointments)

                    mAppointmentAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }
        })
    }
}