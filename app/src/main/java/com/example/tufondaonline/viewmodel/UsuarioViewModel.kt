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

    fun onChangeRut(rut: String){
        _usuario.update {
            it.copy(
                rut = rut,
                errores = it.errores.copy(rut = null )
            )
        }
    }

    fun onChangeNombre(nombre: String){
        _usuario.update {
            it.copy(
                nombre = nombre,
                errores = it.errores.copy(nombre = null )
            )
        }
    }

    fun onChangeApellido(apellido: String){
        _usuario.update {
            it.copy(
                apellido = apellido,
                errores = it.errores.copy(apellido = null )
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

    fun onChangeFechaNac(fechaNac: String){
        _usuario.update {
            it.copy(
                fechaNac = fechaNac,
                errores = it.errores.copy(fechaNac = null )
            )
        }
    }

    fun onChangeDireccion(direccion: String){
        _usuario.update {
            it.copy(
                direccion = direccion,
                errores = it.errores.copy(direccion = null )
            )
        }
    }

    fun onChangeRegion(region: String){
        _usuario.update {
            it.copy(
                region = region,
                errores = it.errores.copy(region = null )
            )
        }
    }

    fun onChangeComuna(comuna: String){
        _usuario.update {
            it.copy(
                comuna = comuna,
                errores = it.errores.copy(comuna = null )
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
            nombre = if (u.nombre.isBlank()) "El nombre no puede estar vacio" else null,
            correo = if (u.correo.isBlank() || !u.correo.contains("@")) "Ingrese un formato valido" else null,
            password = if (u.password.isBlank()) "La contraseña no puede estar vacía" else null,
            acepterTerminos = if(u.aceptarTerminos==false) "Debe aceptar los términos de la empresa" else null
        )
        _usuario.update {
            it.copy(errores = errores)
        }
        if (errores.nombre==null && errores.correo==null && errores.password==null
            && errores.acepterTerminos==null){
            return true
        }else{
            return false
        }
    }
}