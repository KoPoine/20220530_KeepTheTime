package com.neppplus.a20220530_keepthetime.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.neppplus.a20220530_keepthetime.R
import com.neppplus.a20220530_keepthetime.models.PlaceData

class StartPlaceSpinnerAdapter(
    val mContext: Context,
    val resId: Int,
    val mList: List<PlaceData>
) : ArrayAdapter<PlaceData>(mContext, resId, mList) {

    //    눈에 보여지는 Spinner의 모습
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(resId, null)
        }
        row!!

        val data = mList[position]

        val placeNameTxt = row.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = row.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPlaceMapImg = row.findViewById<ImageView>(R.id.viewPlaceMapImg)

        placeNameTxt.text = data.name
        if (!data.isPrimary) {
            isPrimaryTxt.visibility = View.GONE
        }
        viewPlaceMapImg.visibility = View.GONE

        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var row = convertView
        if (row == null) {
            row = LayoutInflater.from(mContext).inflate(resId, null)
        }
        row!!

        val data = mList[position]

        val placeNameTxt = row.findViewById<TextView>(R.id.placeNameTxt)
        val isPrimaryTxt = row.findViewById<TextView>(R.id.isPrimaryTxt)
        val viewPlaceMapImg = row.findViewById<ImageView>(R.id.viewPlaceMapImg)

        placeNameTxt.text = data.name
        if (!data.isPrimary) {
            isPrimaryTxt.visibility = View.GONE
        }
        viewPlaceMapImg.visibility = View.GONE

        return row
    }

}