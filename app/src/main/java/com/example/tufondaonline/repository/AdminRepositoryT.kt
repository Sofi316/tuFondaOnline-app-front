package com.example.tufondaonline.repository

import android.util.Log
import com.example.tufondaonline.model.Categoria
import com.example.tufondaonline.model.OrdenResponse
import com.example.tufondaonline.model.Producto
import com.example.tufondaonline.model.Usuarios
import com.example.tufondaonline.remote.ApiService
import com.example.tufondaonline.remote.RetrofitInstance
import retrofit2.Response

class AdminRepositoryT() {
    private val api = RetrofitInstance.apiBack

    suspend fun obtenerOrdenes(): Response<List<OrdenResponse>> {
        return try {
            val response = api.obtenerTodasLasOrdenes()
            if (!response.isSuccessful) {
                Log.e("AdminRepo", "Error al obtener órdenes: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e("AdminRepo", "Excepción al obtener órdenes: ${e.message}")
            throw e
        }
    }
}

class AdminProductosRepositoryT(private val apiService: ApiService) {

        suspend fun obtenerProductos() = RetrofitInstance.apiBack.obtenerProductos()

        suspend fun crearProducto(producto: Producto): Response<Producto> =
            RetrofitInstance.apiBack.crearProducto(producto)

        suspend fun actualizarProducto(id: Long, producto: Producto) =
            RetrofitInstance.apiBack.actualizarProducto(id, producto)

        suspend fun eliminarProducto(id: Long) =
            RetrofitInstance.apiBack.eliminarProducto(id)

        suspend fun obtenerCategorias(): Response<List<Categoria>>{
            return RetrofitInstance.apiBack.obtenerCategorias()
        }
    }

    class AdminUsuarioRepositoryT{
        private val api = RetrofitInstance.apiBack
        suspend fun obtenerUsuarios()= api.obtenerUsuarios()
        suspend fun crearUsuario(usuario: Usuarios): Response<Usuarios> =
            api.crearUsuario(usuario)
        suspend fun actualizarUsuario(id: Long, usuario: Usuarios)=
            api.actualizarUsuario(id,usuario)
        suspend fun eliminarUsuario(id: Long)=
            api.eliminarUsuario(id)

    }
