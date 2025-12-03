package com.example.tufondaonline.model

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    val email: String,
    @SerializedName("password")
    val password: String
)
