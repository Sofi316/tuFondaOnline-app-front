package com.example.tufondaonline.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tufondaonline.model.Usuarios
import com.example.tufondaonline.repository.AdminUsuarioRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AdminUsuarioViewModel: ViewModel() {

    private val repository = AdminUsuarioRepository()
    private val _usuarios = MutableStateFlow<List<Usuarios>>(emptyList())
    val usuarios: StateFlow<List<Usuarios>> = _usuarios
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    var usuarioEditando by mutableStateOf<Usuarios?>(null)
    var mostrarDialogo by mutableStateOf(false)

    init {
        cargarUsuarios()
    }
    fun cargarUsuarios() {
        viewModelScope.launch {
            _isLoading.value = true
            val response= repository.obtenerUsuarios()
            if (response.isSuccessful){
                _usuarios.value = response.body() ?: emptyList()
            }
            _isLoading.value = false

        }
    }
    fun crearUsuario(usuario: Usuarios) {
        viewModelScope.launch {
            try {
                val response = repository.crearUsuario(usuario)
                if (response.isSuccessful) {
                    cargarUsuarios()
                } else {
                    Log.e("AdminUsuarioVM", "Error en crear usuario: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.e("AdminUsuarioVM", "Excepción al crear usuario: ${e.message}")
            }
        }
    }
    fun editarUsuario(usuario: Usuarios){
        viewModelScope.launch {
            val response = repository.actualizarUsuario(usuario.id, usuario)
            if(response.isSuccessful){
                cargarUsuarios()
            }
        }
    }
    fun eliminarUsuario(id: Long){
        viewModelScope.launch {
            val response = repository.eliminarUsuario(id)
            if(response.isSuccessful){
                cargarUsuarios()
            }
        }
    }


}