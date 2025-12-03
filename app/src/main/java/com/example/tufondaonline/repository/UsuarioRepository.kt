package com.example.tufondaonline.repository

import android.util.Log
import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.model.Usuarios
import com.example.tufondaonline.remote.RetrofitInstance
import retrofit2.Response

class UsuarioRepository {
    private val api = RetrofitInstance.apiBack

    suspend fun hacerLogin(request: LoginRequest): Response<Usuarios> {
        return try {
            val response = api.login(request)
            if (!response.isSuccessful) {
                Log.e("UsuarioRepository", "Error en el login: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Error en el login: ${e.message}")
            throw e
        }
    }

    suspend fun hacerRegistro(usuario: Usuarios): Response<Usuarios> {
        return try {
            val response = api.registro(usuario)

            if (!response.isSuccessful) {
                Log.e("UsuarioRepository", "Error Registro: ${response.code()}")
            }
            response
        } catch (e: Exception) {
            Log.e("UsuarioRepository", "Excepción Registro: ${e.message}")
            throw e
        }
    }
}