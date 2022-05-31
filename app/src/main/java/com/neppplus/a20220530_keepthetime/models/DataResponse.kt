package com.neppplus.a20220530_keepthetime.models

class DataResponse (
    val user : UserData,
    val token : String,
    val users : List<UserData>,
    val friends : List<UserData>,
    val places : List<PlaceData>,
        ) {
}