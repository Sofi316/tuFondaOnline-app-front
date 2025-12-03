package com.example.tufondaonline.remote

import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.model.Usuarios
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuarios>

    @POST("auth/register")
    suspend fun registro(@Body request: Usuarios): Response<Usuarios>
}