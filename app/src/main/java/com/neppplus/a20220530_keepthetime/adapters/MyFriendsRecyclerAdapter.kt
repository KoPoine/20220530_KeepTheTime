package com.neppplus.a20220530_keepthetime.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.models.UserData

class MyFriendsRecyclerAdapter(
    val mContext : Context,
    val mList : List<UserData>
) : RecyclerView.Adapter<MyFriendsRecyclerAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(view : View) : RecyclerView.ViewHolder(view) {

        val profileImg = view.findViewById<ImageView>(R.id.profileImg)
        val nicknameTxt = view.findViewById<TextView>(R.id.nicknameTxt)
        val addFriendBtn = view.findViewById<Button>(R.id.addFriendBtn)

        fun bind (item : UserData) {
            Glide.with(mContext).load(item.profileImg).into(profileImg)
            nicknameTxt.text = item.nickname
            addFriendBtn.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.list_item_user, parent, false)
        return ItemViewHolder(row)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

}