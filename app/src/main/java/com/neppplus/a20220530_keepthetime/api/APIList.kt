package com.neppplus.a20220530_keepthetime.api

import com.neppplus.a20220530_keepthetime.models.BasicResponse
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*

interface APIList {

//    search
    @GET("/search/user")
    fun getRequestSearchUser(@Query("nickname") nickname: String) : Call<BasicResponse>


//    user
    @GET("/user")
    fun getRequestMyInfo() : Call<BasicResponse>

    @FormUrlEncoded
    @PATCH("/user")
    fun patchRequestEditUserInfo(
        @Field("field") field : String,
        @Field("value") value: String,
    ) : Call<BasicResponse>

    @FormUrlEncoded
    @POST("/user")
    fun postRequestLogin (
    @Field("email") email: String,
    @Field("password") password : String,
    ) : Call<BasicResponse>

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

    @Multipart
    @PUT("/user/image")
    fun putRequestUserImage(@Part profileImg : MultipartBody.Part) : Call<BasicResponse>


//    user/place
    @GET("/user/place")
    fun getRequestMyPlace () : Call<BasicResponse>
}