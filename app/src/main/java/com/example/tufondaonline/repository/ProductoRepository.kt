package com.example.tufondaonline.repository

import android.util.Log
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.remote.RetrofitInstance
import retrofit2.Response

class ProductoRepository {
    private val api = RetrofitInstance.apiBack

    suspend fun obtenerProductos(): Response<List<Producto>> {
        return try {
            val response = api.obtenerProductos()
            if (!response.isSuccessful) {
                Log.e("ProductoRepo", "Error: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e("ProductoRepo", "Excepción: ${e.message}")
            throw e
        }
    }
}