package com.example.tufondaonline.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.LoginRequest
import com.example.tufondaonline.model.OrdenResponse
import com.example.tufondaonline.remote.RetrofitInstance
import com.example.tufondaonline.repository.AdminRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AdminViewModel : ViewModel() {

    private val adminRepo = AdminRepository()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _loginError = MutableStateFlow<String?>(null)
    val loginError: StateFlow<String?> = _loginError.asStateFlow()

    private val _ordenes = MutableStateFlow<List<OrdenResponse>>(emptyList())
    val ordenes: StateFlow<List<OrdenResponse>> = _ordenes.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loginAdmin(email: String, password: String) {
        viewModelScope.launch {
            _loginError.value = null
            _loginSuccess.value = false

            try {
                val response = RetrofitInstance.apiBack.login(LoginRequest(email, password))

                if (!response.isSuccessful) {
                    _loginError.value = "Credenciales incorrectas"
                    return@launch
                }

                val user = response.body()
                if (user == null) {
                    _loginError.value = "Error inesperado (usuario nulo)"
                    return@launch
                }

                if (user.rol == null || user.rol.uppercase() != "ADMIN") {
                    _loginError.value = "No tienes permisos de administrador"
                    return@launch
                }

                _loginSuccess.value = true
            } catch (e: Exception) {
                Log.e("AdminVM", "loginAdmin error", e)
                _loginError.value = "Error de conexión: ${e.message}"
            }
        }
    }

    // ----- cargarOrdenes -----
    fun cargarOrdenes() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = adminRepo.obtenerOrdenes()
                if (response.isSuccessful) {
                    _ordenes.value = response.body() ?: emptyList()
                } else {
                    _ordenes.value = emptyList()
                    _error.value = "Error servidor: ${response.code()}"
                }
            } catch (e: Exception) {
                Log.e("AdminVM", "cargarOrdenes error", e)
                _ordenes.value = emptyList()
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}
