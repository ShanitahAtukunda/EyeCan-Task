package com.eyecan.app.models

import com.example.testingtask.models.Data

data class SignupResponseModel(
    val `data`: Data,
    val message: String,
    val status: Boolean
)