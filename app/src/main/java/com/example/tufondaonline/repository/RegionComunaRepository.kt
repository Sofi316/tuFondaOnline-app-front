package com.example.tufondaonline.repository

import com.example.tufondaonline.remote.RetrofitInstance

class RegionComunaRepository {
    private val api = RetrofitInstance.apiBack

    suspend fun obtenerRegiones() = api.obtenerRegiones()

    suspend fun obtenerComunasPorRegion(idRegion: Long) =
        api.obtenerComunasPorRegion(idRegion)
}