package com.example.tufondaonline.viewmodel

import androidx.lifecycle.ViewModel
import com.example.tufondaonline.model.Usuario
import com.example.tufondaonline.model.UsuarioErrores
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ContactoViewModel:  ViewModel() {
    private val _usuario = MutableStateFlow(Usuario())
    val usuario: StateFlow<Usuario> = _usuario

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
    fun onChangeMensaje(mensaje: String){
        _usuario.update {
            it.copy(
                mensaje = mensaje,
                errores = it.errores.copy(mensaje= null)
            )
        }
    }

    fun validarContacto(): Boolean{
        val u = _usuario.value
        val errores = UsuarioErrores(
            nombre = if (u.nombre.isBlank()) "El nombre no puede estar vacio" else null,
            correo = if (u.correo.isBlank() || !u.correo.contains("@")) "Ingrese un formato valido" else null,
            mensaje = when {
                u.mensaje.isBlank() -> "El mensaje no puede estar vacío"
                u.mensaje.length > 300 -> "El mensaje no puede superar los 300 caracteres"
                else -> null
            }
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.nombre==null &&
            errores.correo==null &&
            errores.mensaje==null){
            return true
        }else{
            return false
        }
    }
}