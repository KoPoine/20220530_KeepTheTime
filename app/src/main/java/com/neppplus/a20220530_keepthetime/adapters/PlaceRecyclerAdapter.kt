package com.neppplus.a20220530_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.databinding.ListItemPlaceBinding
import com.neppplus.a20220530_keepthetime.models.PlaceData

class PlaceRecyclerAdapter(
    val mContext : Context,
    val mList : List<PlaceData>
) : RecyclerView.Adapter<PlaceRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder (val binding : ListItemPlaceBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind (item : PlaceData) {
            binding.placeNameTxt.text = item.name

            if (!item.isPrimary) {
                binding.isPrimaryTxt.visibility = View.GONE
            }

            binding.viewPlaceMapImg.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(ListItemPlaceBinding.inflate(LayoutInflater.from(mContext), parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}