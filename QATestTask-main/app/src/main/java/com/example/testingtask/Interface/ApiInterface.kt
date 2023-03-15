package com.eyecan.app.interfaces

import com.eyecan.app.models.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    //authentication
    @Headers("Content-Type:application/json")
    @POST("auth/login")
    fun signIn(@Body userData: SignInRequest): Call<ResponseBody>

    @Headers("Content-Type:application/json")
    @POST("auth/register")
    fun registerUser(
        @Body userData: SignupRequestModel
    ): Call<ResponseBody>

}
