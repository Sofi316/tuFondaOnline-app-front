package com.example.tufondaonline.remote

import com.example.tufondaonline.model.Comuna
import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.model.Region
import com.example.tufondaonline.model.Usuarios
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<Usuarios>

    @POST("auth/register")
    suspend fun registro(@Body request: Usuarios): Response<Usuarios>

    @GET("api/regiones")
    suspend fun obtenerRegiones(): Response<List<Region>>

    @GET("api/comunas/{idRegion}")
    suspend fun obtenerComunasPorRegion(
        @Path("idRegion") idRegion: Long
    ): Response<List<Comuna>>
}