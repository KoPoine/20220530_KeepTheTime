package com.neppplus.a20220530_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.a20220530_keepthetime.databinding.ListItemAppointmentBinding
import com.neppplus.a20220530_keepthetime.models.AppointmentData
import java.text.SimpleDateFormat

class MyAppointmentRecyclerViewAdapter (
    val mContext : Context,
    val mList : List<AppointmentData>,
        ) : RecyclerView.Adapter<MyAppointmentRecyclerViewAdapter.ItemViewHolder>() {

    inner class ItemViewHolder (val binding : ListItemAppointmentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item : AppointmentData) {

            binding.titleTxt.text = item.title

            val sdf = SimpleDateFormat("M/d a h:mm")

            binding.timeTxt.text = "${sdf.format(item.datetime)}"
            binding.placeNameTxt.text = "약속 장소 : ${item.place}"
            binding.memberCountTxt.text = "참여 인원 : ${item.invitedFriends.size}명"

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            ListItemAppointmentBinding
                .inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}