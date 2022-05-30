package com.neppplus.a20220530_keepthetime.api

import com.neppplus.a20220530_keepthetime.models.BasicResponse
import retrofit2.Call
import retrofit2.http.*

interface APIList {

//    user
    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
    @Field("email") email : String,
    @Field("password") pw : String,
    @Field("nick_name") nickname : String,
    ) : Call<BasicResponse>

    @GET("/user/check")
    fun getRequestUserCheck (
    @Query("type") type : String,
    @Query("value") value : String,
) : Call<BasicResponse>

}