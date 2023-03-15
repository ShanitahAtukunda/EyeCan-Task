package com.eyecan.app.models

data class SignupRequestModel(
    val fullName: String?,
    val emailId: String,
    val mobile: String?,
    val role: String?,
    val address:String?,
    val occupation:String?,
    val country:String?,
    val postcode:String?,
    val age:String?,
    val password: String
)

