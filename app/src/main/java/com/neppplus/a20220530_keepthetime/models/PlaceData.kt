package com.neppplus.a20220530_keepthetime.models

import com.google.gson.annotations.SerializedName

class PlaceData (
    val id : Int,
    val name : String,
    val latitude : Double,
    val longitude : Double,
    @SerializedName("is_primary")
    val isPrimary : Boolean,
        ) {
}