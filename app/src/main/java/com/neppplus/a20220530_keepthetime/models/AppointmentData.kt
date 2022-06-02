package com.neppplus.a20220530_keepthetime.models

import com.google.gson.annotations.SerializedName

data class AppointmentData (
    val id : Int,
    val title : String,
    val datetime : String,
    @SerializedName("start_place")
    val startPlace : String,
    @SerializedName("start_latitude")
    val startLatitude : Double,
    @SerializedName("start_longitude")
    val startLongitude : Double,
    val place : String,
    val latitude : Double,
    val longitude : Double,
    val user : UserData,
    @SerializedName("invited_friends")
    val invitedFriends : List<UserData>
        ) {
}