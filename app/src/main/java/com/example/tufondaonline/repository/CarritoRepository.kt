package com.example.tufondaonline.repository

import android.util.Log
import com.example.tufondaonline.model.DetalleEnvio
import com.example.tufondaonline.model.OrdenRequest
import com.example.tufondaonline.model.OrdenResponse
import com.example.tufondaonline.remote.RetrofitInstance
import retrofit2.Response

class CarritoRepository {

    private val api = RetrofitInstance.apiBack

    // Paso 1
    suspend fun crearOrden(orden: OrdenRequest): Response<OrdenResponse> {
        return try {
            val response = api.crearOrden(orden)
            if (!response.isSuccessful) Log.e("Repo", "Error Orden: ${response.code()}")
            response
        } catch (e: Exception) {
            throw e
        }
    }

    // Paso 2
    suspend fun guardarDetalle(detalle: DetalleEnvio): Response<Void> {
        return try {
            val response = api.crearDetalle(detalle)
            if (!response.isSuccessful) Log.e("Repo", "Error Detalle: ${response.code()}")
            response
        } catch (e: Exception) {
            throw e
        }
    }
}