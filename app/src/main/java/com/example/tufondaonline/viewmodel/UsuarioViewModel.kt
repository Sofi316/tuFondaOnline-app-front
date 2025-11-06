package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tufondaonline.model.UsuarioErrores
import com.example.tufondaonline.model.Usuarios
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel: ViewModel(){
    private val _usuario = MutableStateFlow(Usuarios())
    val usuario: StateFlow<Usuarios> =_usuario

    fun onChangeNombre(nombre: String){
        _usuario.update {
            it.copy(
                nombre = nombre,
                errores = it.errores.copy(nombre = null )
            )
        }
    }

    fun onChangeCorreo(correo: String){
        _usuario.update {
            it.copy(
                correo = correo,
                errores = it.errores.copy(correo = null )
            )
        }
    }

    fun onChangePassword(pass: String){
        _usuario.update {
            it.copy(
                password = pass,
                errores = it.errores.copy(password = null )
            )
        }
    }

    fun onChangeAceptarTerminos(valor: Boolean){
        _usuario.update { it.copy(aceptarTerminos = valor) }
    }

    fun validarLogin(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            correo = if (u.correo.isBlank() || !u.correo.contains("@")) "Ingrese un formato valido" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.correo==null && errores.password==null){
            return true
        }else{
            return false
        }
    }
}